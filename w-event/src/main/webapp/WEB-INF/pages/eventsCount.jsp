<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
</head>
<style>
table td, table th{
		border: 1px solid #ccc;
	}
</style>
<body>
<h1>Events Count</h1>
		<table id="eventCount" width="40%" cellpadding="10" cellspacing="10">
		  <thead>
		  <tr>
		    <th style="text-align: left;" width="160">Event Type</th>
		    <th style="text-align: left;" width="160">Count</th>
		  </tr>
		  </thead>
		</table>
		<br><br>
		<input id="timeInterval" type="radio" name="timeInterval" value="8" checked>Last 8 hours<br>
		<input id="timeInterval" type="radio" name="timeInterval" value="24">Last 24 hours<br>
		<input id="timeInterval" type="radio" name="timeInterval" value="168">Last 7 days	
</body>
<script>
var eventsCountTable;
function populateEventsCount() {
	var timeInterval;
	if(eventsCountTable != undefined) {
		eventsCountTable.fnDestroy();
	}
	eventsCountTable = $('#eventCount').dataTable(
		{
			"bProcessing" : true,
			"bFilter" : false,
			"bRetrieve" : true,
			"bLengthChange" : true,
			"bServerSide" : true,
			"bInfo":false,
			"bPaginate":false,
			"iDisplayLength" : 4,
			"sDom": '<"top"iflp<"clear">>rt<"bottom"if<"paginate_length"lp><"clear">>',
			"sPaginationType" : "full_numbers",
			"sAjaxSource" : "getEventsCount.htm?timeInterval="+$("input[name=timeInterval]:checked").val(),
			"sAjaxDataProp" : "eventList",
			 "oLanguage": {
			      "sEmptyTable": "No events occured",
			      "sZeroRecords": "No events occured"
			 },				
			 "aoColumns" : [
							 { "mDataProp": function(source, type, val) {
								return 'Event' + source.type;
							}
							},
							{
								"bSortable" : false,
								"mDataProp" : function(source, type, val) {
									return source.count;									
								}
							} 
							
					],
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					"dataType" : 'json',
					"type" : "POST",
					"cache" : false,
					"url" : sSource,
					"data" : aoData,
					"success" : function (data) {
						fnCallback(data);
						timeInterval = data.timeInterval;
					}
				});

			}
		});
}

$(document).ready(function(){
	populateEventsCount();
	$('input[name="timeInterval"]:radio').change(function () {
		populateEventsCount();
	});
});


</script>
</html>