import Vue from 'vue'
import Router from 'vue-router'
import admin from '@/components/Admin'
import connexion from '@/components/Connexion'
import serie from '@/components/serie'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Connexion',
      component:connexion
    },
    {
      path: '/admin',
      name: 'admin',
      component:admin
    },
    {
      path:'/serie',
      name:'serie',
      component:serie
    }
  ]
})
