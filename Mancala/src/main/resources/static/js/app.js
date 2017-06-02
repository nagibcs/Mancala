var app = angular.module('App', ['ngRoute']);

app.filter('range', function() {
	  return function(input, total) {
	    total = parseInt(total);

	    for (var i=0; i<total; i++) {
	      input.push(i);
	    }

	    return input;
	  };
	});

app.filter('reverse', function() {
	  return function(input, firstNElements) {
		if (input == undefined || input.length == 0)
			return input;
		if (firstNElements > input.length)
			firstNElements = input.length;
	    return input.slice(0, firstNElements).reverse();
	  };
	});



app.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
	$routeProvider.
		when('/home', {
			templateUrl: 'views/home.html',
			controller: 'BoardController'
		}).
		otherwise({
			redirectTo: '/home'
		});
}]);
