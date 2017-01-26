<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
		#allmap{height:500px;width:100%;}
		#r-result{width:100%; font-size:14px;}
	</style>
	<title>城市名定位</title>
</head>
<body>
	<div id="r-result">
		经度: <input id="longitude" type="text" value="116.1627" style="width:100px; margin-right:10px;">
		纬度: <input id="latitude" type="text" value="39.5730" style="width:100px; margin-right:10px;">
		<input type="button" value="查询地址" onclick="theLocation()" />
		<input type="button" value="查询经纬度" onclick="getPositionSuccess()" />
	</div>
</body>
<script type="text/javascript">
	//H5获取当前经纬度
	//position_option json格式数据，获取经纬度的参数
	var position_option = {
                enableHighAccuracy: true,
                maximumAge: 30000,
                timeout: 20000
            };
	//获取到包含经纬度的对象
	navigator.geolocation.getCurrentPosition(getPositionSuccess, getPositionError, position_option);
	//获取成功时执行的方法
	function getPositionSuccess( position ){
        var lat = position.coords.latitude;
        var lng = position.coords.longitude;
        alert( "您所在的位置： 纬度" + lat + "，经度" + lng );
        var $info = position.coords;
        console.info($info);
        //如果地址信息不为空，输出国家、省、市
        if(typeof position.address !== "undefined"){
                var country = position.address.country;
                var province = position.address.region;
                var city = position.address.city;
                alert(' 您位于 ' + country + province + '省' + city +'市');
        }
	}
	//获取失败时执行的方法
	function getPositionError(error) {
	    switch (error.code) {
	        case error.TIMEOUT:
	            alert("连接超时，请重试");
	            break;
	        case error.PERMISSION_DENIED:
	            alert("您拒绝了使用位置共享服务，查询已取消");
	            break;
	        case error.POSITION_UNAVAILABLE:
	            alert("获取位置信息失败");
	            break;
	    }
	}
	// 用经纬度设置地图中心点
	function theLocation(){
		if(document.getElementById("longitude").value != "" && document.getElementById("latitude").value != ""){
			//根据经纬得到地址信息  需要从JSON里获取
			window.location.href="http://api.map.baidu.com/geocoder/v2/?ak=DGyeCzXVqrSBMOGdmE7HXGxQ49vx2AvT&callback=renderReverse&location="+document.getElementById("longitude").value+","+document.getElementById("latitude").value+"&output=json&pois=1";
		}
	}
</script>
</html>
