<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><!DOCTYPE html>
<html ng-app="jmwywApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>管理后台</title>
<link rel="stylesheet" href="calendar/bootstrap.min.css">
<link rel="stylesheet" href="calendar/fonts.css">
<link rel="stylesheet" href="calendar/font-awesome.min.css">
<link rel="stylesheet" href="calendar/fullcalendar.css">
<link rel="stylesheet" href="calendar/jquery.gritter.css">
<link rel="stylesheet" href="calendar/style.css">

<script type="text/javascript" src="/js/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function(){
		$("#signin").text('${signintime==1?"已签到":"未签到"}');
	});
	function signIn(){
		if($("#signin").text()=='未签到')
		$.ajax({
			url : '/APP/signIn',
			type : "POST",
			data : {
				id : $("#id").val(),
			},
			success : function(result) {
				if(result.success)
				$("#signin").text('已签到');
			}
		});
	}
</script>
</head>
  <body>
	<div id="wrapper">
						<div class="row">
							<div class="col-lg-12">
							
								<!-- START YOUR CONTENT HERE -->
			<div class="row">
				<div class="col-sm-9">
					<div class="well white">
						<div id="calendar"></div>
					</div>
				</div>

			</div>
				</div><!-- /#page-wrapper -->	  
		</div>  
	</div> 
	 
    <!-- core JavaScript -->
    <script src="bootstrapJS/jquery.min.js"></script>
    <script src="bootstrapJS/bootstrap.min.js"></script>
	<script src="bootstrapJS/fullcalendar.min.js"></script>
	<script src="bootstrapJS/fullcalendar.init.js"></script>
  </body>
