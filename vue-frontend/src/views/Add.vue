<template>
  <v-container grid-list-md text-xs-center>
    <v-layout row wrap>
      <v-flex mb-5 xs12>
        <h1>NEW AUCTION</h1>
      </v-flex>
      <v-flex xs7>
        <v-text-field
          label="Title of the auction"
          :rules="rules"
          counter="40"
          solo
          required
          id="title"
          v-model="title"
        ></v-text-field>
      </v-flex>
      <v-flex xs5>
        <v-date-picker id="datePicker" v-model="date" :min="minDate" :max="maxDate"></v-date-picker>
      </v-flex>

      <v-flex xs7 id="productDescription">
        <v-textarea
          :rules="textRules"
          counter="300"
          solo
          name="input-7-4"
          v-model="description"
          id="description"
          label="Add description"
          value
          required
        ></v-textarea>
      </v-flex>
      <v-flex xs4></v-flex>

      <v-flex xs2 id="price">
        <v-text-field
          v-model="price"
          label="Asking Price"
          id="price"
          prefix="£"
          mask="###########"
          required
          solo
        ></v-text-field>
      </v-flex>
      <v-flex xs10></v-flex>
      <v-flex class="mb-5" xs2>
        <form method="POST" enctype="multipart/form-data" action="/upload-files">
        <v-btn raised @click="onPickFile">Add Image</v-btn>
        <input
          type="file"
          style="display:none"
          ref="fileInput"
          accept="image/*"
          @change="onFilePicked"
        >
        </form>
      </v-flex>
      <v-flex sm2 class="imageOne">
        <img :src="imageUrl" height="200">
      </v-flex>
    </v-layout>
    <v-btn :disabled="!formIsValid" @click="addAuction">Add Auction</v-btn>
  </v-container>
</template>


<script>
export default {
  components: {},
  data() {
    return {
      dialog: false,
      profilePic: ""
    };
  },
  methods: {
    addImage() {
      this.dialog = false;
      this.profilePic = this.$store.state.profilePicture;
    }
  },
  computed: {
    formIsValid() {
      return this.title !== "" && this.description !== "" && this.price !== "";
    },
    minDate() {
      const today = new Date().toISOString().slice(0, 10);
      return today;
    },
    maxDate() {
      let maxDate = new Date()
      maxDate.setMonth(maxDate.getMonth()+1)
      maxDate = maxDate.toISOString().slice(0,10)
      return maxDate
    }
  }
};
</script>

<style>
#productDescription {
  margin-top: -220px;
}
.imageOne{
  margin-top: -84px;
  margin-left: 20px;
}
</style>
