appmw.controller("loginController", function($scope, $http, fileUpload){
	
	$scope.errorCampo = false;
	
	$http.get('/init').success(function(data){
			$scope.homeVO = data;
		}).error(function(data, status, headers, config) {			
						
		}
	);
	
	$scope.logar = function (){

		if($scope.formLogin.$invalid){
			$scope.errorCampo = true;
			$scope.homeVO.msg = "Favor preencher campos obrigatórios."
			return;
		}
			
		$scope.errorCampo = false;
		$scope.homeVO.msg = null;

		var parametros = {nome:$scope.nomeUsuario,
				senha:$scope.senhaUsuario};
		
		var config = {
				 params: parametros,
				 headers : {'Accept' : 'application/json'}
				};

		$http.get('/logar',config).success(function(data){
				$scope.homeVO = data;
			}).error(function(data, status, headers, config ) {			

			}
		);
	};
	
	$scope.logout = function (){

		$http.get('/logout').success(function(data){
				$scope.homeVO = data;
			}).error(function(data, status, headers, config) {			
							
			}
		);
	};
	
	$scope.stopMusic = function (){

		$http.get('/stopMusic').success(function(data){

			}).error(function(data, status, headers, config) {			
							
			}
		);
	};
	
	
	$scope.cadastrar = function (){
		
		if($scope.formLogin.$invalid){
			$scope.errorCampo = true;
			$scope.homeVO.msg = "Favor preencher campos obrigatórios."
			return;
		}
			
		$scope.errorCampo = false;
		$scope.homeVO.msg = null;
		
		var usuario = {nome:$scope.nomeUsuario,senha:$scope.senhaUsuario};

		$http.post('/cadastrarUsuario',usuario).success(function(data){
				$scope.homeVO = data;
			}).error(function(data, status, headers, config) {			
							
			}
		);
	};
	
	$scope.addMusica = function (){
		
		if($scope.formLogin.$invalid){
			$scope.errorCampo = true;
			$scope.homeVO.msg = "Favor preencher campos obrigatórios."
			return;
		}
		
		$scope.errorCampo = false;
		$scope.homeVO.msg = null;

		$http.post('/addMusica',$scope.homeVO.musica).success(function(data){			
				$scope.homeVO = data;	
			}).error(function(data, status, headers, config) {			
							
			}
		);
	};
	
	$scope.addMusicPlayList = function (musica){

		$http.post('/addMusicPlayList',musica).success(function(data){
			
			$scope.homeVO = data;
	
			}).error(function(data, status, headers, config) {			
							
			}
		);
	};
	
	$scope.removeMusicPlayList = function (musica){

		$http.put('/removeMusicPlayList',musica).success(function(data){
			
			$scope.homeVO = data;
	
			}).error(function(data, status, headers, config) {			
							
			}
		);
	};
	
	$scope.removeMusic = function (musica){

		$http.put('/removeMusic',musica).success(function(data){
			
			$scope.homeVO = data;
	
			}).error(function(data, status, headers, config) {			
							
			}
		);
	};
	
	$scope.verificaForm = function (camp,error){
		if(error && camp)
			return true
			
		return false;
	};
	
	$scope.executePlayList = function (){	
		console.log('executePlayList');
		$scope.playMusic(0);		
	};
	
	$scope.playMusic = function (pos){
		console.log('playMusic - ' + pos);		
		var array = $scope.homeVO.listPlayList.length;
			
		if(pos < array){
			var parametros = {id:$scope.homeVO.listPlayList[pos].id};
			
			var config = {
					 params: parametros,
					 headers : {'Accept' : 'application/json'}
				};
		
			$http.get('/playMusic',config).success(function(data){								
					
				if($scope.contador(data,pos))
					i++;
									
				}).error(function(data, status, headers, config ) {			
			
				});	
		}

	};
	
	$scope.contador = function (tmp,pos){		
		console.log('contador - inicio '+ tmp + ' - ' + pos);
		var temp = tmp*1000;
		
		var intervalo = window.setInterval(function() {
		}, 1000);
	
		window.setTimeout(function() {			
		    clearInterval(intervalo);
		    console.log('contador - fim '+ tmp);
		    $scope.playMusic(pos+1);		    
		}, temp);		
	};
	


    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var uploadUrl = "/upload";

        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(data){
        	
        	$scope.homeVO = data;
        	
        })
        .error(function(){
        });
        
        
        
    };
	
}).directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]).service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
}]);

