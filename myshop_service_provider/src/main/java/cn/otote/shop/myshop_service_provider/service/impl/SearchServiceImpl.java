package cn.otote.shop.myshop_service_provider.service.impl;

import cn.otote.shop.entity.Goods;
import cn.otote.shop.entity.SolrPage;
import cn.otote.shop.service.ISearchService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author otote
 * Created on 2018/11/20 17:52.
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public Integer addIndex(Goods goods) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        //给索引设值
        solrInputDocument.addField("id", goods.getId());
        solrInputDocument.addField("gtitle", goods.getTitle());
        solrInputDocument.addField("ginfo", goods.getGinfo());
        solrInputDocument.addField("gprice", goods.getPrice());
        solrInputDocument.addField("gimage", goods.getGimage());


        try {
            //添加索引到索引库
            solrClient.add(solrInputDocument);
            //提交
            solrClient.commit();

            //添加成功
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SolrPage<Goods> search(String keyWord, SolrPage<Goods> page) {
        //搜索的条件
        String queryStr = null;

        if ("".equals(keyWord) || keyWord == null || "*:*".equals(keyWord)) {
            queryStr = "*:*";
        } else {
            queryStr = "gtitle:" + keyWord + "|| ginfo:" + keyWord;
        }

        //用来存放查到的结果
        List<Goods> goodsList=new ArrayList<>();

        SolrQuery solrQuery = new SolrQuery();
        //设置搜索条件
        solrQuery.setQuery(queryStr);

        //开启高亮
        solrQuery.setHighlight(true);

        //高亮的前缀
        solrQuery.setHighlightSimplePre("<font color='red'>");
        //高亮的后缀
        solrQuery.setHighlightSimplePost("</font>");

        //需要高亮的字段
        solrQuery.addHighlightField("gtitle");


        try {

            //分页  设置查询的开始位置
            solrQuery.setStart((page.getCurrPage()-1)*page.getPageSize());
            //每页显示的条数
            solrQuery.setRows(page.getPageSize());


            //开始根据关键词查询
            QueryResponse queryResponse = solrClient.query(solrQuery);
            //获取结果集
            SolrDocumentList results = queryResponse.getResults();

            //获取高亮的结果
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            //设置总条数
            page.setCount((int) results.getNumFound());


            for (SolrDocument result : results) {
                try {
                    Integer id = Integer.parseInt(result.get("id").toString());
                    String title = (String) result.get("gtitle");
                    String ginfo = (String) result.get("ginfo");
                    float price = (float) result.get("gprice");
                    String gimage = (String) result.get("gimage");
                    Integer gcount = (Integer) result.get("gcount");

                    //判断是否有高亮
                    if (highlighting.containsKey(id.toString())){
                        //根据id获取高亮的结果
                        Map<String, List<String>> map = highlighting.get(id.toString());
                        List<String> gtitle = map.get("gtitle");

                        //赋给高亮后的值
                        if (gtitle!=null){
                            title=gtitle.get(0);
                        }
                    }

                    goodsList.add(new Goods(id, title, ginfo, gcount, null, null, (double) price, gimage));
                }catch (Exception e){
                    System.out.println("ISearchServiceImpl.search:值获取失败");
                    e.printStackTrace();
                }

            }

            page.setDatas(goodsList);

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return page;

    }
}
