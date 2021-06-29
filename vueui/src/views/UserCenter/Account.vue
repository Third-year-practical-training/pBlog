<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="2" class="el-menu-vertical-demo">
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
      <el-form :model="account" label-width="80px" :rules="rules" ref="account">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="account.email" style="width: 40%;float: left"></el-input>
          <el-button type="primary" v-loading="loading" style="float: left;margin-left: 5px" @click="updateEmail('account')">修改邮箱</el-button>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="account.username" :disabled="true" style="width: 40%;float: left"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="account.password" :disabled="true" style="width: 40%;float: left" type="password"></el-input>
          <el-button type="text" disabled style="float: left;margin-left: 5px" @click="updatePassword('user')">修改密码</el-button>
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
      loading:false,
      account: {
        username: '',
        password: '********',
        email: '',
      },
      user:{
        username: '',
        nickname: '',
        real_name: '',
        email: '',
        sex: '',
        birthday: '',
        edu_bg: '',
        description: '',
      },
      rules: {
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {
            pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/,
            message: '邮箱格式要正确',
          }],
      }
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user.username = this.$store.getters.getUser.username;
      this.user.nickname = this.$store.getters.getUser.nickname;
      this.user.real_name = this.$store.getters.getUser.real_name;
      this.user.email = this.$store.getters.getUser.email;
      this.user.sex = this.$store.getters.getUser.sex;
      this.user.birthday = this.$store.getters.getUser.birthday;
      this.user.edu_bg = this.$store.getters.getUser.edu_bg;
      this.user.description = this.$store.getters.getUser.description;
      this.account.username = this.user.username;
      this.account.email = this.user.email;
    }
  },
  methods: {
    updateEmail(formName) {
      if(this.$refs[formName].validate((valid)=>{
        if(valid){
          this.loading = true;
          this.user.email = this.account.email;
          const _this = this;
          this.$axios({
                url: 'http://localhost:8080/user/updateinfo',
                method: 'put',
                data: JSON.stringify(this.user),
                headers:
                    {
                      'Content-Type': 'application/json'
                    }
              }
          ).then(res => {
            _this.loading = false;
            _this.$message('修改成功');
          });
        }else {
          this.$message('请注意格式');
        }
      }));
    },
    updatePassword(formName) {

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