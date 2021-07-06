<template>
  <el-container>
    <el-aside width="200px">
      <el-col :span="12">
        <el-menu default-active="1" class="el-menu-vertical-demo">
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
        <el-tab-pane name="MyComments" label="我的评论">
          <div v-for="item in mycomments" :key="item" class="el-card" style="text-align: left">
            <div>
              <span v-if="item.toUserId === '' || item.toUserId == null"
                    style="color:#7d7d7d;margin-left: 5px">你评论了以下内容:</span>
              <span v-if="item.toUserId !== '' &&  item.toUserId != null"
                    style="color:#7d7d7d;margin-left: 5px">你回复了</span>
              <el-avatar v-if="item.toUserId !== '' &&  item.toUserId != null" :src="getAvatar(item.toUserId)"
                         size="medium"
                         style="margin-top: 5px;margin-left: 5px"></el-avatar>
              <el-link v-if="item.toUserId !== '' &&  item.toUserId != null"
                       style="margin-left: 5px;color: cornflowerblue;font-size: medium">{{ item.toUserNickname }}
              </el-link>
              <span v-if="item.toUserId !== '' &&  item.toUserId != null" style="color:#7d7d7d;">的评论</span>
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
          <div class="block">
            <el-pagination
                layout="prev, pager, next"
                :current-page="user.pageNum"
                :page-size="user.pageSize"
                :total="user.total"
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
  name: "Notices",
  data() {
    return {
      activeName: 'MyComments',
      user: {
        id: '',
        pageNum: 1,
        total: 0,
        pageSize: 0,
      },
      mycomments: [],
    }
  },
  created() {
    if (this.$store.getters.getUser.id) {
      this.user.id = this.$store.getters.getUser.id;
    }
    this.page(1);
  },
  methods: {
    page(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/comment/selectById', {
        params: {
          id: this.user.id,
          pageNum: current,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.mycomments = res.data.data.list;
          _this.user.pageNum = res.data.data.pageNum;
          _this.user.pageSize = res.data.data.pageSize;
          _this.user.total = res.data.data.total;
        }
      });
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
  }
}
</script>

<style scoped>

</style>