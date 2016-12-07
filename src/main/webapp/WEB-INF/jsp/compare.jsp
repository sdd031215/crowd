<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%-- <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  

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
                    <li ><a href="/">日志解析</a></li>
                    <li class="active"><a href="/comare">结果比较</a></li>
                </ul>
                <span style="float:right"> eLong</span>
            </div>
            
        </div>
        
    </nav>
	<div class="container">
		 
        <div class="row row-offcanvas row-offcanvas-right ">
            <div class="col-xs-12 col-sm-12">
				<div style="z-index:9999;left:20px;float: left;  top: 60px;">
					<!-- <form action="/rerankLog" method="post">	 -->
						<div class="container">
							<h3>比较结果：</h3>
							
							${result }
						</div>
					<!-- </form> -->
				</div>
			</div>
		</div>
	</div>
	
</body>

</html>
