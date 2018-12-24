'use strict';

angular.module('ownerDetails')
    .controller('OwnerDetailsController', ['$http', '$stateParams', function ($http, $stateParams) {
        var self = this;
        debugger;
        $http.get('owners/' + $stateParams.ownerId).then(function (resp) {
            self.owner = resp.data;
            self.owner.pets=[];
            $http.get(self.owner._links.pets.href).then(function (respP) {
              angular.forEach(respP.data._embedded.pets, function (p, key) {
            	  debugger;
              var pet={name:p.name, birthDate:p.birthDate}
              self.owner.pets.push(pet);
            });
         });
        });
    }]);
