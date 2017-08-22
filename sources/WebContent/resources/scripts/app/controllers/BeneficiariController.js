// scripts/app/controllers/CategorieController.js

(function() {

    'use strict';

    angular.module('myMoneyApp').controller('BeneficiariController', BeneficiariController);

    function BeneficiariController($scope, $routeParams, NgTableParams, BeneficiarioService, CONSTANTS) {
    	$scope.tableCategorie;
    	$scope.newBeneficiario = {};
    	
    	$scope.init = function() {
    		BeneficiarioService.getAll().then(function(data) {
    			$scope.ctrl.listaBeneficiari = data;
    			$scope.tableBeneficiari = new NgTableParams({}, {dataset: data, counts: []});
    		});
    		$scope.newBeneficiario = {};
    	}
    	
    	$scope.inserisci = function() {
    		console.log('New categoria: ', $scope.newBeneficiario);
    		BeneficiarioService.inserisci($scope.newBeneficiario).then($scope.init);
    	}
    	
    	$scope.init();
    	
     } // end controller

})();   	