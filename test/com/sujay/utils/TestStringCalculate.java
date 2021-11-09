package com.sujay.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestStringCalculate {

    @Test
    void testEmptyValSum(){
            StringCalculate stringCalc = new StringCalculate();
            assertEquals(0, stringCalc.add(""));
    }
}
