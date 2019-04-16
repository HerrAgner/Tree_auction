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
    <v-btn :disabled="!formIsValid" @click="addAuction">Add Auction</v-btn>
  </v-container>
</template>


<script>
export default {
  components: {},
  data() {
    return {
      title: "",
      description: "",
      price: "",
      imageUrl: "",
      image: null,
      date: "",
      rules: [v => v.length <= 40 || "Max 40 characters"],
      textRules: [v => v.length <= 300 || "Max 300 characters"]
    };
  },
  methods: {
    addAuction() {
      var dateToday = new Date();
      var dateEnd = new Date(
        this.date +
          " " +
          dateToday.getHours() +
          ":" +
          dateToday.getMinutes() +
          ":" +
          dateToday.getSeconds()
      );
      dateEnd = dateEnd.toISOString().replace("Z", "+0000");

      const productData = {
        title: this.title,
        description: this.description,
        start_price: this.price,
        seller_id: "eric.rl@me.com",
        end_time: dateEnd,
        added_time: dateToday
      };
      this.$store.dispatch("addAuctionToDB", productData);
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
        console.log(this.imageUrl);
      });
      fileReader.readAsDataURL(files[0]);
      this.image = files[0];
      console.log(fileName);
      console.log(this.image);
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
</style>
