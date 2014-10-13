'use strict';
var module = angular.module('listModule');

function sync(callback){
	console.log('Start sync');
	var socket = $.atmosphere;
	var request = { url: 'sync',
			contentType : "application/json",
			logLevel : 'debug',
			transport : 'websocket' ,
			fallbackTransport: 'long-polling'};
	
	request.onOpen = function(response) {
		$.atmosphere.log('info', ["Connected: " + response.transport]);
	};	

	request.onMessage = function (response) {
		var data = "<empty>";
		try {
			data = $.parseJSON(response.responseBody);
		} catch (err) {
			$.atmosphere.log('error', ["JSON parse error: " + err]);
		}
		callback(data);
	};

	var subSocket = socket.subscribe(request);
}

module.factory('ListService', function($http, $modal){
	return{
		
		getList: function(listId, callback){
			$http.get('api/getList?listId='+listId).success(callback);
		},
		
		addItem: function(listId, item, callback){
			$http.post('api/addItem?listId='+listId, item).success(callback);
		},
		
		deleteItem: function(listId, item, callback){
			$http.post('api/deleteItem?listId='+listId+'&id='+item.id).success(callback);
		},
		
		deleteItems: function(listId, items, callback){
			$http.post('api/deleteItems?listId='+listId, items).success(callback);
		},
		
		updateItem: function(listId, item, callback){
			$http.post('api/updateItem?listId='+listId, item).success(callback);
		}
		
	};
});

module.directive('clickOutside', function($document){
	return {
	    restrict: 'A',
	    link: function(scope, elem, attr, ctrl) {
	      elem.bind('click', function(e) {
	        // this part keeps it from firing the click on the document.
	        e.stopPropagation();
	      });
	      $document.bind('click', function() {
	        // magic here
	        scope.$apply(attr.clickOutside);
	      })
	    }
	  }
});
	
//CONTOLLER
module.controller('ListController', ['$scope', 'ListService', function($scope, ListService){
	
	$scope.units = ["kpl","kg","g","L","dL"];
	
	$scope.list;
	$scope.items = [];
	$scope.newItem = {};
	initNewItem();
	
	//Callback function for sync
	sync(function(items){
		console.log("Sync items");
		console.log(items);
		$scope.$apply(function(){
			$scope.items = items;
		});
	});
	
	$scope.init = function(listId){
		console.log("Init list controller with id "+listId);
		ListService.getList(listId, function(itemList){
			console.log("Get list");
			console.log(itemList);
			$scope.list = itemList;
			$scope.items = itemList.items;
		});
	};
	
	$scope.addItem = function(newItem){
		console.log("Add item");
		console.log(newItem);
		newItem.syncing = true;
		$scope.items.push(newItem);
		initNewItem();
		ListService.addItem($scope.list.id, cleanItem(newItem), function(itemId){
			console.log("Added item <"+itemId+"> at the server");
			newItem.id = itemId;
			newItem.syncing = false;
		});
	};
	
	$scope.deleteItem = function(item){
		console.log("Delete item");
		console.log(item);
		ListService.deleteItem($scope.list.id, item, function(respone){
			var idx = $scope.items.indexOf(item);
			console.log("delete item " + idx);
			$scope.items.splice(idx, 1);
		});
	};
	
	$scope.markItem = function(item){
		//console.log("Mark item");
		//console.log(item);
		if(!item.editing){	//Do not mark if item is being edited
			item.marked =! item.marked;
			ListService.updateItem($scope.list.id, cleanItem(item), function(response){
				console.log("Marked item at the server");
			});
		}
	};
	
	$scope.deleteMarkedItems = function(){
		console.log("Delete marked items");
		var remainingItems = [];
		var deletedItems = [];
		for(var i = 0; i < $scope.items.length; i++){
			if($scope.items[i].marked == true){ //Remove marked
				deletedItems.push(cleanItem($scope.items[i]));
			}else{
				remainingItems.push($scope.items[i]);
			}
		}
		console.log(deletedItems);
		if(deletedItems.length == 0){
			return;	//Do not make server query for empty list
		}
		$scope.items = remainingItems;
		ListService.deleteItems($scope.list.id, deletedItems, function(respone){
			console.log("Deleted "+deletedItems.length+" items at the server");
		});
	};
	
	$scope.editItem = function(item){
		item.editing = true;
	};
	
	$scope.updateItem = function(item){
		console.log("Update item");
		console.log(item);
		item.editing = false;
		item.syncing = true;
		ListService.updateItem($scope.list.id, cleanItem(item), function(response){
			console.log("Updated item at the server");
			item.syncing = false;
		});
	};
	
	function initNewItem(){
		$scope.newItem = {};
		$scope.newItem.amount = 1;
		$scope.newItem.unit = $scope.units[0];
	};
	
	function cleanItem(item){
		var item2 = {};
		item2.id = item.id;
		item2.name = item.name;
		item2.amount = item.amount;
		item2.unit = item.unit;
		item2.marked = item.marked;
		return item2;
	};
}]);
