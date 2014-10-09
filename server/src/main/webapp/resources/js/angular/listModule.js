'use strict';
var module = angular.module('listModule');

module.factory('ListService', function($http, $modal){
	return{
		
		getList: function(callback){
			$http.get('api/getList').success(callback);
		},
		
		addItem: function(listId, item, callback){
			$http.post('api/addItem?listId='+listId+'&item='+item.name+'&amount='+item.amount).success(callback);
		},
		
		//getUser TODO
		deleteItem: function(listId, itemId, callback){
			$http.get('api/getUser?login='+login).success(callback);
		}
	};
});

	
//CONTOLLER
module.controller('ListController', ['$scope', 'ListService', function($scope, ListService){
	
	$scope.list;
	$scope.items = [];
	
	ListService.getList(function(itemList){
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
			$scope.item = {};
		});
	};
	
	//TODO delete
	
}]);
