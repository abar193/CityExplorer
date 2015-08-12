function applyUser() {
    window.userName = $('#textInput').val();
    console.log(window.userName);
    updateUser();
}

function appendPoint() {
    sendData(true, getCoord());
}

function newPoint() {
    sendData(false, getCoord());
}

function percentage() {
    ajaxRequest("/shape/0?user=" + window.userName, fillUserInfo, dataPrinter, "GET");
}
