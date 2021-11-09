package com.sujay.utils;

public class StringCalculate {

    public int add(String numbers) {
        if(numbers!=null && !numbers.isEmpty()){
            String[] numberArr = numbers.split(",");
            if(numberArr.length==1)
                return Integer.parseInt(numberArr[0]);
            else
                return Integer.parseInt(numberArr[0])+Integer.parseInt(numberArr[1]);
            }
        return 0;
    }
}
