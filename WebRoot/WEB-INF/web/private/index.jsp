<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<meta name='viewport' content='width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no' />
<meta charset="utf-8">
<title>demo签到</title>
<link rel="stylesheet" type="text/css" href="app/css/weui.min.css"/>
<link rel="stylesheet" href="app/style/bootstrap.min.css"/>
<link rel="stylesheet" href="app/font/iconfont.css"/>
<link rel="stylesheet" href="app/style/common.css"/>
<script type="text/javascript" src="app/script/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="app/script/bootstrap.min.js"></script>
</head>
<style>
	.bg{background: url(app/img/bg.png) no-repeat; margin:0 auto; height:380px;background-size:100% 100%; }
</style>
  <body>
      <div class="box" style="padding-top: 20px;">
          <div class="bg-box">
              <div class="bg" id='list'>
              	<div class="siis">
<%-- 					<p><span>${mon}月签到日历</span></p> --%>
					<p><span>签到日历</span></p>
				</div>
				<div class="title">&nbsp;</div>
              </div>
          </div>
      </div>
 <div class="weui_btn_area" ${noact}>
<%--     <a onclick="sign();" class="weui_btn weui_btn_primary" href="javascript:act();">签到　(第${count}天)</a> --%>
    <a class="weui_btn weui_btn_primary" href="javascript:sign();">签到　</a>
</div> 
<!--BEGIN dialog2-->
<div class="weui_dialog_alert" id="dialog2" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">错误</strong></div>
        <div class="weui_dialog_bd" id='errinfo'></div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog primary">确定</a>
        </div>
    </div>
</div>
<!--END dialog2-->
<!--BEGIN toast-->
<div id="toast" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_toast"></i>
        <p class="weui_toast_content">签到+<span id='num'></span></p>
        <input type="hidden" id="openid" value="${openid}">
    </div>
    <div class="weui_uploader_input_wrp">
</div>
<!--end toast-->
<!-- loading toast -->
<div id="loadingToast" class="weui_loading_toast" style="display:none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <div class="weui_loading">
            <div class="weui_loading_leaf weui_loading_leaf_0"></div>
            <div class="weui_loading_leaf weui_loading_leaf_1"></div>
            <div class="weui_loading_leaf weui_loading_leaf_2"></div>
            <div class="weui_loading_leaf weui_loading_leaf_3"></div>
            <div class="weui_loading_leaf weui_loading_leaf_4"></div>
            <div class="weui_loading_leaf weui_loading_leaf_5"></div>
            <div class="weui_loading_leaf weui_loading_leaf_6"></div>
            <div class="weui_loading_leaf weui_loading_leaf_7"></div>
            <div class="weui_loading_leaf weui_loading_leaf_8"></div>
            <div class="weui_loading_leaf weui_loading_leaf_9"></div>
            <div class="weui_loading_leaf weui_loading_leaf_10"></div>
            <div class="weui_loading_leaf weui_loading_leaf_11"></div>
        </div>
        <p class="weui_toast_content">加载中...</p>
    </div>
</div> 
  </body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script>
	wx.config({
		debug: false,${wxConStr}
		jsApiList: ['closeWindow','hideOptionMenu','hideMenuItems','hideAllNonBaseMenuItem']
	});
	wx.ready(function(){
		wx.hideOptionMenu();
	});
// 	console.info('${item}');
// 	var item = ${item};
	
	$(function(){
		$.ajax({
			url : '/APP/day',
			type : "POST",
			data : {
				openid : $("#openid").val(),
			},
			success : function(result) {
				console.info(result.obj);
				var item = result.obj;
		var html = "";
		var zhou = "";
		var isover = 0;
		var test = "<div class='row rl'>" + "</div>"; 
		for ( var z = 0; z < 10; z++ ){
			zhou = "<div class='row rl'>";
			for ( var d = 0; d < 7; d++ ){
				var index = z*7+d;
				if ( index >= item.length ) {
					isover = 1;
					break;
				}
				zhou += addday(item[index].id, item[index].day, item[index].sign, item[index].act, item[index].curr, item[index].s);
			}
			zhou += "</div>";
			if ( zhou != test ) html += zhou;
			if ( isover == 1 ) break;
		}
		$("#list").html( $("#list").html() + html );
			},
			error : function(result){
				alert("加载出错。");
			}
		});
	});
	
	function addday( id, day, sign, act, curr, s ){
		var html = "<div class='mate' onclick='info(" + id + ");'><div>";
		html += "<p class='one'>" + day + "</p><p class='two'>" + sign + "</p>";
		if ( curr == 1 ) html += "<img src='app/img/shang_1.png' alt='' class='hongquan' />";
		if ( act == 1 ) html += "<img src='app/img/shang_2.png' alt='' class='duigou' />";
		if ( s == 1 ) html += "<span class='shang'></span>";
		html += "</div></div>";
		return html;
	}
	
	function act(){
		$('#loadingToast').show();
		$.post("/wxactdayapi/act",{},function(json){
			$('#loadingToast').hide();
			json = JSON.parse(json);
			if ( json.status == 1 ){
				$("#num").html( json.obj );
	            $('#toast').show();
	            setTimeout(function () {
	                window.location.replace( "/wxactday/main" );
	            }, 2000);
			}else{
				$("#errinfo").html( json.msg );
				$('#dialog2').show().on('click', '.weui_btn_dialog', function () {
                   	$('#dialog2').off('click').hide();
               	});
			}
		});
	}
	function sign(){
		$.ajax({
			url : '/APP/signIn',
			type : "POST",
			data : {
				openid : $("#openid").val(),
			},
			success : function(result) {
				alert(result.msg);				
				if(result.success){
					window.location.href="/index";
				}
			}
		});
	}
</script>
</html>

<style>
.bg{background: url(app/img/bg.png) no-repeat; margin:0 auto; height:380px;background-size:100% 100%; }
</style>
<body>
<input type="file" accept="image/jpg,image/jpeg,image/png,image/gif" multiple="">
</body>
</html>
