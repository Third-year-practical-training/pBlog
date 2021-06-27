import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import MainPage from "../views/MainPage";
import SelfInformation from "../views/UserCenter/SelfInformation";
import Account from "../views/UserCenter/Account";
import Attention from "../views/UserCenter/Attention";
import ContentManage from "../views/UserCenter/ContentManage";
import NewBlog from "../views/NewBlog";
import BlogShow from "../views/BlogShow";
import Comments from "../views/Notice/Comments";
import PersonalMessage from "../views/Notice/PersonalMessage";
import SystemNotice from "../views/Notice/SystemNotice";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Login
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/mainpage',
    name: 'MainPage',
    component: MainPage
  },
  {
    path: '/comments',
    name: 'Comments',
    component: Comments
  },
  {
    path: '/personalmessage',
    name: 'PersonalMessage',
    component: PersonalMessage
  },
  {
    path: '/systemnotice',
    name: 'SystemNotice',
    component: SystemNotice
  },
  {
    path: '/user-center/selfinformation',
    name: 'selfinformation',
    component: SelfInformation
  },
  {
    path: '/user-center/account',
    name: 'Account',
    component: Account
  },
  {
    path: '/user-center/attention',
    name: 'Attention',
    component: Attention
  },
  {
    path: '/user-center/contentmanage',
    name: 'ContentManage',
    component: ContentManage
  },
  {
    path: '/newblog',
    name: 'NewBlog',
    component: NewBlog
  },
  {
    path: '/blogshow',
    name: 'BlogShow',
    component: BlogShow
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
