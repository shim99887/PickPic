<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<template>
  <div class="container">
    <div class="card mb-5">
      <login-modal
        :btnView="true"
        :dialog="loginPlz"
        @loginSuccess="loginPlz = false"
      />
      <div class="card-body">
        <v-row align="center">
          <v-textarea
            label="댓글 입력"
            rows="2"
            v-model="userComment"
          ></v-textarea>
          <v-btn color="secondary" outlined @click="writeComment">작성</v-btn>
        </v-row>
      </div>
    </div>
    <div class="dropdown pull-right">
      <button
        type="button"
        class="btn dropdown-toggle theme--light primary v-btn--is-elevated"
        data-toggle="dropdown"
      >
        Sort
      </button>
      <div class="dropdown-menu">
        <a class="dropdown-item" @click="[(reset = true), refreshData('zzz')]"
          >Time</a
        >
        <a class="dropdown-item" @click="[(reset = true), refreshData('like')]"
          >Like</a
        >
      </div>
    </div>
    <br /><br /><br />
    <v-row v-for="(comment, index) in comments" :key="index">
      <v-col cols="1">
        <div class="thumbnail">
          <img
            class="img-responsive user-photo"
            :src="comment.Comment.profileImg"
            onerror='this.src ="https://ssl.gstatic.com/accounts/ui/avatar_2x.png"'
          />
        </div>
        <!-- /thumbnail -->
      </v-col>
      <!-- /col-sm-1 -->
      <v-col cols="11">
        <div class="panel panel-default">
          <div class="panel-heading">
            <strong>{{ comment.Comment.name }} </strong>
            <span class="text-muted">{{ comment.Comment.uploadDate }}</span>
            <v-menu transition="slide-y-transition" bottom>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="purple mb-3"
                  color="primary"
                  style="float:right; height:30px; min-width:30px"
                  v-bind="attrs"
                  v-on="on"
                  v-if="
                    !(
                      comment.Comment.email != $store.getters.getUserEmail &&
                      $store.getters.getRole != 'admin'
                    )
                  "
                >
                  <v-icon>fas fa-list</v-icon>
                </v-btn>
              </template>

              <v-list>
                <v-list-item v-for="(item, i) in items" :key="i">
                  <v-btn
                    :disabled="
                      comment.Comment.email != $store.getters.getUserEmail &&
                        $store.getters.getRole != 'admin'
                    "
                    color="secondary"
                    outlined
                    @click="menuclick($event, comment)"
                    >{{ item }}</v-btn
                  >
                </v-list-item>
              </v-list>
            </v-menu>
          </div>

          <div class="panel-body">
            <span v-if="dis != comment.Comment.commentNo">{{
              comment.Comment.content
            }}</span>
            <v-text-field
              color="black"
              v-model="comment.Comment.content"
              v-else
              rows="1"
              :value="comment.Comment.content"
            />
            <v-row>
              <v-spacer />
              <span style="font-size:13px" class="mr-3 mt-2">
                좋아요 {{ comment.Comment.likeCnt }}개
              </span>
              <!-- <img
                :src="heart(comment)"
                @click="heartClick(comment)"
                width="30px"
                alt=""
              /> -->
              <div v-if="comment.likeCheck == 'Y'">
                <i
                  class="fas fa-heart fa-2x mr-2"
                  style="color:red"
                  @click="heartClick(comment)"
                ></i>
              </div>
              <div v-else>
                <i
                  class="far fa-heart fa-2x mr-2"
                  style="color:red"
                  @click="heartClick(comment)"
                ></i>
              </div>
              <!-- <i
                class="far fa-heart fa-2x"
                style="color:red"
                v-if="comment.Comment.likeCheck == 'N'"
                @click="heartClick(comment)"
              ></i> -->
            </v-row>
          </div>
          <!-- /panel-body -->
        </div>
        <!-- /panel panel-default -->
      </v-col>
      <!-- /col-sm-5 -->
    </v-row>
    <!-- <div class="card" v-for="(comment, index) in comments" :key="index">
      <div class="card-body">
          <h6 class="mr-2"
            >{{ comment.Comment.name }}</h6
          >
        <v-row align="center">
            <h7 v-if="dis != comment.Comment.commentNo">{{comment.Comment.content}}</>
          <v-text-field
            color="black"
            v-model="comment.Comment.content"
            v-else
            rows="1"
            :value="comment.Comment.content"
          />
          <v-spacer/>
          <v-menu transition="slide-y-transition" bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn class="purple mb-5" color="primary" v-bind="attrs" v-on="on">
                <v-icon>fas fa-list</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item v-for="(item, i) in items" :key="i">
                <v-btn
                  :disabled="comment.Comment.email != getUserEmail"
                  @click="menuclick($event, comment)"
                  >{{ item }}</v-btn
                >
              </v-list-item>
            </v-list>
          </v-menu>
        </v-row>
        <v-row>
          <span style="font-size:13px">
            좋아요 {{ comment.Comment.likeCnt }}개</span
          >
          <v-spacer />
          <img :src="heart(comment)" @click="heartClick(comment)" width="30px" alt="" />
        </v-row>
      </div>
    </div> -->
    <infinite-loading
      @infinite="infiniteHandler"
      ref="InfiniteLoading"
      spinner="waveDots"
    >
      <div
        slot="no-more"
        style="color: rgb(102, 102, 102); font-size: 14px; padding: 10px 0px;"
      >
        댓글이 더 없어요 ㅠㅠ
      </div>
    </infinite-loading>
  </div>
