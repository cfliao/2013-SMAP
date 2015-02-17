// JSONP的重點是要加上?_jsonp=?，另外，伺服端也要配合，詳見Server.java

$(document).ready(function() {
	    var url = 'http://192.168.4.108:8192/data/208/sensor/all?_jsonp=?';
	    $.getJSON(
					url,
					function(data) {
						console.log(data);
						$('#temperature').html(data.temperature+' degrees centigrade');
						$('#humidity').html(data.humidity+ ' %');
						$('#light').html(data.light+ ' lux');
						$('#ir').html(data.ir+' cm');
				  });
	});