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
        <h3>Pick an end date for the auction</h3>
        <v-date-picker id="datePicker" 
        v-model="date" 
        :min="minDate" 
        :max="maxDate"
        required
        
        ></v-date-picker>
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
          id="file"
          style="display:none"
          ref="file"
          accept="image/*"
          @change="onFilePicked"
          v-on:change="handleFileUpload"
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
      title: "",
      description: "",
      price: "",
      imageUrl: "",
      image: null,
      file: '',
      date: "",
      rules: [v => v.length <= 40 || "Max 40 characters"],
      textRules: [v => v.length <= 300 || "Max 300 characters"]

    };
  },
  methods: {
    addAuction() {
      if (this.date== '') {
        return alert ("Please pick an end date")
      } else {
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

      const imageData = {
        auction_id: 1,
        picture: this.image 
      }

      // let formData = new FormData();
      // formData = formData.append('file', this.file);
      // console.log("loggar this.file" + this.file)
      // this.$store.dispatch("addPictureToDB", formData)

      this.$store.dispatch("addPictureToDB", imageData);
      this.$store.dispatch("addAuctionToDB", productData);
      // this.$router.push('/') //Går till startsidan
      }
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0];
    },
    onPickFile() {
      this.$refs.file.click();
    },
    onFilePicked(event) {
      let formData = new FormData();

      let files = event.target.files;
      let fileName = files[0].name;

      if (fileName.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!");
      }
      const fileReader = new FileReader();
      fileReader.addEventListener("load", () => {
        this.imageUrl = fileReader.result;
        // console.log("this.imageUrl: " +  this.imageUrl);
      });
      fileReader.readAsDataURL(files[0]);
      this.image = files[0];

      formData.append('image', files[0])
    }
  },
  computed: {
    formIsValid() {
      return this.title !== "" && this.description !== "" && this.price !== "" && this.title.length < 40 && this.description.length < 300;
    },
    minDate() {
      let minDate = new Date()
      minDate.setDate(minDate.getDate()+1)
      minDate = minDate.toISOString().slice(0, 10);
      return minDate;
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
