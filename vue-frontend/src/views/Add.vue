<template>
  <v-container grid-list-md text-xs-center>
    <v-layout row wrap>
      <v-flex mb-5 xs12>
        <h1>NEW AUCTION</h1>
      </v-flex>
      <v-flex xs7>
        <v-text-field label="Title of the auction" solo required id="title"></v-text-field>
      </v-flex>
      <v-flex xs5>
        <v-date-picker id="datePicker" v-model="picker" :landscape="landscape" :reactive="reactive"></v-date-picker>
      </v-flex>
      <v-flex xs7 id="description">
        <v-textarea solo name="input-7-4" id="productDescription" label="Add description" value required></v-textarea>
      </v-flex>
      <v-flex xs4></v-flex>

      <v-flex xs2 id="price">
        <v-text-field label="Asking Price" id="price" required solo></v-text-field>
      </v-flex>
      <v-flex xs10></v-flex>
      <v-flex class="mb-5" xs2>
        <v-btn raised @click="onPickFile">Add Image</v-btn>
        <input
          type="file"
          style="display:none"
          ref="fileInput"
          accept="image/*"
          @change="onFilePicked"
        >
      </v-flex>
      <v-flex sm2>
        <img :src="imageUrl" height="150">
      </v-flex>
    </v-layout>
    <v-btn>Add Auction</v-btn>
  </v-container>
</template>


<script>
export default {
  components: {},
  data() {
    return {
      imageUrl: "",
      image: null
    };
  },
  methods: {
    addImage() {
      this.dialog = false;
      this.profilePic = this.$store.state.profilePicture;
    },
    onPickFile() {
      this.$refs.fileInput.click();
    },
    onFilePicked(event) {
      let files = event.target.files;
      let fileName = files[0].name;
      if (fileName.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!");
      }
      const fileReader = new FileReader();
      fileReader.addEventListener("load", () => {
        this.imageUrl = fileReader.result;
      });
      fileReader.readAsDataURL(files[0]);
      this.image = files[0];
    }
  },
  computed: {
    
  }
};
</script>

<style>
#description{
  margin-top: -220px;
}
#price{

}
</style>
