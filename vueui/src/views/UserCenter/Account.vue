<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="1" class="el-menu-vertical-demo">
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
      <el-form :model="user" label-width="80px">
        <el-form-item label="邮箱">
          <el-input v-model="user.email" :disabled="true" style="width: 40%;float: left"></el-input>
          <el-link type="primary"style="float: left;margin-left: 5px" @click="updateEmail">修改邮箱</el-link>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="user.username" :disabled="true" style="width: 40%;float: left"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="user.password" :disabled="true" style="width: 40%;float: left"></el-input>
          <el-link type="primary" style="float: left;margin-left: 5px" @click="updatePassword">修改密码</el-link>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "Account",
  data() {
    return {
      user: {
        id: '',
        username: '',
        password: '********',
        email: '',
      }
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
      this.user.username = this.$store.getters.getUser.username;
      this.user.email = this.$store.getters.getUser.email;
    }
  },
  methods: {
    updateEmail() {
      this.$message("修改完成");
    },
    updatePassword() {
      this.$message("修改完成");
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