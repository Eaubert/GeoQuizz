<template>
<div>
<div id="mapid" v-on:click.capture="onMapClick" >
  <v-map :zoom=13 :center="[jeu.mlat ,jeu.mlng ]" ref="map">
    <v-tilelayer url="https://api.tiles.mapbox.com/v4/mapbox.streets/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibmV4emlvIiwiYSI6ImNqZDI0Z2xsejV0ZTMycXJvZG9tMGE0bjIifQ.CHThkqzyGXGNhCh9KRnUpw"></v-tilelayer>
    <v-marker :lat-lng="[jeu.lat, jeu.lng]">
    </v-marker>
  </v-map>
</div>
<div id="image"><img v-bind:src="jeu.url"/></div>
<div id="question">Replacer l'image au bon endroit !</div>
<div id="score">{{jeu.score}}</div>
</div>
</template>

<script>

const jeu = {
      score:0,
      lat:0,
      lng:0,
      mlat:0,
      mlng:0,
      ilat:0,
      ilng:0,
      url:'',
      distance:0,
      temps:0
}

import Vue from 'vue'
import Router from 'vue-router'
import router from '../router'
import Vue2Leaflet from 'vue2-leaflet';
import geodist from 'geodist';
import Vuex from 'vuex'
import api from '../api/api'

export default {
name: 'app',
  data () {
    return {
      images:[],
      len:0,
      jeu,
      maps:[]
    }
  },
  components: {
			'v-map': Vue2Leaflet.Map,
      'v-tilelayer': Vue2Leaflet.TileLayer,
      'v-marker': Vue2Leaflet.Marker,
      'v-popup': Vue2Leaflet.Popup
		},
  created () {
    if(this.$route.params.token!=sessionStorage.getItem('user')){
      alert('token invalide');
      router.push('/');
    }
    api.get('/maps/'+this.$route.params.map)
    .then((response) => {
      this.maps=response.data;
      jeu.mlat=this.maps.map.latitude;
      jeu.mlng=this.maps.map.longitude;
      jeu.distance=this.maps.map.distance;
    })
    .catch(function (error) {
      alert('Map indisponible');
    });
    api.get('/parties/'+this.$route.params.id+'/photos?token='+sessionStorage.getItem('user'))
    .then((response) => {
      this.images=response.data;
      jeu.url=this.images.photos[0].photo.url;
    })
    .catch(function (error) {
      alert('Images indisponible');
    });
    jeu.score=0;
    jeu.lat=0;
    jeu.lng=0;
    jeu.temps=new Date();
  },
  methods:{
    onMapClick(e) {
    if(this.len<10){
      jeu.ilng=this.images.photos[this.len].photo.longitude;
      jeu.ilat=this.images.photos[this.len].photo.latitude;
      this.len+=1;
      if(this.len!=10){
        jeu.url=this.images.photos[this.len].photo.url;
      }
      else{
          jeu.url=this.images.photos[this.len-1].photo.url;
      }
          this.$refs.map.mapObject.on('click',function(e){

            var temps_fin=new Date();
            var total=(temps_fin-jeu.temps)/1000
            var multiplicateur=1;

            if(total<5){
              multiplicateur=4
            }
            else if(total<10){
              multiplicateur=2
            }

            var curseur ={lat:e.latlng.lat , lon:e.latlng.lng };
            var img={lat:jeu.ilat, lon:jeu.ilng};
            var dist=Math.round(geodist(curseur, img, {exact: true, unit: 'meters'})*100)/100+" mètres";

            if(Math.round(geodist(curseur, img, {exact: true, unit: 'meters'})*100)/100>1000){
              dist=Math.round(geodist(curseur, img, {exact: true, unit: 'km'})*100)/100+" km"
            }

            var res="vous êtes à: "+dist;
            var D=jeu.distance;
            var c1;
            var c2;
            var c3;

            if(sessionStorage.getItem('diff')=='Facile'){
              c1=D*5;
              c2=D*10;
              c3=D*15;
            }
            else if(sessionStorage.getItem('diff')=='Normale'){
              c1=D;
              c2=D*5;
              c3=D*10;
            }
            else if(sessionStorage.getItem('diff')=='Difficile'){
              c1=D/2;
              c2=D;
              c3=D*2;
            }

            if(geodist(curseur, img, {exact: true, unit: 'meters'})<c1){
              jeu.score=jeu.score+5*multiplicateur;
              res+=" Vous avais gagné "+5*multiplicateur+" points";
            }
            else if(geodist(curseur, img, {exact: true, unit: 'meters'})<c2){
            jeu.score=jeu.score+2*multiplicateur;
              res+=" Vous avais gagné "+2*multiplicateur+" points";
            }
            else if(geodist(curseur, img, {exact: true, unit: 'meters'})<c3){
              jeu.score=jeu.score+1*multiplicateur;
              res+=" Vous avais gagné "+1*multiplicateur+" points";
            }
            else if(geodist(curseur, img, {exact: true, unit: 'meters'})>=c3){
              res+="Vous êtes trop loin";
            }
            jeu.lat=img.lat;
            jeu.lng=img.lon;
            alert(res);
            this.removeEventListener("click");
            jeu.temps=new Date();
          });
    }
    else{
        alert('partie finie');
        router.push('/sauvegarde/'+this.$route.params.map+'/'+jeu.score+'/'+this.$route.params.id);
      }
    }
  }

}

</script>

<style lang="scss">
  @import 'src/assets/scss/design.scss';

html{
  background: url('/./static/home.jpg') no-repeat center fixed;
}
</style>
