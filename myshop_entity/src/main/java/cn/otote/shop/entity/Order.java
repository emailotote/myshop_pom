package cn.otote.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author otote
 * Created on 2018/11/29 14:30.
 */
@Setter
@Getter
@ToString
public class Order implements Serializable {

    private Integer id;
    private Integer orderid;
    private Integer uid;
    private String person;
    private String address;
    private String phone;
    private String code;
    private Double oprice;
    private Integer status=0;

    private List<OrderDetail> details;

}
