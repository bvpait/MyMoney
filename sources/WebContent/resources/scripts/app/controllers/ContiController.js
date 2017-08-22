// scripts/app/controllers/CategorieController.js

(function() {
    'use strict';

    angular.module('myMoneyApp').controller('ContiController', ContiController);

    function ContiController($scope, $routeParams, NgTableParams, ContoService, MensileDefaultService, CONSTANTS) {
    	$scope.newConto = {};
    	
    	$scope.init = function() {
    		ContoService.getAll().then(function(data) {
    			$scope.ctrl.listaConti = data;
    			data.forEach(function(conto) {
    				$scope.ctrl.listaContiMap[conto.id] = conto.nome;
    			});
    			$scope.tableConti = new NgTableParams({}, {dataset: data, counts: []});
    		});
    		$scope.newConto = {};
    	}
    	
    	$scope.inserisciConto = function() {
    		console.log('New conto: ', $scope.newConto);
    		var checkNome = $scope.ctrl.listaConti.find(function(c) {
    			return c.nome.toUpperCase() === $scope.newConto.nome.toUpperCase();
    		})
    		if (checkNome !== undefined) {
    			alert('Nome già usato');
    		} else {
    			ContoService.inserisci($scope.newConto).then($scope.init);
    		}
    	}
    	
    	$scope.associaTransazioniBase = function(idConto) {
    		$scope.ctrl.idContoCorrente = idConto;
    		MensileDefaultService.getAll(idConto).then(function(data) {
    			data.forEach(function(row) {
    				row.isSelected = row.id > 0;
    				row.tipo = $scope.ctrl.listaTipoTransazione[row.tipo];
    			});
    			console.table(data);
    			$scope.tableMensileDefault = new NgTableParams({group: "nomeCategoria"}, {dataset: data, counts: []});
    			$scope.tableMensileDefault.reload();
    		});
    	}
    	$scope.selezionaContoCorrente = function(idConto) {
    		$scope.ctrl.idContoCorrente = idConto;
    	}
    	
    	$scope.selectGroup = function(group) {
    		group.data.forEach(function(elem) {
    			elem.isSelected = group.isSelected;
    		});
    	}
    	
    	$scope.checkAll = function(group) {
    		var isSelected = true;
    		group.data.forEach(function(elem) {
    			isSelected = isSelected && elem.isSelected;
    		});
    		return isSelected;
    	}
    	
    	$scope.confermaAssociazioniTransazioni = function() {
    		var lista = [];
    		$scope.tableMensileDefault.data.forEach(function(group) {
    			group.data.forEach(function(row) {
    				if (row.isSelected || row.id > 0) {
    					lista.push({
    						id: row.id,
    						idConto: $scope.ctrl.idContoCorrente,
    						idCategoria: row.idSottoCategoria,
    						idBeneficiario: row.idBeneficiario,
    						importo: row.importo,
    						tipo: row.tipo,
    						note: row.note,
    						idTag: row.idTag,
    						active: row.isSelected
    					});
    				}
    			});
    		});
    		console.table(lista);
    		MensileDefaultService.inserisci(lista).then(function() {
    			alert('Categorie associate al conto configurate correttamente');
    		});
    	}
    	
    	$scope.init();
    	
     } // end controller

})();   	