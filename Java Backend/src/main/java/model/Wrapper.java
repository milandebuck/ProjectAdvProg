package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Wrapper object, used to encapsulate data.
 * Created by milan on 22.11.16.
 */
@JsonSerialize
public class Wrapper<T> {
    private Boolean succes;
    private String msg;
    private T data;

    /**
     * Empty constructor for Wrapper object.
     */
    public  Wrapper() {}


    /**
     * Constructor for Wrapper object.
     * @param succes true when object is valid
     * @param msg message for diagnostic purposes
     * @param data encapsulated data
     */
    public Wrapper(Boolean succes, String msg, T data) {
        this.data = data;
        this.msg = msg;
        this.succes = succes;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSucces() {
        return this.succes;
    }

    public void setSucces(Boolean succes) {
        this.succes = succes;
    }
}
