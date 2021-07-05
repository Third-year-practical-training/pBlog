<template>
  <el-container>
    <el-form v-loading.fullscreen.lock="loading" ref="editForm" status-icon :model="editForm" :rules="rules" label-width="100px">
      <el-form-item label="标题" prop="title" style="margin-top: 10px">
        <el-input v-model="editForm.title"></el-input>
      </el-form-item>
      <el-form-item label="分类" prop="articleTypeId">
        <el-select v-model="editForm.articleTypeId" placeholder="请选择">
          <el-option
              v-for="item in select"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标签" prop="tag">
        <el-tag
            :key="tag"
            v-for="tag in editForm.tag"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
          {{ tag.name }}
        </el-tag>
        <el-input
            class="input-new-tag"
            v-if="inputVisible"
            v-model="inputValue"
            ref="saveTagInput"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <mavon-editor style="height:500px;width: 1200px" v-model="editForm.content"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-loading.fullscreen.lock="loading" @click="submitForm('editForm')"
                   style="margin-left: 550px">立即创建
        </el-button>

      </el-form-item>
    </el-form>
  </el-container>
</template>

<script>
export default {
  name: "NewBlog",
  data() {
    return {
      inputVisible: true,
      inputValue: '',
      loading: false,
      editForm: {
        id: null,
        userId: '',
        articleTypeId: '',
        title: '',
        date: '',
        content: '',
        tag: [],

      },
      select: [],
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
          {min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  created() {
    this.loading = true;
    if (this.$store.getters.getUser.id) {
      this.editForm.userId = this.$store.getters.getUser.id;
    }
    if (this.$route.params.blogId) {
      this.editForm.id = this.$route.params.blogId;
    }
    const _this = this;
    this.$axios.get('http://localhost:8080/type/findall').then(res => {
      if (res.data.code == 100) {
        _this.select = JSON.parse(JSON.stringify(res.data.data));
      }
    });
    if (this.editForm.id != null) {
      this.$axios.get('http://localhost:8080/article/findById', {
        params: {
          id: this.editForm.id,
        }
      }).then(res => {
        _this.editForm.userId = res.data.data.userId;
        _this.editForm.articleTypeId = res.data.data.articleType.id;
        _this.editForm.title = res.data.data.title;
        _this.editForm.date = _this.formatDate(res.data.data.date);
        _this.editForm.content = res.data.data.content;
        _this.editForm.tag = res.data.data.tagList;
      });
    }
    this.loading = false;
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.loading = true;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.editForm.id == null || this.editForm.id == '') {
            this.editForm.date = this.formatDate(new Date().getTime());
            this.$axios({
                  url: 'http://localhost:8080/article/new',
                  method: 'post',
                  data: JSON.stringify(this.editForm),
                  headers:
                      {
                        'Content-Type': 'application/json'
                      }
                }
            ).then(res => {
              _this.loading = false;
              _this.$message("创建成功");
              _this.$router.push('/user-center/contentmanage');
            });
          } else {
            this.$axios({
                  url: 'http://localhost:8080/article/update',
                  method: 'put',
                  data: JSON.stringify(this.editForm),
                  headers:
                      {
                        'Content-Type': 'application/json'
                      }
                }
            ).then(res => {
              _this.loading = false;
              _this.$message("修改成功");
              _this.$router.push('/user-center/contentmanage');
            });
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    formatDate(date) {
      let time = new Date(date);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      return `${y}-${m}-${d}`;
    }
    ,

    handleClose(tag) {
      this.editForm.tag.splice(this.editForm.tag.indexOf(tag), 1);
    }
    ,

    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    }
    ,

    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue) {
        this.editForm.tag.push({id: null, name: inputValue});
      }
      this.inputVisible = false;
      this.inputValue = '';
    }
  },

}
</script>

<style>

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

</style>