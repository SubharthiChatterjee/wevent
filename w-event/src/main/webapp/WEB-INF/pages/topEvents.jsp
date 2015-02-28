<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
</head>
<body>
<table id="topEvent" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border: 1px solid #dadada;">
					<thead>
						<tr>
						    <th style="text-align: center;" width="160">Event Type</th>
						    <th style="text-align: center;" width="160">Value1</th>
						</tr>
					</thead>
					<tbody >
					</tbody>
</table>
<input id="timeInterval" type="radio" name="timeInterval" value="8" checked>Last 8 hours
<br>
<input id="timeInterval" type="radio" name="timeInterval" value="24">Last 24 hours<br>
<input id="timeInterval" type="radio" name="timeInterval" value="168">Last 7 days
</body>
<script>
var lastEventId = '';


var topEventsTable;
function populateRecentEvents() {
	if(topEventsTable != undefined) {
		topEventsTable.fnDestroy();
	}
	topEventsTable = $('#topEvent').dataTable(
		{
			"bProcessing" : true,
			"bFilter" : false,
			"bRetrieve" : true,
			"bLengthChange" : true,
			"aPaginationLengths": [15,25,50,100],
			"bServerSide" : true,
			"iDisplayLength" : 15,
			"sDom": '<"top"iflp<"clear">>rt<"bottom"if<"paginate_length"lp><"clear">>',
			"sPaginationType" : "full_numbers",
			"sAjaxSource" : "getTopEvents.htm?lastEventId="+lastEventId+"&timeInterval="+$("input[name=timeInterval]:checked").val(),
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
									return source.value1;									
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
						lastEventId = data.lastEventId;
					}
				});

			}
		});
};



$(document).ready(function(){
	populateRecentEvents();
});

$('input[name=timeInterval]:radio').change(function () {
	populateRecentEvents();
});
</script>
</html>