<template>
  <el-container>
    <el-header style="background-color: white">
      <el-page-header style="background-color: white" @back="goBack" content="个人信息页面">
      </el-page-header>
    </el-header>
    <el-container>
      <el-aside>
        <el-card v-model="user">
          <el-avatar :size="150" :src="user.photoUrl"></el-avatar>
          <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ user.nickname }}</div>
          <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ user.description }}</div>
          <div style="display: inline-block">
            <el-button round size="small" @click="sendMessage">私信</el-button>
            <el-button round size="small" :loading="buttonLoading" @click="changeAttention(user.id)" v-show="show1">
              关注
            </el-button>
            <el-button round size="small" :loading="buttonLoading" @click="changeAttention(user.id)" v-show="show2">
              取消关注
            </el-button>
          </div>
        </el-card>
      </el-aside>
      <el-main>
        <el-tabs v-model="activeName">
          <el-tab-pane label="他的文章" name="otherArticle" v-loading="loading">
            <div v-for="item in blogs" :key="item" class="el-card" style="text-align: left">
              <h4>
                <router-link :to="{name: 'BlogShow', params: {blogId: item.id}}"
                             style="font-size: large;font-family: 'Arial Black';color: #333333;text-align: center;margin-left: 30px">
                  {{ item.title }}
                </router-link>
              </h4>
              <span style="margin-left: 30px">{{ formatDate(item.date) }}</span>
              <span style="font-size: small;color: gray;margin-left: 10px">标签: </span>
              <div style="display: inline" v-for="tag in item.articleTagList" :key="tag" class="el-tag">
                {{ tag.name }}
              </div>
            </div>
            <div class="block">
              <el-pagination
                  layout="prev, pager, next"
                  :current-page="blogPage.blogPageNum"
                  :page-size="blogPage.blogPageSize"
                  :total="blogPage.blogTotal"
                  @current-change="blogPageChange">
              </el-pagination>
            </div>
          </el-tab-pane>
          <el-tab-pane label="他的收藏" name="otherCollection">
            <div v-for="collection in collections" :key="collection" class="el-card" style="text-align: left">
              <h4>
                <router-link :to="{name: 'BlogShow', params: {blogId: collection.id}}"
                             style="font-size: large;font-family: 'Arial Black';color: #333333;text-align: center;margin-left: 30px">
                  {{ collection.title }}
                </router-link>
              </h4>
              <span style="margin-left: 30px">{{ formatDate(collection.date) }}</span>
              <span style="font-size: small;color: gray;margin-left: 10px">标签: </span>
              <div style="display: inline" v-for="tag in collection.articleTagList" :key="tag" class="el-tag">
                {{ tag.name }}
              </div>
            </div>
            <div class="block">
              <el-pagination
                  layout="prev, pager, next"
                  :current-page="collectionPage.colpageNum"
                  :page-size="collectionPage.colpageSize"
                  :total="collectionPage.coltotal"
                  @current-change="collectionPageChange">
              </el-pagination>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "AttentionPage",
  data() {
    return {
      user: {},
      curUser: {},
      blogs: {},
      blogPage: {
        blogPageNum: 1,
        blogPageSize: 0,
        blogTotal: 0,
      },
      collections: {},
      collectionPage: {
        colPageNum: 1,
        colPageSize: 0,
        colTotal: 0,
      },
      buttonLoading: false,
      activeName: 'otherArticle',
      loading: false,
      show1: true,
      show2: false,
    }
  },
  created() {
    this.loading = true;
    if (this.$route.query.id) {
      let id = this.$route.query.id;
      this.getUser(id);
      this.getBlogs(id, 1);
      this.getCollections(id, 1);
    }
    if (this.$store.getters.getUser.id) {
      this.curUser = this.$store.getters.getUser;
    }
    this.changeShow(this.curUser.myAttention);
    this.loading = false;
  },
  methods: {
    getUser(id) {
      const _this = this;
      this.$axios.get('http://localhost:8080/user/findByUserId', {
        params: {
          id: id,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.user = res.data.data;
        } else {
          console.log(res.data.msg);
        }
      });
    },
    blogPageChange(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/articles/findByUserId', {
        params: {
          id: this.user.id,
          pageNum: current,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.blogs = res.data.data.list;
          _this.blogPage.blogPageNum = res.data.data.pageNum;
          _this.blogPage.blogTotal = res.data.data.total;
          _this.blogPage.blogPageSize = res.data.data.pageSize;
        } else {
          console.log(res.data.msg);
        }
      });

    },
    collectionPageChange(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/articles/collectList', {
        params: {
          id: this.user.id,
          pageNum: current,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.collections = res.data.data.list;
          _this.collectionPage.colpageNum = res.data.data.pageNum;
          _this.collectionPage.coltotal = res.data.data.total;
          _this.collectionPage.colpageSize = res.data.data.pageSize;
        } else {
          console.log(res.data.msg);
        }
      });
    },
    getBlogs(id, pageNum) {
      const _this = this;
      this.$axios.get('http://localhost:8080/articles/findByUserId', {
        params: {
          id: id,
          pageNum: pageNum,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.blogs = res.data.data.list;
          _this.blogPage.blogPageNum = res.data.data.pageNum;
          _this.blogPage.blogTotal = res.data.data.total;
          _this.blogPage.blogPageSize = res.data.data.pageSize;
        } else {
          console.log(res.data.msg);
        }
      });
    },
    getCollections(id, pageNum) {
      const _this = this;
      this.$axios.get('http://localhost:8080/articles/collectList', {
        params: {
          id: id,
          pageNum: pageNum,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.collections = res.data.data.list;
          _this.collectionPage.colpageNum = res.data.data.pageNum;
          _this.collectionPage.coltotal = res.data.data.total;
          _this.collectionPage.colpageSize = res.data.data.pageSize;
        } else {
          console.log(res.data.msg);
        }
      });
    },
    formatDate(date) {
      let time = new Date(date);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      return `${y}-${m}-${d}`;
    },
    getAvatar(id) {
      return 'http://localhost:8080/user/showPhotoById?userId=' + id;
    },
    changeShow(is) {
      if (is == false) {
        this.show1 = true;
        this.show2 = false;
      } else {
        this.show1 = false;
        this.show2 = true;
      }
    },
    changeAttention(id) {
      if (id == this.curUser.id) {
        this.$message('不能关注自己');
      } else {
        const _this = this;
        this.buttonLoading = true;
        let data = new FormData();
        data.append('id', id);
        this.$axios.put('http://localhost:8080/user/changeAttention', data).then(res => {
          if (res.data.code == 100) {
            _this.curUser.myAttention = !_this.curUser.myAttention;
            _this.changeShow(_this.curUser.myAttention);
            _this.$store.commit("SET_USERINFO", _this.curUser);
            _this.buttonLoading = false;
          }
        });
      }
    },
    sendMessage() {
      this.$prompt('请输入私信内容', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        const _this = this;
        let message = new FormData();
        message.append('fromId', this.curUser.id);
        message.append('fromName', this.curUser.nickname);
        message.append('toId', this.user.id);
        message.append('toName', this.user.nickname);
        message.append('date', new Date());
        message.append('content', value);
        this.$axios.put('http://localhost:8080/message/new', message).then(res => {
          if (res.data.code == 100) {
            _this.$message('发送成功')
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    goBack() {
      this.$router.back()
    }
  }
}
</script>

<style scoped>

</style>