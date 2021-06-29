<template>
  <el-container>
    <el-header>
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
    <el-container>
      <el-main>
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
          <el-card class="box-card">
            <div v-for="o in 4" :key="o" class="block">
              <el-card>
                <h4>
                  <p>
                    <router-link to="/blogshow">博客标题</router-link>
                  </p>
                </h4>
                <p>博客简介</p>
                <el-tag>标签一</el-tag>
                <el-tag type="success">标签二</el-tag>
                <el-tag type="info">标签三</el-tag>
                <el-tag type="warning">标签四</el-tag>
                <el-tag type="danger">标签五</el-tag>
              </el-card>
            </div>
          </el-card>
          <div class="block">
            <el-pagination
                layout="prev, pager, next"
                :current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                @current-change="page">
            </el-pagination>
          </div>
        </div>
      </el-main>
      <el-aside>
        <el-card class="box-card" v-loading.fullscreen.lock="loading">
          <el-avatar :size="150" :src="this.user.url"></el-avatar>
          <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ this.user.nickname }}</div>
          <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ this.user.description }}</div>
        </el-card>
        <el-card class="box-card" style="margin-top: 30px">
          <div slot="header" class="clearfix">
            <span>热门标签</span>
          </div>
          <div v-for="o in 4" :key="o" class="el-tag">
            {{ '标签' + o }}
          </div>
        </el-card>
        <el-card class="box-card" style="margin-top: 30px">
          <div slot="header" class="clearfix">
            <span>热门文章</span>
          </div>
          <div v-for="o in 4" :key="o" class="text item">
            {{ '文章标题 ' + o }}
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
      blogs: {},
      currentPage: 1,
      total: 25,
      pageSize: 5,
      loading: false,
      user: {
        id: '',
        nickname: '',
        description: '',
        url: '',
      },
      images: [
        'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
        'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg',
        'https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg',
        'https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg',
        'https://fuss10.elemecdn.com/d/e6/c4d93a3805b3ce3f323f7974e6f78jpeg.jpeg',
        'https://fuss10.elemecdn.com/3/28/bbf893f792f03a54408b3b7a7ebf0jpeg.jpeg',
        'https://fuss10.elemecdn.com/2/11/6535bcfb26e4c79b48ddde44f4b6fjpeg.jpeg'
      ]
    }
  },
  created() {
    this.loading = true;
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
      this.user.url = 'http://localhost:8080/user/showPhotoById?userId=' + this.user.id;
      this.user.description = this.$store.getters.getUser.description;
      this.user.nickname = this.$store.getters.getUser.nickname;
    } else {
      this.$message("请先登录");
      this.$router.push('/');
    }
    this.loading = false;
  },
  methods: {
    page(currentPage){

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
    }
  }
}
</script>

<style>

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-header {
  background-color: #FFFFFF;
  color: #333;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
}


.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
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


</style>