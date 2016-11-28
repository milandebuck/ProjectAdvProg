package model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by milan on 22.11.16.
 */
@JsonSerialize
public class Wrapper<T> {
    private Boolean succes;
    private String msg;
    private T data;

    public Wrapper(Boolean succes,String msg,T data){
        this.data = data;
        this.msg=msg;
        this.succes=succes;
    }

    public Wrapper(){}

    public T getData(){
        return this.data;
    }

    public void setData(T data){
        this.data = data;
    }

    public String getMsg(){
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public Boolean getSucces(){
        return this.succes;
    }

    public void setSucces(Boolean succes){
        this.succes=succes;
    }
/*

public class Wrapper {
    private boolean valid;
    private String msg;
    private String name;
    private Object object;

    public Wrapper() {
        this.valid = true;
    }

    public Wrapper(Object object) {
        this.valid = true;
        this.name = object.getClass().getSimpleName();
        this.object = object;
    }

    public boolean getValid() { return valid; }
    public void setValid(boolean valid) { this.valid=valid; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg=msg; }

    public String getName() { return name; }
    public void setName(String name) { this.name=name; }

    public Object getObject() { return this.object; }

    public void setObject(Object object) { this.object=object; }
}*/
