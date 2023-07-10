package com.example.demo.exception;

public class InvestmentTypeInvalidException extends FlexibleSuccessFeesException {
    private final String investmentType;

    public InvestmentTypeInvalidException(String message, String investmentType) {
        super(message);
        this.investmentType = investmentType;
    }

    public String getInvestmentType() {
        return investmentType;
    }
}
