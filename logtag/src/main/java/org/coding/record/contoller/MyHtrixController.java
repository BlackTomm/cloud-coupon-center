package org.coding.record.contoller;

import org.coding.record.service.HunterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Create by blacktom on 2023/12/14
 */
@RestController
public class MyHtrixController {
    @Autowired
    private HunterServiceImpl service;

    @RequestMapping("/hunterCar")
    @ResponseBody
    public String reqHunterAnno() {
        service.printInvokeLog();
        return "honda!!";
    }
}
