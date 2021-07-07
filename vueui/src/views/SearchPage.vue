<template>
  <div>
    <div>
      <el-main>
        <div>
          <el-input v-model="searchInfo.keyWord" placeholder="请输入搜索内容" style="max-width: 500px"></el-input>
          <el-button type="primary" @click="Search(1)" icon="el-icon-search" style="margin-left: 10px">搜索</el-button>
        </div>

        <div style="text-align: center">
          <el-select v-model="searchInfo.type" placeholder="请选择搜索范围" @change="changeShow">
            <el-option label="全站" value="0"></el-option>
            <el-option label="分类" value="1"></el-option>
          </el-select>
          <el-select v-model="searchInfo.typeId" placeholder="请选择分类" v-show="show">
            <el-option
                v-for="item in articleTypes"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </div>
      </el-main>
    </div>
    <el-card>
      <div v-for="item in result" :key="item" class="el-card" style="text-align: left">
        <h4>
          <router-link :to="{name: 'BlogShow', params: {blogId: item.id}}"
                       style="font-size: large;font-family: 'Arial Black';color: #333333;text-align: center;margin-left: 30px"
                       v-html="item.title">
          </router-link>
        </h4>
        <span style="margin-left: 30px">{{ formatDate(item.date) }}</span>
        <span style="font-size: small;color: gray;margin-left: 10px">标签: </span>
        <div style="display: inline" v-for="tag in item.articleTagList" :key="tag" class="el-tag">
          {{ tag.name }}
        </div>
      </div>
      <div class="block" style="text-align: center;">
        <el-pagination
            layout="prev, pager, next"
            :current-page="resultPage.pageNum"
            :page-size="resultPage.pageSize"
            :total="resultPage.total"
            @current-change="Search">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "SearchPage",
  data() {
    return {
      searchInfo: {
        keyWord: '',
        type: '',
        typeId: 0,
      },
      articleTypes: [],
      result: [],
      resultPage: {
        pageNum: 1,
        pageSize: 0,
        total: 0,
      },
      show: false,
    }
  },
  created() {
    const _this = this;
    this.$axios.get('http://localhost:8080/type/findall').then(res => {
      if (res.data.code == 100) {
        _this.articleTypes = JSON.parse(JSON.stringify(res.data.data));
      }
    });
  },
  methods: {
    Search(current) {
      const _this = this;
      this.$axios.get('http://localhost:8080/article/searchByKeyWord', {
        params: {
          keyWord: this.searchInfo.keyWord,
          pageNum: current,
          type: this.searchInfo.type,
          id: this.searchInfo.typeId,
        }
      }).then(res => {
        if (res.data.code == 100) {
          _this.result = res.data.data.list;
          _this.resultPage.pageNum = res.data.data.pageNum;
          _this.resultPage.total = res.data.data.total;
          _this.resultPage.pageSize = res.data.data.pageSize;
          _this.$message('搜索成功');
        }
      })
    },
    formatDate(date) {
      let time = new Date(date);
      let y = time.getFullYear();
      let m = time.getMonth() + 1;
      let d = time.getDate();
      return `${y}-${m}-${d}`;
    },
    changeShow() {
      if (this.searchInfo.type == 1) {
        this.show = true;
      } else {
        this.show = false;
      }
      console.log(this.show);
    }
  }
}
</script>

<style scoped>

</style>