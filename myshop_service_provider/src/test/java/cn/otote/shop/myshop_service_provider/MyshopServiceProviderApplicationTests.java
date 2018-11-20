package cn.otote.shop.myshop_service_provider;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyshopServiceProviderApplicationTests {


    /**
     * 获取客户端
     */
    @Autowired
    private SolrClient solrClient;

    @Test
    public void contextLoads() throws IOException, SolrServerException {
        SolrInputDocument solrInputDocument=new SolrInputDocument();

        //给索引字段设值
        solrInputDocument.addField("id",111 );
        solrInputDocument.addField("gtitle","格力空调" );
        solrInputDocument.addField("ginfo", "好空调，格力造");
        solrInputDocument.addField("gprice",123 );
        solrInputDocument.addField("gcount", 11);
        solrInputDocument.addField("gimage", "www.google.com");

        //添加进索引库
        solrClient.add(solrInputDocument);
        //提交到索引库
        solrClient.commit();

    }

}
