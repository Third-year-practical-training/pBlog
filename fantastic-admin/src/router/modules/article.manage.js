import Layout from '@/layout'

export default {
    path: '/article_manage',
    component: Layout,
    redirect: '/articlemanage/article_show',
    name: 'articleManage',
    meta: {
        title: '后台管理',
        icon: 'sidebar-component'
    },
    children: [
        {
            path: 'article_show',
            name: 'articleShow',
            component: () => import('@/views/article_manage/article.show'),
            meta: {
                title: '文章管理'
            }
        },
        {
            path: 'classify',
            name: 'classify',
            component: () => import('@/views/article_manage/classify'),
            meta: {
                title: '分类管理'
            }
        },
        {
            path: 'user',
            name: 'user',
            component: () => import('@/views/article_manage/user'),
            meta: {
                title: '用户管理'
            }
        },
        {
            path: 'notice',
            name: 'notice',
            component: () => import('@/views/article_manage/notice'),
            meta: {
                title: '系统通知'
            }
        }
    ]
}
