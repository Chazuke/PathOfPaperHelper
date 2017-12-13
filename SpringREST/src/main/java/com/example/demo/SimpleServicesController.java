package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleServicesController {
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/rolld10")
    public d10 rollD10(@RequestParam(value="numDice", required=false) Integer numDice) {
    	Random rand = new Random();
    	if (numDice == null) return new d10(counter.incrementAndGet(), (rand.nextInt(10)+1) );
    	else if (numDice > 1) {
        	int[] ret = new int[numDice];
        	int sum = 0;
        	for (int i = 0; i < numDice; i++) {
        		ret[i] = (rand.nextInt(10)+1);
        		sum += ret[i];
        	}
        	return new d10(counter.incrementAndGet(), ret, sum);
        }
        else {
        	return new d10(counter.incrementAndGet(), (rand.nextInt(10)+1) );
        }
    }
    
}
