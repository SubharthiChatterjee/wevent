
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
<style>
table td{
	padding: 5%;
}
	table td input[type="button"]{
		background: #ccc;
		padding: 3% 6%;
		border: 1px solid #dadada;
		color: #333;
		font-size: 25px;
		cursor: pointer;
	}
</style>
<script>
$(document).ready(function(){
$("#event1").click(function(){
		eventClicked(1);
	});
$("#event2").click(function(){
	eventClicked(2);
});
$("#event3").click(function(){
	eventClicked(3);
});
$("#event4").click(function(){
	eventClicked(4);
});

});

function eventClicked(event) {
	alert( event + ' clicked');
		$.ajax( {
	        type: "POST",
	        url: 'addEvent.htm?eventType='+event,
	        success: function(data,textStatus,jqXhr) {
	            // var response = $.parseJSON(data);
	            var response = data;
	            if (response.code == 0) {
					alert(response.message);
				}else{
					alert("Event Added");
				}
	        }
	});
};
</script>
</head>


<body>
	<h1 >Home</h1>
	<table width="100%" cellpadding="10" cellspacing="10" border="0">
		<tr>
			<td align="center">
				<input id="event1" type="button" value="Trigger Event 1"/>
			</td>
			<td align="center">
				<input id="event2" type="button" value="Trigger Event 2" />
			</td>
		</tr>
		
		<tr>
			<td align="center">
				<input id="event3" type="button" value="Trigger Event 3" />
			</td>
			<td align="center">
				<input id="event4" type="button" value="Trigger Event 4" />
			</td>
		</tr>
		
	</table>
	
	<br><a href="<%=request.getContextPath()%>/getRecentEventsPage.htm">Recent Events</a><br>
	<br><a href="<%=request.getContextPath()%>/getTopEventsPage.htm">Top Events</a><br>
	<br><a href="<%=request.getContextPath()%>/getEventsCountPage.htm">Events Count</a>

</body>
</html>