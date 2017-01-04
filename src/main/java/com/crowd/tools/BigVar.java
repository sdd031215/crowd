package com.crowd.tools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 存储各个文件处理后的内容
 * @author user
 *
 */
@Component
public class BigVar {

	public static Map<String, String> hid2area = new HashMap<String, String>();
	public static Map<String, String> hid2city = new HashMap<String, String>();
	public static Map<String, String> hid2star = new HashMap<String, String>();
	public static Map<String, String> hid2group = new HashMap<String, String>();
	public static Map<String, String> hid2iseoc = new HashMap<String, String>();
	public static Map<String, Map> user2cvthid = new HashMap<String, Map>();
	public static Map<String, String> feat2w = new HashMap<String, String>();
	
	@Value("${rerank.monitor.path:/home/hotel-revenue/dong.shao/rerank/test/}")
	//private String path = "/home/hotel-revenue/dong.shao/rerank/test/";
	private String path = "";
	//private static BigVar bigVar = new BigVar();
	
	public BigVar(){
		LogUtils.logInfo(path+"---------");
		//启动一个线程，监听日志文件的内容
		new Thread(new MonitorThread(path)).start();
		
	}
}