</template>
<style scoped>
.thumbnail {
  padding: 0px;
}
.panel {
  position: relative;
}
.panel > .panel-heading:after,
.panel > .panel-heading:before {
  position: absolute;
  top: 11px;
  left: -16px;
  right: 100%;
  width: 0;
  height: 0;
  display: block;
  content: " ";
  border-color: transparent;
  border-style: solid solid outset;
  pointer-events: none;
}
.panel > .panel-heading:after {
  border-width: 7px;
  border-right-color: #f7f7f7;
  margin-top: 1px;
  margin-left: 2px;
}
.panel > .panel-heading:before {
  border-right-color: #ddd;
  border-width: 8px;
}
</style>

<script>
import axios from 'axios';
import { mapGetters } from 'vuex';
import LoginModal from "../core/LoginModal.vue";
import InfiniteLoading from 'vue-infinite-loading';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data() {
    return {
      userComment: '',
      modComment: '',
      comments: {},
      heartIcon: '',
      no: '',
      dis: 0,
      items: ['수정', '삭제'],
      limit: 1,
      loginPlz: false,
      sortBy: '',
      profileImg: 'sibal',
      like: false,
      reset: false,
    };
  },
  components: {
    InfiniteLoading,
    LoginModal,
  },
  computed: {
    ...mapGetters(['getAccessToken', 'getUserEmail', 'getUserName', 'getRole']),
  },
  created() {
    const params = new URLSearchParams();
    params.append('email', this.getUserEmail);
    this.refreshData('uploadDate');
    this.no = this.$route.params.no;
  },
  methods: {
    infiniteHandler($state) {
      const params = new URLSearchParams();
      params.append('email', this.getUserEmail);
      params.append('pg', this.limit);
      params.append('sortBy', this.sortBy);
      axios
        .post(`${SERVER_URL}/comment/` + this.$route.params.no, params)
        .then((response) => {
          console.log(response);
          setTimeout(() => {
            if (response.data.length) {
              for (var i = 0; i < response.data.length; i++) {
                response.data[i].Comment.profileImg =
                  'https://apfbucket.s3.ap-northeast-2.amazonaws.com/' +
                  response.data[i].Comment.profileImg;
              }
              this.comments = this.comments.concat(response.data);
              $state.loaded();
              this.limit += 1;
              // 끝 지정(No more data) - 데이터가 EACH_LEN개 미만이면
              if (!response.data) {
                $state.complete();
              }
            } else {
              // 끝 지정(No more data)
              $state.complete();
            }
          }, 1000);
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    refreshData(sortTo) {
      if(this.reset) {
        this.$refs.InfiniteLoading.stateChanger.reset();
        this.reset=false;
      }
      this.limit = 1;
      this.sortBy = sortTo;
      const params = new URLSearchParams();
      params.append('email', this.getUserEmail);
      params.append('sortBy', this.sortBy);

      axios
        .get(`${SERVER_URL}/comment/` + this.$route.params.no, {
          params,
        })
        .then((response) => {
          this.comments = response.data;
          console.log(this.comments);
          // this.comments.profileImg =
          //   'https://apfbucket.s3.ap-northeast-2.amazonaws.com/' +
          //   this.comment.profileImg;
          for (var i = 0; i < this.comments.length; i++) {
            this.comments[i].Comment.profileImg =
              'https://apfbucket.s3.ap-northeast-2.amazonaws.com/' +
              this.comments[i].Comment.profileImg;
          }
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
    },
    heart(cmt) {
        if (cmt.likeCheck == 'Y') {
          this.like = !this.like;
      // return require('@/assets/heart.jpg');
        } else {
          this.like = !this.like;
          // return require('@/assets/blank heart.png');
        }
    },
    modifyCommentBtn(event) {
      this.dis = event.currentTarget.id;
    },
    modifyCommentCompleteBtn(event) {
      this.dis = 0;
    },
    writeComment() {
      if (!this.$store.getters.getUserEmail) {
        this.$alert('로그인이 필요한 서비스입니다.','','warning');
        this.loginPlz = true;
      } else {
        if (!this.userComment) {
          this.$alert('댓글을 입력해주세요','','warning');
        } else {
          axios
            .post(`${SERVER_URL}/comment`, {
              email: this.$store.getters.getUserEmail,
              postNo: this.$route.params.no,
              content: this.userComment,
            })
            .then((response) => {
              this.userComment = '';

              this.reset = true;
              this.refreshData();
            })
            .catch((error) => {
              this.$router.push({
                path: '/Error',
                query: { status: error.response.status },
              });
            });
        }
      }
    },
    menuclick(event, cmt) {
      if (event.target.innerText == '삭제') {
        axios
          .delete(`${SERVER_URL}/comment`, {
            data: {
              commentNo: cmt.Comment.commentNo,
            },
          })
          .then((response) => {
            this.$alert('삭제 완료','','success');

            const params = new URLSearchParams();
            params.append('email', this.getUserEmail);
            this.reset = true;
            this.refreshData();
          })
          .catch((error) => {
            this.$router.push({
              path: '/Error',
              query: { status: error.response.status },
            });
          });
      } else if (event.target.innerText == '수정') {
        this.dis = cmt.Comment.commentNo;
        event.target.innerText = '완료';
      } else if (event.target.innerText == '완료') {
        axios
          .put(`${SERVER_URL}/comment`, cmt)
          .then((response) => {
            event.target.innerText = '수정';
            this.$alert('수정 완료','','success');
            this.dis = 0;
          })
          .catch((error) => {
            this.$router.push({
              path: '/Error',
              query: { status: error.response.status },
            });
          });
      }
    },
    heartClick(comment) {
       if (!this.getUserEmail) {
        this.$alert('로그인이 필요한 서비스입니다.','','warning');
        this.loginPlz = true;
      }else{
        const params = new URLSearchParams();
      params.append('email', this.getUserEmail);
      params.append('commentNo', comment.Comment.commentNo);

      axios
        .put(`${SERVER_URL}/comment/like`, params)
        .then((res) => {
          comment.likeCheck = res.data.likeCheck;
          comment.Comment.likeCnt = res.data.likeCnt;
        })
        .catch((error) => {
          this.$router.push({
            path: '/Error',
            query: { status: error.response.status },
          });
        });
          }
    },
  },
};
</script>
