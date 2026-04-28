package com.tourism.controller;

import com.tourism.dto.CaptchaResponse;
import com.tourism.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/captcha")
@CrossOrigin("*")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping
    public CaptchaResponse getCaptcha() {
        return captchaService.createCaptcha();
    }
}
