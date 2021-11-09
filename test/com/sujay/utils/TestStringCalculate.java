package com.sujay.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestStringCalculate {

    @ParameterizedTest(name = "{index}. Input:\"{0}\" , Expected o/p: \"0\"")
    @EmptySource
    void testEmptyValAdd(String actual){
        StringCalculate stringCalc = new StringCalculate();
        assertEquals(0, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input:\"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {"1:1","0:0","100:100","1000000000:1000000000","2147483647:2147483647"},delimiter = ':')
    void testSingleValAdd(String actual,int expected){
        StringCalculate stringCalc = new StringCalculate();
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {"1,2:3","0,0:0","0,1:1","10,10:20","-1,-1:-2","2147473647,10000:2147483647"},delimiter = ':')
    void testMultiValAdd(String actual,int expected){
        StringCalculate stringCalc = new StringCalculate();
        assertEquals(expected, stringCalc.add(actual));
    }
}
