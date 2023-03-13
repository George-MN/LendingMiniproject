package com.example.loanlending.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Response {
    public static ResponseEntity<Object> responseHelper(String description, HttpStatus httpStatus,Object obj,int respDesc){
        Map<String, Object> response= new HashMap<>();
        response.put("ResponseDescription",description);
        response.put("ResponseCode",respDesc);
        response.put("data",obj);
        return  new ResponseEntity<>(response,httpStatus);
    }
}
