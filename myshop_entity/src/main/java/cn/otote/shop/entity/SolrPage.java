package cn.otote.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author otote
 * Created on 2018/11/21 15:30.
 */
public class SolrPage<T> implements Serializable {

    private Integer currPage = 1;
    private Integer pageSum;
    private Integer pageSize = 8;
    private Integer count;
    private List<T> datas;
    private List<Integer> indexs;

    public SolrPage() {
    }


    @Override
    public String toString() {
        return "SolrPage{" +
                "currPage=" + currPage +
                ", pageSum=" + pageSum +
                ", pageSize=" + pageSize +
                ", count=" + count +
                ", datas=" + datas +
                ", indexs=" + indexs +
                '}';
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSum() {
        return pageSum;
    }

    public void setPageSum(Integer pageSum) {
        this.pageSum = pageSum;
        setIndexs(new ArrayList<>());
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<Integer> indexs) {
        if (pageSum == null) {
            return;
        }

        //设置中间的页数   1 2 3 4 5
        int begin = Math.max(1, currPage - 2);
        int end = Math.min(pageSum, currPage + 2);

        for (int i = begin; i <= end; i++) {
            indexs.add(i);
        }
        this.indexs=indexs;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        if (this.count % this.pageSize == 0) {
            this.pageSum = this.count / this.pageSize;
        } else {
            this.pageSum = (this.count / this.pageSize) + 1;
        }

        setIndexs(new ArrayList<Integer>());
    }
}
