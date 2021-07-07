<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="2" class="el-menu-vertical-demo">
          <el-menu-item index="1" @click="menuClick">
            <span slot="title">评论</span>
          </el-menu-item>
          <el-menu-item index="2" @click="menuClick">
            <span slot="title">私信</span>
          </el-menu-item>
          <el-menu-item index="3" @click="menuClick">
            <span slot="title">系统通知</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-aside>
    <el-main>
      <el-container>
        <el-aside>
          <el-menu default-active="1" class="el-menu-vertical-demo" @select="handleMessage">
            <div v-for="(item,i) in users" :key="i">
              <el-menu-item index="i">
                <template slot="title">
                  <el-avatar :src="item.photoUrl"></el-avatar>
                  <span>{{ item.nickname }}</span>
                </template>
              </el-menu-item>
            </div>
          </el-menu>
        </el-aside>
        <el-main>
          <div style="border-radius: 0px" class="el-card">
            <div v-for="(item,i) in messages">
                <span>{{item.fromName}}</span>
                <span>{{formatDate(item.date)}}</span>
                <span>{{item.content}}</span>
            </div>
          </div>
          <el-input
              type="textarea"
              autosize
              placeholder="请输入内容"
              v-model="inputs">
          </el-input>
        </el-main>
      </el-container>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "PersonalMessage",
  data() {
    return {
      activeName: 'toMyMessage',
      pageNum: 1,
      pageSize: 0,
      total: 0,
      curUser: {},
      users: [],
      messages: [],
      inputs: ''
    }
  },
  created() {
    this.curUser = _this.$store.getters.getUser;
    this.getUsers();
    this.page(1);
  },
  methods: {
    page(pageNum) {

    },
    getUsers() {
      const _this = this;
      this.$axios.get('http://localhost:8080/message/getMyUser', {
        params: {
          id: this.curUser.id
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.users = res.data.data;
        }
      })
    },
    menuClick(menuItem) {
      if (menuItem.index == 1) {
        this.$router.push('/comments');
      }
      if (menuItem.index == 2) {
        this.$router.push('/personalmessage');
      }
      if (menuItem.index == 3) {
        this.$router.push('/systemnotice');
      }
    },
    getAvatar(id) {
      return 'http://localhost:8080/user/showPhotoById?userId=' + id;
    },
    formatDate(date) {
      let time = new Date(date);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      return `${y}-${m}-${d}`;
    },
    handleMessage(index) {
      const _this = this;
      this.$axios.get('http://localhost:8080/message/find', {
        params: {
          fromId: this.curUser.id,
          toId: this.users[index].id,
          pageNum: 1
        }
      }).then(res => {
        _this.messages = res.data.data.list;
      })
    }
  }
}
</script>

<style scoped>

</style>