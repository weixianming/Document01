<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
(function(){
window.onload = init;
function init(){
	var btn = document.getElementById("btn1");
	btn.onclick = show;
}

function show(){
	var xhr = createXMLHttpRequest();
	
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4&&xhr.status==200){
			var txt = xhr.responseText;
			var ta = document.getElementById("testajxa");
			ta.innerHTML = txt;
			
		}
	}
	xhr.open("GET","test.txt",true);
	xhr.send();
}

function createXMLHttpRequest() {
	if(window.XMLHttpRequest) {
		//针对其他主流浏览器
		return new XMLHttpRequest();
	} else if(window.ActiveXObject) {
		//针对IE5和IE6
		return new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		alert("你使用的浏览器不支持XMLHttpRequest，请换一个浏览器再试！");
		return null;
	}
}
})();
</script>
</head>
<body>
<div id="testajxa"></div>
<input type="button" id="btn1" value="测试">
</body>
</html>