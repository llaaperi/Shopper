<!DOCTYPE html>
<html ng-app="shopperApp">
	<head>
		<title>Shopper</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.0/css/bootstrap.min.css">
		<link href="css/bootstrap_jumbo.css" rel="stylesheet">
		<link href="css/shopper.css" rel="stylesheet">
		
		<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/atmosphere/2.1.2/atmosphere.min.js"></script>
		<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery.atmosphere/2.1.2/jquery.atmosphere.min.js"></script>
		<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.1.1/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.2.16/angular.min.js"></script>
		<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.10.0/ui-bootstrap-tpls.min.js"></script>
		<script type="text/javascript" src="js/angular/app.js"></script>
		<script type="text/javascript" src="js/angular/listModule.js"></script>
		
	</head>
  
  <body>
  
  <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/shopper">Shopper</a>
          <a class="navbar-brand" href="myList">MyList</a>
        </div>
        <div class="navbar-collapse collapse">
          <form class="navbar-form navbar-right">
            <div class="form-group">
              <input type="text" placeholder="Email" class="form-control">
            </div>
            <div class="form-group">
              <input type="password" placeholder="Password" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">Sign in</button>
          </form>
        </div><!--/.navbar-collapse -->
      </div>
    </div>
    
    <div class="container" ng-controller="ListController">
	
      <h1>MyList</h1>
	  
      <table class="table">
        
        <thead>
          <tr>
            <th class="col-xs-7 col-sm-7">Item</th>
            <th class="col-xs-1 col-sm-2">Amount</th>
            <th class="col-xs-3 col-sm-2">Unit</th>
            <th class="text-center" class="col-xs-1 col-sm-1">
              <button class='btn btn-default' ng-hide="true" ng-click="">Clear</button>
              <span class="glyphicon glyphicon-trash" ng-click="deleteMarkedItems()"></span>
            </th>
          </tr>
        </thead>
        
        <tbody>
			<tr ng-repeat="item in items">
		        	<td ng-class="{markedItem: item.marked && !item.editing}" ng-click="markItem(item)">
		        		<div ng-class="{syncingItem: item.syncing}" ng-hide="item.editing">
		        			{{item.name}}
		        		</div>
		        		<div ng-show="item.editing">
		        			<input class="form-control" type="text" placeholder="Item" value="" ng-model="item.name">
		        		</div>
		        	</td>
					<td ng-class="{markedItem: item.marked && !item.editing}" ng-click="markItem(item)">
						<div ng-class="{syncingItem: item.syncing}" ng-hide="item.editing">
							{{item.amount}}
						</div>
						<div ng-show="item.editing">
							<input class="form-control" type="number" min="1" value="1" ng-model="item.amount">
						</div>
					</td>
					<td ng-class="{markedItem: item.marked && !item.editing}" ng-click="markItem(item)">
						<div ng-class="{syncingItem: item.syncing}" ng-hide="item.editing">
							{{item.unit}}
						</div>
						<div ng-show="item.editing">
							<select class="form-control" ng-model="item.unit" ng-options="unit for unit in units"></select>
						</div>
					</td>
					<td class="text-center" ng-class="{markedItem: item.marked && !item.editing}" ng-style="itemStyle">
						<span class="glyphicon glyphicon-ok" ng-show="item.marked && !item.editing"></span>
						<span class="glyphicon glyphicon-refresh" ng-show="item.syncing"></span>
						<span class="glyphicon glyphicon-pencil" ng-hide="item.editing || item.marked || item.syncing" ng-click="editItem(item)"></span>
						<button class='btn btn-default btn-sm' ng-show="item.editing" ng-click="updateItem(item)">Save</button>
					</td>
			</tr>
        </tbody>
        
        <tfoot>
        	<tr>
        	<form>
        	  <td><input class="form-control" type="text" placeholder="Item" value="" ng-model="newItem.name"></td>
        	  <td><input class="form-control" type="number" min="1" value="1" ng-model="newItem.amount"></td>
        	  <td><select class="form-control" ng-model="newItem.unit" ng-options="unit for unit in units"></select></td>
        	  <td>
        	  	<button type="submit" class="btn btn-default" ng-click="addItem(newItem)">
        	  	  Add
        	  	</button>
        	  </td>
        	</form>
        	</tr>
        </tfoot>
        
      </table>
    </div>
    
  </body>
</html>