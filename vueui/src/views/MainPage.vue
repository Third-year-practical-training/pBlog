<template>
  <el-container id="mainpage">
    <el-header id="header">
      <div class="block"
           style="display: inline-block;font-size: xx-large;font-family: 'Arial Black';margin-left: 600px">IT技术论坛
      </div>
      <el-tabs style="float: right" @tab-click="handleClick">
        <el-tab-pane label="搜索" name="search"></el-tab-pane>
        <el-tab-pane label="个人中心" name="self"></el-tab-pane>
        <el-tab-pane label="关于" name="about"></el-tab-pane>
        <el-tab-pane label="通知" name="notice"></el-tab-pane>
        <el-tab-pane label="退出登录" name="exit"></el-tab-pane>
      </el-tabs>
    </el-header>
    <el-container id="maincontainer">
      <el-main id="main">
        <div class="block">
          <span class="demonstration"></span>
          <el-carousel height="200px" autoplay="true">
            <el-carousel-item v-for="item in images" :key="item">
              <el-image :src="item" style="width: 1200px; height: 200px">
                <div slot="placeholder" class="image-slot">
                  加载中<span class="dot">...</span>
                </div>
              </el-image>
            </el-carousel-item>
          </el-carousel>
          <el-card class="box-card" v-loading="loading">
            <div v-for="item in blogs" :key="item.id" style="margin-left: 100px">
              <el-card style="min-height: 150px;max-width: 900px">
                <div>
                  <div>
                    <el-image style="width: 200px;height: 200px;float: left;margin-bottom: 10px"
                              :src="item.photoUrl">
                    </el-image>
                  </div>
                  <router-link :to="{path: '/blogshow', query: {blogId: item.id}}"
                               style="font-size: x-large;font-family: 'Arial Black';color: #333333;text-decoration: none;">
                    {{ item.title }}<i v-if="item.isFeature == true" class="el-icon-reading"></i>
                  </router-link>
                  <div>
                    <span
                        style="min-height: 100px;text-align: left;max-width: 600px;margin-left: 50px;float: left;margin-top: 30px">{{
                        item.summary
                      }}</span>
                  </div>
                  <div style="float: right">
                  <span style="color: #7d7d7d;font-size: small"><i class="el-icon-date"></i> 发表于：{{
                      formatDate(item.date)
                    }}</span>
                    <el-divider direction="vertical"></el-divider>
                    <span style="color: #7d7d7d;font-size: small" @click="toAttentionPage(item.userId)"><i
                        class="el-icon-user-solid"></i> 作者：<el-button type="text"
                                                                      style="color: aqua">{{
                        item.userNickname
                      }}</el-button>
                      </span>
                    <el-divider direction="vertical"></el-divider>
                    <span style="color: #7d7d7d;font-size: small"><i class="el-icon-collection-tag"></i> 标签：</span>
                    <div style="display: inline" v-for="tag in item.articleTagList" :key="tag" class="el-tag">
                      {{ tag.name }}
                    </div>
                    <el-divider direction="vertical"></el-divider>
                    <span style="color: #7d7d7d;font-size: small"><i
                        class="el-icon-collection-tag"></i> 收藏数： {{ item.collectCount }}</span>
                    <el-divider direction="vertical"></el-divider>
                    <span style="color: #7d7d7d;font-size: small"><i
                        class="el-icon-collection-tag"></i> 评论数： {{ item.commentCount }}</span>
                  </div>
                </div>
              </el-card>
            </div>
          </el-card>
          <div class="block">
            <el-pagination
                layout="prev, pager, next"
                :current-page="pageNum"
                :page-size="pageSize"
                :total="total"
                @current-change="page">
            </el-pagination>
          </div>
        </div>
      </el-main>
      <el-aside id="asidepage">
        <el-card class="box-card">
          <el-avatar :size="150" :src="this.user.url"></el-avatar>
          <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ this.user.nickname }}</div>
          <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ this.user.description }}</div>
        </el-card>
        <el-card class="box-card" style="margin-top: 30px">
          <div slot="header" class="clearfix">
            <span>热门标签</span>
          </div>
          <div v-for="tag in hotTags" :key="tag.id" class="el-tag">
            {{ tag.name }}
          </div>
        </el-card>
        <el-card class="box-card" style="margin-top: 30px">
          <div slot="header" class="clearfix">
            <span>热门文章</span>
          </div>
          <div v-for="item in hotArticles" :key="item" class="text item">
            <router-link :to="{name: 'BlogShow', params: {blogId: item.id}}"
                         style="font-family: 'Arial Black';color: red;text-decoration: none">
              {{ item.title }}
            </router-link>
            <el-divider></el-divider>
          </div>
        </el-card>
      </el-aside>
    </el-container>
  </el-container>
