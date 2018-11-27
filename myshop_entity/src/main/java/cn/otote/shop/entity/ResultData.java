package cn.otote.shop.entity;

import java.io.Serializable;

/**
 * @author otote
 * Created on 2018/11/27 11:43.
 */
public class ResultData<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    @Override
    public String toString() {
        return "ResultData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
