package com.leonid.javalintest.dto;

/**
 * @author Leonid Cheremshantsev
 */
public class Response {

    private Boolean error;
    private Object data;

    public Response() {
    }

    public static Response error(){
        Response response = new Response();
        response.setError(true);
        return response;
    }

    public static Response error(String message){
        Response response = new Response();
        response.setError(true);
        response.setData(message);
        return response;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
