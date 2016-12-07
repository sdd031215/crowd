package com.elong.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.elong.domain.RerankTroubleShootRequest;

public class HttpRequest {

	private static RequestConfig requestConfig = RequestConfig.custom()  
            .setSocketTimeout(15000)  
            .setConnectTimeout(15000)  
            .setConnectionRequestTimeout(15000)  
            .build();  

	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }      
    
    
    
    public static void main(String[] args) {
    	RerankTroubleShootRequest request = new RerankTroubleShootRequest();
    	request.setChannel(2);
    	request.setBucketId("r0");
    	request.setDeviceId("AF3ADF18-3465-4B95-AEF3-028B23069D51");
    	request.setCityId(101);
    	request.setCheckIn(new Date());
    	request.setCheckOut(new Date());
    	request.setServiceSeq(1);
    	request.setDistrictType(0);
    	request.setKeyWord("五道口");
    	
    	JSONObject jo = new JSONObject(); 
    	//{"channel":2,"bucketId":"r0","deviceId":"AF3ADF18-3465-4B95-AEF3-028B23069D51","cityId":101,"checkIn":"2016-11-28","checkOut":"2016-11-29","serviceSeq":1,"districtType":0,"keyWord":"五道口"}
    	try {
			jo.put("channel", 2);
			jo.put("bucketId", "r0");
	    	jo.put("deviceId", "AF3ADF18-3465-4B95-AEF3-028B23069D51");
	    	jo.put("cityId", 101);
	    	jo.put("checkIn", "2016-11-28");
	    	jo.put("checkOut", "2016-11-29");
	    	jo.put("serviceSeq", 1);
	    	jo.put("districtType", 0);
	    	jo.put("keyWord", "五道口");
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    	String url =  "http://sa-view.vip.elong.com//monitor/rerankTroubleShooting";
    	System.out.println(jo.toString());
    	
    	//String content = executor.execute(Request.Get("https://www.yahoo.com/")).returnContent().asString();
//    	reporter.start(1, TimeUnit.SECONDS);
//        httpGet httpGet = new HttpGet("https://www.yahoo.com/tech/s/favorite-iphone-feature-apple-added-ios-9-2-183202009.html");
//        HttpResponse response = httpClient.execute(httpGet);
//        EntityUtils.toString(response.getEntity());
    	
    	//发送 POST 请求
//    	HttpClient httpClient = new HttpClient();
//
//    	StringRequestEntity requestEntity = null;
//		try {
//			requestEntity = new StringRequestEntity(
//			        jo.toString(), // json的序列化后的string，比如这里应该就是USER对象序列化成json的str
//			        "application/json",
//			        "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//
//    	PostMethod postMethod = new PostMethod(url);
//    	postMethod.setRequestEntity(requestEntity);
//
//    	String resStr = null;
//    	try {
//			httpClient.executeMethod(postMethod);
//			resStr = postMethod.getResponseBodyAsString();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block∂
//			e.printStackTrace();
//		}
//
//    	System.out.println("post res: " + resStr);
    	
	}
    
    
    /** 
     * 发送Post请求 
     * @param httpPost 
     * @return 
     */  
    private static String sendHttpPost(HttpPost httpPost) {  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;  
        try {  
            // 创建默认的httpClient实例.  
            httpClient = HttpClients.createDefault();  
            httpPost.setConfig(requestConfig);  
            // 执行请求  
            response = httpClient.execute(httpPost);  
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
                if (httpClient != null) {  
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }  


}
