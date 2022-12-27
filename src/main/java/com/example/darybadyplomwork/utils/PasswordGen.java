package com.example.darybadyplomwork.utils;

import net.bytebuddy.utility.RandomString;
import org.springframework.util.StringUtils;

public class PasswordGen {
    static public String generatePass(){
        return RandomString.make();
    }

}
