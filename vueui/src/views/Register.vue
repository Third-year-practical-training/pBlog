<template>
  <body id="poster" >
  <el-form class="register-container" label-position="left" label-width="0px" >
    <h3 class="register_title">账号注册</h3>
    <el-form-item>
      <el-input type="text" v-model="RegisterForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>

    <el-form-item>
      <el-input type="password" v-model="RegisterForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>

    <el-form-item>
      <el-input type="text" v-model="RegisterForm.email" auto-complete="off" placeholder="邮箱"></el-input>
    </el-form-item>

    <el-form-item style="width: 100%">
      <el-button type="primary"  style="width: 100%; background: #505458;border: none" v-loading.fullscreen.lock="loading"
                 v-on:click="register">注册账号
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
      loading: false,
    }
  },
  methods: {
    register() {
       if(this.RegisterForm.username != '' && this.RegisterForm.password.length >= 6 && this.RegisterForm.email != ''){
         let data = new FormData();
         const _this = this;
         this.loading = true;
         data.append('username',this.RegisterForm.username);
         data.append('password',this.RegisterForm.password);
         data.append('email',this.RegisterForm.email);
         axios.post('http://localhost:8080/user/signup',data).then(res=>{
           if(res.data.code == 100){
              _this.loading = false;
              _this.$message("注册成功");
              _this.$router.push('/');
           }else{
             _this.loading = false;
             _this.$message(res.data.msg);
           }
         });
       }else {
         this.$message("账号密码邮箱不能为空或密码长度小于6！")
       }
    }
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
body{
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