package org.locus.learn.numbermaster.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/numbermaster")
public class NumbermasterController {

    @GetMapping("/addone/{number}")
    public Integer addone(@PathVariable Integer number) {
        return number + 1;
    }

    @GetMapping("/square/{number}")
    public Integer square(@PathVariable Integer number) {
        return Double.valueOf(Math.pow(number, 2)).intValue();
    }
}
