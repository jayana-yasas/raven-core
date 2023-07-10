package com.example.demo.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCostComponent {

    public double getByUnits(double numberOfUnits, String phoneNumber) {

        double costPerMessage = getCost(phoneNumber);
        return numberOfUnits * costPerMessage;
    }

    private double getCost(String phoneNumber) {

        double cost = 0;
        if (phoneNumber.substring(0, 4).equalsIgnoreCase("9471")) {
            cost = 0.31D;
        } else if (phoneNumber.substring(0, 4).equalsIgnoreCase("9472")) {
            cost = 0.31D;
        } else {
            cost = 0;
        }
        return cost;
    }

    private boolean isValid(String phoneNumber) {

        if (phoneNumber.length() < 11) {
            return false;
        }
        if (!phoneNumber.startsWith("94")) {
            return false;
        }
        return true;
    }
}
