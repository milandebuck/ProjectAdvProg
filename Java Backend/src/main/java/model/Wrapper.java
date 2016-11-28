package main.java.model;

/**
 * Created by milan on 22.11.16.
 */
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
}
