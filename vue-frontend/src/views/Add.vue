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
        <v-date-picker id="datePicker" v-model="date" :min="minDate" :max="maxDate" required></v-date-picker>
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
        <v-form method="POST" @submit.prevent="addAuction" id="addAuction">
          <v-btn small raised @click="onPickFile">Add Image</v-btn>
          <input
            type="file"
            name="files"
            style="display:none"
            ref="fileInput"
            accept="image/*"
            @change="onFilePicked"
            value="Upload file(s)"
            multiple
          >
          <v-btn small type="submit" form="addAuction" :disabled="!formIsValid">Add Auction</v-btn>
        </v-form>
      </v-flex>
      <v-flex sm2 class="imageOne">
        <v-img :src="imageUrl[0]" height="100" contain/>
        <p>Primary image</p>
      </v-flex>
    </v-layout>
  </v-container>
</template>


<script>
import router from "@/router.js";
export default {
  components: {},
  data() {
    return {
      title: "",
      description: "",
      price: "",
      imageUrl: "",
      image: null,
      images: [],
      date: "",
      rules: [v => v.length <= 40 || "Max 40 characters"],
      textRules: [v => v.length <= 300 || "Max 300 characters"],
      formData: new FormData(),
      fileReader: new FileReader()
    };
  },

  methods: {
    async addAuction() {
      await this.uploadFiles(this.formData)
        .then(image => {
          this.image = image;
          console.log(this.image);
          console.log(this.formData);
          console.log("this.uploadFiles(formData) i addAuction");
        })
        .catch(console.warn);

      if (this.date == "") {
        return alert("Please pick an end date");
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

        const productData = JSON.stringify({
          title: this.title,
          description: this.description,
          start_price: this.price,
          seller_id: "eric.rl@me.com",
          end_time: dateEnd,
          addedTime: dateToday,
          image: this.image[0]
        });
        console.log("this.image[0] " + this.image[0]);

        await this.$store.dispatch("addAuctionToDB", productData);
        // await this.$router.push("/"); //Går till startsidan
      }
    },
    onPickFile() {
      this.$refs.fileInput.click(); //Reffererar till fileInput så man kan ha annat utseende på knapp
    },
    onFilePicked(event) {
      let files = event.target.files;

      if (!files.length) return; //Validering
      let fileName = files[0].name;
      if (fileName.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!"); //Validering av fil
      }

      this.fileReader.addEventListener("load", () => {
        this.imageUrl = ""
        console.log("this.imageUrl" + this.imageUrl)
        this.imageUrl = this.fileReader.result; //För att visa bilden
        console.log("this.fileReader.result: " + this.fileReader.result);
        console.log("this.imageUrl" + this.imageUrl);
      });
      this.fileReader.readAsDataURL(files[0]);
      this.image = files[0];
      this.formData.append("files", files[0], files[0].name);

      console.log("this.formData " + this.formData);
      console.log("this.image " + this.image);
    },
    uploadFiles(formData) {
      //Spara till disk
      console.log("UploadFiles(formData");
      return fetch("http://localhost:7999/upload-files", {
        method: "POST",
        body: formData
      }).then(response => response.json());
    }
  },
  computed: {
    formIsValid() {
      return (
        this.title !== "" &&
        this.description !== "" &&
        this.price !== "" &&
        this.title.length < 40 &&
        this.description.length < 300 &&
        this.image !== null
      );
    },
    minDate() {
      let minDate = new Date();
      minDate.setDate(minDate.getDate() + 1);
      minDate = minDate.toISOString().slice(0, 10);
      return minDate;
    },
    maxDate() {
      let maxDate = new Date();
      maxDate.setMonth(maxDate.getMonth() + 1);
      maxDate = maxDate.toISOString().slice(0, 10);
      return maxDate;
    }
  },
  created: async function() {
    if (this.$store.state.status === false) {
      this.$router.push({ path: "/login" });
    }

    this.imageUrl = [
      {
        src: await fetch("http://localhost:7999/images/noImage.jpg").then(
          res => res.url
        )
      }
    ];
  }
};
</script>

<style>
#productDescription {
  margin-top: -220px;
}
.imageOne {
  margin-left: 20px;
}
</style>
