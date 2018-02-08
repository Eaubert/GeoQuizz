<template>
  <div>
    <h3>Séries disponibles</h3>
    <ul class="list-group series">
        <li class="list-group-item"  v-on:click="addPhoto(item.map)" v-for="item in series"><h4>{{ item.map.ville }}</h4></li>
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
      addPhoto(map){
        router.push('/photo/'+map.id)
      }
    }
  }
</script>
