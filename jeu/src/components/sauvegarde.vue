<template>
<div id="final">
<div id="fscore">
{{score}}
</div>
<div id="b">
<button type="submit" v-on:click="save" id="save" class="btn btn-primary">Sauvegarder</button>
<button type="submit" v-on:click="restart" id="restart" class="btn btn-primary">Recommencer</button>
</div>
<table class="table table-inverse">
  <thead>
    <tr>
      <th></th>
      <th>Map</th>
      <th>Nom</th>
      <th>Score</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">1</th>
      <td>{{map}}</td>
      <td>{{t1.nom}}</td>
      <td>{{t1.score}}</td>
    </tr>
    <tr>
      <th scope="row">2</th>
      <td>{{map}}</td>
      <td>{{t2.nom}}</td>
      <td>{{t2.score}}</td>
    </tr>
    <tr>
      <th scope="row">3</th>
      <td>{{map}}</td>
      <td>{{t3.nom}}</td>
      <td>{{t3.score}}</td>
    </tr>
  </tbody>
</table>
</div>
</template>

<script>
import Vue from 'vue'
import Router from 'vue-router'
import router from '../router'
import api from '../api/api'

export default {
name: 'app',
  data () {
    return {
      score:'Votre score est de ',
      partie:[],
      t1:{"nom":"","score":0},
      t2:{"nom":"","score":0},
      t3:{"nom":"","score":0},
      map:""
    }
  },
  created () {
    this.score+=this.$route.params.score+' points';
    api.get('/maps/'+this.$route.params.map+'/parties')
    .then((response) => {
      this.partie=response.data
      for(var i=0;i<this.partie.parties.length;i++){

        if(this.partie.parties[i].partie.score>this.t1.score){
          this.t2=this.t1
          this.t1={"nom":this.partie.parties[i].partie.joueur,"score":this.partie.parties[i].partie.score}
        }
        else if(this.partie.parties[i].partie.score>this.t2.score){
          this.t3=this.t2
          this.t2={"nom":this.partie.parties[i].partie.joueur,"score":this.partie.parties[i].partie.score}
        }
        else if(this.partie.parties[i].partie.score>this.t3.score){
          this.t3={"nom":this.partie.parties[i].partie.joueur,"score":this.partie.parties[i].partie.score}
        }
      }
    })
    .catch(function (error) {
    });
    api.get('/maps/'+this.$route.params.map)
    .then((response) => {
      this.map=response.data.map.ville
    })
    .catch(function (error) {
      alert('Service indisponible');
    });
  },
  methods:{
    save(){
    api.put('/parties/'+this.$route.params.id,{score:this.$route.params.score})
    .then((response) => {
      alert('Partie sauvegardée')
    })
    .catch(function (error) {
      alert('Service indisponible');
    });
    },
    restart(){
      router.push('/')
    },
  }
}

</script>


<style lang="scss">
  @import 'src/assets/scss/design.scss';

html{
  background: url('/./static/home.jpg') no-repeat center fixed;
}
</style>
