<template>
  <div class="vue-poll">
    <h3 class="qst" v-html="question"></h3>
    <div class="ans-cnt">
      <div
        v-for="(a, index) in calcAnswers"
        :key="index"
        :class="{ ans: true, [a.custom_class]: a.custom_class }"
      >
        <template>
          <div
            :class="{ 'ans-voted': true, final: true, selected: a.selected }"
          >
            <span v-if="a.percent" class="percent" v-text="a.percent"></span>
            <span class="txt" v-html="a.text"></span>
          </div>
          <span
            :class="{ bg: true, selected: mostVotes == a.votes }"
            :style="{ width: a.percent }"
          ></span>
        </template>
      </div>
    </div>
    <div class="votes" v-text="totalVotesFormatted + ' votes'"></div>
  </div>
</template>

<script>
export default {
  name: 'Poll',
  props: {
    question: {
      type: String,
      required: true,
    },
    answers: {
      type: Array,
      required: true,
    },
    showResults: {
      type: Boolean,
      default: false,
    },
    showTotalVotes: {
      type: Boolean,
      default: true,
    },
    finalResults: {
      type: Boolean,
      default: false,
    },
    submitButtonText: {
      type: String,
      default: 'Submit',
    },
    customId: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      visibleResults: JSON.parse(this.showResults),
    };
  },
  computed: {
    totalVotes() {
      let totalVotes = 0;
      this.answers.filter((a) => {
        if (!isNaN(a.votes) && a.votes > 0) totalVotes += parseInt(a.votes);
      });
      return totalVotes;
    },
    totalVotesFormatted() {
      // return this.totalVotes;
      return this.totalVotes.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
    mostVotes() {
      let max = 0;
      this.answers.filter((a) => {
        if (!isNaN(a.votes) && a.votes > 0 && a.votes >= max) max = a.votes;
      });

      return max;
    },
    calcAnswers() {
      if (this.totalVotes === 0)
        return this.answers.map((a) => {
          a.percent = '0%';
          return a;
        });

      //Calculate percent
      return this.answers.filter((a) => {
        if (!isNaN(a.votes) && a.votes > 0)
          a.percent =
            Math.round((parseInt(a.votes) / this.totalVotes) * 100) + '%';
        else a.percent = '0%';

        return a;
      });
    },
    totalSelections() {
      return this.calcAnswers.filter((a) => a.selected).length;
    },
  },
  methods: {
    handleVote(a) {
      if (this.multiple) {
        if (a.selected === undefined) a.selected = !a.selected;
        return;
      }

      a.votes++;
      a.selected = true;
      this.visibleResults = true;

      let obj = { value: a.value, votes: a.votes, totalVotes: this.totalVotes };

      if (this.customId) obj.customId = this.customId;

      this.$emit('addvote', obj);
    },
  },
};
</script>

<style>
.vue-poll {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.vue-poll .noselect {
  -webkit-touch-callout: none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

.vue-poll .qst {
  font-weight: normal;
}
.vue-poll .ans-cnt {
  margin: 20px 0;
}
.vue-poll .ans-cnt .ans {
  position: relative;
  margin-top: 10px;
}
.vue-poll .ans-cnt .ans:first-child {
  margin-top: 0;
}

.vue-poll .ans-cnt .ans-no-vote {
  text-align: center;
  border: 2px solid #77c7f7;
  box-sizing: border-box;
  border-radius: 5px;
  cursor: pointer;
  padding: 5px 0;
  transition: background 0.2s ease-in-out;
  -webkit-transition: background 0.2s ease-in-out;
  -moz-transition: background 0.2s ease-in-out;
}

.vue-poll .ans-cnt .ans-no-vote .txt {
  color: #77c7f7;
  transition: color 0.2s ease-in-out;
  -webkit-transition: color 0.2s ease-in-out;
  -moz-transition: color 0.2s ease-in-out;
}

.vue-poll .ans-cnt .ans-no-vote.active {
  background: #77c7f7;
}

.vue-poll .ans-cnt .ans-no-vote.active .txt {
  color: #fff;
}

.vue-poll .ans-cnt .ans-voted {
  padding: 5px 0;
}

.vue-poll .ans-cnt .ans-voted .percent,
.vue-poll .ans-cnt .ans-voted .txt {
  position: relative;
  z-index: 1;
}
.vue-poll .ans-cnt .ans-voted .percent {
  font-weight: bold;
  min-width: 51px;
  display: inline-block;
  margin: 0 10px;
}

.vue-poll .ans-cnt .ans-voted.selected .txt:after {
  content: '✔';
  margin-left: 10px;
}

.vue-poll .ans-cnt .ans .bg {
  position: absolute;
  width: 0%;
  top: 0;
  left: 0;
  bottom: 0;
  z-index: 0;
  background-color: #e1e8ed;
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
  transition: all 0.3s cubic-bezier(0.5, 1.2, 0.5, 1.2);
  -webkit-transition: all 0.3s cubic-bezier(0.5, 1.2, 0.5, 1.2);
  -moz-transition: all 0.3s cubic-bezier(0.5, 1.2, 0.5, 1.2);
}

.vue-poll .ans-cnt .ans .bg.selected {
  background-color: #77c7f7;
}

.vue-poll .votes {
  font-size: 14px;
  color: #8899a6;
  margin: -10px 0 15px 10px;
}

.vue-poll .submit {
  display: block;
  text-align: center;
  margin: 0 auto;
  max-width: 80px;
  text-decoration: none;
  background-color: #41b882;
  color: #fff;
  padding: 10px 25px;
  border-radius: 5px;
}
</style>
