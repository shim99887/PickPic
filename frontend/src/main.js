import Vue from 'vue'
import vuetify from './plugins/vuetify'
import './plugins/base'
import App from './App.vue'
import router from './router'
import store from './store'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import "@/fontAwesomeIcon.js";
import "tui-image-editor/dist/tui-image-editor.css";
import VueSimpleAlert from "vue-simple-alert";

Vue.use(VueSimpleAlert);
Vue.use(BootstrapVue)
Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
