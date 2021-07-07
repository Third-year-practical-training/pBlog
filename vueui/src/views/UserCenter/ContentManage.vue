<template>
  <el-container>
    <el-header style="background-color: white">
      <el-page-header @back="goBack" content="个人中心页面" style="background-color: white">
      </el-page-header>
    </el-header>
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
            <div v-for="(item,i) in blogs" :key="i" class="el-card" style="text-align: left">
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
                           @click="updateArticle(i)"></el-button>
                <el-button type="danger" icon="el-icon-delete" circle style="margin-right: 10px"
                           @click="deleteArticle(i)"></el-button>
              </div>
            </div>
            <div class="block">
              <el-pagination
                  layout="prev, pager, next"
                  :current-page="blog.pageNum"
                  :page-size="blog.pageSize"
                  :total="blog.total"
                  @current-change="blogPage">
              </el-pagination>
            </div>
          </el-tab-pane>
          <el-tab-pane label="我的收藏" name="MyCollection">
            <div v-for="(collection,j) in collections" :key="j" class="el-card" style="text-align: left">
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
                           @click="cancelCollection(j)"></el-button>
              </div>
            </div>
            <div class="block">
              <el-pagination
                  layout="prev, pager, next"
                  :current-page="collection.colpageNum"
                  :page-size="collection.colpageSize"
                  :total="collection.coltotal"
                  @current-change="collectionPage">
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
  name: "ContentManage",

  data() {
    return {
      blogs: [],
      collections: [],
      collection: {
        colpageNum: 1,
        colpageSize: 5,
        coltotal: 0,
      },
      blog: {
        pageNum: 1,
        pageSize: 0,
        total: 0,
      },
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
    this.blogPage(1);
    this.collectionPage(1);
    this.loading = false;
  },
  methods: {
    blogPage(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/articles/findByUserId', {
        params: {
          id: this.user.id,
          pageNum: current,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.blogs = res.data.data.list;
          _this.blog.pageNum = res.data.data.pageNum;
          _this.blog.total = res.data.data.total;
          _this.blog.pageSize = res.data.data.pageSize;
        } else {
          console.log(res.data.msg);
        }
      });
    },
    collectionPage(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/articles/collectList', {
        params: {
          id: this.user.id,
          pageNum: current,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.collections = res.data.data.list;
          _this.collection.colpageNum = res.data.data.pageNum;
          _this.collection.coltotal = res.data.data.total;
          _this.collection.colpageSize = res.data.data.pageSize;
        } else {
          console.log(res.data.msg);
        }
      });
    },
    deleteArticle(index) {
      const _this = this;
      this.$axios.delete('http://localhost:8080/article/deleteById', {
        params: {
          id: this.blogs[index].id
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.blogs.splice(index, 1);
          _this.$message('删除成功');
        }
      })
    },
    updateArticle(index) {
      this.$router.push({
        name: 'NewBlog',
        params: {
          blogId: this.blogs[index].id,
        }
      });
    },
    cancelCollection(index) {
      const _this = this;
      let data = new FormData();
      data.append('userId', this.user.id);
      data.append(' articleId', this.collections[index].id);
      this.$axios.put('http://localhost:8080/article/changeCollection', data).then(res => {
        if (res.data.code == 100) {
          _this.collections.splice(index, 1);
          _this.$message('删除收藏成功');
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
    goBack() {
      this.$router.push('/mainpage')
    }
  }
}
</script>

<style>

</style>