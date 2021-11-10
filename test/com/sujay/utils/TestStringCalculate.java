package com.sujay.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestStringCalculate {

    static StringCalculate stringCalc;

    @BeforeAll
    public static void setup() {
        stringCalc = new StringCalculate();
    }

    private static Stream<Arguments> provideStringsWithNewLineChar() {  //Passing values to testDoubleValNewLineBwNumbersAdd test method
        return Stream.of(
                Arguments.of("1\n2,3", 6),
                Arguments.of("100\n200,300", 600),
                Arguments.of("100\n200,300\n8,6,8\n777,350", 1749),
                Arguments.of("123\n456,879,12,473\n1000\n123", 3066)
        );
    }

    private static Stream<Arguments> provideStringsWithCustomizedDelimChar() {  //Passing values to testDoubleValNewLineBwNumbersAdd test method
        return Stream.of(
                Arguments.of("//;\n1;2",3),
                Arguments.of("//:\n100\n200:300", 600),
                Arguments.of("//.\n200.300\n8.6.8\n677", 1199),
                Arguments.of("//$\n123\n456$879$12$473\n1000\n123", 3066)
        );
    }

    private static Stream<Arguments> provideStringsWithCustomizedMultiDelimChar() {  //Passing values to testMultiValWithCustomizedMultiDelim test method
        return Stream.of(
                Arguments.of("//[***][?]\n1***2?3",6),
                Arguments.of("//[:][^^^][___]\n100\n200:300^^^400___500", 1500),
                Arguments.of("//[...][..][.][{][?]\n200...300\n8..6.8\n67{7?11", 607),
                Arguments.of("//[$$][%][&][#]\n123\n456$$879$$12473\n10%5&6\n123#12", 1614)
        );
    }

    @ParameterizedTest(name = "{index}. Input:\"{0}\" , Expected o/p: \"0\"")
    @EmptySource
    void testEmptyValAdd(String actual) {
        assertEquals(0, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input:\"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {"1:1", "0:0", "100:100", "999:999", "1000:1000"}, delimiter = ':')
    void testSingleValAdd(String actual, int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {"1,2:3", "0,0:0", "0,1:1", "10,10:20", "1,1:2", "1000,1000:2000"}, delimiter = ':')
    void testDoubleValAdd(String actual, int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {",2:2", "0,:0", ",1000:1000", "20,:20", "1,:1"}, delimiter = ':')
    void testDoubleValOneEmptyAdd(String actual, int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected Exception!!")
    @CsvSource(value = {"9223372036854775808,0:Value \"9223372036854775808\" is not convertable to int range",
            "9223372036854775807,9223372036854775807:Value \"9223372036854775807\" is not convertable to int range",
            "2147482223647,-10222222000000:Value \"2147482223647\" is not convertable to int range"}, delimiter = ':')
    void testDoubleValUnknownAmountOfNumbersAddCheck(String actual, String expectedMessage) {
        Exception thrown;
        if(expectedMessage.startsWith("V")){
            thrown = assertThrows(NumberFormatException.class, () -> stringCalc.add(actual));
        }else{
            thrown = assertThrows(ArithmeticException.class, () -> stringCalc.add(actual));
        }
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {"1,2,3:6", "0,1,100,200:301",
            "100,200,300,190,1,10,20,30,40,50,1,2,3,4,5,6,7,8,999," +
            "20,30,40,50,1,278,3,4,75,6,7,8,9,12:2519", "10,20,30,40,50,1,2,3,4,5,6,7,8,9,12:207",
            "10,20,30,40,50,1,2,3,4,5,6,7,8,9,1000,210,20,30,40,50,1,2,3,4,5,6,7,8,9,12:1602"}, delimiter = ':')
    //@CsvFileSource(resources = "/resources/Values.txt",delimiter = ':',maxCharsPerColumn=20000)
    void testMultiValAdd(String actual, int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input with \\n: \"{0}\" , Expected o/p: \"{1}\"")
    @MethodSource("provideStringsWithNewLineChar")
    void testMultiValNewLineBwNumbersAdd(String actual,int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @MethodSource("provideStringsWithCustomizedDelimChar")
    void testMultiValWithCustomizedSingleDelim(String actual, int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {"-1,213,-232,2,8,78,56,-75:negatives not allowed,Values_-1,-232,-75",
            "-1,21,-11,0,3,-232,2,8,-100,-1000000,78,56,-75:negatives not allowed,Values_-1,-11,-232,-100,-1000000,-75"},delimiter = ':')
    void testMultiValWithNegativeValCheck(String actual, String expectedMessage) {
        Exception thrown = assertThrows(ArithmeticException.class, () -> stringCalc.add(actual));
        assertEquals(expectedMessage, thrown.getMessage());
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @CsvSource(value = {"1000,2:1002", "12130,2133:0", "1000,1:1001", "1000,1000:2000","1000000,1000000:0"}, delimiter = ':')
    void testMultiValSkipMoreThanThousandAdd(String actual, int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

    @ParameterizedTest(name = "{index}. Input: \"{0}\" , Expected o/p: \"{1}\"")
    @MethodSource("provideStringsWithCustomizedMultiDelimChar")
    void testMultiValWithCustomizedMultiDelim(String actual, int expected) {
        assertEquals(expected, stringCalc.add(actual));
    }

}
