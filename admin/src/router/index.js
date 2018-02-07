import Vue from 'vue'
import Router from 'vue-router'
import admin from '@/components/Admin'
import connexion from '@/components/Connexion'
import serie from '@/components/serie'
import photo from '@/components/photo'

Vue.use(Router)

const router = new Router({
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
    },
    {
      path:'/photo/:idMap',
      name:'photo',
      component:photo
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.name == 'Connexion' && sessionStorage.getItem("isConnected") == "Connect") {
    next({name: 'admin' })
  }
  else if (to.name != 'Connexion' && sessionStorage.getItem("isConnected") != "Connect" ){
    next({name: 'Connexion' })
  }
  else{
   next()
 }
})

export default router
