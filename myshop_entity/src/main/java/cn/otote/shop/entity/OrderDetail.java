package cn.otote.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author otote
 * Created on 2018/11/29 14:34.
 */
@Setter
@Getter
@ToString
public class OrderDetail implements Serializable {

    private Integer id;
    private Integer oid;
    private Integer gid;
    private String gname;
    private String ginfo;
    private Double price;
    private Integer gcount;
    private String gimage;

}
