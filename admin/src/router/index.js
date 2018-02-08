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
      path:'/photos/:idMap',
      name:'photos',
      component:photo
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.name == 'Connexion' && localStorage.getItem("token") != undefined) {
    next({name: 'admin' })
  }
  else if (to.name != 'Connexion' && localStorage.getItem("token") == undefined ){
    next({name: 'Connexion' })
  }
  else{
   next()
 }
})

export default router
