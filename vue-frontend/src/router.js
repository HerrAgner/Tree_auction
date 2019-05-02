import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";


Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: "/",
      name: "home",
      component: Home
    },
    {
      path: "/page/:page",
      name: "home/page",
      component: Home
    },
    {
      path: "/search/:search/page/:page",
      name: "home/search",
      component: Home
    },
    {
      path: "/login",
      name: "LoginRegister",
      component: () =>
        import("./views/LoginRegister.vue")
    },
    {
      path: "/chat/:id/:user",
      name: "Chatroom",
      component: () =>
        import("./views/Chatroom.vue")
    },
    {
      path: "/auction/:id",
      name: "AuctionDetailsView",
      component: () =>
          import("./views/AuctionDetailsView.vue")
    },
    {
      path: "/add-auction",
      name: "AddAuction",
      component: () =>
        import(/* webpackChunkName: "about" */ "./views/Add.vue")
    },
    {
      path: "*",
      name: "Missing-Page",
      component: () =>
       import("./views/MissingPage.vue")
    }
  ]
});