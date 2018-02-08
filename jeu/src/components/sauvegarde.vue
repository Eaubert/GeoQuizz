<template>
<div id="final">
<div id="fscore">
{{score}}
</div>
<div id="b">
<button type="submit" v-on:click="save" id="save" class="btn btn-primary">Sauvegarder</button>
<button type="submit" v-on:click="restart" id="restart" class="btn btn-primary">Recommencer</button>
</div>
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
      score:'Votre score est de '
    }
  },
  created () {
    this.score+=this.$route.params.score+' points';
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
