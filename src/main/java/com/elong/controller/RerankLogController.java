package com.elong.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.validation.Valid;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elong.domain.HotelInfo;
import com.elong.domain.WeightScore;
import com.elong.tools.BigVar;
import com.elong.tools.HttpRequest;
import com.elong.tools.LogUtils;
import com.elong.tools.PostTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class RerankLogController {

	@GetMapping("/rerank")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		return "welcome";
	}
	
	@GetMapping("/compare")
	public String compare(Map<String, Object> model) {
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
	    	LocalDate today = LocalDate.now();
	    	jo.put("checkIn", today.plusDays(-1));
	    	jo.put("checkOut", today);
	    	jo.put("serviceSeq", 1);
	    	jo.put("districtType", 0);
	    	jo.put("keyWord", "五道口");
	    	jo.put("requestType", 0);
	    	jo.put("serviceSeq", 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    	String response = PostTest.doPost(url, jo);
    	net.sf.json.JSONObject poststr = net.sf.json.JSONObject.fromObject(response);
    	String rr =  poststr.get("rerankDebugResponse").toString();
    	LogUtils.logInfo("rr:"+rr);
    	String agentLog = net.sf.json.JSONObject.fromObject(poststr).get("agentLog").toString();
		LogUtils.logInfo("agentLog:"+agentLog);
    	String dids =  net.sf.json.JSONObject.fromObject(rr).get("dids").toString();
		dids = dids.replaceAll("\\[", "").replaceAll("\\]", "");
		String[] strs = dids.split(",");
		
		String myResult = rerankHandler(agentLog);
		net.sf.json.JSONObject ss = net.sf.json.JSONObject.fromObject(myResult);
		net.sf.json.JSONArray arr = (net.sf.json.JSONArray) ss.get("hotels");
		boolean flag = true;
		for(int i =0; i< strs.length; i++){
			String sjson = arr.get(i).toString();
			net.sf.json.JSONObject sj = net.sf.json.JSONObject.fromObject(sjson);
			if(! strs[i].equals(sj.get("hid"))){
				flag = false;
				break;
			}
		}
		
		LogUtils.logInfo(agentLog);
		StringBuilder sb = new StringBuilder();
		sb.append("请求参数:");
		sb.append("{\"checkIn\":\"2016-12-06\",\"requestType\":0,\"channel\":2,\"serviceSeq\":0,\"districtType\":0,\"bucketId\":\"r1\",\"cityId\":101,\"checkOut\":\"2016-12-07\",\"deviceId\":\"AF3ADF18-3465-4B95-AEF3-028B23069D51\",\"keyWord\":\"五道口\"}");
		sb.append("<br>");
		sb.append("<font color=red>");
		
		sb.append("结论:").append(flag==true?"结果一致":"结果不一致");
		sb.append("</font>");
		sb.append("<br>");
//		if(flag==false){
		sb.append("离线结果：").append(dids);
		sb.append("<br>");
		sb.append("在线结果：").append(arr.toString());
//		}
		
		sb.append("<br>").append("<br>").append("<br>");
		sb.append("日志为：").append("<br>");
		sb.append(agentLog);
		
		sb.append("<br>");
		sb.append("直接copy上面日志到《日志解析》用不了，因为解析日志时，用的是tab分割的。");
		
		model.put("result", sb.toString());
		
		return "compare";
	}

	@PostMapping("/rerankLog")
	@ResponseBody
	public String rerankHandler(@Valid String log) {
		// LogUtils.logInfo(log);

		// 从cache中读取相关的数据，然后计算
		// 其他地方维护着两个变量，这里是进行实时的处理
		String[] strs = log.trim().split("\t");
		if(strs.length< 5)
			strs = log.trim().split("    ");
		
		String id = strs[0];
		String logtype = strs[1];
		if(!logtype.equals("1"))	return "logtype!=1";
		//if(!id.contains("rerank_1_bucket_2"))	return " !=rerank_1_bucket_2 ";
		
		String deviceid = strs[3];
		String listtime = strs[6];
		if(strs.length>=13){
			listtime = strs[7];
		}
		String request = strs[8];
		LogUtils.logInfo(request);
		
		
		int wz = 8;
		if(request.length() < 100){
			wz = 9;
			request = strs[wz++];
		}
		//临时这么处理，json对象中套json串？？？？
		request =  request.replaceAll("\\\\", "")
				.replaceAll("mvt_strategy\":\"", "mvt_strategy\": ")
				.replaceAll("\",\"need7daygift", ",\"need7daygift").replaceAll("\n", "");
		//LogUtils.logInfo(request);
		
		String mhotelidlists = "";
		try{
			JSONObject obj = new JSONObject(request);
			String user_info =  obj.getString("user_info");
			JSONObject obj2 = new JSONObject(user_info);
			try{
				String os = obj2.getString("os");
				if("Html5Wap".equals(os))  return "Html5Wap";
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String caller_attr =  obj.getString("caller_attr");
			if(caller_attr == null) return "caller_attr is null";
			JSONObject obj3 = new JSONObject(user_info);
			
			String SearchFrom = "";
			try{
			 SearchFrom = obj3.getString("SearchFrom");
			}catch(Exception e){
				//if(SearchFrom == null) return "SearchFrom is null";
			}
			
			//if("2".equals(SearchFrom)) return "SearchFrom != 2";
			
			String response = strs[wz];
			LogUtils.logInfo(response);
			response = response.replaceAll("\\\\", "").replaceAll("\n", "");
			LogUtils.logInfo("response:::"+response);
			
			//JSONObject objresponse = new JSONObject(response);
			JSONArray objarr = new JSONArray(response);
			//JSONArray objarr = objresponse.getJSONArray(response);  
			if(objarr.length() == 0)	return "response = 0";
			
			
			for (int i = 0; i < objarr.length(); i++) {  
			    JSONObject temp = new JSONObject(objarr.getString(i));  
			    String mhotelId = Integer.parseInt(temp.getString("mhotelId")) + "";
			    String minPrice = temp.getString("minPrice");
			    String distance = temp.getString("distance");
			    String hotelFlags = temp.getString("hotelFlags");
			    String[] ffs = hotelFlags.split(",");
			    String flags = "";
			    for(String f:ffs){
			    	flags += f + "^D" ;
			    }
			    flags = flags.substring(0, flags.length()-2);
			    
			    String dist = (distance!=null?(distance+""):"");
			    String mhotelidlist = mhotelId + "," + minPrice + "," + dist + "," + flags;
			    mhotelidlists += mhotelidlist + ";";
			}  
		}catch(Exception e){
			e.printStackTrace();
			LogUtils.logError("解析 request，response 出错");
		}
		mhotelidlists = mhotelidlists.substring(0, mhotelidlists.length()-1);
		
		String hidlist = mhotelidlists;
		String content = log.trim();
		String executetime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		try {
			Date d = sdf.parse(listtime);
			executetime = sdf2.format(d);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//String executetime = listtime;
		String[] hidlists = hidlist.split(";");
		int listday = Integer.parseInt(executetime.substring(0, 8));
		listtime = executetime.substring(0, 8);
		// listtime = LocalDate.parse(listtime,
		// DateTimeFormatter.ofPattern("yyyyMMdd"));
		int querylength = hidlists.length;

		List<String> mhidlist = new ArrayList<String>();
		Map<String, Float> hid2dist = new TreeMap<String, Float>();
		Map<String, Float> hid2price = new TreeMap<String, Float>();

		// 组装数据
		for (int i = 0; i < hidlists.length; i++) {
			String[] hiss = hidlists[i].split(",");
			String hid = hiss[0];
			String price = hiss[1];
			String dist = hiss[2];

			if (dist.compareTo("0") > 0) {
				hid2dist.put(hid, Float.parseFloat(dist));
			}

			mhidlist.add(hid);
			hid2price.put(hid, Float.parseFloat(price));
		}

		// 对酒店距离排序
		Map<String, String> hid2distrank = new LinkedHashMap<String, String>();
		ArrayList<Map.Entry<String, Float>> entitys = getSortedHashtableByValue(hid2dist,true);

		String maxdisthid = "";
		if (hid2dist.size() != 0) {
			int idx = 0;
			for (Map.Entry<String, Float> entity : entitys) {
				if (idx == entitys.size() - 1)
					maxdisthid = entity.getKey();
				LogUtils.logInfo(entity.getKey() + " = " + entity.getValue());
				hid2distrank.put(entity.getKey(), idx++ + "");

			}
		}

		// 对酒店价格排序
		int idx = 0;
		String maxpricehid = "";
		String minprice = "";
		Map<String, String> hid2pricerank = new LinkedHashMap<String, String>();

		ArrayList<Map.Entry<String, Float>> entityprices = getSortedHashtableByValue(hid2price,true);
		for (Map.Entry<String, Float> entity : entityprices) {
			if (idx == 0)
				minprice = entity.getKey();
			if (idx == hid2price.size() - 1)
				maxpricehid = entity.getKey();

			LogUtils.logInfo(entity.getKey() + " = " + entity.getValue());
			hid2pricerank.put(entity.getKey(), idx++ + "");
		}

		/*
		 * 计算分数变量定义
		 */
		Map<String, Map> inst2score = new HashMap<String, Map>();
		Map<String, Map> inst2y = new HashMap<String, Map>();
		
		// 假设最远距离，超过都为这个值
		int maxdist = 10;

		for (int j = 0; j < hidlists.length; j++) {
			String[] hiddetaillist = hidlists[j].split(",");
			String hid = hiddetaillist[0];
			int price = Integer.parseInt(hiddetaillist[1]);
			float dist = (float) (Float.parseFloat(hiddetaillist[2]) / 1000.0);

			// 组装feature
			Map<String, String> queryfeat = new HashMap<String, String>();
			Map<String, String> hotelfeat = new HashMap<String, String>();
			Map<String, String> contextfeat = new HashMap<String, String>();

			for (String chid : mhidlist) {
				queryfeat.put("qry_" + chid, "1");
			}

			//contextfeat.put("rank_" + j, "1");
			contextfeat.put("rank_default", "1");
			contextfeat.put("F0", "1");
			//contextfeat.put("rank_" + j, BigVar.feat2w.get("rank_default"));

			// 判断是否是周末
			int weekday = -1;
			try {
				weekday = isoweekday(listtime);
			} catch (ParseException e) {
				LogUtils.logError(e.getMessage());  
				e.printStackTrace();
			}
			contextfeat.put("weekday_" + weekday, "1");
			contextfeat.put("hidweekday_" + weekday + "_" + hid, "1");
			hotelfeat.put("hid_" + hid, "1");

			if (hiddetaillist.length == 4) {
				String[] flaglist = hiddetaillist[3].split("\\^D");
				for (String flag : flaglist) {
					if (flag.length() == 0)
						continue;
					//flag = flag.replace("^D", "");
					hotelfeat.put("hid_flag_" + flag, "1");
				}
			}

			if (dist >= maxdist)
				dist = maxdist;
			if (dist > 0) {
				hotelfeat.put("dist_hid", Math.log(dist + 1) + "");
				hotelfeat.put("dist_rank_" + hid2distrank.get(hid), "1");

				if (hid.equals(maxdisthid)) {
					hotelfeat.put("dist_max", "1");
				}

				if (BigVar.hid2city.get(hid) != null) {
					String city = BigVar.hid2city.get(hid);
					hotelfeat.put("dist_hid_city_" + city, Math.log(dist + 1) + "");
					hotelfeat.put("dist_rank_city_" + city + "_" + hid2distrank.get(hid), "1");
				}
			}

			// 价格数据
			hotelfeat.put("price_hid", Math.log(price + 1) + "");
			hotelfeat.put("price_rank_" + hid2pricerank.get(hid), "1");
			if (hid.equals(maxpricehid))
				hotelfeat.put("price_max", "1");
			if (hid.equals(minprice))
				hotelfeat.put("price_min", "1");
			if (BigVar.hid2city.get(hid) != null) {
				String city = BigVar.hid2city.get(hid);
				hotelfeat.put("price_hid_city_" + city, Math.log(price + 1) + "");
				hotelfeat.put("price_rank_city_" + city + "_" + hid2pricerank.get(hid), "1");
			}

			Map<String, String> pairwisefeat = new HashMap<String, String>();
			for (String chid : mhidlist) {
				if (chid.equals(hid))
					continue;

				pairwisefeat.put("pair_" + chid + "_" + hid, "1");
			}

			Set<String> histcvthid = new HashSet<String>();
			Set<String> histstarset = new HashSet<String>();
			Set<String> histgroupset = new HashSet<String>();
			List<String> histpricelist = new ArrayList<String>();
			List<String> samecityhidlist = new ArrayList<String>();

			if (BigVar.user2cvthid.get(deviceid) != null) {
				Map<String, Map> itemMap = BigVar.user2cvthid.get(deviceid);
				for (Map.Entry<String, Map> mm : itemMap.entrySet()) {
					String k = mm.getKey();
					int histlistday = Integer.parseInt(k.substring(0, 8));
					Map<String, Map> items = mm.getValue();
					// 一个或多个以listtime为key的
					if (histlistday >= listday)
						continue;
					Map<String, Integer> cvthid2cnt = mm.getValue();

					for (Map.Entry<String, Integer> kk : cvthid2cnt.entrySet()) {

						String cvthid = kk.getKey();
						int histprice = kk.getValue();

						histcvthid.add(cvthid);
						if (BigVar.hid2star.get(cvthid) != null) {
							histstarset.add(BigVar.hid2star.get(cvthid));
						}
						if (BigVar.hid2group.get(cvthid) != null) {
							histgroupset.add(BigVar.hid2group.get(cvthid));
						}

						// String histprice = cvthid2cnt.get(cvthid); //????
						histpricelist.add(histprice + "");
						if (BigVar.hid2city.get(cvthid) != null && BigVar.hid2city.get(hid) != null
								&& BigVar.hid2city.get(hid).equals(BigVar.hid2city.get(cvthid))) {

							samecityhidlist.add(histprice + "");
						}
					}
				}
			}

			float avgprice = 0;
			List<String> agvpricelist = new ArrayList<String>();
			if (histpricelist.size() != 0) {
				float histpricelistSum = 0;
				for (String price1 : histpricelist)
					histpricelistSum += Float.parseFloat(price1);
				avgprice = (float) (histpricelistSum * 1.0 / histpricelist.size());
				agvpricelist.add(avgprice+"");
			}

			if (samecityhidlist.size() != 0) {
				float histpricelistSum = 0;
				for (String price1 : samecityhidlist)
					histpricelistSum += Float.parseFloat(price1);
				avgprice = (float) (histpricelistSum * 1.0 / histpricelist.size());
				agvpricelist.add(avgprice+"");
			}

			for (int i = 0; i < agvpricelist.size(); i++) {
				avgprice = Float.parseFloat(agvpricelist.get(i));
				if (avgprice > 0) {
					float pricevar = (float) (price * 1.0 / avgprice);
					hotelfeat.put("pricevar1_" + i, Math.log(pricevar + 1.0) + "");
					pricevar = (float) ((Math.abs(price - avgprice) + 1) * 1.0 / avgprice);
					hotelfeat.put("pricevar2_" + i, Math.log(pricevar + 1.0) + "");
					pricevar = (float) Math.abs(price - avgprice) + 1;
					hotelfeat.put("pricevar3_" + i, Math.log(pricevar + 1.0) + "");
				}
			}

			if (histgroupset.size() != 0) {
				// BigVar.hid2group.get(key)
				for (Map.Entry<String, String> kk : BigVar.hid2group.entrySet()) {
					if ( hid.equals(kk.getKey()) && !"".equals(kk.getValue().trim())   && histgroupset.contains(kk.getValue().trim())) {
						LogUtils.logInfo(kk.getValue().trim()+"=====group_match=======kk.getValue===========");
						hotelfeat.put("group_match", "1");
						break;
					}
				}
			}
			if (histstarset.size() != 0) {
				for (Map.Entry<String, String> kk : BigVar.hid2star.entrySet()) {
					if ( hid.equals(kk.getKey()) && !"".equals(kk.getValue().trim()) && !"0".equals(kk.getValue().trim()) && histstarset.contains(kk.getValue().trim())) {
						LogUtils.logInfo(kk.getValue().trim()+"=====star_match=======kk.getValue===========");
						hotelfeat.put("star_match", "1");
						break;
					}
				}
			}

			// 是否下单成功
			if (histcvthid.contains(hid)) {
				pairwisefeat.put("hist_histcvt", "1");
			}

			for (String cvthid : histcvthid) {
				pairwisefeat.put("rec_" + cvthid + "_" + hid, "1");
			}

			// 组装最后的输出
			StringBuffer sb = new StringBuffer(300);
			sb.append(id + "*" + deviceid + "*" + executetime + "*" + j + "*" + hid + "\t");
			for (Map.Entry<String, String> kk : queryfeat.entrySet()) {
				sb.append(kk.getKey()).append(":").append(kk.getValue()).append("\t");
			}

			for (Map.Entry<String, String> kk : hotelfeat.entrySet()) {
				sb.append(kk.getKey()).append(":").append(kk.getValue()).append("\t");
			}

			for (Map.Entry<String, String> kk : contextfeat.entrySet()) {
				sb.append(kk.getKey()).append(":").append(kk.getValue()).append("\t");
			}

			for (Map.Entry<String, String> kk : pairwisefeat.entrySet()) {
				sb.append(kk.getKey()).append(":").append(kk.getValue()).append("\t");
			}

			LogUtils.logInfo(sb.toString());
			
			/*
			 * 计算分数
			 */
//			Map<String, Map> inst2score = new HashMap<String, Map>();
//			Map<String, Map> inst2y = new HashMap<String, Map>();
			
			String inst = id+"*"+deviceid+"*"+executetime;
			//StringBuilder sbuild = new StringBuilder();
			
			float score = 0;
			for (Map.Entry<String, String> kk : queryfeat.entrySet()) {
				if(BigVar.feat2w.get(kk.getKey())!=null){
					//sbuild.append(kk.getKey());
					score += Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue());
					if(hid.equals("40101687"))
							LogUtils.logInfo(hid + "--" + kk.getKey() + ":" + BigVar.feat2w.get(kk.getKey())+"*"+ Float.parseFloat(kk.getValue())+"="+(Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue())));
				}
			}
			for (Map.Entry<String, String> kk : hotelfeat.entrySet()) {
				if(BigVar.feat2w.get(kk.getKey())!=null){
					//sbuild.append(kk.getKey());
					score += Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue());
					if(hid.equals("40101687"))
						LogUtils.logInfo(hid + "--"  + kk.getKey() + ":" + BigVar.feat2w.get(kk.getKey())+"*"+ Float.parseFloat(kk.getValue())+"="+(Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue())));

				}
			}
			for (Map.Entry<String, String> kk : contextfeat.entrySet()) {
				if(BigVar.feat2w.get(kk.getKey())!=null){
					//sbuild.append(kk.getKey());
					if(("rank_" + j).equals(kk.getKey())){
						LogUtils.logInfo(("rank_" + j)+"===============================");
					}
					score += Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue());
					if(hid.equals("40101687"))
						LogUtils.logInfo(hid + "--"  + kk.getKey() + ":" + BigVar.feat2w.get(kk.getKey())+"*"+ Float.parseFloat(kk.getValue())+"="+(Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue())));

				}
			}
			for (Map.Entry<String, String> kk : pairwisefeat.entrySet()) {
				if(BigVar.feat2w.get(kk.getKey())!=null){
					//sbuild.append(kk.getKey());
					score += Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue());
					if(hid.equals("40101687"))
						LogUtils.logInfo(hid + "--"  + kk.getKey() + ":" + BigVar.feat2w.get(kk.getKey())+"*"+ Float.parseFloat(kk.getValue())+"="+(Float.parseFloat(BigVar.feat2w.get(kk.getKey())) * Float.parseFloat(kk.getValue())));

				}
			}
			
			if(inst2score.get(inst) != null){
				Map<String,Float> km = inst2score.get(inst);
				inst2score.remove(inst);
				km.put(hid+";"+price , score);
				inst2score.put(inst, km);
				
			}else{
				Map<String,Float> km = new HashMap<String, Float>();
				km.put(hid+";"+price , score);
				inst2score.put(inst, km);
			}
		}
		
		StringBuffer sb2 = new StringBuffer(300);
		for(String inst2:inst2score.keySet()){
//			sb2.append(inst2).append("\t").append("");
			WeightScore ws = new WeightScore();
			ws.setId(inst2);
			List<HotelInfo> his = new ArrayList<HotelInfo>();
			
			Map<String,String> instmap = inst2score.get(inst2);
			
			//Map<String, Float> hidinstscore = new LinkedHashMap<String, Float>();
			ArrayList<Map.Entry<String, Float>> enti = getSortedHashtableByValue(instmap,false);
			
			for (Map.Entry<String, Float> entity : enti) {
				
				LogUtils.logInfo(entity.getKey() + " = " + entity.getValue());
				//hidinstscore.put(entity.getKey(), entity.getValue());
				
				HotelInfo hi = new HotelInfo();
				String[] hikey = entity.getKey().split(";");
				hi.setHid(hikey[0] );
				hi.setScore(entity.getValue());
				hi.setPrice(hikey[1]);
				
				his.add(hi);
			}
			ws.setHotels(his);
			
			ObjectMapper mapper = new ObjectMapper(); //转换器  
	          
	        String json = null;
			try {
				json = mapper.writeValueAsString(ws);
			} catch (JsonProcessingException e) {
				LogUtils.logError(e.getMessage());  
				e.printStackTrace();
			} //将对象转换成json  
			LogUtils.logInfo(json);
	        return json;
		}
			
			
			/*
			 * 
	for inst in inst2score :
        print inst, "\t".join([x[0]+":"+str(x[1])+":"+inst2y[inst][x[0]] 
        	for x in sorted(inst2score[inst].items(), key = lambda x : x[1], reverse = True)])
			 */
			
		

		return "空";
	}

	/**
	 * 判断是否是周末
	 * 
	 * @return
	 * @throws ParseException
	 */
	private static int isoweekday(String bDate) throws ParseException {
		DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		Date bdate = format1.parse(bDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(bdate);
		if (cal.get(Calendar.DAY_OF_WEEK) - 1 == 0)
			return 7;
		return cal.get(Calendar.DAY_OF_WEEK) - 1;

	}

	/**
	 * @param h
	 * @sortBool 从小到大，true；从大到小，false；
	 * 
	 * @return 实现对map按照value升序排序
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList getSortedHashtableByValue(Map h, final boolean sortBool) {
		ArrayList<Map.Entry<String, Float>> l = new ArrayList<Map.Entry<String, Float>>(h.entrySet());
		Collections.sort(l, new Comparator<Map.Entry<String, Float>>() {

			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
				if(sortBool){
					if(Math.abs(o1.getValue() - o2.getValue())<1){
						return (int) ((o1.getValue() - o2.getValue())* 100000);
					}else{
						return (int) (o1.getValue() - o2.getValue());
					}
				}else{
					if(Math.abs(o2.getValue() - o1.getValue())<1){
						return (int) ((o2.getValue() - o1.getValue())* 100000);
					}else{
						return (int) (o2.getValue() - o1.getValue());
					}
					
				}
			}
		});
		return l;
	}

//	public static void main(String[] args) throws ParseException {
//		String str = "abc^Ddef^Dhjk";
//		LogUtils.logInfo(str.split("\\^D").length);
//	}

}
