'use strict';

angular.module('userDetails')
    .controller('UserDetailsController', ['$http', '$stateParams', function ($http, $stateParams) {
        var self = this;
        $http.get('users/' + $stateParams.userId).then(function (resp) {
            self.user = resp.data;
         });
    }]);
