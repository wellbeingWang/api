<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body style="margin: 0; padding: 0;">

	<script type="text/javascript" language="javascript">
		function iFrameHeight() {
			var ifm = document.getElementById("iframepage");
			var subWeb = document.frames ? document.frames["iframepage"].document
					: ifm.contentDocument;
			if (ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
				ifm.width = subWeb.body.scrollWidth;
			}
		}
	</script>
     <a href="" target="iframepage">
         <img src="" />
     </a>
 	<iframe src="" id="iframepage" frameborder="0" scrolling="no"
		marginheight="0" marginwidth="0" onLoad="iFrameHeight()"></iframe>

</body>

</html>