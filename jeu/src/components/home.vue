<template>
<div>

  <h1 class='homeh1'>GEOQUIZZ</h1>
  <form @submit="start">
  <div class="form-group">
    <label for="exampleInputEmail1">Nom du joueur</label>
    <input v-model="name" type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Entrez nom" required>
  </div>
  <div class="form-group">
    <label for="exampleSelect1">Ville</label>
    <select required class="form-control" id="exampleSelect1"  v-model="selected">
    <option disabled value="">Choisissez</option>
    <option  v-for="(v,index) in ville">{{v.map.ville}}</option>
    </select>
  </div>
  <div class="form-group">
    <label for="exampleSelect1">Difficultés</label>
    <select required class="form-control" id="exampleSelect1"  v-model="diff">
    <option disabled value="">Choisissez</option>
    <option>Facile</option>
    <option>Normale</option>
    <option>Difficile</option>
    </select>
  </div>
  <button type="submit" id="com" class="btn btn-primary">Commencez</button>
</form>
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
      partie:[],
      selected:'',
      name:'',
      diff:'',
      ville:[]
    }
  },
  created(){
    api.get('/maps')
    .then((response) => {
      this.ville=response.data.maps
    })
    .catch(function (error) {
      alert('Service indisponible');
    });
  },
  methods:{
    start(){
    var select;
      for(var i=0;i<this.ville.length;i++){
        if(this.ville[i].map.ville==this.selected){
          select=this.ville[i].map.id
        }
      }
      api.post('/parties',{nbPhotos:"10",joueur:this.name,idMap:select})
      .then((response) => {
        this.partie=response.data;
        sessionStorage.setItem('user',this.partie.partie.token);
        sessionStorage.setItem('diff',this.diff);
        router.push('/jeu/'+this.partie.partie.idMap+'/'+this.partie.partie.id+'/'+this.partie.partie.token);
      })
      .catch(function (error) {
        alert('Service indisponible');
      });

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
