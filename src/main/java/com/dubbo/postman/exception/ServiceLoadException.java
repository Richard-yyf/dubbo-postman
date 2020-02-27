package com.dubbo.postman.exception;

/**
 * @author Richard_yyf
 * @version 1.0 2020/2/27
 */
public class ServiceLoadException extends Exception {

    private String serviceName;

    public ServiceLoadException(Throwable cause, String serviceName) {
        super(cause);
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
