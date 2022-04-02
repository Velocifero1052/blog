package ru.bagration.spring.utils;

import java.util.UUID;

public class Utility {

    public static String generateUuid(){
        return UUID.randomUUID().toString();
    }

}
