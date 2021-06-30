<template>
  <el-container>
    <el-main>
      <el-row type="flex" class="row-bg" justify="space-around">
        <el-col :span="21">
          <div class="grid-content bg-purple-light">
            <h1>{{ blog.title }}</h1>
            <div>
                  <span style="color: #7d7d7d;font-size: small"><i class="el-icon-date"></i> 发表于：{{
                      blog.timestamp
                    }}</span>
              <el-divider direction="vertical"></el-divider>
              <span style="color: #7d7d7d;font-size: small"><i class="el-icon-user-solid"></i> 作者：{{
                  blog.nickname
                }}</span>
              <el-divider direction="vertical"></el-divider>
              <span style="color: #7d7d7d;font-size: small"><i class="el-icon-document"></i> 分类：{{
                  blog.classify.name
                }}</span>
              <br>
              <span style="color: #7d7d7d;font-size: small"><i class="el-icon-collection-tag"></i> 标签：</span>
              <div style="display: inline" v-for="tag in blog.tags" :key="tag" class="el-tag">
                {{ tag.name }}
              </div>
              <br>
              <article class="markdown-body" style="text-align: left;line-height: 2em;font-size: 17px"
                       v-html="blog.content"></article>
              <div class="grid-content bg-purple-light">
                <p class="author-text"><b>版权声明：</b>本文为博主的原创文章，遵循 CC 4.0 BY-SA 版权协议，禁止转载。</p>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
      <div style="margin-top: 10px;text-align: left">
        <el-avatar :size="40" :src="user.url" style="margin-left: 22px;float: left;margin-left: 50px"></el-avatar>
        <div>
          <el-input type="textarea" autosize contenteditable="true" placeholder="请输入内容"
                    style="max-width: 1000px;margin-left: 10px" v-model="mycomment"></el-input>
        </div>
        <div style="float: right;margin-right: 380px;margin-top: 5px">
          <el-button size="medium" type="primary" @click="pushComment">发表评论</el-button>
        </div>
      </div>
      <div v-for="item in comments" :key="item" style="text-align: left;margin-top: 0px;margin-left: 50px;width: 1060px"
           class="el-card">
        <div>
          <el-avatar size="large" :src="item.headImg" style="margin-top: 10px;margin-left: 10px"></el-avatar>
          <span style=" color: black;font-size: 18px;font-weight: bold;margin-left: 2px;">{{ item.name }}</span>
          <span style="color: #7d7d7d;font-size: 5px;margin-left: 5px">{{ item.time }}</span>
        </div>
        <div style="margin-top: 10px;margin-left: 10px">
          <span>{{ item.comment }}</span>
        </div>
        <div>
          <div v-for="reply in item.reply" :key="reply" class="el-card" style="margin-left: 20px">
            <div>
              <el-avatar size="large" :src="reply.fromHeadImg" style="margin-top: 10px;margin-left: 10px"></el-avatar>
              <span style=" color: black;font-size: 18px;font-weight: bold;margin-left: 2px;">{{ reply.from }}</span>
              <span style="color: #7d7d7d;font-size: 5px;margin-left: 5px">{{ reply.time }}</span>
            </div>
            <div style="margin-top: 10px;margin-left: 10px">
              <span>{{ reply.comment }}</span>
            </div>
          </div>
        </div>
      </div>


    </el-main>
  </el-container>

</template>

<script>
import marked from "marked";

let rendererMD = new marked.Renderer();
marked.setOptions({
  renderer: rendererMD,
  gfm: true,
  tables: true,
  breaks: false,
  pedantic: false,
  sanitize: false,
  smartLists: true,
  smartypants: false,
});
export default {
  name: "BlogShow",
  data() {
    return {
      mycomment:'',
      blog: {
        id: null,
        nickname: '',
        title: '',
        classify: {},
        tags: [],
        timestamp: '',
        content: ''
      },
      user: {
        id:'',
        url: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
      },
      comments: [
        {
          name: 'Lana Del Rey',
          id: 19870621,
          headImg: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
          comment: '我发布一张新专辑Norman Fucking Rockwell,大家快来听啊',
          time: '2019年9月16日 18:43',
          commentNum: 2,
          like: 15,
          inputShow: false,
          reply: [
            {
              from: 'Taylor Swift',
              fromId: 19891221,
              fromHeadImg: 'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
              to: 'Lana Del Rey',
              toId: 19870621,
              comment: '我很喜欢你的新专辑！！',
              time: '2019年9月16日 18:43',
              commentNum: 1,
              like: 15,
              inputShow: false
            },
            {
              from: 'Ariana Grande',
              fromId: 1123,
              fromHeadImg: 'https://ae01.alicdn.com/kf/Hf6c0b4a7428b4edf866a9fbab75568e6U.jpg',
              to: 'Lana Del Rey',
              toId: 19870621,
              comment: '别忘记宣传我们的合作单曲啊',
              time: '2019年9月16日 18:43',
              commentNum: 0,
              like: 5,
              inputShow: false

            }
          ]
        },
        {
          name: 'Taylor Swift',
          id: 19891221,
          headImg: 'https://ae01.alicdn.com/kf/H94c78935ffa64e7e977544d19ecebf06L.jpg',
          comment: '我发行了我的新专辑Lover',
          time: '2019年9月16日 18:43',
          commentNum: 1,
          like: 5,
          inputShow: false,
          reply: [
            {
              from: 'Lana Del Rey',
              fromId: 19870621,
              fromHeadImg: 'https://ae01.alicdn.com/kf/Hd60a3f7c06fd47ae85624badd32ce54dv.jpg',
              to: 'Taylor Swift',
              toId: 19891221,
              comment: '新专辑和speak now 一样棒！',
              time: '2019年9月16日 18:43',
              commentNum: 25,
              like: 5,
              inputShow: false
            }
          ]
        },
        {
          name: 'Norman Fucking Rockwell',
          id: 20190830,
          headImg: 'https://ae01.alicdn.com/kf/Hdd856ae4c81545d2b51fa0c209f7aa28Z.jpg',
          comment: 'Plz buy Norman Fucking Rockwell on everywhere',
          time: '2019年9月16日 18:43',
          commentNum: 0,
          like: 5,
          inputShow: false,
          reply: []
        },
      ]
    }
  },
  created() {
    this.getBlog();
  },
  methods: {
    getBlog() {
      this.blog.id = this.$route.params.blogId;
      this.user.id = this.$store.getters.getUser.id;
      const _this = this
      this.$axios.get('http://localhost:8080/article/findById', {
        params: {
          id: this.blog.id,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.blog.nickname = res.data.data.userNickname;
          _this.user.url = 'http://localhost:8080/user/showPhotoById?userId=' + res.data.data.userId;
          _this.blog.title = res.data.data.title;
          _this.blog.classify = res.data.data.articleType;
          _this.blog.tags = res.data.data.tagList;
          _this.blog.timestamp = _this.formatDate(res.data.data.date);
          _this.blog.content = marked(res.data.data.content);
        }
      });
    },
    formatDate(date) {
      let time = new Date(date);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      return `${y}-${m}-${d}`;
    },
    pushComment(){
      const _this = this;
      this.$axios.post('http://localhost:8080/comment/new',{
        params:{
          userId: this.user.id,
          articleId: this.blog.id,
          date:this.formatDate(new Date()),
          content:this.mycomment,
        }
      }).then(res =>{
        _this.$message('评论成功');
      })
    }
  },

}
</script>

<style scoped>

.bg-purple-light {
  background: #FFFFFF;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.row-bg {
  padding: 10px 0;
  background-color: rgba(255, 255, 255, 0);
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}

</style>