package com.elong.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

//import org.json.JSONArray;
//import org.json.JSONObject;

public class MonitorThread implements Runnable {
	private String path;

	public MonitorThread() {
	}

	public MonitorThread(String path) {
		this.path = path;
	}

	public void run(){  
		LogUtils.logInfo("---------------》enter monitor《----------------------");
		WatchService watchService;
		try {
			watchService = FileSystems.getDefault().newWatchService();
			Paths.get(path).register(watchService, 
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
			while(true)
			{
				//多长时间检测一次
				try {
					Thread.currentThread().sleep(1000*60*5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				WatchKey key = null;
				try {
					key = watchService.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(WatchEvent<?> event:key.pollEvents())
				{
					LogUtils.logInfo(event.context()+"发生了"+ event.kind() +"事件");
					
					
					
					if("hotel_revenue_hotel_info_dwd".equals(event.context().toString())
							&& ("ENTRY_MODIFY".equals(event.kind().toString()) 
									|| "ENTRY_CREATE".equals(event.kind().toString()))){
						LogUtils.logInfo(event.context()+"发生了"+ event.kind() +"事件");
						
						BigVar.hid2area.clear();
						BigVar.hid2city.clear();
						BigVar.hid2star.clear();
						BigVar.hid2group.clear();
						BigVar.hid2iseoc.clear();
						BigVar.hid2city.clear();
						readBigFile_hotel(path+"/hotel_revenue_hotel_info_dwd");
						//TODO:清空，然后重新加载数据到内存
					}
					
					if("user_hist_cvthotel_info".equals(event.context().toString())
							&& ("ENTRY_MODIFY".equals(event.kind().toString()) 
									|| "ENTRY_CREATE".equals(event.kind().toString()))){
						LogUtils.logInfo(event.context()+"发生了"+ event.kind() +"事件");
						BigVar.user2cvthid.clear();
						readBigFile_cvt(path+"/user_hist_cvthotel_info");
						//TODO:清空，然后重新加载数据到内存
					}
					
					if("cvr_feat_weight".equals(event.context().toString())
							&& ("ENTRY_MODIFY".equals(event.kind().toString()) 
									|| "ENTRY_CREATE".equals(event.kind().toString()))){
						
						LogUtils.logInfo(event.context()+"发生了"+ event.kind() +"事件");
						BigVar.feat2w.clear();
						readBigFile_weight(path+"/cvr_feat_weight");
						//TODO:清空，然后重新加载数据到内存
					}
					
				}
				if(!key.reset())
				{
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
    }

	public static void readBigFile_hotel(String inputFile) {
		long start = System.currentTimeMillis();// 开始时间
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);// 10M缓存

			while (in.ready()) {
				String line = in.readLine();
				line = line.replaceAll("\t", ";&");
				String[] strs = line.split(";&");
				if (strs.length != 15)  continue;
				
				 String s_hotelid = strs[0];
				 String hid = strs[1];
				 String hotel_name = strs[2];
				 String g_group_id = strs[3];
				 String g_group_name = strs[4];
				 String province_id = strs[5];
				 String a_province_name = strs[6];
				 String city_id = strs[7];
				 String a_city_name = strs[8];
				 String area_id = strs[9];
				 String a_area_name = strs[10];
				 String star = strs[11];
				 String m_iseconomic = strs[12];
				 String m_isapartment = strs[13];
				 String m_address = strs[14];
				 
				 hid = hid.replaceFirst("[0]*", "");
				 if(a_area_name != null && !"NULL".equals(a_area_name)){
					 String area = a_city_name.trim() + "_" + a_area_name.trim();
					 BigVar.hid2area.put(hid, area);
					 BigVar.hid2city.put(hid, Integer.parseInt(city_id) + "");
				 }
				 if(star.trim()!=""  )
					 BigVar.hid2star.put(hid, star);
				 
				 if(g_group_id.trim()!=""  )
					 BigVar.hid2group.put(hid, g_group_id);
				 BigVar.hid2iseoc.put(hid, m_iseconomic);
				 //BigVar.hid2city.put(hid, city_id);
				 
			}
			in.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		long end = System.currentTimeMillis();// 结束时间
		LogUtils.logError("readBigFile_hotel，总共耗时：" + (end - start) + "ms" + ",文件hotel_revenue_hotel_info_dwd，BigVar.hid2star个数"+ BigVar.hid2star.size());
	}
	
	public static void readBigFile_cvt(String inputFile) {
		long start = System.currentTimeMillis();// 开始时间
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);// 10M缓存

			while (in.ready()) {
				String line = in.readLine();
				String[] strs = line.split(" ");
				if (strs.length < 4)  continue;
				
				String deviceid = strs[0];
				String listtime = strs[1];
				String cvthid = strs[2];
				Integer price = Integer.parseInt(strs[3]);
				
				//价格为0的过滤掉
				if(price == 0) continue;
				
				if(deviceid == "" ||deviceid == "||" || listtime == "" || cvthid=="" ){
					LogUtils.logInfo(line);
				}
				
				//拼装成python三维数组
				Map deviceidMap = BigVar.user2cvthid.get(deviceid);
				if(deviceidMap != null){
					Map listtimeMap = (Map) deviceidMap.get(listtime);
					if(listtimeMap != null){
						listtimeMap.put(cvthid, price);
						deviceidMap.remove(listtime);
						deviceidMap.put(listtime, deviceidMap);
						
					}else{
						Map<String, Integer> map =  new HashMap<String, Integer>();
						map.put(cvthid, price);
						deviceidMap.put(listtime, map);
					}
					BigVar.user2cvthid.remove(deviceid);
					BigVar.user2cvthid.put(deviceid, deviceidMap);
				}else{
					Map<String, Integer> map =  new HashMap<String, Integer>();
					map.put(cvthid, price);
					Map<String, Map> map2 =  new HashMap<String, Map>();
					map2.put(listtime, map);
					BigVar.user2cvthid.put(deviceid, map2);
				}
				
				//BigVar.user2cvthid.put(deviceid, map2);
				
			}
			in.close();
			int i = 0;
			int j = 0;
			for (Map.Entry<String, Map> mm : BigVar.user2cvthid.entrySet()) {
				if(mm.getValue().size()>1){
					i += mm.getValue().size();
					j++;
				}
			}
			LogUtils.logInfo(i+"---"+j);
			LogUtils.logInfo(BigVar.user2cvthid.size() + "");

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		long end = System.currentTimeMillis();// 结束时间
		LogUtils.logError("readBigFile_cvt，总共耗时：" + (end - start) + "ms" + ",文件user_hist_cvthotel_info，BigVar.user2cvthid个数"+ BigVar.user2cvthid.size());
	}
	
	public static void readBigFile_weight(String inputFile) {
		long start = System.currentTimeMillis();// 开始时间
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);// 10M缓存

			while (in.ready()) {
				String line = in.readLine();
				String[] strs = line.trim().split(" ");
				if(strs.length < 2) continue;
				BigVar.feat2w.put(strs[0], strs[1]);
			}
			in.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		long end = System.currentTimeMillis();// 结束时间
		LogUtils.logError("readBigFile_weight，总共耗时：" + (end - start) + "ms"+ ",文件cvr_feat_weight，BigVar.feat2w个数"+ BigVar.feat2w.size());
	}
	
//	public static void main(String[] args) {
//		MonitorThread.readBigFile_hotel("/Users/user/Downloads/reranktest/"+"/hotel_revenue_hotel_info_dwd");
//	}
}
