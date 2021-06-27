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
                  blog.classify
                }}</span>
              <br>
              <span style="color: #7d7d7d;font-size: small"><i class="el-icon-collection-tag"></i> 标签：</span>
              <div style="display: inline" v-for="tag in blog.tags" :key="tag" class="el-tag">
                {{ tag }}
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
      <div v-clickoutside="hideReplyBtn" @click="inputFocus" class="my-reply">
        <el-avatar class="header-img" :size="40" :src="user.url"></el-avatar>
        <div class="reply-info">
          <div
              tabindex="0"
              contenteditable="true"
              id="replyInput"
              spellcheck="false"
              placeholder="输入评论..."
              class="reply-input"
          >
          </div>
        </div>
        <div class="reply-btn-box">
          <el-button class="reply-btn" size="medium" type="primary">发表评论</el-button>
        </div>
      </div>
      <div v-for="(item,i) in comments" :key="i" class="author-title">
        <el-avatar class="header-img" :size="40" :src="item.headImg"
                   style="float: left;display: inline-block;margin-left: 50px"></el-avatar>
        <div class="author-info">
          <span class="author-name">{{ item.name }}</span>
          <span class="author-time">{{ item.time }}</span>
        </div>
        <div class="talk-box">
          <span class="reply">{{ item.comment }}</span>
        </div>
        <div class="reply-box">
          <div v-for="(reply,j) in item.reply" :key="j" class="author-title">
            <el-avatar class="header-img" :size="40" :src="reply.fromHeadImg"></el-avatar>
            <div class="author-info">
              <span class="author-name">{{ reply.from }}</span>
              <span class="author-time">{{ reply.time }}</span>
            </div>
            <div class="talk-box">
              <span class="reply">回复 {{ reply.to }}: {{ reply.comment }}</span>
            </div>
            <el-divider></el-divider>
            <div class="reply-box"></div>
          </div>
          <el-divider></el-divider>
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
      btnShow: false,

      blog: {
        id: null,
        nickname: 'cy1798',
        title: 'markdown文件示例',
        classify: '学习',
        tags: ['前端', '语法'],
        description: 'markdown文件示例',
        timestamp: '2020-5-21',
        content: '<h2><a id="CSS_0"></a>CSS入门属性</h2> <h3><a id="1css__1"></a>1.css 是什么</h3>'
      },
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
  methods: {
    inputFocus() {
      let replyInput = document.getElementById('replyInput');
      replyInput.style.padding = "8px 8px"
      replyInput.style.border = "2px solid blue"
      replyInput.focus()
    },
    hideReplyBtn() {
      this.btnShow = false
      let replyInput = document.getElementById('replyInput');
      replyInput.style.padding = "10px"
      replyInput.style.border = "none"
    },
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

.my-reply {
  padding: 10px;
  background-color: #fafbfc;
}

.header-img {
  display: inline-block;
  vertical-align: top;
  float: left;
  margin-left: 10px;
  margin-top: 5px;;
}

.reply-info {
  display: inline-block;
  margin-left: 5px;
  width: 90%;
}

.reply-input {
  min-height: 20px;
  line-height: 22px;
  text-align: left;
  padding: 10px 10px;
  color: black;
  background-color: darkgray;
  border-radius: 5px;
}

.reply-btn-box {
  height: 25px;
  margin: 10px 0;
}

.reply-btn {
  position: relative;
  float: right;
  margin-right: 15px;
}

.talk-box {
  position: relative;
}

.reply-box {
  margin: 10px 0 0 50px;
  background-color: #efefef;
}

.reply {
  font-size: 16px;
  color: #000;
  margin-top: 45px;
  display: flex;
  justify-content: left;
  align-items: flex-start;

}

.author-name {
  color: black;
  font-size: 18px;
  font-weight: bold;
  margin-left: 2px;
}

.author-time {
  color: #7d7d7d;
  font-size: 5px;
  display: flex;
}

.author-title {
  padding: 10px;
}

.author-info {
  margin-left: 5px;
  line-height: 20px;
  float: left;
  margin-top: 5px;
}
</style>