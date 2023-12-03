package org.coding.coupon.customer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.caculation.ShoppingComponent;
import org.coding.coupon.caculation.SimulationResponse;
import org.coding.coupon.customer.dao.CouponCustomerDao;
import org.coding.coupon.customer.domian.BaseResponse;
import org.coding.coupon.customer.domian.SearchCoupponParam;
import org.coding.coupon.customer.domian.SendCouponParam;
import org.coding.coupon.customer.entity.Coupon;
import org.coding.coupon.customer.enums.CouponStatus;
import org.coding.coupon.customer.enums.SystemErrorCode;
import org.coding.coupon.customer.service.CouponCustomerService;
import org.coding.coupon.template.domains.CouponInfo;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Description:
 * Create by blacktom on 2023/11/26
 */
@Slf4j
@Service
public class CouponCustomerServiceImpl implements CouponCustomerService {
//    @Autowired
//    private CouponTemplateService templateService;
//
//    @Autowired
//    private CouponCalculationService calculationService;

    @Autowired
    private CouponCustomerDao couponCustomerDao;

    @Autowired
    private WebClient.Builder webClientBuilder;



    @Override
    public Coupon sendCoupons(SendCouponParam sendCouponRequest) {
        CouponTemplateInfo couponTemplateInfo = webClientBuilder.build()
                //http get请求
                .get()
                .uri("http://coupon-template-serv/template/loadTemplate?id="+sendCouponRequest.getTemplateId())
                //把出参转化为目标类
                .retrieve()
                .bodyToMono(CouponTemplateInfo.class)
                //同步阻塞
                .block();

        if (couponTemplateInfo == null) {
            log.error("invalid template id = {}", sendCouponRequest.getTemplateId());
            throw new IllegalArgumentException("Invalid template id");
        }

        //判断是否有效，不过期
        Date endTime = couponTemplateInfo.getRule().getEndTime();
        if (!couponTemplateInfo.isAvailable() || (endTime.before(Calendar.getInstance().getTime()))) {
            log.error("template is not available id={} or has expired, endTime is {}", sendCouponRequest.getTemplateId(), endTime);
            throw new IllegalArgumentException("template is unavailable");
        }

        //限制领券上限
        long couponCount = couponCustomerDao.countByUserIdAndTemplateId(sendCouponRequest.getUserId(), sendCouponRequest.getTemplateId());
        if (couponCount >= couponTemplateInfo.getRule().getLimitation()) {
            log.error("current userId: {} getting same templateId :{}  exceeds rule limitation {}",
                    sendCouponRequest.getUserId(), sendCouponRequest.getTemplateId(), couponTemplateInfo.getRule().getLimitation());
            throw new IllegalArgumentException("user getting same templateId exceeds maximum number");
        }

        Coupon coupon = Coupon.builder().userId(sendCouponRequest.getUserId())
                .templateId(sendCouponRequest.getTemplateId())
                .shopId(couponTemplateInfo.getShopId())
                .createdTime(new Date())
                .couponStatus(CouponStatus.AVAILIABLE)
                .build();
        coupon = couponCustomerDao.save(coupon);

        return coupon;
    }

    @Override
    public BaseResponse invalidCoupon(String userId, String couponId) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(false);

        Coupon couponExample = Coupon.builder()
                .userId(userId)
                .couponId(Long.parseLong(couponId))
                .couponStatus(CouponStatus.AVAILIABLE)
                .build();
        ExampleMatcher matcher = ExampleMatcher.matching() // 构建对象
                .withIgnorePaths("shopId","templateId");

        Optional<Coupon> optionalCoupon = couponCustomerDao.findAll(Example.of(couponExample, matcher)).stream()
                .findFirst();
        if (!optionalCoupon.isPresent()) {
            response.setDesc(SystemErrorCode.COUPON_DATA_NOT_FOUND.getDesc());
            response.setDescCode(SystemErrorCode.COUPON_DATA_NOT_FOUND.getCode());
            return response;
        }

        Coupon searchParam = optionalCoupon.get();
        searchParam.setCouponStatus(CouponStatus.INVALID);
        couponCustomerDao.save(searchParam);

