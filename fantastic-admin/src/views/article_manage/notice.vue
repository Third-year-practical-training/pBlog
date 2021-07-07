<template>
    <div>
        <page-header title="系统通知后台管理" content="删除系统通知或者新增系统通知"/>
        <page-main>
            <el-table :data="dataList" border stripe highlight-current-row>
                <el-table-column type="index" width="50"/>
                <el-table-column prop="date" label="日期" width="180" :formatter="formatDate"/>
                <el-table-column prop="content" label="内容" width="180"/>
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
                    :current-page="pageNum"
                    :page-size="pageSize"
                    :total="total"
                    @current-change="getNotices"
                >
                </el-pagination>
            </div>
            <el-button type="primary" style="margin-top: 10px;" @click="handleAdd">新增</el-button>
            <page-main v-if="showAddInput != false">
                <el-input v-model="newNotice"></el-input>
                <el-button type="primary" style="margin-top: 10px;" @click="addNew">确定</el-button>
            </page-main>
        </page-main>
    </div>
</template>

<script>
import PageHeader from '@/components/PageHeader'
import PageMain from '@/components/PageMain'

export default {
    name: 'notice',
    components: {PageMain, PageHeader},
    data() {
        return {
            dataList: [],
            showAddInput: false,
            newNotice: '',
            pageNum: 1,
            pageSize: 0,
            total: 0
        }
    },
    created() {
        this.getNotices(1)
    },
    methods: {
        getNotices(pageNum) {
            const _this = this
            this.$axios.get('http://localhost:8080/admin/notice/getAll', {
                params: {
                    pageNum: pageNum
                }
            }).then(res => {
                if (res.data.code == 100) {
                    _this.dataList = res.data.data.list
                    _this.pageNum = res.data.data.pageNum
                    _this.pageSize = res.data.data.pageSize
                    _this.total = res.data.data.total
                }
            })
        },
        handleDelete(index) {
            const _this = this
            this.$axios.delete('http://localhost:8080/admin/notice/delete', {
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
        handleAdd() {
            this.showAddInput = true
        },
        addNew() {
            const _this = this
            let a = {}
            a.date = new Date()
            a.isRead = 0
            a.content = this.newNotice
            this.showAddInput = false
            let notice = new FormData()
            notice.append('date', new Date())
            notice.append('isRead', 0)
            notice.append('content', this.newNotice)
            this.newNotice = ''
            this.$axios.post('http://localhost:8080/admin/notice/new', notice).then(res => {
                if (res.data.code == 100) {
                    _this.$message('添加成功')
                    _this.dataList.push(a)
                }
            })
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
