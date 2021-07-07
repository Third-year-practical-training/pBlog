<template>
  <el-container>
    <el-header style="background-color: white">
      <el-page-header  style="background-color: white" @back="goBack" content="通知页面" >
      </el-page-header>
    </el-header>
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
            <el-menu default-active="1" class="el-menu-vertical-demo">
              <div v-for="(item,i) in users" :key="i">
                <el-menu-item index="i" @click="handleMessage(i)">
                  <template slot="title">
                    <el-avatar :src="item.photoUrl"></el-avatar>
                    <span>{{ item.nickname }}</span>
                  </template>
                </el-menu-item>
              </div>
            </el-menu>
          </el-aside>
          <el-main>
            <div style="border-radius: 0px;max-width: 600px" class="el-card">
              <div v-for="(item,i) in messages" style="min-height: 50px;">
                <div v-if="item.fromId != curUser.id" style="float: left">
                  <span style="color: #333333">{{ item.fromName }}  </span>
                  <span style="font-size: x-small;color: #7d7d7d">{{ formatDate(item.date) }}</span>
                </div>
                <div v-if="item.fromId != curUser.id" style="margin-top: 20px;float: left">
                  <span>{{ item.content }}</span>
                </div>
                <div v-if="item.fromId == curUser.id" style="float: right">
                  <span style="font-size: x-small;color: #7d7d7d">{{ formatDate(item.date) }}</span>
                  <span style="color: #333333">  {{ item.fromName }}</span>
                </div>
                <div v-if="item.fromId == curUser.id" style="margin-top: 20px;float: right">
                  <span>{{ item.content }}</span>
                </div>
              </div>
            </div>
            <div style="text-align: left">
              <el-input
                  type="textarea"
                  autosize
                  placeholder="请输入内容"
                  v-model="inputs"
                  style="max-width: 600px;float: left">
              </el-input>
              <el-button type="primary" size="small" style="margin-left: 5px" @click="sendMessage">发送</el-button>
            </div>
          </el-main>
        </el-container>
      </el-main>
    </el-container>
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
      inputs: '',
      activeIndex: 1,
    }
  },
  created() {
    this.curUser = this.$store.getters.getUser;
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
      this.activeIndex = index;
      this.$axios.get('http://localhost:8080/message/find', {
        params: {
          fromId: this.curUser.id,
          toId: this.users[index].id,
          pageNum: 1
        }
      }).then(res => {
        _this.messages = res.data.data.list;
      })
    },
    sendMessage() {
      const _this = this;
      let message = new FormData();
      let a = {};
      a.id = null;
      a.fromId = this.curUser.id;
      a.fromName = this.curUser.nickname;
      a.toId = this.users[this.activeIndex].id;
      a.toName = this.users[this.activeIndex].nickname;
      a.date = new Date();
      a.content = this.inputs;
      message.append('fromId', this.curUser.id);
      message.append('fromName', this.curUser.nickname);
      message.append('toId', this.users[this.activeIndex].id);
      message.append('toName', this.users[this.activeIndex].nickname);
      message.append('date', new Date());
      message.append('content', this.inputs);
      this.$axios.put('http://localhost:8080/message/new', message).then(res => {
        if (res.data.code == 100) {
          _this.$message('发送成功');
          _this.inputs = '';
          _this.messages.push(a);
        }
      })
    },
    goBack() {
      this.$router.push('/mainpage')
    }
  }
}
</script>

<style scoped>

</style>