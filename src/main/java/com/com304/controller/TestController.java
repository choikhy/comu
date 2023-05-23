package com.com304.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/test")
@Controller
public class TestController {

    @GetMapping("/abc")
    public void abc(){

    }
}
