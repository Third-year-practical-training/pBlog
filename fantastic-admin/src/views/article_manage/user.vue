<template>
    <div>
        <page-header title="用户后台管理" content="设置管理员权限和禁言状态"/>
        <page-main>
            <el-table :data="dataList" border stripe highlight-current-row>
                <el-table-column type="index" width="50"/>
                <el-table-column prop="name" label="用户名" width="180"/>
                <el-table-column prop="isManager" laabel="管理员权限">
                    <template slot-scope="scope">
                        <div v-if="dataList[scope.$index].isManager == false">否</div>
                        <div v-if="dataList[scope.$index].isManager != false">是</div>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            v-if="dataList[scope.$index].isManager == false"
                            size="mini"
                            icon="el-icon-circle-plus"
                            @click="handleManager(scope.$index)">设置管理员权限
                        </el-button>
                        <el-button
                            v-if="dataList[scope.$index].isManager != false"
                            size="mini"
                            type="danger"
                            icon="el-icon-remove"
                            @click="handleManager(scope.$index)">取消管理员权限
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="isBan" laabel="禁言状态">
                    <template slot-scope="scope">
                        <div v-if="dataList[scope.$index].isBan == false">否</div>
                        <div v-if="dataList[scope.$index].isBan != false">是</div>
                    </template>
                </el-table-column>
                <el-table-column>
                    <template slot-scope="scope">
                        <el-button
                            v-if="dataList[scope.$index].isManager == false"
                            size="mini"
                            icon="el-icon-circle-plus"
                            @click="handleBan(scope.$index)">禁言
                        </el-button>
                        <el-button
                            v-if="dataList[scope.$index].isManager != false"
                            size="mini"
                            type="danger"
                            icon="el-icon-remove"
                            @click="handleBan(scope.$index)">取消禁言
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
    name: 'user',
    components: {PageMain, PageHeader},
    data() {
        return {
            dataList: [
                {
                    name: '王小虎',
                    isManager: false,
                    isBan: false
                },
                {
                    name: '李阳',
                    isManager: false,
                    isBan: false
                }
            ]
        }
    },
    methods: {
        handleManager(index) {
            if (this.dataList[index].isManager == false) {
                this.dataList[index].isManager = true
                this.$message('设置管理员权限成功')
            } else{
                this.dataList[index].isManager = false
                this.$message('取消管理员权限成功')
            }
        },
        handleBan(index) {
            if (this.dataList[index].isBan == false) {
                this.dataList[index].isBan = true
                this.$message('禁言成功')
            } else{
                this.dataList[index].isBan = false
                this.$message('取消禁言')
            }
        },
    }
}
</script>
