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
        <el-select v-model="select.name" placeholder="请选择">
          <el-option
              v-for="item in select"
              :key="item.id"
              :label="item.name"
              :value="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标签" prop="tags">
        <el-tag
            :key="tag"
            v-for="tag in editForm.tags"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)">
          {{ tag }}
        </el-tag>
        <el-input
            class="input-new-tag"
            v-if="editForm.inputVisible"
            v-model="editForm.inputValue"
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
        <el-button type="primary" @click="submitForm('editForm')" style="margin-left: 550px">立即创建</el-button>

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
        blogid: null,
        title: '',
        description: '',
        content: '',
        tags: [],
        classify: '',
        inputVisible: true,
        inputValue: ''
      },
      select: [],
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
    const _this = this;
    this.$axios.get('http://localhost:8080/type/findall').then(res => {
      if (res.data.code == 100) {
          _this.select = JSON.parse(JSON.stringify(res.data.data));
      }
    })
  },
  methods: {
    submitForm(formName) {
      const _this = this
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let data = new FormData();

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    handleClose(tag) {
      this.editForm.tags.splice(this.editForm.tags.indexOf(tag), 1);
    },

    showInput() {
      this.editForm.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      let inputValue = this.editForm.inputValue;
      if (inputValue) {
        this.editForm.tags.push(inputValue);
      }
      this.editForm.inputVisible = false;
      this.editForm.inputValue = '';
    }
  }
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