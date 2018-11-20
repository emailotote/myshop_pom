package cn.otote.shop.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author otote
 * Created on 2018/11/20 18:54.
 */
public class HttpClientUtils {

    /**
     * 模拟浏览器发送请求
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String sendJson(String url,String json) {
        String result = null;

        try {
            //构建一个http客户端
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();

            //创建post请求
            HttpPost post=new HttpPost(url);
            //设置头信息
            post.addHeader("Content-Type","application/json" );
            //创建一个请求体
            HttpEntity httpEntity=new StringEntity(json,"utf-8");
            //设置请求体
            post.setEntity(httpEntity);
            //发送请求
            CloseableHttpResponse httpResponse = httpClient.execute(post);

            //获取响应体
            HttpEntity responseEntity = httpResponse.getEntity();
            //从响应体中解析数据
            result = EntityUtils.toString(responseEntity);

            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }


}
