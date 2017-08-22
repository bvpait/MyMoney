// app.js

(function() {

    'use strict';

    var myMoneyApp = angular.module('myMoneyApp', ['ngRoute', 'ngTable', 'ui.bootstrap', 'messageService', 'beneficiarioService', 'contoService', 'categoriaService', 'mensileDefaultService', 'bilancioMensileService']);
    
    myMoneyApp.factory('InterceptorService', ['$q', '$window', '$rootScope', function($q, $window, $rootScope) {
    	
    	var InterceptorService = {
			'request': function(config) {
				//console.log('Request');
	            config.requestTimestamp = new Date().getTime();
	            return config;
	        },
	        'requestError': function(rejection) {
	        	console.log('Request error', rejection);
				return $q.reject(rejection);
			},
            'response': function(response) {
            	return response;
            },
			'responseError': function(response) {
				var status = response.status;
				console.log('Response error: ', status, " - ", response);

				switch (status) {
					// In caso di codice -1 (il server non risponde), 0 (non parte la chiamata Ajax dal browser) o 404, si rimanda sulla pagina di errore
					case -1:
					case 0:
						if ($window === undefined)
							window.location.href = 'error.html';
						else
							$window.location.href = 'error.html';
						break;
					// In caso di codice 401 o 403, si rimanda sulla pagina di login
					case 401:
					case 403:
						if ($window === undefined)
							window.location.href = 'login.html';
						else
							$window.location.href = 'login.html';
						break;
					default:
						if ($q !== undefined) {
							return $q.reject(response);
						}
				}
			}
        };

    	return InterceptorService;
    }]);

    myMoneyApp.config(['$locationProvider', '$httpProvider', '$routeProvider', '$logProvider', '$qProvider', 'CONSTANTS'
                      , function($locationProvider, $httpProvider, $routeProvider, $logProvider, $qProvider, CONSTANTS) {
    	
    	// enable or disable debug level messages
    	$logProvider.debugEnabled(CONSTANTS.DEBUG);
    	
    	$.config = {};
    	$.config.urlArgs = "?" + CONSTANTS.URL_ARG_NAME + "=" + CONSTANTS.URL_ARG_VALUE;

    	$routeProvider
    		.when('/home', {
    			templateUrl: 'resources/views/includes/riepilogoConti.html' + $.config.urlArgs
    		})
    		.when('/beneficiari', {
    			templateUrl: 'resources/views/includes/beneficiari.html' + $.config.urlArgs,
    			controller: 'BeneficiariController'
    		})
    		.when('/categorie', {
    			templateUrl: 'resources/views/includes/categorie.html' + $.config.urlArgs,
    			controller: 'CategorieController'
    		})
    		.when('/conti', {
    			templateUrl: 'resources/views/includes/conti.html' + $.config.urlArgs,
    			controller: 'ContiController'
    		})
    		.when('/spesaMensile', {
    			templateUrl: 'resources/views/includes/bilancioMensile.html' + $.config.urlArgs,
    			controller: 'BilancioMensileController', 
    		});
    	
    	$locationProvider.html5Mode(true);
	
		$httpProvider.interceptors.push('InterceptorService');
		
		//initialize get if not there
        if (!$httpProvider.defaults.headers.get) {
            $httpProvider.defaults.headers.get = {};    
        }    

        //disable IE ajax request caching
        $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        // extra
        $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
        // error handling
        $qProvider.errorOnUnhandledRejections(false);
    }]);
    
	/* 
	 * Override della gestione delle eccezioni di AngularJS.
	 * In questo modo si possono effettuare elaborazioni aggiuntive al flusso standard.
	 */
    myMoneyApp.config(['$provide', function ($provide) {
		$provide.decorator('$exceptionHandler', ['$delegate', '$log', function ($delegate, $log) {
			return function(exception, cause) {
    			// Stampa l'eccezione prima di procedere con il flusso standard
				//var stacktrace = ErrorStackParser != null ? ErrorStackParser.parse(exception) : exception;
				$log.debug(angular.toJson(exception));
				
				// Chiamata all'oggetto standard $exceptionHandler di AngularJS
				$delegate(exception, cause);
			};
		}]);
	}]);
    
    Array.prototype.remove = function(idx) {
    	for (var i = this.length - 1; i >= 0; i--) {
    	    if (this[i] !== undefined && this[i] != null && this[i].idx === idx) {
    	    	this.splice(i, 1);
    	    }
    	}
    }
    
})();