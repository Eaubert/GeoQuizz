<template>
  <div>
    <h3>Séries disponibles</h3>
    <ul>
        <li class="series"  v-on:click="addPhoto(item.map)" v-for="item in series">{{ item.map.ville }}</li>
  </ul>
  </div>
</template>

<script>
  import confApi from '../configApi'
  import router from '../router'

  export default {

    name: 'Serie',
    data () {
      return {
        series : ""
      }
    },
    created() {
      confApi.get('/maps').then((response) =>{
        this.series = response.data.maps
      }).catch((error)=> {
        console.log(error);
      })
    },
    methods:{
    }
  }
</script>
