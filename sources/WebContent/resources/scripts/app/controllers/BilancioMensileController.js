// scripts/app/controllers/SpesaMensileController.js

(function() {
    'use strict';

    angular.module('myMoneyApp').controller('BilancioMensileController', BilancioMensileController);

    function BilancioMensileController($scope, $routeParams, NgTableParams, MensileDefaultService, CategoriaService, BilancioMensileService, CONSTANTS) {
    	var idx = 0;
    	
    	function isDataTableError() {
    		var isTableError = false;
    		$scope.tableCategoria.settings().dataset.forEach(function(row) {
    			row.isError = false;
    			if (row.id > 0 && row.form.$pristine)
    				return;
    			
    			if (row.idBeneficiario > 0 || (row.importo !== undefined && row.importo > 0)) {
    				if (row.importo === undefined || row.importo === 0) {
        				row.isError = true;
        				isTableError = true;
        				row.messageError = CONSTANTS.ERROR[2];
        			}
    				if (row.tipo === undefined) {
        				row.isError = true;
        				isTableError = true;
        				row.messageError = CONSTANTS.ERROR[3];
        			}
    				if (row.importo !== undefined && row.importo > 0 && (row.exp === undefined || row.exp === '')) {
    					row.isError = true;
    					isTableError = true;
    					row.messageError = 'Importo non calcolato';
    				}
    				if (row.errorExp !== undefined && row.errorExp) {
    					row.messageError = 'Colonna calcola non valido';
    				}
    			}
    		});
    		
    		return isTableError;
    	}
    	
    	// selezione del prima categoria
    	function setPrimaCategoria() {
			$scope.selectedIdCategoria = 0;
			var id = 0, nome = '';
			$scope.listaCategorieBilancioMensile.forEach(function(c) {
				if (id === 0 || nome > c.nome) {
					id = c.id;
					nome = c.nome;
				}
			});
			$scope.selectCategoria(id);
    	}
    	
    	function formatTransazioni(data) {
    		$scope.ctrl.listaCategorieMap = {'TRASF': []};
    		data.forEach(function(row) {
    			if (row.idContoTrasferimento !== undefined && row.idContoTrasferimento > 0)
    				row.idCategoria = 'TRASF';
    			
				row.tipo = $scope.ctrl.listaTipoTransazione[row.tipo];
				
				if ($scope.ctrl.listaCategorieMap[row.idCategoria] === undefined) {
					$scope.ctrl.listaCategorieMap[row.idCategoria] = [];
					$scope.listaCategorieBilancioMensile.push({id: row.idCategoria, nome: row.nomeCategoria})
				}
				row.idx = idx++;
				$scope.ctrl.listaCategorieMap[row.idCategoria].push(row);
			});
			setPrimaCategoria();
			$scope.ctrl.operationInPending = false;
    	}
    	
    	$scope.bilancioMensileCorrente = {idConto: $scope.ctrl.idContoCorrente};
    	$scope.selectedIdCategoria = 0;
    	$scope.selected;
    	$scope.tableCategoria;
    	$scope.listaCategorieBilancioMensile = [];
    	
    	$scope.monthPopup = {
    		opened: false
    	};
    	$scope.format = 'MM.yyyy';
    	$scope.openMonthPopup = function() {
    		$scope.monthPopup.opened = true; 
    	}
    	$scope.dateOptions = {
    		minDate: new Date(2015,1,1),
    		minMode: 'month',
    		showWeeks: false
    	};
    	
    	$scope.init = function() {
    		$scope.bilancioMensileCorrente.date = new Date();
    	}
    	
    	$scope.selectCategoria = function(id) {
    		if ($scope.selectedIdCategoria > 0 && $scope.tableCategoria !== undefined && isDataTableError()) {
    			return;
    		}
    		$scope.selectedIdCategoria = id;
    		$scope.tableCategoria = new NgTableParams({count: 25}, {dataset: $scope.ctrl.listaCategorieMap[id], counts: []});
    	}
    	
    	$scope.aggiungiRiga = function(row) {
    		if ($scope.selectedIdCategoria == 'TRASF') {
    			$scope.tableCategoria.settings().dataset.push({idx: idx++, id: 0, idCategoria: 'TRASF', idSottoCategoria: 0, nomeSottoCategoria: '', idBeneficiario: -2});
    		} else {
    			$scope.tableCategoria.settings().dataset.push({idx: idx++, id: 0, idCategoria: $scope.selectedIdCategoria, idSottoCategoria: 0, idBeneficiario: -1});
    		}
    		$scope.tableCategoria.reload();
    	}
    	
    	$scope.eliminaRiga = function(row) {
    		if (row.isRemoved)
    			return;
    		
    		if (row.id > 0) {
    			row.isRemoved = true;
    			if (row.isEditing)
    				row.isEditing = false;
    		} else {
        		$scope.tableCategoria.settings().dataset.remove(row.idx);
        		$scope.tableCategoria.reload();
    		}
    	}
    	
    	$scope.calcolaRiga = function(row) {
    		try {
    			row.importo = (row.exp === undefined || row.exp === '') ? 0 : (parseFloat(eval(row.exp)).toFixed(2));
    			row.errorExp = false;
    		} catch (e) {
    			row.importo = '';
				row.errorExp = true;
			}
    	}
    	
    	$scope.modificaCategoria = function() {
    		if ($scope.bilancioMensileCorrente.isNew)
    			return;
    		
    		$scope.tableCategoria.settings().dataset.forEach(function(row) {
    			if (!row.isRemoved)
    				row.isEditing = true;
    		});
    		$scope.tableCategoria.reload();
    	}
    	
    	$scope.aggiungiCategoria = function() {
    		var listaCategorieFilter = [];
    		$scope.ctrl.listaCategoriePrincipali.forEach(function(c) {
    			if ($scope.ctrl.listaCategorieMap[c.id] === undefined) {
    				listaCategorieFilter.push({id: c.id, nome: c.nome})
    			}
    		});
    		var confirmCategorieSelected = function(lista) {
    			lista.forEach(function(c) {
    				if (c.selected) {
    					$scope.listaCategorieBilancioMensile.push({id: c.id, nome: c.nome});
    					$scope.ctrl.listaCategorieMap[c.id] = [];
    				}
    			});
    			console.table(lista);
    		}
    		if (listaCategorieFilter.length > 0) {
        		$scope.ctrl.showCategoriePrincipali({
        			lista: listaCategorieFilter,
        			confirm: confirmCategorieSelected
        		});
    		} else {
    			alert('Nessuna categoria da aggiungere');
    		}
    	}
    	
    	$scope.apriDettaglioMensile = function() {
    		if ($scope.bilancioMensileCorrente.date === undefined)
    			return;
    		
    		$scope.ctrl.operationInPending = true;
    		$scope.listaCategorieBilancioMensile = [];
    		
    		idx = 0;
    		$scope.bilancioMensileCorrente.mese = $scope.bilancioMensileCorrente.date.getMonth()+1;
    		$scope.bilancioMensileCorrente.anno = $scope.bilancioMensileCorrente.date.getFullYear();
    		console.log('Selezionato Anno:', $scope.bilancioMensileCorrente.anno, 'mese:', $scope.bilancioMensileCorrente.mese);
    		BilancioMensileService.check($scope.bilancioMensileCorrente).then(function(data) {
    			$scope.bilancioMensileCorrente.isNew = data.isNew;
    			if (data.isNew) {
    				MensileDefaultService.get($scope.ctrl.idContoCorrente).then(formatTransazioni);
    			} else {
    				BilancioMensileService.getDettaglio($scope.bilancioMensileCorrente).then(formatTransazioni);
    			}
    		});
    	}
    	
    	$scope.sum = function(tipo) {
    		if ($scope.ctrl.listaCategorieMap === undefined || $scope.selectedIdCategoria === 0)
    			return 0;
    		
    		var sumCategoria = 0;
    		$scope.ctrl.listaCategorieMap[$scope.selectedIdCategoria].forEach(function(row) {
    			if (row.tipo === $scope.ctrl.listaTipoTransazione[tipo] && !row.isError && row.importo !== undefined) {
    				try {
    					sumCategoria += parseFloat(row.importo);
    	    		} catch (e) { }
    			}
    		});
    		return sumCategoria.toFixed(2);
    	}
    	
    	$scope.avanzaMeseCorrente = function(step) {
    		$scope.bilancioMensileCorrente.date.setMonth($scope.bilancioMensileCorrente.date.getMonth() + step);
    		$scope.apriDettaglioMensile();
    	}
    	
    	$scope.salvaDati = function() {
    		if (isDataTableError()) 
    			return;
    		
    		BilancioMensileService.salva($scope.bilancioMensileCorrente, $scope.ctrl.listaCategorieMap).then(function(data) {
    			alert('Dati salvati');
    		});
    	}
    	
    	$scope.init();
    	
     } // end controller

})();   	