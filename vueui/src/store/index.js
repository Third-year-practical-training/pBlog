import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: JSON.parse(sessionStorage.getItem("userInfo"))
  },
  mutations: {
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
    },
    REMOVE_INFO: (state) => {
      sessionStorage.setItem("userInfo", JSON.stringify(''))
      state.userInfo = {}
    }
  },
  getters:{
    getUser: state => {
      return state.userInfo
    }
  },
  actions: {
  },
  modules: {
  }
})