</template>

<script>

import axios from "axios";

export default {
  name: "MainPage",
  data() {
    return {
      blogs: [],
      hotTags: [],
      hotArticles: [],
      pageNum: 1,
      total: 0,
      pageSize: 0,
      hasNextPage: false,
      loading: false,
      user: {
        id: '',
        nickname: '',
        description: '',
        url: '',
      },
      images: [
        'https://gitee.com/luobuyu/BlogImg/raw/master/img/20210707223946.jpg',
        'https://gitee.com/luobuyu/BlogImg/raw/master/img/20210707223959.jpg',
		'https://gitee.com/luobuyu/BlogImg/raw/master/img/20210707224015.jpg',
		'https://gitee.com/luobuyu/BlogImg/raw/master/img/20210707224110.png'
      ]
    }
  },
  created() {
    this.loading = true;
    const _this = this;
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
      this.user.url = this.$store.getters.getUser.photoUrl;
      this.user.description = this.$store.getters.getUser.description;
      this.user.nickname = this.$store.getters.getUser.nickname;
      this.page(1);
      this.getHotTags();
      this.getHotArticles();
    } else {
      this.$message("请先登录");
      this.$router.push('/');
    }

    this.loading = false;
  },
  methods: {
    page(pageNum) {
      const _this = this;
      this.$axios.get('http://localhost:8080/article/findAllArticle', {
        params: {
          pageNum: pageNum,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.pageNum = res.data.data.pageNum;
          _this.total = res.data.data.total;
          _this.pageSize = res.data.data.pageSize;
          _this.blogs = res.data.data.list;
        } else {
          console.log(res.data.msg);
        }
      });
    },
    getHotTags() {
      const _this = this;
      this.$axios.get('http://localhost:8080/getHotTags').then(res => {
        if (res.data.code == 100) {
          _this.hotTags = res.data.data;
        } else {
          console.log(res.data.msg);
        }
      })
    },
    getHotArticles() {
      const _this = this;
      this.$axios.get('http://localhost:8080//article/getHotArticles').then(res => {
        if (res.data.code == 100) {
          _this.hotArticles = res.data.data;
        } else {
          console.log(res.data.msg);
        }
      })
    },
    formatDate(date) {
      let time = new Date(date);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      return `${y}-${m}-${d}`;
    },
    logout() {
      this.$alert('确定要退出登录吗？', '退出确认', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(() => {
        const _this = this;
        axios.post('http://localhost:8080/user/signout').then(res => {
          _this.$store.commit('REMOVE_INFO')
          _this.$router.push('/')
        })
      }).catch(() => {
        this.$message("取消退出");
      });
    },
    handleClick(tab, event) {
      if (tab.name == 'exit') {
        this.logout();
      }
      if (tab.name == 'self') {
        this.$router.push('/user-center/selfinformation');
      }
      if (tab.name == 'notice') {
        this.$router.push('/comments');
      }
      if (tab.name == 'search') {
        this.$router.push('/searchpage');
      }
    },
    toAttentionPage(id) {
      this.$router.push({path: '/attentionpage', query: {id: id}});
    },
    getAvatarUrl(id) {
      let url = '';
      this.$axios.get('http://localhost:8080/user/showPhotoById', {
        params: {
          userId: id
        }
      }).then(res => {
        if (res.data.code == 100) {
          url = res.data.data;
          return url;
        }
      })
    }

  }
}
</script>

<style>

#mainpage {
  background-size: 100% 100%;
  position: page;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 100%;
}


/*.bg-purple-light {*/
/*  background: #FFFFFF;*/
/*}*/

/*.grid-content {*/
/*  border-radius: 4px;*/
/*  min-height: 36px;*/
/*}*/

</style>