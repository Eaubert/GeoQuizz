import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/home'
import jeu from '@/components/jeu'
import save from '@/components/sauvegarde'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/jeu/:map/:id/:token',
      name: 'jeu',
      component: jeu
    },
    {
      path: '/sauvegarde/:map/:score/:id',
      name: 'save',
      component: save
    }
  ]
})
