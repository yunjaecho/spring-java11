package com.yunjae.springjava11;

import org.junit.Test;

/**
 * java 11 Stirng 관련 추가 메서드
 */
public class Java11StringTest {

    /**
     * strip 유니코드 관련 공백 문자도 제거
     */
    @Test
    public void stripTest() {
        String name = " yunjae \u205F";
        System.out.println(name.trim());
        System.out.println(name.trim().length());
        System.out.println(name.strip());
        System.out.println(name.strip().length());
    }

    @Test
    public void isBlank() {
        String name = " \u205F";
        System.out.println(name.isEmpty());
        System.out.println(name.isBlank());
    }

    @Test
    public void lines() {
        String name = "You will see a list of plugins used in \nyour project with newer versions available.\nUpdate all of those plugins to the lastest stable version.\nAfter you've updated your plugin versions make sure that your \nproject still compiles and runs properly.";
        name.lines().forEach(System.out::println);
    }

    @Test
    public void repeat() {
        String name = "ou will see a list of plugins used\n";
        System.out.println(name.repeat(100));
    }

}
