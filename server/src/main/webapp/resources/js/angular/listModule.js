'use strict';
var module = angular.module('listModule');

module.factory('ListService', function($http, $modal){
	return{
		
		getList: function(callback){
			$http.get('api/getList').success(callback);
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
	
	$scope.markItem = function(item){
		//console.log("Mark item");
		//console.log(item);
		if(!item.editing){	//Do not mark if item is being edited
			item.marked =! item.marked;
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
		ListService.deleteItems($scope.list.id, deletedItems, function(respone){
			console.log("deleted "+deletedItems.length+" items from the server");
			$scope.items = remainingItems;
		});
	};
	
	$scope.editItem = function(item){
		item.editing = true;
	};
	
	$scope.updateItem = function(item){
		console.log("Update item");
		console.log(item);
		ListService.updateItem($scope.list.id, cleanItem(item), function(response){
			item.editing = false;
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
		return item2;
	};
}]);
