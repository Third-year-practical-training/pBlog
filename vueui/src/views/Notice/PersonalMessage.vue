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
      <el-tabs v-model="activeName">
        <el-tab-pane name="toMyMsg" label="我收到的私信">
          <div v-for="item in toMyMsg" :key="item" class="el-card" style="text-align: left">
            <div>
              <el-avatar v-if="item.fromId !== '' &&  item.fromId != null" :src="getAvatar(item.fromId)"
                         size="medium"
                         style="margin-top: 5px;margin-left: 5px"></el-avatar>
              <span style="color:cornflowerblue;">{{ item.fromName }}</span>
              <span>{{ formatDate(item.date) }}</span>
              <span>发来的私信: </span>
              <span style="color:lightcoral;">{{ item.content }}</span>
              <el-button type="primary" icon="el-icon-message" circle
                         style="float: right;margin-right: 5px"></el-button>
              <el-button type="danger" icon="el-icon-delete" circle style="float: right;margin-right: 5px"></el-button>
            </div>
            <div style="margin-top: 10px">
              <span style="font-size: large;margin-left: 40px;margin-top: 50px;background-color:gray ">{{
                  item.content
                }}</span>
            </div>
            <el-link style="margin-left: 10px;margin-top: 20px;margin-bottom: 10px">文章链接: {{
                item.articleTitle
              }}
            </el-link>
            <el-button type="danger" icon="el-icon-delete" circle style="float: right;margin-right: 5px"></el-button>
          </div>
          <div class="block" style="text-align: center;">
            <el-pagination
                layout="prev, pager, next"
                :current-page="pageNum"
                :page-size="pageSize"
                :total="total"
                @current-change="page">
            </el-pagination>
          </div>
        </el-tab-pane>
      </el-tabs>
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
      toMyMsg: []
    }
  },
  created() {
    this.page(1);
  },
  methods: {
    page(pageNum){
      this.$axios.get()
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
  }
}
</script>

<style scoped>

</style>