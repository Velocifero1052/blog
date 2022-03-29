package ru.bagration.spring.utils;

import lombok.Data;

import java.util.Collections;
import java.util.HashMap;

@Data
public class ResponseData{

    private Object data;
    private String errorMessage;
    private Long timeStamp;

    public static ResponseData ok(Object data){
        var responseData = new ResponseData();
        responseData.setData(data);
        responseData.setErrorMessage("");
        responseData.setTimeStamp(System.currentTimeMillis());
        return responseData;
    }

    public static ResponseData savedSuccess(String message){
        var responseData = new ResponseData();
        var messageMap = Collections.singletonMap("successMessage", message);
        responseData.setData(messageMap);
        responseData.setErrorMessage("");
        responseData.setTimeStamp(System.currentTimeMillis());
        return responseData;
    }

    public static ResponseData responseBadRequest(String errorMessage){
        var responseData = new ResponseData();
        responseData.setErrorMessage(errorMessage);
        responseData.setTimeStamp(System.currentTimeMillis());
        return responseData;
    }


}
