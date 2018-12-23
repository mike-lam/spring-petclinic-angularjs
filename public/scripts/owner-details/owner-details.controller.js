'use strict';

angular.module('ownerDetails')
    .controller('OwnerDetailsController', ['$http', '$stateParams', function ($http, $stateParams) {
        var self = this;
        $http.get('owners/' + $stateParams.ownerId).then(function (resp) {
            self.owner = resp.data;
            //angular.forEach(self.owner.pets, function (value, key) {
            alert(resp.data._links.pets.href);
                $http.get(resp.data._links.pets.href).then(function (resp) {
                	alert(JSON.stringify(resp.data));
                	debugger;
                    value.owner.pets=resp.data;
                });
            //});            
            angular.forEach(self.owner.pets, function (value, key) {
                $http.get('visits/search/pet?petId='+value.id).then(function (resp) {
                    value.visits=resp.data._embedded.visits;
                });
            });            
         });
    }]);
