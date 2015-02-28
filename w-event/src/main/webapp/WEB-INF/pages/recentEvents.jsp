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
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.5/js/jquery.dataTables.min.js"></script>
</head>
<style>
	table td, table th{
		border: 1px solid #ccc;
	}
</style>
<body>
<table id="recentEvent" width="100%" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border: 1px solid #dadada;">
					<thead>
						<tr>
						    <th style="text-align: center;" width="160">Event Type sd</th>
						    <th style="text-align: center;" width="160">Value1</th>
							<th style="text-align: center;" width="160">Value2</th>
						</tr>
						
					</thead>
					<tbody>
						
					</tbody>
				</table>
</body>
<script>
var lastEventId = '';


var recentEventsTable;
function populateRecentEvents() {
	if(recentEventsTable != undefined) {
		recentEventsTable.fnDestroy();
	}
$('#recentEvent').dataTable(
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
			"sAjaxSource" : "getRecentEvents.htm?lastEventId="+lastEventId,
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
							},
							{
								"bSortable" : false,
								"mDataProp" : function(source,type,val) {
									return source.value2;
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
</script>
</html>