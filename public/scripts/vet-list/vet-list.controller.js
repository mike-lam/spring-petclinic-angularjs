'use strict';

angular.module('vetList')
    .controller('VetListController', ['$http', function ($http) {
        var self = this;

        $http.get('vets').then(function (resp) {
            self.vetList = resp.data._embedded.vets;
            angular.forEach(self.vetList, function (value, key) {
            	value.specialties='';
                $http.get(value._links.specialties.href).then(function (respS) {
                    angular.forEach(respS.data._embedded.specialties, function (spec, key) {
                    	value.specialties=value.specialties+' '+spec.name;
                    });
                });
            });            
        });
    }]);
