package org.locus.learn.stringmaster.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stringmaster")
public class StringmasterController {

    @GetMapping("/sayhello/{name}")
    public String sayhello(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("/length/{name}")
    public Integer length(@PathVariable String name) {
        return StringUtils.isNotEmpty(name) ? name.length() : 0;
    }

}
