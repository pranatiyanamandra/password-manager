package com.py.projects.trello.data.UserDataServiceTest;


import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDataServiceTest {

    @Test
    public void testMethod(){
        String input = "[{emailAddress=1@gmail.com, password=1, pin=1, title=1, userName=1},{emailAddress=2@gmail.com, password=2, pin=2, title=2, userName=2},{emailAddress=3@gmail.com, password=3, pin=3, title=3, userName=3}]";
        int length = input.length();
        int index = 2;
        int startIndex = StringUtils.ordinalIndexOf(input,"{",index+1);
        int endIndex = StringUtils.ordinalIndexOf(input,"}",index+1);
        input = input.substring(0,startIndex)+input.substring(endIndex+2,length);
        System.out.println(input);
    }
}
