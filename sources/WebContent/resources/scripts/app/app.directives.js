var myMoneyApp = angular.module('myMoneyApp');

myMoneyApp.directive("destinatario", function($compile) {
    return {
        restrict: "E",
        scope: {
            row: "=",
            ctrl: "=",
            id_cat: "@"
        },
        transclude: true,
        controller: ['$scope', function($scope) {
        	
        	function updateRow(selected) {
        		var check;
        		
        		if ($scope.row.isSelected !== undefined) {
        			var cat = $scope.tableMensileDefault.data.find(function(group) {
            			return group.idCategoria === $scope.row.idCategoria;
            		});
            		
            		if (cat !== undefined) {
            			check = cat.data.find(function(elem) {
            				return parseInt(elem.idSottoCategoria) === parseInt($scope.row.idSottoCategoria) && elem.idBeneficiario === selected.id;
            			});
            		}
        		} else {
            		check = $scope.ctrl.listaCategorieMap[$scope.row.idCategoria].find(function(elem) {
        				return parseInt(elem.idSottoCategoria) === parseInt($scope.row.idSottoCategoria) && elem.idBeneficiario === selected.id;
        			});
        		}
        		
        		if (check === undefined) {
        			$scope.row.idBeneficiario = selected.id;
        			$scope.row.nomeBeneficiario = selected.nome;
        			return true;
        		}
        		return false;
        	}
          
        	$scope.chiudiPopupDestinatario = function(selected) {
        		if (selected !== undefined && selected.id !== undefined) {
        			if (!updateRow(selected))
        				alert('Beneficiario già presente.');
        		}
        			
        		this.$parent.isOpened = false;
        	};
        	
        	$scope.rimuoviBeneficiario = function(row) {
        		var selected = {id: -1, nome: ''};
        		if (!updateRow(selected))
        			alert('Presente categoria senza Beneficiario. Rimuovere la riga intera');
        	}
        }],
        templateUrl: 'resources/views/popups/destinatario.html' + $.config.urlArgs
    };
});

myMoneyApp.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;
          
			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);