package all.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Karl Rules!
 * 2023/11/23
 * now File Encoding is UTF-8
 */
@Controller
public class TestController {
    @RequestMapping("/hi")
    public String hi(){
        System.out.println("hi");
        return "hi";
    }
}

