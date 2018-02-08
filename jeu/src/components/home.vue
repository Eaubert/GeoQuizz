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
    <option value="1">Nancy</option>
    <option value="2">Metz</option>
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
      diff:''
    }
  },
  methods:{
    start(){
      api.post('/parties',{nbPhotos:"10",joueur:this.name,idMap:this.selected})
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
