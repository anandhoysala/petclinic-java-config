
package com.spring66.petclinic.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

    @RequestMapping
    public String welcome() {
        return "welcome";
    }
    
}
