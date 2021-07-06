<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="1" class="el-menu-vertical-demo">
          <el-menu-item index="1" @click="menuClick">
            <span slot="title">评论</span>
          </el-menu-item>
          <el-menu-item index="2" @click="menuClick">
            <span slot="title">私信</span>
          </el-menu-item>
          <el-menu-item index="3" @click="menuClick">
            <span slot="title">系统通知</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-aside>
    <el-main>
      <el-tabs v-model="activeName">
        <el-tab-pane name="MyComments" label="我的评论">
          <div v-for="item in myNotice" :key="item" class="el-card" style="text-align: left">

          </div>
          <div class="block">
            <el-pagination
                layout="prev, pager, next"
                :current-page="user.pageNum"
                :page-size="user.pageSize"
                :total="user.total"
                @current-change="page">
            </el-pagination>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "SystemNotice",
  data(){
    return{
      activeName: 'MyNotice',
      user: {
        id: '',
        pageNum: 1,
        total: 0,
        pageSize: 0,
      },
      myNotice: [],
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
    }
    // this.page(1);
  },
  methods: {
    page(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/comment/selectById', {
        params: {
          id: this.user.id,
          pageNum: current,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.myNotice = res.data.data.list;
          _this.user.pageNum = res.data.data.pageNum;
          _this.user.pageSize = res.data.data.pageSize;
          _this.user.total = res.data.data.total;
        }
      });
    },
    menuClick(menuItem) {
      if (menuItem.index == 1) {
        this.$router.push('/comments');
      }
      if (menuItem.index == 2) {
        this.$router.push('/personalmessage');
      }
      if (menuItem.index == 3) {
        this.$router.push('/systemnotice');
      }
    },
    getAvatar(id) {
      return 'http://localhost:8080/user/showPhotoById?userId=' + id;
    },
  }
}
</script>

<style scoped>

</style>