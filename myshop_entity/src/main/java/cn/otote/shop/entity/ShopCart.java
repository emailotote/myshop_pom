package cn.otote.shop.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author otote
 * Created on 2018/11/27 19:39.
 * 购物车
 */
public class ShopCart implements Serializable {

    private Integer id;

    private Integer gid;

    private Integer gnumber;

    private Double money;

    private Goods goods;

    private Integer uid;

    @Override
    public String toString() {
        return "ShopCart{" +
                "id=" + id +
                ", gid=" + gid +
                ", gnumber=" + gnumber +
                ", money=" + money +
                ", goods=" + goods +
                ", uid=" + uid +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getGnumber() {
        return gnumber;
    }

    public void setGnumber(Integer gnumber) {
        this.gnumber = gnumber;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Goods getGoods() {
        return goods;
    }


    public void setGoods(Goods goods) {
        this.goods = goods;
        BigDecimal price =BigDecimal.valueOf(goods.getPrice());
        BigDecimal number =BigDecimal.valueOf(getGnumber());
        this.setMoney(price.multiply(number).doubleValue());
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
