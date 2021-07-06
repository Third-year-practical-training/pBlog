import api from '@/api'

const state = {
    userInfo: JSON.parse(sessionStorage.getItem('userInfo')) || '',
    permissions: []
}

const getters = {
    isLogin: state => {
        let retn = false
        if (state.userInfo != '') {
            retn = true
        }
        return retn
    }
}

const actions = {
    login({commit}, data) {
        return new Promise((resolve, reject) => {
            // 通过 mock 进行登录
            api.post('http://localhost:8080/admin/signin', data).then(res => {
                commit('setUserData', res.data.data)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
    logout({commit}) {
        return new Promise((resolve, reject) => {
            // 通过 mock 进行登录
            api.post('http://localhost:8080/admin/signout').then(() => {
                commit('removeUserData')
                commit('menu/invalidRoutes', null, {root: true})
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
    // 获取我的权限
    getPermissions({state, commit}) {
        return new Promise(resolve => {
            api.get('mock/member/permission', {
                params: {
                    account: state.account
                }
            }).then(res => {
                commit('setPermissions', res.data.permissions)
                resolve(res.data.permissions)
            })
        })
    }
}

const mutations = {
    setUserData(state, data) {
        state.userInfo = data
        sessionStorage.setItem('userInfo', JSON.stringify(data))
    },
    removeUserData(state) {
        sessionStorage.setItem('userInfo', JSON.stringify(''))
        state.userInfo = {}
    },
    setPermissions(state, permissions) {
        state.permissions = permissions
    }
}

export default {
    namespaced: true,
    state,
    actions,
    getters,
    mutations
}
