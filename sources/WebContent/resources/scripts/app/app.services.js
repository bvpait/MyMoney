angular.module('messageService', []).factory('MessageService', function($http, $rootScope, CONSTANTS) {
	
	var MessageService = {
		
		promiseGET: function(url, successCallback) {
			if (successCallback === undefined)
				successCallback = function(response) {
					return response.data;
				}
			return $http.get(url).then(successCallback, MessageService.send);
		},
		
		promisePOST: function(url, data, successCallback) {
			if (successCallback === undefined)
				successCallback = function(response) {
					return response.data;
				}
			return $http.post(url, data).then(successCallback, MessageService.send);
		},
		
		promisePUT: function(url, data, successCallback) {
			if (successCallback === undefined)
				successCallback = function(response) {
					return response.data;
				}
			return $http.put(url, data).then(successCallback, MessageService.send);
		},
		
		send: function(response) {
			console.log('[SERVICE ERROR] ', response);
			$rootScope.$broadcast('serviceError');
		}
	};
	
	return MessageService;
});

angular.module('categoriaService', []).factory('CategoriaService', function($http, CONSTANTS, MessageService) {
	
	var CategoriaService = {
			
		getAll: function() {
			return MessageService.promiseGET(CONSTANTS.PATH_REST.CATEGORIA_ALL);
		},
		
		inserisci: function(newValue) {
			var data = {
				nome: newValue.nome,
				idPadre: parseInt(newValue.idPadre),
				entrate: 0,
				uscite: 0
			}
			return MessageService.promisePOST(CONSTANTS.PATH_REST.CATEGORIA_INSERT, newValue);
		},
		
		uploadFile: function(data) {
			var fd = new FormData();
			fd.append('file', data.content);

			var promise = $http.post(CONSTANTS.PATH_REST.CATEGORIA_UPLOAD, fd, {
				transformRequest: angular.identity,
				headers: {'Content-Type': undefined}
			})
			.then(function(response) {
				return response.data;
			}, function errorCallback(response) {
				console.log('[SERVICE ERROR]', 'Add Verbale: ', response);
			});
			return promise;
		}
	}
	
	return CategoriaService;
});

angular.module('bilancioMensileService', []).factory('BilancioMensileService', function(CONSTANTS, MessageService) {
	
	var BilancioMensileService = {
			
		getDettaglio: function(data) {
			return MessageService.promisePOST(CONSTANTS.PATH_REST.BILANCIO_MENSILE_DETTAGLIO, data);
		},
		
		salva: function(bilancioMensileCorrente, listabyCategoria) {
			var listaTransazioni = [];
			for (var c in listabyCategoria) {
				if (Array.isArray(listabyCategoria[c]) && listabyCategoria[c].length > 0) {
					listabyCategoria[c].forEach(function(row) {
						if (row.id > 0 && row.form.$pristine)
							return;
							
						if (c === 'TRASF') {
							row.idCategoria = null;
							row.tipo = parseInt(row.tipo) + 2;
						}
						
						row.tipoOperazione = CONSTANTS.TIPO_OP[row.id === 0 ? 'ADDED' : (row.isRemoved ? 'REMOVED' : 'CHANGED')];
						
						if (row.importo !== undefined && row.importo > 0)
							listaTransazioni.push(row);
					});
				}
			}
			var data = {
				idConto: bilancioMensileCorrente.idConto,
				anno: bilancioMensileCorrente.anno,
				mese: bilancioMensileCorrente.mese,
				listaTransazioni: listaTransazioni
			}
			return MessageService.promisePUT(CONSTANTS.PATH_REST.BILANCIO_MENSILE, data);
		},
		
		check: function(data) {
			var successCallback = function(response) {
				var dataResponse = {};
				dataResponse.isNew = response.status === 204;
				return dataResponse;
			};
			return MessageService.promisePOST(CONSTANTS.PATH_REST.BILANCIO_MENSILE_CHECK, data, successCallback);
		}
	}
	
	return BilancioMensileService;
});

angular.module('contoService', []).factory('ContoService', function(CONSTANTS, MessageService) {
	
	var ContoService = {
			
		getAll: function() {
			return MessageService.promiseGET(CONSTANTS.PATH_REST.CONTO_ALL);
		},
		
		check: function(nome) {
			var successCallback = function(response) {
				var dataResponse = {};
				dataResponse.isNew = response.status === 204;
				return dataResponse;
			};
			return MessageService.promiseGET(CONSTANTS.PATH_REST.CONTO_CHECK + nome, successCallback);
		},
		
		inserisci: function(newValue) {
			var data = {
				nome: newValue.nome
			}
			return MessageService.promisePOST(CONSTANTS.PATH_REST.CONTO_INSERT, newValue);
		}
	}
	
	return ContoService;
});

angular.module('beneficiarioService', []).factory('BeneficiarioService', function(CONSTANTS, MessageService) {
	
	var BeneficiarioService = {
			
		getAll: function() {
			return MessageService.promiseGET(CONSTANTS.PATH_REST.BENEFICIARIO_ALL);
		},
		
		check: function(nome) {
			var successCallback = function(response) {
				var dataResponse = {};
				dataResponse.isNew = response.status === 204;
				return dataResponse;
			};
			return MessageService.promiseGET(CONSTANTS.PATH_REST.CONTO_CHECK + nome, successCallback);
		},
		
		inserisci: function(newValue) {
			var data = {
				nome: newValue.nome
			}
			return MessageService.promisePOST(CONSTANTS.PATH_REST.BENEFICIARIO_INSERT, newValue);
		}
	}
	
	return BeneficiarioService;
});

angular.module('mensileDefaultService', []).factory('MensileDefaultService', function(CONSTANTS, MessageService) {
	
	var MensileDefaultService = {
			
		getAll: function(idConto) {
			return MessageService.promiseGET(CONSTANTS.PATH_REST.MENSILE_DEFAULT_ALL + idConto);
		},
		
		get: function(idConto) {
			return MessageService.promiseGET(CONSTANTS.PATH_REST.MENSILE_DEFAULT + '/' + idConto);
		},
		
		check: function(nome) {
			var successCallback = function(response) {
				var dataResponse = {};
				dataResponse.isNew = response.status === 204;
				return dataResponse;
			};
			return MessageService.promiseGET(CONSTANTS.PATH_REST.CONTO_CHECK + nome, successCallback);
		},
		
		inserisci: function(listaTransazioneDefault) {
			return MessageService.promisePUT(CONSTANTS.PATH_REST.MENSILE_DEFAULT, listaTransazioneDefault);
		}
	}
	
	return MensileDefaultService;
});
