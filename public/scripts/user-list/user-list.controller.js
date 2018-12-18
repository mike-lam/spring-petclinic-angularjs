'use strict';

angular.module('userList')
    .controller('UserListController', ['$http', function ($http) {
        var self = this;

        $http.get('users').then(function (resp) {
            self.users = resp.data._embedded.users;
        });
    }]);
