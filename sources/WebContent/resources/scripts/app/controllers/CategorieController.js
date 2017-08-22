// scripts/app/controllers/CategorieController.js

(function() {
    'use strict';

    angular.module('myMoneyApp').controller('CategorieController', CategorieController);

    function CategorieController($scope, $routeParams, NgTableParams, CategoriaService, CONSTANTS) {
    	$scope.tableCategorie;
    	$scope.newCategoria = {};
    	$scope.fileCat = {};
    	
    	$scope.init = function() {
    		CategoriaService.getAll().then(function(data) {
    			$scope.ctrl.listaCategorie = data;
    			$scope.tableCategorie = new NgTableParams({}, {dataset: data, counts: []});
    		});
    		$scope.newCategoria = {};
    	}
    	
    	$scope.inserisciCategoria = function() {
    		console.log('New categoria: ', $scope.newCategoria);
    		CategoriaService.inserisci($scope.newCategoria).then($scope.init);
    	}
    	
    	$scope.uploadFile = function() {
    		CategoriaService.uploadFile($scope.fileCat).then(function(data) {
    			alert('OK');
    		});
    	}
    	
    	$scope.init();
    	
     } // end controller

})();   	