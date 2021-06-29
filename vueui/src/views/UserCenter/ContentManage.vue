<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="4" class="el-menu-vertical-demo">
          <el-menu-item index="1" @click="menuClick">
            <span slot="title">个人信息</span>
          </el-menu-item>
          <el-menu-item index="2" @click="menuClick">
            <span slot="title">账号信息</span>
          </el-menu-item>
          <el-menu-item index="3" @click="menuClick">
            <span slot="title">我的关注</span>
          </el-menu-item>
          <el-menu-item index="4" @click="menuClick">
            <span slot="title">内容管理</span>
          </el-menu-item>
          <el-menu-item index="5" @click="menuClick">
            <span slot="title">撰写文章</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-aside>

    <el-main>
      <el-tabs v-model="activeName">
        <el-tab-pane label="我的文章" name="MyArticle">
          <div v-for="o in 4" :key="o" class="block">
            <el-card>
              <h4>
                <p>博客标题</p>
              </h4>
              <p>博客简介</p>
              <el-tag>标签一</el-tag>
              <el-tag type="success">标签二</el-tag>
              <el-tag type="info">标签三</el-tag>
              <el-tag type="warning">标签四</el-tag>
              <el-tag type="danger">标签五</el-tag>
              <el-button type="danger" icon="el-icon-delete" circle style="display: inline-block;float: right"
                         @click="deleteArticle"></el-button>
            </el-card>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的收藏" name="MyCollection">
          <div v-for="o in 4" :key="o" class="block">
            <el-card>
              <h4>
                <p>博客标题</p>
              </h4>
              <p>博客简介</p>
              <el-tag>标签一</el-tag>
              <el-tag type="success">标签二</el-tag>
              <el-tag type="info">标签三</el-tag>
              <el-tag type="warning">标签四</el-tag>
              <el-tag type="danger">标签五</el-tag>
              <el-button type="warning" icon="el-icon-star-off" circle style="display: inline;float: right"
                         @click="cancelCollection"></el-button>
            </el-card>

          </div>
        </el-tab-pane>
        <el-tab-pane label="我的评论" name="MyComment">
          <div v-for="o in 4" :key="o" class="el-card">
            <el-link type="primary" style="font-size: medium">博客标题</el-link>
            <div type="text" style="margin-top: 30px">默认评论</div>
            <div type="text" v-text="timestamp" style="margin-top: 10px"></div>
            <el-button type="danger" icon="el-icon-delete" circle
                       style="float: right;margin-top: 50px;margin-right: 5px"
                       @click="deleteComment"></el-button>

          </div>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>

</template>

<script>
export default {
  name: "ContentManage",

  data() {
    return {
      timestamp: '2018-04-15',
      activeName: 'MyArticle',
      user: {
        id: '',
      },
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
    }
  },
  methods: {
    deleteArticle() {
      this.$message("删除成功");
    },
    cancelCollection() {
      this.$message("取消收藏");
    },
    deleteComment() {
      this.$message("删除成功");
    },
    menuClick(menuItem) {
      if (menuItem.index == 1) {
        this.$router.push('/user-center/selfinformation');
      }
      if (menuItem.index == 2) {
        this.$router.push('/user-center/account');
      }
      if (menuItem.index == 3) {
        this.$router.push('/user-center/attention');
      }
      if (menuItem.index == 4) {
        this.$router.push('/user-center/contentmanage');
      }
      if (menuItem.index == 5) {
        this.$router.push('/newblog');
      }
    },
  }
}
</script>

<style>

.el-aside {
  background-color: #FFFFFF;
  color: #333;
  text-align: center;

}

.el-main {
  background-color: #FFFFFF;
  color: #333;
  text-align: center;
}


</style>