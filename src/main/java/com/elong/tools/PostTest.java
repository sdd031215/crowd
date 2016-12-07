package com.elong.tools;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 以Post方式提交请求并使用ResponseHandler简化响应结果的处理
 *
 * @author www.yshjava.cn
 */
public class PostTest {

	private static final String APPLICATION_JSON = "application/json";
    
    
 // 接口地址  
    private static String apiURL = "http://172.21.32.141:8080//monitor/rerankTroubleShooting"; 
    private static Log logger = LogFactory.getLog(PostTest.class);  
    private static final String pattern = "yyyy-MM-dd HH:mm:ss:SSS";  
    private static HttpClient httpClient = new DefaultHttpClient();  

    private static HttpPost method = new HttpPost(apiURL);  
    private static long startTime = 0L;  
    private static long endTime = 0L;  
    private static int status = 0;  
    
    public static void main(String[] args) throws Exception {
        //目标页面
    	String url =  "http://sa-view.vip.elong.com/monitor/rerankTroubleShooting";
    	
        //创建一个默认的HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        
        //以post方式请求网页http://www.yshjava.cn
        HttpPost httppost = new HttpPost(url);
        //添加HTTP POST参数
        JSONObject jo = new JSONObject(); 
    	//{"channel":2,"bucketId":"r0","deviceId":"AF3ADF18-3465-4B95-AEF3-028B23069D51","cityId":101,"checkIn":"2016-11-28","checkOut":"2016-11-29","serviceSeq":1,"districtType":0,"keyWord":"五道口"}
    	try {
			jo.put("channel", 2);
			jo.put("bucketId", "r1");
	    	jo.put("deviceId", "AF3ADF18-3465-4B95-AEF3-028B23069D51");
	    	jo.put("cityId", 101);
	    	jo.put("checkIn", "2016-12-06");
	    	jo.put("checkOut", "2016-12-07");
	    	jo.put("serviceSeq", 1);
	    	jo.put("districtType", 0);
	    	jo.put("keyWord", "五道口");
	    	jo.put("requestType", 0);
	    	jo.put("serviceSeq", 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    	String response = doPost(url, jo);
    	net.sf.json.JSONObject poststr = net.sf.json.JSONObject.fromObject(response);
    	String rr =  poststr.get("rerankDebugResponse").toString();
    	String agentLog = net.sf.json.JSONObject.fromObject(poststr).get("agentLog").toString();
		String dids =  net.sf.json.JSONObject.fromObject(rr).get("dids").toString();
		dids = dids.replaceAll("\\[", "").replaceAll("\\]", "");
		String[] strs = dids.split(",");
		System.out.println(agentLog);

    }
    
    public static String doPost(String url,JSONObject json){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		net.sf.json.JSONObject response = null;
		try {
			//注意：必须要加在此,"UTF-8"，进行指明，否则出错。
			StringEntity s = new StringEntity(json.toString(),"UTF-8");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");//发送json数据需要设置contentType
			post.setEntity(s);
			post.setHeader("Content-Type", "application/json");
			HttpResponse res = client.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				response = net.sf.json.JSONObject.fromObject(result);
				
				return response.toString();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}