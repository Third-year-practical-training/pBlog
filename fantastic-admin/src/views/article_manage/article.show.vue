<template>
    <div>
        <page-header title="文章后台管理" content="删除文章或加精文章"/>
        <page-main>
            <el-table :data="dataList" border stripe highlight-current-row>
                <el-table-column type="index" width="50"/>
                <el-table-column prop="date" label="日期" width="180"/>
                <el-table-column prop="userNickname" label="昵称" width="180"/>
                <el-table-column prop="collectCount" label="收藏数"/>
                <el-table-column prop="commentCount" label="评论数"/>
                <el-table-column prop="isSpecial" label="是否加精">
                    <template slot-scope="scope">
                        <div v-if="dataList[scope.$index].isSpecial == false">否</div>
                        <div v-if="dataList[scope.$index].isSpecial != false">是</div>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            v-if="dataList[scope.$index].isSpecial == false"
                            size="mini"
                            icon="el-icon-star-off"
                            @click="handleStar(scope.$index)">加精
                        </el-button>
                        <el-button
                            v-if="dataList[scope.$index].isSpecial != false"
                            size="mini"
                            type="success"
                            icon="el-icon-star-on"
                            @click="handleStar(scope.$index)">取消加精
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            size="mini"
                            type="danger"
                            icon="el-icon-delete"
                            @click="handleDelete(scope.$index)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
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
            dataList: [
                {
                    date: '2016-05-02',
                    userNickname: '王小虎',
                    collectCount: 45,
                    commentCount: 10,
                    isSpecial: false
                },
                {
                    date: '2016-05-04',
                    userNickname: '王小虎',
                    collectCount: 45,
                    commentCount: 10,
                    isSpecial: true
                }
            ]
        }
    },
    methods: {
        handleDelete(index) {
            this.dataList.splice(index, 1)
            this.$message('删除成功')
        },
        handleStar(index) {
            if (this.dataList[index].isSpecial == false) {
                this.dataList[index].isSpecial = true
                this.$message('加精成功')
            } else {
                this.dataList[index].isSpecial = false
                this.$message('取消加精')
            }
        }
    }
}
</script>
