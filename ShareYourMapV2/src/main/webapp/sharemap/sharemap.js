/*var map = L.map('map');
map.setView([48.8266496,2.3826648], 20);
var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png');
map.addLayer(osmLayer);
L.Routing.control({
    geocoder: L.Control.Geocoder.nominatim()
}).addTo(map);

var ma = L.marker([48.8266496,2.3826648]).on("click",onClick);
ma.addTo(map);
var popup = L.popup();

function onClick(e) {
    popup
        .setLatLng(e.latlng)
        .setContent("You clicked the map at " + e.zoom.toString())
        .openOn(map);
}

$("#bt").click(show);

function show() {
    if( document.getElementById("toggle").style.transform == "translateX(0px)"){
        document.getElementById("toggle").style.transform = "translateX(-340px)";
        document.getElementById("bt").style.transform = "translateX(-200px)";
    }
    else {
        document.getElementById("toggle").style.transform = "translateX(0px)";
        document.getElementById("bt").style.transform = "translateX(0px)";
    }
}
*/
$(function (){

    var mymap = L.map('map').setView([51.505, -0.09], 13);
    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
    maxZoom: 18,
    id: 'mapbox/streets-v11',
    tileSize: 512,
    zoomOffset: -1,
    accessToken: 'pk.eyJ1IjoicGxhZGp5ODIiLCJhIjoiY2s4enhvdmExMW1uNTNybzc2MW96NnFscSJ9.GkIyvTIYw_XrJkBUsHYZlQ'
    }).addTo(mymap);

    L.Routing.control({
        geocoder: L.Control.Geocoder.nominatim()
    }).addTo(mymap);
    
    
});
