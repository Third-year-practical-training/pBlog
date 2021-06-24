<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="1" class="el-menu-vertical-demo">
          <el-menu-item index="1">
            <span slot="title">个人信息</span>
          </el-menu-item>
          <el-menu-item index="2">
            <span slot="title">账号信息</span>
          </el-menu-item>
          <el-menu-item index="3">
            <span slot="title">我的关注</span>
          </el-menu-item>
          <el-menu-item index="4">
            <span slot="title">内容管理</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-aside>

    <el-main>
      <el-card class="box-card">
        <el-avatar :size="150" :src="this.user.url"></el-avatar>
        <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ this.user.nickname }}</div>
      </el-card>

    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "SelfPage",
  data() {
    return {
      user: {
        id: '',
        username: '',
        password: '',
        nickname: '',
        real_name: '',
        email: '',
        sex: '',
        birthday: '',
        edu_bg: '',
        description: '',
        url: '',
      }
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
      this.user.username = this.$store.getters.getUser.username;
      this.user.password = this.$store.getters.getUser.password;
      this.user.nickname = this.$store.getters.getUser.nickname;
      this.user.real_name = this.$store.getters.getUser.real_name;
      this.user.email = this.$store.getters.getUser.email;
      this.user.sex = this.$store.getters.getUser.sex;
      this.user.birthday = this.$store.getters.getUser.birthday;
      this.user.edu_bg = this.$store.getters.getUser.edu_bg;
      this.user.description = this.$store.getters.getUser.description;
      this.user.url = 'http://localhost:8080/user/showPhotoById?userId=' + this.user.id;
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