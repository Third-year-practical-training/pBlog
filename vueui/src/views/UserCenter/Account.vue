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
      <el-form :model="user" label-width="80px" :rules="rules" ref="user">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" style="width: 40%;float: left"></el-input>
          <el-link type="primary" style="float: left;margin-left: 5px" @click="updateEmail('user')">修改邮箱</el-link>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="user.username" :disabled="true" style="width: 40%;float: left"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" style="width: 40%;float: left" type="password"></el-input>
          <el-link type="primary" style="float: left;margin-left: 5px" @click="updatePassword('user')">修改密码</el-link>
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
      },
      rules: {
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {
            pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/,
            message: '邮箱格式要正确',
          }],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 8, max: 15, message: '长度在 8 到 15 个字符', trigger: 'blur'},
          {pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]/, message: '至少含有数字和字母'}
        ],
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
    updateEmail(formName) {
      if(this.$refs[formName].validate((valid)=>{
        if(valid){
          let data = new FormData();
          data.append('email', this.user.email);
          const _this = this;
          this.$axios.put('http://localhost:8080/user/updateinfo', data).then(res => {
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