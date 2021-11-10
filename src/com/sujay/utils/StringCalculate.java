package com.sujay.utils;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculate {

    public int add(String numbers) {
        int result = 0;

        if (checkNumberStrNotNullAndNotEmpty(numbers)) {

            String[] numberArr;
            String delim = "[\n,]";

            if (numbers.startsWith("/")) {
                String[] headAndValues = numbers.split("\n",2);
                String delims = headAndValues[0].substring(2);

                if(delims.charAt(0)=='[') {
                    delims = delims.substring(1, delims.length() - 1);
                }

                delim = Stream.of(delims.split("]\\["))
                            .map(Pattern::quote)                    //\Q and \E would be added to the string
                                                                    // restrict the special chars functionality and act
                                                                    // as normal strings
                            .collect(Collectors.joining("|"));
                delim = delim+"|\n";

                numbers = headAndValues[1];
                numberArr = numbers.split(delim);
            } else {
                numberArr = numbers.split(delim);
            }

            for (String strVal : numberArr) {
                if (strVal.length() != 0) {

                    try {
                        int val = Integer.parseInt(strVal);
                        if( checkValNonNegative(val) ) {
                            if (val < 1001)
                                result = Math.addExact(result, val);   //addExact throws exception if any overflow/underflow would be there in integer addition#Java 1.8 or above reqd.
                        }else
                            throw new ArithmeticException("negatives not allowed,Values_"+ Arrays.stream(numberArr)
                                                                                            .filter(values -> Integer.parseInt(values)<0)
                                                                                            .collect(Collectors.joining(",")));
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Value \"" + strVal + "\" is not convertable to int range");
                    } catch (ArithmeticException e) {
                        throw new ArithmeticException(e.getMessage());
                    }
                }
            }
        }
        return result;
    }

    private boolean checkNumberStrNotNullAndNotEmpty(String str) {
        return str != null && str.length() != 0;
    }

    private boolean checkValNonNegative(int value) {
        return value>-1 ;
    }
}
