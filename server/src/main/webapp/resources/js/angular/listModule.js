'use strict';
var module = angular.module('listModule');

module.factory('ListService', function($http, $modal){
	return{
		
		getList: function(callback){
			$http.get('api/getList').success(callback);
		},
		
		addItem: function(listId, item, callback){
			//$http.post('api/addItem?listId='+listId+'&item='+item.name+'&amount='+item.amount+'&unit='+item.unit).success(callback);
			$http.post('api/addItem?listId='+listId, item).success(callback);
		},
		
		deleteItem: function(listId, item, callback){
			$http.post('api/deleteItem?listId='+listId+'&id='+item.id).success(callback);
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
	
	ListService.getList(function(itemList){
		console.log("Get list");
		console.log(itemList);
		$scope.list = itemList;
		$scope.items = itemList.items;
	});
	
	$scope.addItem = function(newItem){
		console.log("Add item");
		console.log(newItem);
		ListService.addItem($scope.list.id, newItem, function(itemId){
			newItem.id = itemId;
			$scope.items.push(newItem);
			initNewItem();
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
	
	$scope.editItem = function(item){
		item.editing = true;
	};
	
	$scope.updateItem = function(item){
		console.log("Update item");
		console.log(item);
		var it = {};
		it.id = item.id;
		it.name = item.name;
		it.amount = item.amount;
		it.unit = item.unit;

		ListService.updateItem($scope.list.id, it, function(response){
			item.editing = false;
		});
	};
	
	function initNewItem(){
		console.log("Init new item");
		$scope.newItem = {};
		$scope.newItem.amount = 1;
		$scope.newItem.unit = $scope.units[0];
	};
	
	//TODO delete
	
	
}]);
