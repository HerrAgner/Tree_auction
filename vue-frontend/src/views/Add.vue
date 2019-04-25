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
          <v-btn class="imageBtn" small raised @click="onPickFilePrimary
    ">Add Image</v-btn>
          <input
            type="file"
            name="filesPrimary"
            style="display:none"
            ref="fileInputPrimary"
            accept="image/*"
            @change="onFilePicked"
            value="Upload file(s)"
          >
          <v-btn class="imageBtn2" small raised @click="onPickFileSecondary
    ">Add Image</v-btn>
          <input
            type="file"
            name="filesSecondary"
            style="display:none"
            ref="fileInputSecondary"
            accept="image/*"
            @change="onSecondFilePicked"
            value="Upload file(s)"
          >

          <v-btn class="imageBtn3" small raised @click="onPickFileThird
    ">Add Image</v-btn>
          <input
            type="file"
            name="filesThird"
            style="display:none"
            ref="fileInputThird"
            accept="image/*"
            @change="onThirdFilePicked"
            value="Upload file(s)"
          >
          <v-btn small type="submit" form="addAuction" :disabled="!formIsValid">Add Auction</v-btn>
        </v-form>
      </v-flex>
      <v-flex sm2 class="imageOne">
        <v-img :src="imageUrl" height="100" contain/>
        <p>Primary image</p>
      </v-flex>
      <v-flex sm2 class="imageOne">
        <v-img :src="imageUrlExtra1" height="100" contain/>
        <p>Extra image</p>
      </v-flex>
      <v-flex sm2 class="imageOne">
        <v-img :src="imageUrlExtra2" height="100" contain/>
        <p>Extra image</p>
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
      imageUrlExtra1: "",
      imageUrlExtra2: "",
      image: null,
      images: [],
      date: "",
      rules: [v => v.length <= 40 || "Max 40 characters"],
      textRules: [v => v.length <= 300 || "Max 300 characters"],
      fileReader: new FileReader(),
      formData: new FormData(),
      formDataSecond: new FormData()
    };
  },

  methods: {
    async addAuction() {
      await this.uploadFiles(this.formData)
        .then(image => {
          this.image = image;
        })
        .catch(console.warn);

      console.log("Från addAuction " + this.formDataSecond);
      await this.uploadFiles(this.formDataSecond)
        .then(images => {
          this.images = images;
        })
        .catch(console.warn)
      console.log("Från addAuction " + this.formDataSecond);

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

        await this.$store.dispatch("addAuctionToDB", productData);
        // await this.$router.push("/"); //Går till startsidan

        console.log(this.images.length)
        for (let i = 0; i<this.images.length; i++) {

          const imageData = JSON.stringify({
            picture: this.images[i],
            auction_id: "10"
          });
          console.log("Innan det skickas iväg")
  
          await this.$store.dispatch("addImagesToDB", imageData);

        }
      }
    },
    onPickFilePrimary() {
      this.$refs.fileInputPrimary.click(); //Reffererar till fileInput så man kan ha annat utseende på knapp
    },
    onPickFileSecondary() {
      this.$refs.fileInputSecondary.click(); //Reffererar till fileInput så man kan ha annat utseende på knapp
    },
    onPickFileThird() {
      this.$refs.fileInputThird.click(); //Reffererar till fileInput så man kan ha annat utseende på knapp
    },
    onFilePicked(event) {
      let files = event.target.files;
      if (!files.length) return; //Validering
      let fileName = files[0].name;
      if (fileName.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!"); //Validering av fil
      }
      this.fileReader.addEventListener("load", () => {
        this.imageUrl = this.fileReader.result; //För att visa bilden
      });
      this.fileReader.readAsDataURL(files[0]);
      this.formData.append("files", files[0], files[0].name);
    },
    onSecondFilePicked(event) {
      let filesSecondary = event.target.files;
      let fileReader2 = new FileReader();

      if (!filesSecondary.length) return; //Validering
      if (filesSecondary[0].name.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!"); //Validering av fil
      }
      fileReader2.addEventListener("load", () => {
        this.imageUrlExtra1 = fileReader2.result; //För att visa bilden
      });
      fileReader2.readAsDataURL(filesSecondary[0]);
      this.formDataSecond.append(
        "files",
        filesSecondary[0],
        filesSecondary[0].name
      );

    },
    onThirdFilePicked(event) {
      let filesThird = event.target.files;
      let fileReader3 = new FileReader();

      if (!filesThird.length) return; //Validering
      if (filesThird[0].name.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!"); //Validering av fil
      }
      fileReader3.addEventListener("load", () => {
        this.imageUrlExtra2 = fileReader3.result; //För att visa bilden
      });
      fileReader3.readAsDataURL(filesThird[0]);
      this.formDataSecond.append(
        "files",
        filesThird[0],
        filesThird[0].name
      );
    },
    uploadFiles(formData) {
      //Spara till disk
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
        this.description.length < 300
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

    this.imageUrl = {
      src: await fetch("http://localhost:7999/images/noImage.jpg").then(
        res => res.url
      )
    };
    this.imageUrlExtra1 = {
      src: await fetch("http://localhost:7999/images/noImage.jpg").then(
        res => res.url
      )
    };
    this.imageUrlExtra2 = {
      src: await fetch("http://localhost:7999/images/noImage.jpg").then(
        res => res.url
      )
    };
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
.imageBtn {
  margin-left: 140%;
  margin-top: -40%;
}
.imageBtn2 {
  margin-left: 254%;
  margin-top: -70%;
}
.imageBtn3 {
  margin-left: 369%;
  margin-top: -100%;
  z-index: 1;
}
</style>
