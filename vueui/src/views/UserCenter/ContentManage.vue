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
        <el-tab-pane label="我的文章" name="MyArticle" v-loading="loading">
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
            <div style="text-align: right;display: inline-block;float: right">
              <el-button type="primary" icon="el-icon-edit" circle style="margin-right: 20px"
                         @click="updateArticle(item.id)"></el-button>
              <el-button type="danger" icon="el-icon-delete" circle style="margin-right: 10px"
                         @click="deleteArticle(item.id)"></el-button>
            </div>
          </div>
          <div class="block">
            <el-pagination
                layout="prev, pager, next"
                :current-page="pageNum"
                :page-size="pageSize"
                :total="total"
                @current-change="pageChange">
            </el-pagination>
          </div>
        </el-tab-pane>
        <el-tab-pane label="我的收藏" name="MyCollection">
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
            <div style="text-align: right;display: inline-block;float: right">
              <el-button type="danger" icon="el-icon-delete" circle style="margin-right: 10px"
                         @click="cancelCollection(collection.id)"></el-button>
            </div>
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
      blogs: [],
      collections: [],
      pageNum: 1,
      pageSize: 0,
      total: 0,
      loading: false,
      activeName: 'MyArticle',
      user: {
        id: '',
      },
    }
  },
  created() {
    this.loading = true;
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
    }
    const _this = this;
    this.$axios.get('http://localhost:8080/articles/findByUserId', {
      params: {
        id: this.user.id,
        pageNum: this.pageNum,
      }
    }).then(res => {
      if (res.data.code == 100) {
        _this.blogs = res.data.data.list;
        _this.pageNum = res.data.data.pageNum;
        _this.total = res.data.data.total;
        _this.pageSize = res.data.data.size;
      } else {
        console.log(res.data.msg);
      }
    });
    this.$axios.get('http://localhost:8080/articles/collectList', {
      params: {
        id: this.user.id,
      }
    }).then(res => {
      if (res.data.code == 100) {
        _this.collections = res.data.data;
        _this.loading = false;
      } else {
        console.log(res.data.msg);
      }
    });
  },
  methods: {
    pageChange(pageNum) {

    },
    deleteArticle(id) {
      this.$message("删除成功");
    },
    updateArticle(id) {
       this.$router.push({
         name:'NewBlog',
         params:{
           blogId:id,
         }
       });
    },
    cancelCollection(id) {
      this.$message("取消收藏");
    },
    formatDate(date) {
      let time = new Date(date);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      return `${y}-${m}-${d}`;
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

</style>