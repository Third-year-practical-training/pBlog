<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="3" class="el-menu-vertical-demo">
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
        </el-menu>
      </el-col>
    </el-aside>

    <el-main>
      <div v-for="(item,i) in attention" :key="i" class="el-card" style="height: 80px" v-loading="loading">
        <el-avatar :size="50" :src="getAvatar(item.id)"
                   style="float: left;margin-top: 20px;margin-left: 10px"></el-avatar>
        <el-link type="primary" style="float: left;margin-top: 40px;margin-left: 10px" @click="toAttention(item.id)">
          {{ item.nickname }}
        </el-link>
        <el-button type="primary" style="display: inline;float: right;margin-top: 20px;margin-right: 10px"
                   @click="cancelAttention(item.id,i)">取消关注
        </el-button>
      </div>
      <div class="block">
        <el-pagination
            layout="prev, pager, next"
            :current-page="pageNum"
            :page-size="pageSize"
            :total="total"
            @current-change="attentionPage">
        </el-pagination>
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "Attention",
  data() {
    return {
      loading: false,
      user: {
        id: '',
      },
      pageNum: 1,
      pageSize: 0,
      total: 0,
      attention: [],
    }
  },
  created() {
    this.loading = true;
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
    }
    this.attentionPage(1);
    this.loading = false;
  },
  methods: {
    attentionPage(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/users/attentionList', {
        params: {
          id: this.user.id,
          pageNum: current,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.attention = res.data.data.list;
          _this.pageNum = res.data.data.pageNum;
          _this.pageSize = res.data.data.pageSize;
          _this.total = res.data.data.total;
        }
      });
    },
    toAttention(id) {
      this.$router.push({
        name: 'AttentionPage',
        params: {
          id: id,
        }
      });
    },
    cancelAttention(id, i) {
      const _this = this;
      let data = new FormData();
      data.append('id', id);
      this.$axios.put('http://localhost:8080/user/changeAttention', data).then(res => {
        if (res.data.code == 100) {
          _this.$message('取消成功');
        }
      });
      this.attention.splice(i,1);
    },
    getAvatar(id) {
      return 'http://localhost:8080/user/showPhotoById?userId=' + id;
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
    }
  }
}
</script>

<style>

</style>