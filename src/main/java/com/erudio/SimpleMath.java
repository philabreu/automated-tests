package com.erudio;

public class SimpleMath {

    public Double sum(Double first, Double second) {
        return first + second;
    }

    public Double subtraction(Double first, Double second) {
        return first - second;
    }

    public Double multiplication(Double first, Double second) {
        return first * second;
    }

    public Double division(Double first, Double second) {
        if (second.equals(0D)) {
            throw new ArithmeticException("impossivel dividir por zero");
        }

        return first / second;
    }

    public Double mean(Double first, Double second) {
        return (first + second) / 2;
    }

    public Double squareRoot(Double number) {
        return Math.sqrt(number);
    }
}