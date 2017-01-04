package com.crowd.mall.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crowd.mall.service.CityService;

@Controller
public class RerankLogR1Controller {

	@Autowired
	private CityService cityService;

	@GetMapping("/")
	@ResponseBody
	@Transactional(readOnly = true)
	public String helloWorld() {
		return this.cityService.getCity("Bath", "UK").getName();
	}

	
	@GetMapping(value={"/r","/R1"})
	public String R1(Map<String, Object> model) {
		//model.put("time", new Date());
		return "R1";
	}
	
	@GetMapping("/compareR1")
	public String compareR1(Map<String, Object> model) {
		//目标页面
    	String url =  "http://sa-view.vip.elong.com/monitor/rerankTroubleShooting";
    	
		return "compareR1";
	}

	@PostMapping("/rerankLogR1")
	@ResponseBody
	public String rerankHandler(@Valid String log) {
		// LogUtils.logDebug(log);


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

}
