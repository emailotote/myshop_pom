package cn.otote.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author otote
 * Created on 2018/11/29 14:45.
 */
@ToString
@Getter
@Setter
public class Address implements Serializable {

    private Integer id;
    private String person;
    private String address;
    private String phone;
    private String code;
    private Integer uid;
    private Integer isdefault;



}
