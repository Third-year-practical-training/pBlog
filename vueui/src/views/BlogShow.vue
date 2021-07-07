<template>
  <el-container>
    <el-header style="background-color: white">
      <el-page-header  style="background-color: white" @back="goBack" content="文章展示页面" >
      </el-page-header>
    </el-header>
    <el-main v-loading="loading">
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
              <el-divider direction="vertical"></el-divider>
              <el-button v-if="blog.isCollection == false" type="text" style="color: #7d7d7d;font-size: small"
                         @click="collectArticle"><i
                  class="el-icon-collection"></i> 收藏
              </el-button>
              <el-button v-if="blog.isCollection != false" type="text" style="color: #7d7d7d;font-size: small"
                         @click="collectArticle"><i
                  class="el-icon-collection"></i> 取消收藏
              </el-button>
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
        <el-avatar :size="40" :src="user.photoUrl" style="margin-left: 22px;float: left;margin-left: 50px"></el-avatar>
        <div>
          <el-input type="textarea" autosize contenteditable="true" placeholder="请输入内容"
                    style="max-width: 1000px;margin-left: 10px" v-model="mycomment"></el-input>
        </div>
        <div style="float: right;margin-right: 380px;margin-top: 5px">
          <el-button size="medium" type="primary" @click="addComment">发表评论</el-button>
        </div>
      </div>
      <div v-for="(item,i) in comments" :key="i"
           style="text-align: left;margin-top: 0px;margin-left: 50px;width: 1060px"
           class="el-card">
        <div>
          <el-avatar size="large" :src="item.photoUrl"
                     style="margin-top: 10px;margin-left: 10px"></el-avatar>
          <span style=" color: black;font-size: 18px;font-weight: bold;margin-left: 2px;">{{
              item.fromUserNickName
            }}</span>
          <span style="color: #7d7d7d;font-size: 5px;margin-left: 5px">{{ formatDate(item.date) }}</span>
          <el-button type="text" style="color: #409EFF;float: right;margin-right: 10px"
                     @click="showCommentInput(i,item.fromUserId,item.fromUserNickName)">
            回复
          </el-button>
        </div>
        <div style="margin-top: 10px;margin-left: 10px">
          <span>{{ item.content }}</span>
        </div>
        <div>
          <div v-for="(reply,j) in item.childList" :key="j" class="el-card" style="margin-left: 20px">
            <div>
              <el-avatar size="large" :src="reply.photoUrl"
                         style="margin-top: 10px;margin-left: 10px"></el-avatar>
              <span
                  style=" color: black;font-size: 18px;font-weight: bold;margin-left: 2px;">{{
                  reply.fromUserNickName
                }}</span>
              <span style="color: #7d7d7d;font-size: 5px;margin-left: 5px">{{ formatDate(reply.date) }}</span>
              <el-button type="text" style="color: #409EFF;float: right;margin-right: 10px"
                         @click="showReplyCommentInput(i,reply.fromUserId,reply.fromUserNickName)">回复
              </el-button>
            </div>
            <div style="margin-top: 10px;margin-left: 10px">
              <span>回复@{{ reply.toUserNickName }}: {{ reply.content }}</span>
            </div>
          </div>
          <div
              v-if="commentInputs[i].inputValue != undefined && commentInputs[i].inputValue != null && commentInputs[i].inputValue == true">
            <el-input type="textarea" autosize contenteditable="true" placeholder="请输入内容"
                      style="max-width: 500px;margin-left: 30px;margin-top: 5px" v-model="comment"></el-input>
            <el-button size="small" type="primary" @click="pushCommentReply(i)" style="margin-left: 10px">发表评论
            </el-button>
          </div>
        </div>
      </div>
    </el-main>
  </el-container>

</template>

