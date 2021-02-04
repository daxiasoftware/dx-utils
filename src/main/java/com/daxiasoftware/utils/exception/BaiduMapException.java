package com.daxiasoftware.utils.exception;

public class BaiduMapException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public BaiduMapException(int status, String message) {
        super("status: " + status + ", message: " + message);
    }

}
