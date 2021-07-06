<template>
    <div>
        <page-header title="分类后台管理" content="删除文章分类或新增文章分类"/>
        <page-main>
            <el-table :data="dataList" border stripe highlight-current-row>
                <el-table-column type="index" width="50"/>
                <el-table-column prop="name" label="分类名" width="180"/>
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
            <el-button type="primary" style="margin-top: 10px;" @click="handleAdd">新增</el-button>
            <page-main v-if="showAddInput != false">
                <el-input v-model="newClassify"></el-input>
                <el-button type="primary" style="margin-top: 10px;" @click="addNew">确定</el-button>
            </page-main>
        </page-main>
    </div>
</template>

<script>
import PageHeader from '@/components/PageHeader'
import PageMain from '@/components/PageMain'

export default {
    name: 'classify',
    components: {PageMain, PageHeader},
    data() {
        return {
            dataList: [],
            showAddInput: false,
            newClassify: ''
        }
    },
    created() {
        this.getAllClassify()
    },
    methods: {
        getAllClassify() {
            const _this = this
            this.$axios.get('http://localhost:8080/type/findall').then(res => {
                if (res.data.code == 100) {
                    _this.dataList = res.data.data
                }
            })
        },
        handleDelete(index) {
            const _this = this
            this.$axios.delete('http://localhost:8080/admin/deleteTypeById', {
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
            a.name = this.newClassify
            let name = this.newClassify
            this.newClassify = ''
            this.dataList.push(a)
            this.showAddInput = false
            let data = new FormData()
            data.append('type', name)
            this.$axios.put('http://localhost:8080/admin/saveType', data).then(res => {
                if (res.data.code == 100) {
                    _this.$message('添加成功')
                }
            })
        }
    }
}
</script>
