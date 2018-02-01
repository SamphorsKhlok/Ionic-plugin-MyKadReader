# Ionic-plugin-MyKadReader
Ionic or Cordova plugin to read info from MyKad (Malaysia ID card)

Please refer to Docs folder for more detail on how to read the data. There is a reference manual and a website link. The code is quite robust and have been implemented for more than 2 years.

This software is to be used together with portable smart card scanner,
https://www.acs.com.hk/en/products/228/acr38u-nd-pocketmate-smart-card-reader-micro-usb/

```
#!javascript code in AngularJS

$scope.ConnectMyCardPlugin = function (){
    var q = $q.defer();
    q.notify('Connecting card');

    MyKadReader.connect("mReg", function(result){
        q.resolve(result);
    },function(error){
        q.reject(error);
    });
    return q.promise;
};

$scope.ReadMyCardPlugin = function(){
    var q = $q.defer();
    q.notify('Reading card');

    MyKadReader.read("mReg", function (result) {
        q.resolve(result);
    },function(error){
        q.reject(error);
    });
    return q.promise;
};
```
