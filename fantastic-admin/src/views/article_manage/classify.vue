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
                            @click="handleDelete(scope.$index)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button type="primary" style="margin-top: 10px;" @click="handleAdd" >新增</el-button>
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
            dataList: [
                {
                    name: '大数据'
                },
                {
                    name: 'python'
                }
            ],
            showAddInput: false,
            newClassify: ''
        }
    },
    methods: {
        handleDelete(index) {
            this.dataList.splice(index, 1)
            this.$message('删除成功')
        },
        handleAdd() {
            this.showAddInput = true
        },
        addNew() {
            let a = {}
            a.name = this.newClassify
            this.newClassify = ''
            this.dataList.push(a)
            this.showAddInput = false
        }
    }
}
</script>
