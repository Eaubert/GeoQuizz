<template>

    <div class="admin container">

        <div class="row">
            <div class="jumbotron">
                <h1>Administrateur</h1>

                <div class="row">
                  <div class="col-sm-10"></div>
                  <div><button  v-on:click="deconnexion()" class="btn-primary col-sm-2">Deconnexion</button></div>
                </div>

            </div>

            <div class="col-sm-6">

                <form @submit="addMap()">

                    <div class="form-group">
                        <label for="lat" class="decale">Lattitude</label>
                        <input id="lat" v-model="latitude" class="form-control" type="number" step="0.000001" required>
                    </div>

                    <div class="form-group">
                        <label for="long" class="decale">Longitude</label>
                        <input id="long" v-model="longitude" class="form-control" type="number" step="0.000001"
                               required>
                    </div>


                    <div class="form-group">
                        <label for="ville" class="decale">Ville</label>
                        <input id="ville" v-model="ville" class="form-control" type="String" required>
                    </div>

                    <div class="form-group">
                        <label for="dist" class="decale">Distance en mètres</label>
                        <input id="dist" v-model="distance" class="form-control" type="number" required>
                    </div>


                    <button class="btn decale" id="sub" type="submit">Ajouter une série</button>
                </form>
            </div>
            <div class="col-sm-6">
                <series :series="series">

                </series>
            </div>
        </div>
    </div>
  </div>


</template>

<script>

  import confApi from '../configApi'
  import Vue from 'vue'
  import router from '../router'
  import series from './serie'

  export default {
    name: 'app',
    components: {
      series
    },
    data () {
      return {
        email: '',
        password: '',
        series: '',
        latitude: '',
        longitude: '',
        ville: '',
        distance: '',
        show: true
      }
    },
    methods: {
      addMap () {
        var map = {
          "latitude": this.latitude.toString(),
          "longitude": this.longitude.toString(),
          "ville": this.ville,
          "distance": this.distance.toString(),
        }

        confApi.post('/maps', map, {headers: {Authorization: localStorage.getItem("token")}}).then((response) => {
          router.push('/photos/' + response.data.map.id)
        }).catch((error) => {
          localStorage.removeItem("token")
          alert("Veuillez vous reconnecter")
          router.push('/')
        })
      },
      deconnexion(){
        localStorage.removeItem("token")
        router.push('/')
      }
    }
  }

</script>
