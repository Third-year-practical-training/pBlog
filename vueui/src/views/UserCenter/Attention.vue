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
      <div v-for="o in 4" :key="o" class="el-card" style="height: 80px">
        <el-avatar :size="50" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                   style="float: left;margin-top: 20px;margin-left: 10px"></el-avatar>
        <el-link type="primary" style="float: left;margin-top: 40px;margin-left: 10px" @click="toAttention">{{'关注者'+o}}</el-link>
        <el-button type="primary" style="display: inline;float: right;margin-top: 20px;margin-right: 10px">取消关注</el-button>
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "Attention",
  data() {
    return {
      user: {
        id: '',
        attentionCount: '',
      }
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
      this.user.attentionCount = this.$store.getters.getUser.attentionCount;
    }
  },
  methods: {
    update() {
      this.$message("修改完成");
    },
    toAttention(){
      this.$message("进入关注者主页")
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