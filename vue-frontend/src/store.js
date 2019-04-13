import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
const API_URL = "http://localhost:7999/api/";
export default new Vuex.Store({
  state: {
    profilename: "",
    header: "",
    profilePicture: "",
    text: "",
    posts: []
  },
  mutations: {
    setProfilePicture(state, image) {
      state.profilePicture = image;
    },
    setPosts(state, posts) {
      state.posts = posts;
    }
  },
  actions: {
    async getPostsFromDb() {
      let posts = await (await fetch(API_URL + "posts")).json();
      this.commit("setPosts", posts);

    },
    async addPostToDB(state, reqBody) {
      await fetch(API_URL + "posts", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: { "Content-Type": "application/json" }
      });

      // Update the state.blogPosts since we just added a new one
      this.dispatch("getPostsFromDb");
    }
  }
});
