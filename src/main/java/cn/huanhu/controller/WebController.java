package cn.huanhu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

/**
 * @author m
 * @className WebController
 * @description WebController
 * @date 2020/5/11
 */
@Controller
@RequestMapping("web/")
public class WebController {

    private static final Logger logger= Logger.getLogger(String.valueOf(WebController.class));

    @RequestMapping("model")
    public String testModel(Model model){
        model.addAttribute("name","中山");
        logger.info(model.toString());
        return "hello";
    }


}
