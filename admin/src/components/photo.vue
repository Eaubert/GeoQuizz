<template>
  <div class="admin container">
    <div class="row">
      <div class="jumbotron">
        <h1>Photo</h1>
      </div>

    <div class="col-sm-3"></div>
    <div class="col-sm-6">
      <form @submit="addPhoto">
        <div class="form-group">
          <label for="lat" class="decale">Lattitude</label>
          <input id="lat"  v-model="latitude" class="form-control" type="number" step="0.000001" required>
        </div>

        <div class="form-group">
          <label for="long" class="decale">Longitude</label>
          <input id="long"  v-model="longitude" class="form-control" type="number" step="0.000001"  required>
        </div>

        <div class="form-group">
          <label for="img" class="decale">Image</label>
          <input id="img"  class="form-control form-control-file" type="file" required>
        </div>

        <button id="subm" type="submit" class="btn decale ">Ajouter photo</button>
      </form>
    </div>
  </div>
</div>
</template>

<script>
  import confApi from '../configApi'
  import router from '../router'

  export default {

    name: 'photo',
    data () {
      return {
        idmap : "",
        latitude :"",
        longitude:""
      }
    },
    methods:{
      addPhoto(){
        console.log("test")
      }
    }
  }
</script>
