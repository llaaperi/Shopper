'use strict';
var module = angular.module('listModule');

module.factory('ListService', function($http, $modal){
	return{
		
		getList: function(callback){
			$http.get('api/getList').success(callback);
		},
		
		addItem: function(listId, item, callback){
			$http.post('api/addItem?listId='+listId+'&item='+item.name+'&amount='+item.amount+'&unit='+item.unit).success(callback);
		},
		
		//getUser TODO
		deleteItem: function(listId, item, callback){
			$http.post('api/deleteItem?listId='+listId+'&id='+item.id).success(callback);
		}
	};
});

	
//CONTOLLER
module.controller('ListController', ['$scope', 'ListService', function($scope, ListService){
	
	$scope.units = ["kpl","kg","g","L","dL"];
	
	$scope.list;
	$scope.items = [];
	$scope.item = {};
	initItem();
	
	ListService.getList(function(itemList){
		console.log("Get list");
		console.log(itemList);
		$scope.list = itemList;
		$scope.items = itemList.items;
	});
	
	$scope.addItem = function(item){
		console.log("Add item");
		console.log(item);
		ListService.addItem($scope.list.id, item, function(itemId){
			item.id = itemId;
			$scope.items.push(item);
			initItem();
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
	
	
	function initItem(){
		console.log("Init item");
		$scope.item = {};
		$scope.item.amount = 1;
		$scope.item.unit = $scope.units[0];
	};
	
	//TODO delete
	
	
}]);
