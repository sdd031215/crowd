package com.crowd.tools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class MyStartupRunner  implements CommandLineRunner{
	
	//@Value("${rerank.monitor.path:/home/hotel-revenuetest/dong.shao/rerank/test}")
	@Value("${rerank.monitor.path:/home/hotel-revenue/dong.shao/rerank/test/}")
	private String path = "";
	public void run(String... arg0) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>service boot start<<<<<<<<<<<<<");
		
		MonitorThread.readBigFile_hotel(path+"/hotel_revenue_hotel_info_dwd");
		MonitorThread.readBigFile_cvt(path+"/user_hist_cvthotel_info");
		MonitorThread.readBigFile_weight(path+"/cvr_feat_weight");
	}
}
