package com.crowd.mall.domain;

import java.util.List;

public class RerankTroubleShootResponse {
	private String agentLog;  //代理层模拟日志
//    private ReRankResponseWithDebugInfo rerankDebugResponse; //rerank debug结果,会打印所有特征值
//    private ReRankRequest rerankRequest; //rerank请求参数
    private List<HotelShort> v5HotelOrder; //代理层请求酒店结果 
    private List<HotelShort> rerankHotelOrder; //rerank层请求酒店结果
}

 class HotelShort {
    private String name;  //酒店名称
    private int price; //酒店价格
}