        response.setSuccess(true);
        return response;
    }

    @Override
    public List<CouponInfo> findCoupons(SearchCoupponParam searchCoupponParam) {
        // 在真正的生产环境，这个接口需要做分页查询，并且查询条件要封装成一个类
        Coupon coupon = Coupon.builder().userId(searchCoupponParam.getUserId())
                .couponStatus(CouponStatus.convert(searchCoupponParam.getCouponStatus()))
                .shopId(searchCoupponParam.getShopId())
                .build();

        //创建时间降序
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        Pageable pageable = PageRequest.of(searchCoupponParam.getPage(), searchCoupponParam.getPageSize(), sort);

        // 创建匹配器，即规定如何使用查询条件，可忽略部分字段
        ExampleMatcher matcher = ExampleMatcher.matching() // 构建对象
                .withIgnorePaths("couponId","templateId");

//        List<Coupon> coupons = couponCustomerDao.findAllCoupons(Example.of(coupon), pageable);
        List<Coupon> coupons = couponCustomerDao.findAll(Example.of(coupon, matcher), pageable).get().collect(Collectors.toList());

        //查询模版信息
//        Set<Long> templateIds = coupons.stream().map(Coupon::getTemplateId).collect(Collectors.toSet());
        String ids = coupons.stream().map(Coupon::getTemplateId).map(String::valueOf).distinct().collect(Collectors.joining(","));
        Map<Long, CouponTemplateInfo> templateInfoMap =webClientBuilder.build()
                .get()
                .uri("http://coupon-template-serv/template/batchLoadTemplate?ids="+ids)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Long, CouponTemplateInfo>>() {})
                .block();

        coupons.forEach(e->e.setTemplateInfo(templateInfoMap.get(e.getTemplateId())));
        return coupons.stream().map(CouponConverter::convertToCoupon).collect(Collectors.toList());
    }

    /**
     * 假定只支持一张券
     */
    @Override
    public ShoppingComponent useCoupon(ShoppingComponent order) {
        checkOrderParams(order);

        //查询优惠券是否可用
        List<String> couponIds = order.getCouponInfos().stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
        List<Coupon> availableCoupons = couponCustomerDao.findCouponsByIds(order.getUserId(), couponIds, CouponStatus.AVAILIABLE);
        if (CollectionUtils.isEmpty(availableCoupons)) {
            log.error("useCoupon | userId could not find available coupons, order is {}", order);
            throw new IllegalArgumentException("userId could not find available coupons in this order");
        }

        order.setCouponInfos(availableCoupons.stream().map(CouponConverter::convertToCoupon).collect(Collectors.toList()));

        //计算优惠后订单金额
        ShoppingComponent result =  webClientBuilder.build()
                .post()
                .uri("http://coupon-calculation-serv/calculation/calculateOrderPrice")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(ShoppingComponent.class)
                .block();
//        ShoppingComponent result = calculationService.calculateOrderPrice(order);

        if (CollectionUtils.isEmpty(result.getCouponInfos())) {
            log.error("cannot apply coupon to order, couponId={}", couponIds);
            throw new IllegalArgumentException("coupon is not applicable to this order");
        }

        log.info("update coupon status to used, couponIds={}", couponIds);
        for (Coupon availableCoupon : availableCoupons) {
            availableCoupon.setCouponStatus(CouponStatus.USED);
        }
        couponCustomerDao.saveAll(availableCoupons);
        return result;
    }

    @Override
    public SimulationResponse findBestCoupons(ShoppingComponent order) {
        checkOrderParams(order);


        //查询优惠券是否可用
        List<String> couponIds = order.getCouponInfos().stream().map(CouponInfo::getCouponId).collect(Collectors.toList());
        List<Coupon> availableCoupons = couponCustomerDao.findCouponsByIds(order.getUserId(), couponIds, CouponStatus.AVAILIABLE);
        if (CollectionUtils.isEmpty(availableCoupons)) {
            log.error("findBestCoupons | userId could not find available coupons, order is {}", order);
            throw new IllegalArgumentException("findBestCoupons | userId could not find available coupons in this order");
        }

        order.setCouponInfos(availableCoupons.stream().map(CouponConverter::convertToCoupon).collect(Collectors.toList()));

        //计算优惠后订单金额
        return webClientBuilder.build()
                .post()
                .uri("http://coupon-calculation-serv/calculation/findBestCoupons")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(SimulationResponse.class)
                .block();
//        return calculationService.findBestCoupons(order);
    }

    private static void checkOrderParams(ShoppingComponent order) {
        if (order == null || CollectionUtils.isEmpty(order.getProducts())
                || CollectionUtils.isEmpty(order.getCouponInfos())) {
            log.error("useCoupon | check param failed, order is {}", order);
            throw new IllegalArgumentException("order is empty");
        }

        if (order.getCouponInfos().size() > 1) {
            log.error("useCoupon | only can use one coupon, order is {}", order);
            throw new IllegalArgumentException("useCoupon | this order use exceeds one coupon");
        }
    }
}
