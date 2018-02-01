/*global cordova, module*/

module.exports = {
    greet: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "MyKadPlugin", "greet", [name]);
    },
    connect: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "MyKadPlugin", "connect", [name]);
    },
    read: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "MyKadPlugin", "read", [name]);
	}
};

