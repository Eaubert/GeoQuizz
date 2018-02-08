// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuex from 'vuex'

import jQuery from 'jquery'
window.jQuery = window.$ = jQuery
require('bootstrap-sass')

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  data: {
   score:0
  },
  components: { App },
  template: '<App/>'
})
