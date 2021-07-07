<template>
    <div>
        <page-header title="文章后台管理" content="删除文章或加精文章"/>
        <page-main>
            <el-table :data="dataList" border stripe highlight-current-row>
                <el-table-column type="index" width="50"/>
                <el-table-column prop="date" label="日期" width="180" :formatter="formatDate"/>
                <el-table-column prop="title" label="标题" width="180"/>
                <el-table-column prop="userNickname" label="作者" width="180"/>
                <el-table-column prop="collectCount" label="收藏数"/>
                <el-table-column prop="commentCount" label="评论数"/>
                <el-table-column prop="isFeature" label="是否加精">
                    <template slot-scope="scope">
                        <div v-if="dataList[scope.$index].isFeature == false">否</div>
                        <div v-if="dataList[scope.$index].isFeature != false">是</div>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            v-if="dataList[scope.$index].isFeature == false"
                            size="mini"
                            icon="el-icon-star-off"
                            @click="handleStar(scope.$index)"
                        >加精
                        </el-button>
                        <el-button
                            v-if="dataList[scope.$index].isFeature != false"
                            size="mini"
                            type="success"
                            icon="el-icon-star-on"
                            @click="handleStar(scope.$index)"
                        >取消加精
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.$index)"
                        >删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="block" style="text-align: center;">
                <el-pagination
                    layout="prev, pager, next"
                    :current-page="articlePage.pageNum"
                    :page-size="articlePage.pageSize"
                    :total="articlePage.total"
                    @current-change="getArticles"
                >
                </el-pagination>
            </div>
        </page-main>
    </div>
</template>

<script>
import PageHeader from '@/components/PageHeader'
import PageMain from '@/components/PageMain'

export default {
    name: 'ArticleShow',
    components: {PageMain, PageHeader},
    data() {
        return {
            dataList: [],
            articlePage: {
                pageNum: 1,
                pageSize: 0,
                total: 0
            }
        }
    },
    created() {
        this.getArticles(1)
    },
    methods: {
        getArticles(pageNum) {
            const _this = this
            this.$axios.get('http://localhost:8080/article/findAllArticle', {
                params: {
                    pageNum: pageNum
                }
            }).then(res => {
                if (res.data.code == 100) {
                    _this.dataList = res.data.data.list
                    _this.articlePage.pageNum = res.data.data.pageNum
                    _this.articlePage.pageSize = res.data.data.pageSize
                    _this.articlePage.total = res.data.data.total
                } else {
                    console.log(res.data.msg)
                }
            })
        },
        handleDelete(index) {
            const _this = this
            this.$axios.delete('http://localhost:8080/admin/deleteArticle', {
                params: {
                    id: this.dataList[index].id
                }
            }).then(res => {
                if (res.data.code == 100) {
                    _this.dataList.splice(index, 1)
                    _this.$message('删除成功')
                }
            })
        },
        handleStar(index) {
            const _this = this
            if (this.dataList[index].isFeature == false) {
                this.dataList[index].isFeature = true
                let data = new FormData()
                data.append('id', this.dataList[index].id)
                this.$axios.put('http://localhost:8080/admin/changeFeature', data).then(res => {
                    if (res.data.code == 100) {
                        _this.$message('加精成功')
                    }
                })
            } else {
                this.dataList[index].isFeature = false
                this.$axios.put('http://localhost:8080/admin/changeFeature', {
                    params: {
                        id: this.dataList[index].id
                    }
                }).then(res => {
                    if (res.data.code == 100) {
                        _this.$message('取消加精')
                    }
                })
            }
        },
        formatDate(row) {
            if (row.date) {
                let time = new Date(row.date.toString())
                let y = time.getFullYear()
                let m = time.getMonth() + 1
                let d = time.getDate()
                return `${y}-${m}-${d}`
            }
        }
    }
}
</script>
