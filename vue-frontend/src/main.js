import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import './plugins/vuetify'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

Vue.use(Vuetify);
Vue.config.productionTip = false

let ws;
let isConnected = false;
connect();

function connect() {
  ws = new WebSocket('ws://localhost:7999/api/socket');

  ws.onopen = (e) => {
    isConnected = true;
    console.log("Connected!")
  };

  ws.onclose = (e) => {
    console.log("Closing websocket...");
  };

  console.log("Connecting...");
}

function disconnect() {
  if (ws != null) {
    ws.close();
  }
  isConnected = false;
  console.log("Disconnected");
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
