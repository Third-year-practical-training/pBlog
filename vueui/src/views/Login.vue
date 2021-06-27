<template>
  <body id="poster">
  <el-form class="login-container" label-position="left" label-width="0px">
    <h3 class="login_title">IT技术论坛</h3>
    <el-form-item>
      <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>

    <el-form-item>
      <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>

    <el-form-item style="width: 100%">
      <el-button type="primary" style="float: right;background: #505458;border: none" v-loading.fullscreen.lock="loading"
                 v-on:click="login">登录</el-button>
      <el-button type="primary" style="float: left; background: #505458;border: none" v-on:click="register">注册</el-button>
    </el-form-item>


  </el-form>
  </body>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
      },
      loading: false,
    }
  },
  methods: {
    login() {
      if(this.loginForm.username != '' && this.loginForm.password != ''){
        let data = new FormData();
        const _this = this;
        this.loading = true;
        data.append('username',this.loginForm.username);
        data.append('password',this.loginForm.password);
        this.$axios.post('http://localhost:8080/user/signin',data).then(res=>{
          if(res.data.code == 100){
            _this.loading = false;
            _this.$message("登录成功");
            const userInfo = res.data.data;
            _this.$store.commit("SET_USERINFO" , userInfo);
            _this.$router.push('/MainPage');
          }else{
            _this.loading = false;
            _this.$message(res.data.msg);
          }
        });
      }else {
        this.$message("账号密码不能为空")
      }
    },
    register(){
     this.$router.push("/register");
    }

  }
}
</script>

<style >
#poster {
  background: url("../assets/bg.png") no-repeat center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}
body{
  margin: 0px;
  padding: 0;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

</style>