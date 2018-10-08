package com.yunjae.springjava11.controllers;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class DelayController {
    @GetMapping("/delay")
    public String delay(@RequestParam("seconds") int seconds) throws InterruptedException {
        System.out.println("delaying " + seconds + " seconds on " + Thread.currentThread());
        Thread.sleep(seconds * 1000l);
        return "delayed " + seconds + " seconds";
    }
}
