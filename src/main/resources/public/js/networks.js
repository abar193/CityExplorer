function ajaxRequest(path, success, error, method, body) {
    var my_request = {
        url: path,
        crossDomain: true,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success,
        error: error,
        complete: function() {},
        method: method
    };
    if(body) {
        my_request['data'] = JSON.stringify(body);
        my_request['contentType'] = "application/json; charset=utf-8";
    }
    $.ajax(my_request);
}

function sendData(isAppend, coord) {
    if(window.userName) {
        var path = (isAppend) ? "/point/old?user=" : "/point/new?user=";
        ajaxRequest(path + window.userName, updateUser, dataPrinter, "PUT", coord);
    }
}

function dataPrinter(data) {
    console.log("Error ", data);
}

function updateUser() {
    ajaxRequest("/greeting?user=" + window.userName, dataPrinter, dataPrinter, "GET");
    ajaxRequest("/shape/0?user=" + window.userName, fillUserInfo, dataPrinter, "GET");
    ajaxRequest("/points?user=" + window.userName, fillPoints, dataPrinter, "GET");
}