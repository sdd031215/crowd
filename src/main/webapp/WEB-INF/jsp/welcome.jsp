<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> --%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  --%>
<%
try{
%>

<html lang="zh-cn">

<head>
	<meta charset="utf-8">
	<!-- Bootstrap -->
	<link href="../../css/bootstrap.min.css" rel="stylesheet">
	<style type="text/css">
		#showImg {
				width:170px;
				height:25px;
				border:3px solid #C3DAF9;
				position:absolute;
				top:300px;
				left:600px; 
				z-index:10000; 
				background-color:#F7F9FC;
				line-height:25px;
				vertical-align:middle;
				font-size:11pt;
			}
	</style>
</head>
<body>
	<nav class="navbar navbar-fixed-top navbar-inverse">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		            <span class="sr-only">Toggle navigation</span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		            <span class="icon-bar"></span>
		        </button>
                <a class="navbar-brand" href="/">rerank离线工具</a>
            </div>
            <div id="navbar" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/">日志解析</a></li>
                    <li><a href="/compare">结果比较</a></li>
                </ul>
                <span style="float:right"> eLong</span>
            </div>
            
        </div>
        
    </nav>
	<div class="container">
        <div class="row row-offcanvas row-offcanvas-right main-contain">
            <div class="col-xs-12 col-sm-12">
				<div style="z-index:9999;left:0px;float: left;top: 60px;">
					<!-- <form action="/rerankLog" method="post">	 -->
						<div class="container">
							<h3>目前只支持一条日志</h3>
							<%-- <h1>你好，世界！</h1>
							<c:url value="/resources/text.txt" var="url"/>
							<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
							Spring URL: ${springUrl} at ${time}
							<br>
							JSTL URL: ${url}
							<br>
							Message: ${message} --%>
							<br>
							
							<div class="col-xs-10 col-sm-10">
								输入日志：<br>
								<textarea id="log" name="log" class="form-control" rows="12"></textarea>
							</div>
							<div class="col-xs-2 col-sm-2">
								<br><br>
								<button id="comp" type="button" class="btn btn-primary">计  算</button>
								<img id="showImg" src="../../images/loading.gif" alt="正在加载数据,请稍候..."/>
							</div>
							<div class="col-xs-12">
								结 果：<br>
								<textarea id="result1" name="result1" class="form-control" rows="15"></textarea>
							</div>
						</div>
					<!-- </form> -->
				</div>
			</div>
		</div>
	</div>
	
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>
    
    <script type="text/javascript">
		$("#comp").click(function(){
		  	var log = $("#log").val();
		  	console.log(log+"------");
		  	$.ajax({
			  	async: true,
				beforeSend : function () {
					console.log("beforeSend");
					$("#result1").text("");
					ShowDiv();
					$('#comp').attr("disabled","disabled"); 
				},
				complete : function () {
					console.log("complete");
					HiddenDiv();
					$('#comp').attr("disabled",false); 
				},
				type : 'POST' ,
				url : '/rerankLog',
				data : {
					log: log
				},
				success: function (data) {
						console.log(data);
						//console.log(result);
				  		$("#result1").text(data);
						//var obj = JSON.parse(data);
						//var str = JSON.stringify(obj);
						console.log("success");
				}
		  	});
		}); 
		
			//显示加载数据
			function ShowDiv() {
				$("#showImg").show();
			}
			
			//隐藏加载数据
			function HiddenDiv() {
				$("#showImg").hide();
			}
	</script>
</body>

</html>
<%
}catch(Exception e){
	e.printStackTrace();
}
%>