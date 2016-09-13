<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>verifyCode</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="js/jquery-1.4.2.js"></script>
</head>
<body>
	<form action="LoginServlet" id="loginId">
		用户名：<input type="text" name="username"><br> 验证码：<input
			id="verifyCodeId" type="text" name="verifyCode"> <img
			id="verify_img" src="DrawImageServlet" title="点击获取"
			onclick="changeVerifyCode()" style="cursor:pointer;"> <br>
		<input type="button" value="submit" onclick="testVerifyCode()"><br>
	</form>
	<script type="text/javascript">
		var xmlHttpRequest;
		var randomCode;
		function createXmlHttpRequest() {
			try {
				//针对IE5、IE5.5、IE6
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					//针对IE5、IE5.5、IE6
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
					try {
						//针对FireFox、Mozillar、Opera、Safari、IE7、IE8
						xmlHttpRequest = new XMLHttpRequest();//创建XMLHttpRequest对象

						if (xmlHttpRequest.overrideMimeType) {//修正某些浏览器的BUG
							xmlHttpRequest.overrideMimeType("text/xml");
						}
					} catch (e) {
						alert("无法使用ajax");
					}
				}
			}
		}

		function testVerifyCode() {
			createXmlHttpRequest();
			if (xmlHttpRequest != null) {//确定XMLHttpRequest是否创建成功
				var url = "VerifyCodeServlet";
				xmlHttpRequest.open("POST", url, true);//是采用同步还是异步，true为异步
				//post请求要自己设置请求头  注意顺序
				xmlHttpRequest.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded;");

				xmlHttpRequest.onreadystatechange = processRequest; //注册回调函数
				//发送请求
				xmlHttpRequest.send("verifyCode=" + $("#verifyCodeId").val());
			} else {
				alert("不能创建XMLHttpRequest对象实例");
			}
		}
		function processRequest() {
			if (xmlHttpRequest.readyState == 4) {// 判断是否建立连接
				if (xmlHttpRequest.status == 200) {
					var ret = xmlHttpRequest.responseText;
					if (ret == "false") {
						alert("验证码错误");
						return;
					} else if (ret == "true") {
						$("#loginId").submit();
					} else {
						alert("servlet 进入失败");
					}
				} else {
					alert("请求处理返回的数据有错误");
				}
			}
		}
		function changeVerifyCode() {
			var newSrc = "DrawImageServlet?createTypeFlag=ch&random=" + Math.random();
			var verify_img = document.getElementById("verify_img");
			verify_img.setAttribute("src", newSrc);
		}
	</script>
</body>
</html>
