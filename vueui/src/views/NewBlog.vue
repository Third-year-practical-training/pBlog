<template>
  <el-container>
    <el-form ref="editForm" status-icon :model="editForm" :rules="rules" label-width="100px">
      <el-form-item label="标题" prop="title" style="margin-top: 10px">
        <el-input v-model="editForm.title"></el-input>
      </el-form-item>
      <el-form-item label="摘要" prop="description">
        <el-input type="textarea" v-model="editForm.description"></el-input>
      </el-form-item>
      <el-form-item label="分类" prop="classify">
        <el-select v-model="select.values.value" placeholder="请选择">
          <el-option
              v-for="item in select.values"
              :key="item.value"
              :label="item.value"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标签" prop="tags">
        <el-tag
            :key="tag"
            v-for="tag in tags.dynamicTags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
          {{ tag }}
        </el-tag>
        <el-input
            class="input-new-tag"
            v-if="tags.inputVisible"
            v-model="tags.inputValue"
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
        <el-button type="primary" @click="submitForm()" style="margin-left: 550px">立即创建</el-button>

      </el-form-item>
    </el-form>
  </el-container>
</template>

<script>
export default {
  name: "NewBlog",
  data() {
    return {
      editForm: {
        id: null,
        title: '',
        description: '',
        content: '',
        tags:[],
        classify:'',
      },
      select: {
        values: [{
          value: '学习'
        }, {
          value: '笔记'
        }, {
          value: '感悟'
        },],
      },
      tags: {
        dynamicTags: [],
        inputVisible: true,
        inputValue: ''
      },
      rules: {
        title: [
          {required: true, message: '请输入标题', trigger: 'blur'},
          {min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '请输入摘要', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    const blogId = this.$route.params.blogId
    const _this = this
    if (blogId) {

    }
  },
  methods: {
    submitForm() {
      const _this = this
      this.$refs.editForm.validate((valid) => {
        if (valid) {

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    handleClose(tag) {
      this.tags.dynamicTags.splice(this.tags.dynamicTags.indexOf(tag), 1);
    },

    showInput() {
      this.tags.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.tags.inputValue;
      if (inputValue) {
        this.tags.dynamicTags.push(inputValue);
      }
      this.tags.inputVisible = false;
      this.tags.inputValue = '';
    }
  }
}
</script>

<style>

.el-tag {
  margin-left: 10px;
}

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