package cn.otote.shop.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author otote
 * Created on 2018/11/19 13:41.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    private static final long serialVersionUID = 705827603232855332L;

    private Integer id;
    private String title;
    private String ginfo;
    private Integer gcount;
    private Integer tid=1;
    private Double allprice;
    private Double price;
    private String gimage;



}
