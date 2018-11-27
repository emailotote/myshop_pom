package cn.otote.shop.entity;

/**
 * @author otote
 * Created on 2018/11/27 19:39.
 * 购物车
 */
public class ShopCart {

    private Integer gid;

    private Integer gnumber;

    private Double money;

    private Goods goods;

    @Override
    public String toString() {
        return "ShopCart{" +
                "gid=" + gid +
                ", gnumber=" + gnumber +
                ", money=" + money +
                ", goods=" + goods +
                '}';
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
    }
}
