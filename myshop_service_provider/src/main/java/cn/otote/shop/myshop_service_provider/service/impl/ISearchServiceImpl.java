package cn.otote.shop.myshop_service_provider.service.impl;

import cn.otote.shop.entity.Goods;
import cn.otote.shop.service.ISearchService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author otote
 * Created on 2018/11/20 17:52.
 */
@Service
public class ISearchServiceImpl implements ISearchService {

    @Autowired
    private SolrClient solrClient;

    @Override
    public Integer addIndex(Goods goods) {
        SolrInputDocument solrInputDocument=new SolrInputDocument();
        //给索引设值
        solrInputDocument.addField("id",goods.getId());
        solrInputDocument.addField("gtitle",goods.getTitle());
        solrInputDocument.addField("ginfo",goods.getGinfo() );
        solrInputDocument.addField("gprice",goods.getPrice() );
        solrInputDocument.addField("gimage",goods.getGimage() );


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
    public List<Goods> search(String keyWord) {
        return null;
    }
}
