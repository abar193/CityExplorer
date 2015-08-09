var map;
var platform;
var bounds = new H.geo.Rect(52.55405421, 13.28671947, 52.46629387, 13.47898021);
var coord;
var marker;
var behavior;
var points = [];

function init() {
    platform = new H.service.Platform({
      app_id: 'xlhHum3tyAYaSM8AgAdN',
      app_code: 'ZT9ron0l8A_zdJWejvAv2Q',
      useCIT: true,
      useHTTPS: true
    });
    var defaultLayers = platform.createDefaultLayers();

    map = new H.Map(document.getElementById('mapContainer'), defaultLayers.normal.map);

    behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

    var ui = H.ui.UI.createDefault(map, defaultLayers);
    centerAt();
    addRectangleToMap(map);
    setUpClickListener();
    //ajaxRequest("/shape/0/points", rectanglePoints, dataPrinter, "GET");
}

function centerAt() {
    var cameraData = map.getCameraDataForBounds(bounds);
    map.setZoom(cameraData.zoom, true);
    map.setCenter(cameraData.position, true);

    map.getViewModel().addEventListener('sync', function() {
        var center = map.getCenter();

        if (!bounds.containsPoint(center)) {
          if (center.lat > bounds.getTop()) {
            center.lat = bounds.getTop();
          } else if (center.lat < bounds.getBottom()) {
            center.lat = bounds.getBottom();
          }
          if (center.lng < bounds.getLeft()) {
            center.lng = bounds.getLeft();
          } else if (center.lng > bounds.getRight()) {
            center.lng = bounds.getRight();
          }
          map.setCenter(center);
        }
      });
}

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

function dataPrinter(data) {}

function sendData() {
    if(window.userName) {
        ajaxRequest("/point/new?user=" + window.userName, updateUser, dataPrinter, "PUT", coord);
    }
}

function appendData() {
    if(window.userName) {
        ajaxRequest("/point/old?user=" + window.userName, updateUser, dataPrinter, "PUT", coord);
    }
}

function setUpClickListener() {
    map.addEventListener('tap', function (evt) {
        coord = map.screenToGeo(evt.currentPointer.viewportX, evt.currentPointer.viewportY);
        addMarker();
    });
}

function addMarker() {
    if(!marker) {
        marker = new H.map.Marker(coord);
        marker.draggable = true;
        map.addObject(marker);
        map.addEventListener('dragstart', function(ev) {
            var target = ev.target;
            if (target instanceof H.map.Marker) {
              behavior.disable();
            }
          }, false);

          // re-enable the default draggability of the underlying map
          // when dragging has completed
          map.addEventListener('dragend', function(ev) {
            var target = ev.target;
            if (target instanceof mapsjs.map.Marker) {
              behavior.enable();
            }
          }, false);

          // Listen to the drag event and move the position of the marker
          // as necessary
           map.addEventListener('drag', function(ev) {
            var target = ev.target,
                pointer = ev.currentPointer;
            if (target instanceof mapsjs.map.Marker) {
              coord = map.screenToGeo(pointer.viewportX, pointer.viewportY);
              target.setPosition(coord);
            }
          }, false);
    } else {
        marker.setPosition(coord);
    }
}

function addRectangleToMap(map) {
  var customStyle = {
    strokeColor: 'rgba(18, 65, 145, 1)',
    fillColor: 'rgba(255, 50, 85, 0.3)',
    lineWidth: 3,
    lineCap: 'square',
    lineJoin: 'bevel'
  };
  map.addObject(new H.map.Rect(bounds, { style: customStyle }));
}

function fillUserInfo(data) {
    console.log(data['result']);
    $('#userinfo').html("Total covered: " + (data['result']) + "%");
}

function rectanglePoints(data) {
    var customStyle = {
        fillColor: 'rgba(0, 0, 250, 0.6)',
        lineWidth: 0
    };
    for(i in data) {
        var point = data[i];
        var pObj = new H.map.Circle({lat: point.lat, lng: point.lng}, point.radiusKm * 1000,
            { style: customStyle });
        map.addObject(pObj);
    }
}

function fillPoints(data) {
    map.removeObjects(points);
    points = [];
    var customStyle = {
        fillColor: 'rgba(92, 234, 0, 0.4)',
        lineWidth: 0
    };
    var array = data;
    for(i in array) {
        var point = data[i];
        var pObj = new H.map.Circle({lat: point.lat, lng: point.lng}, point.radiusKm * 1000,
                       { style: customStyle });
        points.push(pObj);
        map.addObject(pObj);

    }
    console.log("Finish!");
}

function applyUser() {
    window.userName = $('#textInput').val();
    console.log(window.userName);
    updateUser();
}

function updateUser() {
    ajaxRequest("/greeting?user=" + window.userName, dataPrinter, dataPrinter, "GET");
    ajaxRequest("/shape/0?user=" + window.userName, fillUserInfo, dataPrinter, "GET");
    ajaxRequest("/points?user=" + window.userName, fillPoints, dataPrinter, "GET");
}

function percentage() {
    ajaxRequest("/shape/0?user=" + window.userName, fillUserInfo, dataPrinter, "GET");
}
