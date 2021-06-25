<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="1" class="el-menu-vertical-demo" >
          <el-menu-item index="1" @click="menuClick">
            <span slot="title">个人信息</span>
          </el-menu-item>
          <el-menu-item index="2" @click="menuClick">
            <span slot="title">账号信息</span>
          </el-menu-item>
          <el-menu-item index="3" @click="menuClick">
            <span slot="title" >我的关注</span>
          </el-menu-item>
          <el-menu-item index="4" @click="menuClick">
            <span slot="title">内容管理</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-aside>

    <el-main>
      <el-card class="box-card">
        <el-avatar :size="150" :src="user.url"></el-avatar>
        <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ user.nickname }}</div>
      </el-card>
      <el-form :model="user" label-width="80px">
        <el-form-item label="昵称">
          <el-input v-model="user.nickname"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="user.real_name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="user.sex"></el-input>
        </el-form-item>
        <el-form-item label="教育背景">
          <el-select v-model="user.edu_bg" placeholder="请选择学历" style="float: left">
            <el-option label="小学" value="小学"></el-option>
            <el-option label="中学" value="中学"></el-option>
            <el-option label="高中" value="高中"></el-option>
            <el-option label="本科" value="本科"></el-option>
            <el-option label="硕士" value="硕士"></el-option>
            <el-option label="博士及以上" value="博士及以上"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="user.birthday" style="float: left;"></el-date-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input type="textarea" v-model="user.description"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="update">保存修改</el-button>
        </el-form-item>
      </el-form>
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
      this.user.nickname = this.$store.getters.getUser.nickname;
      this.user.real_name = this.$store.getters.getUser.real_name;
      this.user.email = this.$store.getters.getUser.email;
      this.user.sex = this.$store.getters.getUser.sex;
      this.user.birthday = this.$store.getters.getUser.birthday;
      this.user.edu_bg = this.$store.getters.getUser.edu_bg;
      this.user.description = this.$store.getters.getUser.description;
      this.user.url = 'http://localhost:8080/user/showPhotoById?userId=' + this.user.id;
    }
  },
  methods:{
    update(){
       this.$message("修改完成");
    },
    menuClick(menuItem){
      if(menuItem.index == 1){
        this.$router.push('/user-center/selfinformation');
      }
      if(menuItem.index == 2){
        this.$router.push('/user-center/account');
      }
      if(menuItem.index == 3){
        this.$router.push('/user-center/attention');
      }
      if(menuItem.index == 4){
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