import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const modules = {}
const require_module = require.context('./modules', false, /.js$/)
require_module.keys().forEach(file_name => {
    modules[file_name.slice(2, -3)] = require_module(file_name).default
})

export default new Vuex.Store({
    modules: modules,
    strict: process.env.NODE_ENV !== 'production',
    state: {
        userInfo: JSON.parse(sessionStorage.getItem('userInfo'))
    },
    mutations: {
        SET_USERINFO: (state, userInfo) => {
            state.userInfo = userInfo
            sessionStorage.setItem('userInfo', JSON.stringify(userInfo))
        },
        REMOVE_INFO: state => {
            sessionStorage.setItem('userInfo', JSON.stringify(''))
            state.userInfo = {}
        }
    },
    getters: {
        getUser: state => {
            return state.userInfo
        }
    }
})
