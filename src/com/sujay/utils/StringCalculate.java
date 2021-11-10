package com.sujay.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringCalculate {

    public int add(String numbers) {
        int result = 0;

        if (numbers != null && numbers.length() != 0) {
            String[] numberArr;
            String delim = "[\n,]";
            if (numbers.startsWith("/")) {
                int startIndex = 2;
                int endIndex = numbers.indexOf("\n");

                delim = numbers.substring(startIndex, endIndex);  //numbers.charAt(2) was used previously, modified if delim size increases

                numbers = numbers.substring(endIndex + 1);
                numberArr = numbers.split("[\n" + delim + "]");

            } else {
                numberArr = numbers.split(delim);
            }

            for (String val : numberArr) {
                if (val.length() != 0) {

                    try {
                        if(Integer.parseInt(val)>-1)
                            result = Math.addExact(result, Integer.parseInt(val));   //addExact throws exception if any overflow/underflow would be there in integer addition#Java 1.8 or above reqd.
                        else
                            throw new NumberFormatException("negatives not allowed,Values_"+ Arrays.stream(numberArr)
                                                                                            .filter(values -> Integer.parseInt(values)<0)
                                                                                            .collect(Collectors.joining(",")));
                    } catch (NumberFormatException e) {
                        if(e.getMessage().startsWith("F"))
                            throw new NumberFormatException("Value \"" + val + "\" is not in int range");
                        else
                            throw new NumberFormatException(e.getMessage());
                    } catch (ArithmeticException e) {
                        throw new ArithmeticException("Integer Overflow!");
                    }
                }
            }
        }
        return result;
    }
}
