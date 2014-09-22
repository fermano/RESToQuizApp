	// create the module and name it scotchApp
	var scotchApp = angular.module('scotchApp', ['ngRoute','ngCookies','ngResource']);

	// configure our routes
	scotchApp.config(function($routeProvider) {
		$routeProvider

			// route for the home page
			.when('/', {
				templateUrl : 'pages/home.html',
				controller  : 'mainController'
			})

			// route for the about page
			.when('/vote', {
				templateUrl : 'pages/votar.html',
				controller  : 'mainController'
			})
			
			// route for the about page
			.when('/admin', {
				templateUrl : 'pages/admin.html',
				controller  : 'mainController'
			})
			
			// route for the about page
			.when('/finishedPoll', {
				templateUrl : 'pages/finishedPoll.html',
				controller  : 'mainController'
			})
			
			// route for the about page
			.when('/votoOK', {
				templateUrl : 'pages/success.html',
				controller  : 'mainController'
			})
			
			// route for the about page
			.when('/finishDay', {
				templateUrl : 'pages/finishDay.html',
				controller  : 'mainController'
			})
			// route for the about page
			.when('/finishWeek', {
				templateUrl : 'pages/finishWeek.html',
				controller  : 'mainController'
			})

			// route for the contact page
			.when('/result', {
				templateUrl : 'pages/resultados.html',
				controller  : 'mainController'
			});
	});

	// create the controller and inject Angular's $scope
	scotchApp.controller('mainController', function($scope, $http, $cookieStore, $location) {
		var professionals;
		// REST calls
		$http.get('http://localhost:8080/RESToQuizApp/webresources/myresource/professionals').
		success(function(data) {
			$scope.users = data;
			professionals = data;
		});
		$http.get('http://localhost:8080/RESToQuizApp/webresources/myresource/restaurants').
		success(function(data) {
			$scope.places = data;
		});
		
		$http.get('http://localhost:8080/RESToQuizApp/webresources/myresource/results').
		success(function(data) {
			$scope.results = data;
		});

		$scope.vote = function(placeId) {
			var userId = $scope.getUserIdinCookie();
			var complementURL = userId+"/"+placeId;
			$http.post('http://localhost:8080/RESToQuizApp/webresources/myresource/vote/'+complementURL,null).
			success(function(data) {
				$cookieStore.put('votou',true);
				return $location.path('/votoOK');
			});
		};
		
		
		$scope.finishDay = function() {
			$http.post('http://localhost:8080/RESToQuizApp/webresources/myresource/finishDay',null).
			success(function(data) {
				$cookieStore.put('votou',false);
				alert('Dia encerrado');
			});
		};
		
		$scope.finishWeek = function() {
			$http.post('http://localhost:8080/RESToQuizApp/webresources/myresource/finishWeek',null).
			success(function(data) {
				$cookieStore.put('votou',false);
				alert('Semana encerrada');
			});
		};
		
		// END REST calls
		// Session management
		$scope.getUserIdinCookie = function () {
			return $cookieStore.get('userId');
		};
		
		$scope.userAlreadyVoted = function () {
			if($cookieStore.get('votou') == null) {
				return false;
			} else {
				return  $cookieStore.get('votou');
			}
		};
		
		$scope.addUserIdToCookie = function (userId) {
			$cookieStore.put('userId',userId);
			for (i = 0; i < professionals.length; i++) {
				if(userId == professionals[i].id) {
					$cookieStore.put('votou',professionals[i].votouHoje);
					break;
				}
			}
			location.reload();
		};
		$scope.isSessionActive = function () {
			if ($cookieStore.get('userId') == null) {
				return false;
			} else {
				return true;
			}
		};
		$scope.logout = function () {
			$cookieStore.remove('userId');
		};
		// END Session management
	});
	