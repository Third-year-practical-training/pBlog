<template>
    <div>
        <page-header title="用户后台管理" content="设置管理员权限和禁言状态"/>
        <page-main>
            <el-table :data="dataList" border stripe highlight-current-row>
                <el-table-column type="index" width="50"/>
                <el-table-column prop="nickname" label="用户名" width="180"/>
                <el-table-column prop="privilege" laabel="用户权限">
                    <template slot-scope="scope">
                        <div v-if="dataList[scope.$index].privilege == 1" style="color: #00ff80;">管理员</div>
                        <div v-if="dataList[scope.$index].privilege == 0">普通用户</div>
                        <div v-if="dataList[scope.$index].privilege == -1" style="color: red;">禁言</div>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            v-if="dataList[scope.$index].privilege == 0"
                            size="mini"
                            icon="el-icon-circle-plus"
                            @click="handleManager(scope.$index)"
                        >设置管理员权限
                        </el-button>
                        <el-button
                            v-if="dataList[scope.$index].privilege == 1"
                            size="mini"
                            type="danger"
                            icon="el-icon-remove"
                            @click="handleManager(scope.$index)"
                        >取消管理员权限
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            v-if="dataList[scope.$index].privilege == 0"
                            size="mini"
                            icon="el-icon-circle-plus"
                            @click="handleBan(scope.$index)"
                        >禁言
                        </el-button>
                        <el-button
                            v-if="dataList[scope.$index].privilege == -1"
                            size="mini"
                            type="danger"
                            icon="el-icon-remove"
                            @click="handleBan(scope.$index)"
                        >取消禁言
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="block" style="text-align: center;">
                <el-pagination
                    layout="prev, pager, next"
                    :current-page="userPage.pageNum"
                    :page-size="userPage.pageSize"
                    :total="userPage.total"
                    @current-change="getAllUser"
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
    name: 'user',
    components: {PageMain, PageHeader},
    data() {
        return {
            dataList: [],
            userPage: {
                pageNum: 1,
                pageSize: 0,
                total: 0
            }
        }
    },
    created() {
        this.getAllUser(1)
    },
    methods: {
        getAllUser(pageNum) {
            const _this = this
            this.$axios.get('http://localhost:8080/admin/getAllUser', {
                params: {
                    pageNum: pageNum
                }
            }).then(res => {
                if (res.data.code == 100) {
                    _this.dataList = res.data.data.list
                    _this.userPage.pageNum = res.data.data.pageNum
                    _this.userPage.pageSize = res.data.data.pageSize
                    _this.userPage.total = res.data.data.total
                } else {
                    console.log(res.data.msg)
                }
            })
        },
        handleManager(index) {
            const _this = this
            if (this.dataList[index].privilege == 0) {
                this.dataList[index].privilege = 1
                let data = new FormData()
                data.append('id', this.dataList[index].id)
                data.append('privilege', this.dataList[index].privilege)
                this.$axios.put('http://localhost:8080/admin/changeUserPrivilege', data).then(res => {
                    if (res.data.code == 100) {
                        _this.$message('设置管理员权限成功')
                    }
                })
            } else if (this.dataList[index].privilege == 1) {
                this.dataList[index].privilege = 0
                let data = new FormData()
                data.append('id', this.dataList[index].id)
                data.append('privilege', this.dataList[index].privilege)
                this.$axios.put('http://localhost:8080/admin/changeUserPrivilege', data).then(res => {
                    if (res.data.code == 100) {
                        _this.$message('取消管理员权限成功')
                    }
                })
            }
        },
        handleBan(index) {
            const _this = this
            if (this.dataList[index].privilege == 0) {
                this.dataList[index].privilege = -1
                let data = new FormData()
                data.append('id', this.dataList[index].id)
                data.append('privilege', this.dataList[index].privilege)
                this.$axios.put('http://localhost:8080/admin/changeUserPrivilege', data).then(res => {
                    if (res.data.code == 100) {
                        _this.$message('禁言成功')
                    }
                })
            } else if (this.dataList[index].privilege == -1) {
                this.dataList[index].privilege = 0
                let data = new FormData()
                data.append('id', this.dataList[index].id)
                data.append('privilege', this.dataList[index].privilege)
                this.$axios.put('http://localhost:8080/admin/changeUserPrivilege', data).then(res => {
                    if (res.data.code == 100) {
                        _this.$message('取消禁言')
                    }
                })
            }
        }
    }
}
</script>
