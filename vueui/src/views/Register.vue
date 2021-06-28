<template>
  <body id="poster">
  <el-form class="register-container" label-position="left" label-width="0px" :rules="rules" :model="RegisterForm"
           ref="RegisterForm">
    <h3 class="register_title">账号注册</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="RegisterForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>

    <el-form-item prop="password">
      <el-input type="password" v-model="RegisterForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>

    <el-form-item prop="email">
      <el-input type="text" v-model="RegisterForm.email" auto-complete="off" placeholder="邮箱"></el-input>
    </el-form-item>

    <el-form-item style="width: 100%">
      <el-button type="primary" style="width: 100%; background: #505458;border: none"
                 v-loading.fullscreen.lock="loading"
                 v-on:click="register('RegisterForm')">注册账号
      </el-button>
    </el-form-item>

  </el-form>
  </body>
</template>

<script>
import axios from "axios";

export default {
  name: "Register",
  data() {
    return {
      RegisterForm: {
        username: '',
        password: '',
        email: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, message: '长度至少为3个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 8, max: 15, message: '长度在 8 到 15 个字符', trigger: 'blur'},
          {pattern: /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]/, message: '至少含有数字和字母'}
        ],
        email: [
          {required: true, message: '邮箱不能为空', trigger: 'blur'},
          {
            pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/,
            message: '邮箱格式要正确',
          }
        ]
      },
      loading: false,
    }
  },
  methods: {
    register(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = new FormData();
          const _this = this;
          this.loading = true;
          data.append('username', this.RegisterForm.username);
          data.append('password', this.RegisterForm.password);
          data.append('email', this.RegisterForm.email);
          axios.post('http://localhost:8080/user/signup', data).then(res => {
            if (res.data.code == 100) {
              _this.loading = false;
              _this.$message("注册成功");
              _this.$router.push('/');
            } else {
              _this.loading = false;
              _this.$message(res.data.msg);
            }
          });
        } else {
          this.$message('请注意格式')
        }
      });
    },
  }

}
</script>

<style scoped>
#poster {
  background: url("../assets/bg.png") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}

body {
  margin: 0px;
  padding: 0;
}

.register-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.register_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

</style>