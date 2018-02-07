<template>
  <div class="admin container">
    <div class="row">
      <div class="jumbotron">
        <h1>Administrateur</h1>
      </div>

      <div class="col-sm-6">

        <form @submit="Submit">

          <div class="form-group">
            <label for="lat" class="decale">Lattitude</label>
            <input id="lat" class="form-control" type="number" step="0.0000000000000000000000000000001" required>
          </div>

          <div class="form-group">
            <label for="long" class="decale">Longitude</label>
            <input id="long" class="form-control" type="number" step="0.0000000000000000000000000000001"  required>
          </div>


          <div class="form-group">
            <label for="ville" class="decale">Ville</label>
            <input id="ville" class="form-control" type="String" required>
          </div>

          <div class="form-group">
            <label for="dist" class="decale">Distance en mètres</label>
            <input id="dist" class="form-control" type="number" required>
          </div>


          <button class="btn decale" id="sub" type="submit">Ajouter une série</button>
        </form>
      </div>
      <div class="col-sm-6">
        <h3>Liste des séries</h3>
    </div>
  </div>
</div>


</template>

<script>
import axios from 'axios'
import Vue from 'vue'
import Router from 'vue-router'
import router from '../router'

export default {
  name: 'app',
  data () {
    return {
      email: '',
      password: '',
      show: true
    }
  },
  methods: {
    Submit (evt) {
      evt.preventDefault();
      router.push('/serie/map/id');
    },
    onSubmit (evt) {
      evt.preventDefault();
      router.push('/serie');
    }
  }
}

</script>
