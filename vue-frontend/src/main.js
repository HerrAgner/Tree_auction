import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import './plugins/vuetify'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.use(Vuetify);
Vue.use(require('vue-moment'));
Vue.config.productionTip = false

let ws;
let isConnected = false;
connect();

function connect() {
  ws = new WebSocket('ws://localhost:7999/api/socket');

  ws.onopen = (e) => {
    isConnected = true;
  };

  ws.onclose = (e) => {
  };

}

function disconnect() {
  if (ws != null) {
    ws.close();
  }
  isConnected = false;
}

// function sendSomething() {
//   ws.send(JSON.stringify({firstname: "Hello World!" }));
// }

// function showSomething(message) {
//   document.querySelector("#messages").innerText += message.firstname + " " + message.lastname + "\n";
// }
export {ws, connect, disconnect}
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
