package org.locus.learn.numbermaster.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.IntStream;

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

    @GetMapping("/sqrt/{number}")
    public Double sqrt(@PathVariable Integer number) {
        return Math.sqrt(number);
    }

    @PostMapping("/max")
    public Integer max(@RequestBody Set<Integer> integers) {
        return integers.stream()
                .max(Integer::compareTo).orElse(0);
    }

}
