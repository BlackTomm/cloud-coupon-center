package org.coding.coupon.template.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.coding.coupon.template.converter.CouponTemplateConverter;
import org.coding.coupon.template.dao.CouponTemplate;
import org.coding.coupon.template.dao.CouponTemplateDao;
import org.coding.coupon.template.domains.CouponTemplateInfo;
import org.coding.coupon.template.domains.PagedCouponTemplateInfo;
import org.coding.coupon.template.domains.TemplateSearchParams;
import org.coding.coupon.template.enums.CouponType;
import org.coding.coupon.template.service.TemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Description:
 * Create by blacktom on 2023/10/30
 */
@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private CouponTemplateDao couponTemplateDao;

    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo templateInfo) {
        //限制门店创建处于当前可用状态的券模版数量
        if (templateInfo.getShopId() != null) {
            int count = couponTemplateDao.countByShopIdAndAvailable(templateInfo.getShopId(), true);
            if (count >= 100) {
                log.error("createTemplate # the totals of coupon template exceeds maximum number");
                throw new UnsupportedOperationException("createTemplate # exceeded the maximum of coupon templates that you can create");
            }
        }

        CouponTemplate couponTemplate = CouponTemplate.builder().name(templateInfo.getName())
                .description(templateInfo.getDesc())
                .couponType(CouponType.convert(templateInfo.getType()))
                .available(true)
                .shopId(templateInfo.getShopId())
                .rule(templateInfo.getRule())
                .createTime(new Date())
                .build();
        couponTemplate = couponTemplateDao.save(couponTemplate);

        return CouponTemplateConverter.convertToCouponTemplateInfo(couponTemplate);
    }

    @Override
    public CouponTemplateInfo cloneTemplate(long templateId) {
        log.info("cloning template id {}", templateId);
        CouponTemplate couponTemplate = couponTemplateDao.findById(templateId).orElseThrow(() -> new IllegalArgumentException("invalid template ID"));

        CouponTemplate target = new CouponTemplate();
        BeanUtils.copyProperties(couponTemplate, target);

        target.setAvailable(true);
        target.setId(null);
        couponTemplateDao.save(target);
        return CouponTemplateConverter.convertToCouponTemplateInfo(target);
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams templateSearchParams) {

        CouponTemplate couponTemplate = CouponTemplate.builder()
                .id(templateSearchParams.getId())
                .name(templateSearchParams.getName())
                .couponType(CouponType.convert(templateSearchParams.getType()))
                .shopId(templateSearchParams.getShopId())
                .available(templateSearchParams.isAvailable())
                .build();

        Pageable page = PageRequest.of(templateSearchParams.getPage(), templateSearchParams.getPageSize());
        Page<CouponTemplate> couponTemplatePage = couponTemplateDao.findAll(Example.of(couponTemplate), page);

        List<CouponTemplateInfo> couponTemplateInfos = couponTemplatePage.stream().
                map(CouponTemplateConverter::convertToCouponTemplateInfo).collect(Collectors.toList());

        return PagedCouponTemplateInfo.builder()
                .templates(couponTemplateInfos)
                .page(templateSearchParams.getPage())
                .total(couponTemplatePage.getTotalElements())
                .build();
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(long id) {
        Optional<CouponTemplate> couponTemplate = couponTemplateDao.findById(id);
        return couponTemplate.map(CouponTemplateConverter::convertToCouponTemplateInfo).orElse(null);
    }

    @Override
    @Transactional
    public void deleteTemplate(long id) {
        int rows = couponTemplateDao.makeCouponUnavailable(id);
        if (rows ==0) {
            throw new IllegalArgumentException("Template Not Found: " + id);
        }
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate> couponTemplateInfos = couponTemplateDao.findAllById(ids);

        return couponTemplateInfos.stream().map(CouponTemplateConverter::convertToCouponTemplateInfo)
                .collect(Collectors.toMap(CouponTemplateInfo::getId, Function.identity()));
    }
}
