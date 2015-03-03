<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%-- <script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery-1.9.1.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/jquery.dataTables.js"></script>
	<script type="text/javascript"
	src="<%=request.getContextPath()%>/scripts/dataTables.scrollingPagination.js"></script> --%>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
</head>
<style>
	table td, table th{
		border: 1px solid #ccc;
	}
</style>
<body>
<h1>Recent Events</h1>
<table id="recentEvent" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border: 1px solid #dadada;">
					<thead>
						<tr>
						    <th style="text-align: center;" width="160">Event Type</th>
						    <th style="text-align: center;" width="160">Value1</th>
							<th style="text-align: center;" width="160">Value2</th>
						</tr>
						
					</thead>
					<tbody id="content">
					</tbody>
				</table>
				<a onclick="loadPrevData()">Previous</a>&nbsp&nbsp
				<a onclick="loadNextData()">Next</a>
</body>
<script>
var lastEventId = '';
var firstEventId = '';
var greaterThan = '$gt';
var lessThan = '$lt';
var index = 0;
function loadNextData(){
	$.ajax({
		type : "POST",
		cache : false,
		url : "getRecentEvents.htm?",
		data : "lastEventId="+lastEventId+"&limit="+10+"&compare="+lessThan,
		datatType : 'json',
		success : function (data) {
			var eventList = data.eventList;
			if(eventList.length>0){
				var dataString = '';
				eventList.forEach(function(event){
					dataString = dataString+ '<tr><td>'+"Event"+event.type+'</td><td>'+event.value1+'</td><td>'+event.value2+'</td></tr>';
				});
				$('#content').empty();
				$('#content').append(dataString.toString());
				lastEventId = data.lastEventId;
				firstEventId = data.firstEventId;
		}},
		error : function(data) {
			alert("Error occured code:"+data.code+" Message:"+data.message);
		}
		
	});
}
function loadPrevData(){
	$.ajax({
		type : "POST",
		cache : false,
		url : "getRecentEvents.htm?",
		data : "lastEventId="+firstEventId+"&limit="+10+"&compare="+greaterThan,
		datatType : 'json',
		success : function (data) {
			var eventList = data.eventList;
			if(eventList.length>0){
				var dataString = '';
				eventList.forEach(function(event){
					dataString = dataString+ '<tr><td>'+"Event"+event.type+'</td><td>'+event.value1+'</td><td>'+event.value2+'</td></tr>';
				});
				$('#content').empty();
				$('#content').append(dataString.toString());
				lastEventId = data.lastEventId;
				firstEventId = data.firstEventId;
			}
		},
		error : function(data) {
			alert("Error occured code:"+data.code+" Message:"+data.message);
		}
	});
}

$(document).ready(function(){
	loadNextData();
});
</script>
</html>