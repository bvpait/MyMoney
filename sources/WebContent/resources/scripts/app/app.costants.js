// scripts/app.costants.js

(function() {

    'use strict';
    
    var myMoneyApp = angular.module('myMoneyApp'),
    	PATH_REST = "rest/",
    	PATH_REST_BENEFICIARIO = PATH_REST + 'beneficiario',
    	PATH_REST_CATEGORIA = PATH_REST + "categoria",
    	PATH_REST_CONTO = PATH_REST + "conto",
    	PATH_REST_BILANCIO_MENSILE = PATH_REST + "bilancio_mensile",
    	PATH_REST_MENSILE_DEFAULT = PATH_REST + "mensile_default";
    
    var CONSTANTS = {
    	"DEBUG": true,
    	"LOGIN_PAGE": "login.html",
    	"ERROR_PAGE": "error.html",
    	"URL_ARG_NAME": "v",
    	"URL_ARG_VALUE": Date.now(),
    	"PATH_REST": {
    		"BENEFICIARIO_ALL"			: PATH_REST_BENEFICIARIO,
    		"BENEFICIARIO_CHECK"		: PATH_REST_BENEFICIARIO + "/check/",
    		"BENEFICIARIO_INSERT"		: PATH_REST_BENEFICIARIO + "/insert",
    		"CATEGORIA_ALL"	  			: PATH_REST_CATEGORIA,
    		"CATEGORIA_INSERT"  		: PATH_REST_CATEGORIA + "/insert",
    		"CATEGORIA_DELETE"  		: PATH_REST_CATEGORIA + "/delete",
    		"CATEGORIA_UPLOAD"  		: PATH_REST_CATEGORIA + "/upload",
    		"CONTO_ALL"					: PATH_REST_CONTO,
    		"CONTO_CHECK"				: PATH_REST_CONTO + "/check/",
    		"CONTO_INSERT"				: PATH_REST_CONTO + "/insert",
    		"BILANCIO_MENSILE"			: PATH_REST_BILANCIO_MENSILE,
    		"BILANCIO_MENSILE_CHECK"	: PATH_REST_BILANCIO_MENSILE + "/check",
    		"BILANCIO_MENSILE_DETTAGLIO": PATH_REST_BILANCIO_MENSILE + "/dettaglio",
    		"MENSILE_DEFAULT"			: PATH_REST_MENSILE_DEFAULT,
    		"MENSILE_DEFAULT_ALL"		: PATH_REST_MENSILE_DEFAULT + "/all/",
    		"MENSILE_DEFAULT_CHECK"		: PATH_REST_MENSILE_DEFAULT + "/check",
    		"MENSILE_DEFAULT_DETTAGLIO"	: PATH_REST_MENSILE_DEFAULT + "/dettaglio"
    	},
    	"MESI": ["","Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"],
    	"ERROR": ["", "Destinatario non valido", "Importo non valido", "Tipo non valido"],
    	"TIPO_OP": {"NONE": 0, "ADDED": 1, "CHANGED": 2, "REMOVED": 3}
    };
    
    myMoneyApp.constant('CONSTANTS', CONSTANTS);
    
    return myMoneyApp;
})();