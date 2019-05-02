<template>
  <div class="both">
    <div class="container flip-clock hela" id="countdownTimer">
      <template v-for="data in timeData" v-show="show">
        <span
          v-bind:key="data.label"
          class="flip-clock__piece hela"
          :id="data.elementId"
        >
          <span class="flip-clock__slot">{{ data.label }}</span>
          <span class="flip-clock__card flip-card">
            <b class="flip-card__top">{{ data.current | twoDigits }}</b>
            <b
              class="flip-card__bottom"
              v-bind:data-value="data.current | twoDigits"
            ></b>
            <b
              class="flip-card__back"
              v-bind:data-value="data.previous | twoDigits"
            ></b>
            <b
              class="flip-card__back-bottom"
              v-bind:data-value="data.previous | twoDigits"
            ></b>
          </span>
        </span>
      </template>
    </div>
  </div>
</template>

<script>
let interval = null;
const uuidv4 = require("uuid/v4");

export default {
  name: "flipCountdown",
  props: {
    deadline: {
      type: String
    },
    stop: {
      type: Boolean
    },
    labels: {
      type: Object,
      required: false,
      default: function() {
        return {
          // days: 'Days',
          hours: "H",
          minutes: "M",
          seconds: "S"
        };
      }
    }
  },
  data() {
    const uuid = uuidv4();
    return {
      now: Math.trunc(new Date().getTime() / 1000),
      date: null,
      interval: null,
      diff: 0,
      show: false,
      timeData: [
        {
          current: 0,
          previous: 0,
          label: this.labels.days,
          elementId: "flip-card-days-"
        },
        {
          current: 0,
          previous: 0,
          label: this.labels.hours,
          elementId: "flip-card-hours-"
        },
        {
          current: 0,
          previous: 0,
          label: this.labels.minutes,
          elementId: "flip-card-minutes-"
        },
        {
          current: 0,
          previous: 0,
          label: this.labels.seconds,
          elementId: "flip-card-seconds-"
        }
      ]
    };
  },
  created() {
    const endTime = this.deadline;
    this.date = Math.trunc(Date.parse(endTime.replace(/-/g, "/")) / 1000);
    this.interval = setInterval(() => {
      this.now = Math.trunc(new Date().getTime() / 1000);
    }, 1000);
  },
  mounted() {
    if (this.diff !== 0) {
      
      this.show = true;
    }
  },
  computed: {
    seconds() {
      return Math.trunc(this.diff) % 60;
    },
    minutes() {
      return Math.trunc(this.diff / 60) % 60;
    },
    hours() {
      return Math.trunc(this.diff / 60 / 60) % 24;
    },
    days() {
      return Math.trunc(this.diff / 60 / 60 / 24);
    },
    countdownValue() {
      return this.deadline;
    },
      oneHourLeft() {
        if (this.hours) {
        
        }
      }
  },
  watch: {
    now(value) {
      this.diff = this.date - this.now;
      if (this.diff <= 0 || this.stop) {
        this.diff = 0;
        this.updateTime(3, 0);
        clearInterval(this.interval);
      } else {
        this.updateTime(0, this.days);
        this.updateTime(1, this.hours);
        this.updateTime(2, this.minutes);
        this.updateTime(3, this.seconds);
      }
    }
  },
  filters: {
    twoDigits(value) {
      if (value.toString().length <= 1) {
        return "0" + value.toString();
      }
      return value.toString();
    }
  },
  methods: {
    updateTime(idx, newValue) {
      if (idx >= this.timeData.length || newValue === undefined) {
        return;
      }

      if (window["requestAnimationFrame"]) {
        this.frame = requestAnimationFrame(this.updateTime.bind(this));
      }
      
      // if (idx === 1 && !newValue) {
      //     document.getElementById("countdownTimer").style.color = "red";
      // }

      const d = this.timeData[idx];
      const val = newValue < 0 ? 0 : newValue;

      if (val !== d.current) {
        d.previous = d.current;
        d.current = val;

        const el = document.querySelector(`#${d.elementId}`);
        if (el) {
          el.classList.remove("flip");
          void el.offsetWidth;
          el.classList.add("flip");
        }
      }
    }
  },
  beforeDestroy() {
    if (window["cancelAnimationFrame"]) {
      cancelAnimationFrame(this.frame);
    }
  },
  destroyed() {
    clearInterval(interval);
  }
};
</script>

<style scoped>
#flip-card-days- {
  display: none;
}
#flip-card-hours-,
#flip-card-minutes-,
#flip-card-seconds- {
  padding: 5px;
}
.flip-card {
  padding: 5px;
}
.hela {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
}
.both {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 0;
}
.both p {
  margin: 0;
}
</style>
