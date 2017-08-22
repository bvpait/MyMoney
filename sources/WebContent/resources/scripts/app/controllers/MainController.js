// scripts/app/controllers/MainController.js

(function() {
    'use strict';

    angular.module('myMoneyApp').controller('MainCtrl', MainController);

    function MainController($scope, $routeParams, $uibModal, NgTableParams, CategoriaService, BeneficiarioService, CONSTANTS) {
    	var ctrl = this;
    	
    	ctrl.operationInPending = false;
    	ctrl.listaCategorie;
    	ctrl.listaCategoriePrincipali = [];
    	ctrl.idContoCorrente = 0;
    	ctrl.listaConti = [];
    	ctrl.listaContiMap = {};
    	ctrl.listaBeneficiari;
    	ctrl.listaCategorieMap = [];
    	ctrl.listaMesi = CONSTANTS.MESI;
    	ctrl.listaTipoTransazione = {'USCITA': '1', 'ENTRATA': '2', 'TRASF_USCITA': 3, 'TRASF_ENTRATA': 4};
    	ctrl.listaTipoTransazioneMap = ["", "Uscita", "Entrata"];
    	
    	$scope.loadHome = function() {
    		
//    		$scope.categorie = [{id:1, nome:'Alimentari'}, {id:2, nome: 'Vestiario'}];
    		
//    		$scope.tableCategorie = new NgTableParams({}, {dataset: $scope.categorie});
    	}
    	
    	function init() {
    		if (ctrl.listaCategorie === undefined) {
    			CategoriaService.getAll().then(function(data) {
        			ctrl.listaCategorie = data;
        			data.forEach(function(c) {
        				if (c.idPadre === 0)
        					ctrl.listaCategoriePrincipali.push(c);
        			});
        			console.table(ctrl.listaCategoriePrincipali);
        		});
    		}
    		
    		if (ctrl.listaBeneficiari === undefined) {
    			BeneficiarioService.getAll().then(function(data) {
        			$scope.ctrl.listaBeneficiari = data;
        		});
    		} 
    		
    	}
    	
    	this.showCategoriePrincipali = function(operation) {
    		var modalIstance = $uibModal.open({
    			animation: true,
    			ariaLabelledBy: 'modal-title',
    			ariaDescribedBy: 'modal-body',
    			templateUrl: 'messageTemplate',
    			size: 'sm',
    			controller: function($scope, $uibModalInstance) {
	    	    	$scope.headerTitle = 'My Money - Categorie';
	    	    	$scope.lista = operation.lista;
	    	    
	    			$scope.close = function() {
	    				$uibModalInstance.dismiss('cancel');
	    			};
	    			
	    			$scope.ok = function() {
	    				$scope.close();
	    				operation.confirm($scope.lista);
	    			};
    			} // end modal controller
    		});
    	}
    	
    	init();
    	
     } // end controller

})();   	