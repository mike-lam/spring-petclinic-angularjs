'use strict';

angular.module('ownerList')
    .controller('OwnerListController', ['$http', function ($http) {
        var self = this;

        $http.get('owners').then(function (resp) {
            self.owners = resp.data._embedded.owners;
            angular.forEach(self.owners, function (value, key) {
            	value.pets='';
                $http.get(value._links.pets.href).then(function (respP) {
                    angular.forEach(respP.data._embedded.pets, function (pet, key) {
                    	value.pets=value.pets+' '+pet.name;
                    });
                });
            });            
        });
    }]);
