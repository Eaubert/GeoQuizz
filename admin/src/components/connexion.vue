<template>
  <div class="container">
    <div class="row">

      <div class="col-sm-6">
        <form @submit="signIn">
          <h1>Connexion</h1>
          <div class="form-group">
            <label for="email" class="decale">Adresse mail:</label>
            <input type="email" v-model="emailCo"  class="form-control" id="email" placeholder="Entrer email">
          </div>
          <div class="form-group">
            <label for="pwd" class="decale">Mot de passe:</label>
            <input type="password" v-model="mdpCo" class="form-control" id="pwd" placeholder="Entrer mot de passe">
          </div>
          <button id="subm" type="submit" class="btn decale">Connexion</button>
        </form>
      </div>

      <div class="col-sm-6">
        <h1>Inscription</h1>
        <form @submit="signUp">
          <div class="form-group">
            <label for="pseudo" class="decale">Pseudo:</label>
            <input id="pseudo"  v-model="pseudoInscr" class="form-control" type="text" required>
          </div>
          <div class="form-group">
            <label for="email" class="decale">Email address:</label>
            <input id="mail" class="form-control"  v-model="emailInscr" type="email" required>
          </div>
          <div class="form-group">
            <label for="mdp" class="decale">Password:</label>
            <input id="mdp"  v-model="mdpInscr" class="form-control" required type="password">
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
import confApi from '../configApi'

export default {
  name: 'app',
  data () {
    return {
      emailInscr: '',
      pseudoInscr: '',
      mdpInscr: '',
      emailCo : '',
      mdpCo : '',
      show: true
    }
  },
  methods: {
    signUp () {

      var user = {
          "email": this.emailInscr ,
        	"pseudo": this.pseudoInscr ,
        	"mdp": this.mdpInscr
        }

      confApi.post('/users', user).then((response)=> {
          confApi.post('/authentification', user).then((response)=> {
              sessionStorage.setItem("isConnected", "Connect")
            router.push('/admin')
          })
      }).catch((error)=> {
          alert(error);
      })
    },

    signIn () {

      var user = {
          "email": this.emailCo ,
        	"mdp": this.mdpCo
      }

      confApi.post('/authentification', user ).then((response)=> {
          sessionStorage.setItem("isConnected", "Connect")
          router.push('/admin')
      }).catch((error)=> {
          alert("Mauvais E-mail ou Mot de passe");
      })
    }
  }
}
</script>
