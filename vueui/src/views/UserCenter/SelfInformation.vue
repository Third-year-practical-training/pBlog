<template>
  <el-container>
    <el-header style="background-color: white">
      <el-page-header @back="goBack" content="个人中心页面" style="background-color: white">
      </el-page-header>
    </el-header>
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
        <el-card class="box-card">
          <el-avatar :size="150" :src="user.photoUrl"></el-avatar>
          <div class="text item" style="font-family: 'Arial Black';font-size: large">{{ user.nickname }}</div>
          <div>
            <el-upload
                class="upload-demo"
                ref="upload"
                action="http://localhost:8080/user/changePhoto"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :on-error="handleAvatarError"
                :show-file-list="false">
              <el-button size="small" type="primary" v-loading="avatarLoading">点击上传</el-button>
            </el-upload>
          </div>
        </el-card>
        <el-form :model="user" label-width="80px" style="margin-top: 10px">
          <el-form-item label="昵称">
            <el-input v-model="user.nickname"></el-input>
          </el-form-item>
          <el-form-item label="真实姓名">
            <el-input v-model="user.real_name"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-select v-model="user.sex" style="float: left">
              <el-option label="男" value="男"></el-option>
              <el-option label="女" value="女"></el-option>
            </el-select>
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
              <el-date-picker type="date" placeholder="选择日期" v-model="user.birthday"
                              style="float: left;"></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="个人简介">
            <el-input type="textarea" v-model="user.description"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateInformation" v-loading="infoLoading">保存修改</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

export default {
  name: "SelfPage",
  data() {
    return {
      user: {

      },
      avatarLoading: false,
      infoLoading: false,
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user = this.$store.getters.getUser;
    }
  },
  methods: {
    updateInformation() {
      this.infoLoading = true;
      let time = new Date(this.user.birthday);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      this.user.birthday = `${y}-${m}-${d}`;
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
        _this.infoLoading = false;
        _this.$store.commit("SET_USERINFO" , this.user);
        _this.$message('修改成功');
      });
    },
    updateAvatar(file) {
      let photo = file.raw;
      const _this = this;
      let data = new FormData();
      data.append('photo', photo);
      this.$axios.post('http://localhost:8080/user/changePhoto', data).then(res => {
        _this.$message('头像修改成功');
      })
    },
    handleAvatarSuccess(res, file, fileList) {
      this.user.photoUrl = URL.createObjectURL(file.raw);
      this.$store.commit("SET_USERINFO" , this.user);
      this.updateAvatar(file);
      this.avatarLoading = false;
    },
    beforeAvatarUpload(file) {
      this.avatarLoading = true;
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        this.avatarLoading = false;
      }
      return isLt2M;
    },
    handleAvatarError(err, file, fileList) {
      this.$message('上传失败');
      this.avatarLoading = false;
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
    },
    goBack() {
      this.$router.push('/mainpage')
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