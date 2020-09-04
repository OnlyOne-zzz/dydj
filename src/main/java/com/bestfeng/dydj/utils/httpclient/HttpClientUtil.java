package com.bestfeng.dydj.utils.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
public class HttpClientUtil {
    public static final  Charset UTF8   = Charset.forName("utf-8");
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    public static String doPost(String url, Map<String, String> map, String charset) throws Exception {
        return doPost(url, map, null, charset);
    }

    public static String doPost(String url, Map<String, String> map) throws Exception {
        return doPost(url, map, null, UTF8.displayName());
    }

    /**
     * post请求
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static String doPost(String url,Map<String,String> map,Map<String,String> header,String charset) throws Exception{
        HttpClient httpClient = null;
        HttpPost   httpPost   = null;
        String     result     = null;
        httpClient = SSLClient.getInstance();
        httpPost = new HttpPost(url);  
        //设置参数  
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (!CollectionUtils.isEmpty(map)) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
        }
        if(list.size() > 0){  
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
            httpPost.setEntity(entity);
        }
        if (!CollectionUtils.isEmpty(header)) {
            Iterator iterator = header.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, String> elem = (Entry<String, String>) iterator.next();
                httpPost.addHeader(elem.getKey(), elem.getValue());
            }
        }
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode !=200) {
            LOGGER.warn("发起HTTP请求出错 状态码 {} url {} result {} ",statusCode,url,result);
            httpPost.abort();//取消中途请求
        	LOGGER.error("HttpClient, error status code:{}", statusCode);
			return null;
        }
        if (response != null) {
            HttpEntity resEntity = response.getEntity();  
            if(resEntity != null){  
                result = EntityUtils.toString(resEntity,charset);  
            }
        }  
        return result;  
    }  
    
    /**
	 * post请求(json)
	 * @param url
	 * @param json
	 * @param charset
	 * @return
	 * @throws Exception
	 */
    public static String doPostToJson(String url, String json, String charset) throws Exception{
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        httpClient = SSLClient.getInstance();
        httpPost = new HttpPost(url);  
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        StringEntity se = new StringEntity(json, charset);
        se.setContentType("application/json");
        httpPost.setEntity(se);
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode !=200) {
            LOGGER.warn("发起HTTP请求出错 状态码 {} url {} result {} ",statusCode,url,result);
        	httpPost.abort();//取消中途请求
        	LOGGER.error("HttpClient, error status code:{}", statusCode);
			return null;
        }
        if (response != null) {
            HttpEntity resEntity = response.getEntity();  
            if(resEntity != null){  
                result = EntityUtils.toString(resEntity,charset);  
            }  
        }
        return result;
    }

    /**
     * post请求(json) + hearder
     */
    public static String doPostToJson(String url, String json, Map<String, String> headers, String charset) throws Exception {
        HttpClient httpClient = null;
        HttpPost   httpPost   = null;
        String     result     = null;
        httpClient = SSLClient.getInstance();
        httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        for (Entry<String, String> e : headers.entrySet()) {
            httpPost.setHeader(e.getKey(), e.getValue());
        }
        StringEntity se = new StringEntity(json, charset);
        se.setContentType("application/json");
        httpPost.setEntity(se);
        HttpResponse response = httpClient.execute(httpPost);
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity, charset);
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode!=200){
                LOGGER.warn("发起HTTP请求出错 状态码 {} url {} result {} ",statusCode,url,result);
            }
        }
        return result;
    }
    public static InputStream doPostJsonToInputStream(String url, String json, Map<String, String> headers, String charset) throws Exception {
        HttpClient httpClient = null;
        HttpPost   httpPost   = null;
        String     result     = null;
        httpClient = SSLClient.getInstance();
        httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setHeader("Accept", "application/json");
        if(!CollectionUtils.isEmpty(headers)){
            for (Entry<String, String> e : headers.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
        }
        StringEntity se = new StringEntity(json, charset);
        se.setContentType("application/json");
        httpPost.setEntity(se);
        HttpResponse response = httpClient.execute(httpPost);
        if (response != null) {
            HttpEntity resEntity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode!=200){
                result = EntityUtils.toString(resEntity, charset);
                LOGGER.warn("发起HTTP请求出错 状态码 {} url {} result {} ",statusCode,url,result);
            }
            return resEntity.getContent();
        }
        return null;
    }

    /**
	 * post请求(xml)
	 * @param url
	 * @param xml
	 * @param charset
	 * @return
	 * @throws Exception
	 */
    public static String doPostToXml(String url,String xml,String charset) throws Exception{  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        httpClient = SSLClient.getInstance();
        httpPost = new HttpPost(url);  
        httpPost.addHeader("Content-type","application/xml; charset=utf-8");  
        httpPost.setHeader("Accept", "application/xml");
        StringEntity se = new StringEntity(xml, charset);
        se.setContentType("application/xml");
        httpPost.setEntity(se);
        HttpResponse response = httpClient.execute(httpPost);
        if(response != null){  
            HttpEntity resEntity = response.getEntity();  
            if(resEntity != null){  
                result = EntityUtils.toString(resEntity,charset);  
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode!=200){
                LOGGER.warn("发起HTTP请求出错 状态码 {} url {} result {} ",statusCode,url,result);
            }
        }  
        return result;  
    }
    
    
    /**
	 * get请求
	 * @param url
	 * @param map
	 * @param charset
	 * @return
	 * @throws Exception
	 */
    public static String doGet(String url,Map<String,String> map,String charset) throws Exception{  
        HttpClient httpClient = null;  
        HttpGet httpGet = null;  
        String result = null;  
        httpClient = SSLClient.getInstance();
        httpGet = new HttpGet(url);  
        //设置参数  
        if (map != null && map.size() > 0) {  
            Set<String> keySet = map.keySet();  
            for (String key : keySet) {  
                httpGet.addHeader(key, map.get(key));  
            }  
        }  
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode !=200) {
            LOGGER.warn("发起HTTP请求出错 状态码 {} url {}  ",statusCode,url);
        	httpGet.abort();//取消中途请求
        	LOGGER.error("HttpClient, error status code:{}", statusCode);
			return null;
        }
        if(response != null){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){  
                result = EntityUtils.toString(resEntity,charset);  
            }  
        }  
        return result;  
    }
    /**
	 * get请求
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
    public static HttpEntity doGetHttpEntity(String url,Map<String,String> map) throws Exception{
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        httpClient = SSLClient.getInstance();
        LOGGER.info("请求参数:{}",url);
        httpGet = new HttpGet(url);
        //设置参数
        if (map != null && map.size() > 0) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                httpGet.addHeader(key, map.get(key));
            }
        }
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode !=200) {
            LOGGER.warn("发起HTTP请求出错 状态码 {} url {}  ",statusCode,url);
        	httpGet.abort();//取消中途请求
        	LOGGER.error("HttpClient, error status code:{}", statusCode);
			return null;
        }
        if(response != null){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                return resEntity;
            }
        }
        return null;
    }
}
