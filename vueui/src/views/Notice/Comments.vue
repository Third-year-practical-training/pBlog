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
        <el-tab-pane name="OtherComments" label="别人的评论">
          <div v-for="item in othercomments" :key="item" class="el-card" style="text-align: left;">
            <div>
              <el-avatar :src="item.headImg" size="medium" class="avatar"></el-avatar>
              <el-link v-text="item.name" style="margin-left: 5px;color: #7d7d7d;font-size: medium"></el-link>
              <span style="color:#7d7d7d;">评论了你的博客</span>
            </div>
            <div style="margin-top: 10px">
              <span style="font-size: large;margin-left: 40px;margin-top: 50px;background-color:gray ">{{ item.comment }}</span>
            </div>
            <el-link style="margin-left: 40px;margin-top: 20px;margin-bottom: 10px">文章链接</el-link>
          </div>
        </el-tab-pane>
        <el-tab-pane name="MyComments" label="我的评论">
          <div v-for="item in mycomments" :key="item" class="el-card" style="text-align: left">
              <div>
                <span style="color:#7d7d7d;margin-left: 5px">你评论了</span>
                <el-avatar :src="item.headImg" size="medium" style="margin-top: 5px;margin-left: 5px"></el-avatar>
                <el-link v-text="item.name" style="margin-left: 5px;color: #7d7d7d;font-size: medium"></el-link>
                <span style="color:#7d7d7d;">的博客</span>
              </div>
              <div style="margin-top: 10px">
                <span style="font-size: large;margin-left: 40px;margin-top: 50px;background-color:gray ">{{ item.comment }}</span>
              </div>
              <el-link style="margin-left: 40px;margin-top: 20px;margin-bottom: 10px">文章链接</el-link>
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
      activeName: 'OtherComments',
      mycomments: [{
        name: 'Norman Fucking Rockwell',
        id: 20190830,
        headImg: 'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
        comment: 'Plz buy Norman Fucking Rockwell on everywhere',
        time: '2019年9月16日 18:43',
        commentNum: 0,
        like: 5,
        inputShow: false,
        reply: []
      }, {
        name: 'Norman Fucking Rockwell',
        id: 20190830,
        headImg: 'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
        comment: 'Plz buy Norman Fucking Rockwell on everywhere',
        time: '2019年9月16日 18:43',
        commentNum: 0,
        like: 5,
        inputShow: false,
        reply: []
      },],
      othercomments: [{
        name: 'Norman Fucking Rockwell',
        id: 20190830,
        headImg: 'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
        comment: 'Plz buy Norman Fucking Rockwell on everywhere',
        time: '2019年9月16日 18:43',
        commentNum: 0,
        like: 5,
        inputShow: false,
        reply: []
      }, {
        name: 'Norman Fucking Rockwell',
        id: 20190830,
        headImg: 'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
        comment: 'Plz buy Norman Fucking Rockwell on everywhere',
        time: '2019年9月16日 18:43',
        commentNum: 0,
        like: 5,
        inputShow: false,
        reply: []
      },],
    }
  },
  methods: {
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
    }
  }
}
</script>

<style scoped>

</style>