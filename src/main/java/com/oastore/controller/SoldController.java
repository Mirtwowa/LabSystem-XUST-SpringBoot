package com.oastore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SoldController {
    @RequestMapping("/sold")
    public String sold(){
        return "forward:/sell";
    }
    @GetMapping("/sell")
    public  String returnsell(){
        return "sell";
    }
}
