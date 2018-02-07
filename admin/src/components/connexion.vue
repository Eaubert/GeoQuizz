<template>
  <div class="container">
    <div class="row">
      <div class="col-sm-6">
        <form @submit="onSubmit">
          <h1>Connexion</h1>
          <div class="form-group">
            <label for="email" class="decale">Adresse mail:</label>
            <input type="email" class="form-control" id="email" placeholder="Entrer email">
          </div>
          <div class="form-group">
            <label for="pwd" class="decale">Mot de passe:</label>
            <input type="password" class="form-control" id="pwd" placeholder="Entrer mot de passe">
          </div>
          <button id="subm" type="submit" class="btn decale">Connexion</button>
        </form>
      </div>
      <div class="col-sm-6">
        <h1>Inscription</h1>
        <form @submit="Submit">
          <div class="form-group">
            <label for="nom" class="decale">Nom:</label>
            <input id="nom" class="form-control" type="text" required>
          </div>
          <div class="form-group">
            <label for="email" class="decale">Email address:</label>
            <input id="mail" class="form-control" type="email" v-model="email" required>
          </div>
          <div class="form-group">
            <label for="mdp" class="decale">Password:</label>
            <input id="mdp" v-model="password" class="form-control" required type="password">
          </div>

          <button id="sub" type="submit" class="btn decale">Inscription</button>
        </form>
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
    onSubmit (evt) {
      evt.preventDefault();
      router.push('/admin');
    },
    Submit (evt) {
      evt.preventDefault();
      router.push('/admin');
    }
  }
}
</script>
