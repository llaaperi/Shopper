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
          <a class="navbar-brand" href="#">Shopper</a>
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
      
      <table class="table table-striped table-condensed table-hover">
        
        <thead>
          <tr>
            <th class="col-md-7">Item</th>
            <th class="col-md-2">Amount</th>
            <th class="col-md-2">Unit</th>
            <th class="col-md-1"></th>
          </tr>
        </thead>
        
        <tbody>
			<tr ng-repeat="item in items">
		        	<td>{{item.name}}</td>
					<td>{{item.amount}}</td>
					<td>{{item.unit}}</td>
					<td><button class='btn btn-default btn-sm' ng-click="deleteItem(item)">Delete</button></td>
			</tr>
        </tbody>
        
        <tfoot>
        	<tr>
        	  <td><input class="form-control" type="text" placeholder="Item" value="" ng-model="item.name"></td>
        	  <td><input class="form-control" type="number" min="0" placeholder="Amount" value="1" ng-model="item.amount"></td>
        	  <td><input class="form-control" type="text" placeholder="Unit" value="" ng-model="item.unit"></td>
        	  <td><button type="submit" class="btn btn-default" ng-click="addItem(item)">Add</button></td>
        	</tr>
        </tfoot>
        
      </table>
    </div>
    
  </body>
</html>