'use strict';

angular.module('userList', ['ui.router'])
    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider
            .state('users', {
                parent: 'app',
                url: '/users',
                template: '<user-list></user-list>'
            })
    }]);