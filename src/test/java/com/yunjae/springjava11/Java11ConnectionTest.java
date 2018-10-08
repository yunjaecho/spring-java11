package com.yunjae.springjava11;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Java11ConnectionTest {

    @Test
    public void toArray() {
        List<String> youtube = List.of("구독과", "좋와요", "감사합니다.");
        Object[] objects = youtube.toArray();
        System.out.println(Arrays.toString(objects));

        String[] strings = youtube.toArray(new String[youtube.size()]);
        System.out.println(Arrays.toString(strings));

        String[] strings1 = youtube.toArray(String[]::new);
        System.out.println(Arrays.toString(strings1));
    }
}
