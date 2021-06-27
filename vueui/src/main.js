import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import axios from 'axios'
import mavonEditor from 'mavon-editor'
import 'element-ui/lib/theme-chalk/index.css'
import 'mavon-editor/dist/css/index.css'


Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.prototype.$axios = axios
axios.defaults.withCredentials=true;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
