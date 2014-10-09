function sync(){
	console.log('Start sync');
	var socket = $.atmosphere;
	var request = { url: 'sync',
			contentType : "application/json",
			logLevel : 'debug',
			transport : 'websocket' ,
			fallbackTransport: 'long-polling'};

	
	
	request.onOpen = function(response) {
		$.atmosphere.log('info', ["Connected: " + response.transport]);
		console.log("Connected to server");
	};	
	
	request.onMessage = function (response) {
		var data = "<empty>";
		try {
			data = $.parseJSON(response.responseBody);
		} catch (err) {
			$.atmosphere.log('error', ["JSON parse error: " + err]);
		}
		
		console.log("Received: " + data);
		
		//Refresh table body
		$("#tableBody").empty();
		$.each(data, function(idx, elem){
			$("#tableBody").append("<tr><td>"+elem.name+"</td><td>"+elem.amount+"</td><td><button type='button' class='btn btn-default' onclick='deleteItem(" + elem.id +")'>Delete</button></td></tr>");
		});
	};
	
	var subSocket = socket.subscribe(request);
}