<script>
import marked from "marked";
import axios from "axios";


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
      loading: false,
      mycomment: '',
      comment: '',
      replyInputShow: false,
      from: '',
      fromId: '',
      to: '',
      toId: '',
      blog: {
        id: null,
        userId: '',
        nickname: '',
        title: '',
        classify: {},
        tags: [],
        timestamp: '',
        content: '',
        isCollection: false
      },
      user: {},
      comments: [],
      commentInputs: [],
    }
  },
  created() {
    this.getBlog();
  },
  methods: {
    getBlog() {
      this.loading = true;
      this.blog.id = this.$route.query.blogId;
      this.user = this.$store.getters.getUser;
      const _this = this
      this.$axios.get('http://localhost:8080/article/findById', {
        params: {
          id: this.blog.id,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.blog.userId = res.data.data.userId;
          _this.blog.nickname = res.data.data.userNickname;
          _this.blog.title = res.data.data.title;
          _this.blog.classify = res.data.data.articleType;
          _this.blog.tags = res.data.data.tagList;
          _this.blog.timestamp = _this.formatDate(res.data.data.date);
          _this.blog.content = marked(res.data.data.content);
          _this.blog.isCollection = res.data.data.myCollection;
          _this.comments = res.data.data.commentList;
          for (let i = 0; i < _this.comments.length; i++) {
            let a = {};
            a.inputValue = false;
            _this.commentInputs.push(a);
          }
          _this.loading = false;
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
    getAvatar(id) {
      return 'http://localhost:8080/user/showPhotoById?userId=' + id;
    },
    collectArticle() {
      const _this = this;
      let data = new FormData();
      data.append('userId', this.user.id);
      data.append(' articleId', this.blog.id);
      this.$axios.put('http://localhost:8080/article/changeCollection', data).then(res => {
        if (res.data.code == 100) {
          _this.blog.isCollection = !(_this.blog.isCollection);
          _this.$message('修改收藏成功');
        }
      })
    },
    showCommentInput(i, toId, to) {
      this.commentInputs[i].inputValue = true;
      this.from = this.user.nickName;
      this.fromId = this.user.id;
      this.to = to;
      this.toId = toId;
    },
    showReplyCommentInput(i, toId, to) {
      this.commentInputs[i].inputValue = true;
      this.from = this.user.nickName;
      this.fromId = this.user.id;
      this.to = to;
      this.toId = toId;
    },
    closeReplyCommentInput(i) {
      this.commentInputs[i].inputValue = false;
    },
    addComment() {
      if (this.user.privilege == -1) {
        this.$message('处于禁言状态无法评论，请联系管理员解除禁言');
      } else {
        const _this = this;
        let comment = new FormData();
        const date = this.formatDate(new Date().getTime());
        comment.append('articleId', this.blog.id);
        comment.append('userId', this.user.id);
        comment.append('date', new Date());
        comment.append('fromName', this.user.nickName);
        comment.append('toName', '');
        comment.append('toId', '');
        comment.append('fatherId', '');
        comment.append('content', this.mycomment);
        this.$axios.post('http://localhost:8080/comment/new', comment).then(res => {
          if (res.data.code == 100) {
            _this.$message('评论成功');
            let a = {};
            a.commentId = '';
            a.fromUserId = _this.user.id;
            a.fromUserNickName = _this.user.nickName;
            a.toUserId = '';
            a.toUserNickName = '';
            a.photo = _this.user.avatar;
            a.date = date;
            a.content = _this.mycomment;
            a.fatherId = '';
            a.childList = [];
            a.photoUrl = _this.user.photoUrl
            _this.comments.push(a);
            let b = {};
            b.inputValue = false;
            _this.commentInputs.push(b);
          }
        });
      }
    },
    pushCommentReply(i) {
      this.closeReplyCommentInput(i);
      if (this.user.privilege == -1) {
        this.$message('处于禁言状态无法评论，请联系管理员解除禁言');
      } else {
        const _this = this;
        let comment = new FormData();
        const date = this.formatDate(new Date().getTime());
        comment.append('articleId', this.blog.id);
        comment.append('userId', this.fromId);
        comment.append('date', new Date());
        comment.append('fromName', this.from);
        comment.append('toName', this.to);
        comment.append('toId', this.toId);
        comment.append('fatherId', this.comments[i].commentId);
        comment.append('content', this.comment);
        this.$axios.post('http://localhost:8080/comment/new', comment).then(res => {
          if (res.data.code == 100) {
            _this.$message('评论成功');
            let a = {};
            a.commentId = '';
            a.fromUserId = _this.fromId;
            a.fromUserNickName = _this.from;
            a.toUserId = _this.toId;
            a.toUserNickName = _this.to;
            a.photo = _this.user.avatar;
            a.date = date;
            a.content = _this.comment2;
            a.fatherId = _this.comments[i].commentId;
            a.childList = '';
            _this.comments[i].childList.push(a);
          }
        });
      }
    },
    goBack() {
        this.$router.back()
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