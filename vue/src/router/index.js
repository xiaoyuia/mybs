import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  //   //主页面
  // {
  //   path: '/',
  //   name: 'Manage',
  //   component: () => import ('../views/Manage.vue'),
  //   redirect: "/home",
  //   children: [
  //     {
  //       path: 'home', name:'Home', component: () =>import('../views/Home.vue')
  //     },
  //     {
  //       path: 'student', name:'Student', component: () =>import('../views/Student.vue')
  //     },
  //     {
  //       path: 'teacher', name: 'Teacher', component: () =>import('../views/Teacher.vue')
  //     },
  //     {
  //       path: 'clazz', name: 'Clazz', component: () =>import('../views/Clazz.vue')
  //     },
  //     {
  //       path: 'grade', name: 'Grade', component: () =>import('../views/Grade.vue')
  //     },
  //     {
  //       path: 'score', name: 'Score', component: () =>import('../views/Score.vue')
  //     },
  //     {
  //       path: 'admin', name: 'Admin', component: () =>import('../views/Admin.vue')
  //     },
  //     {
  //       path: 'info', name: 'Info', component: () =>import('../views/Info.vue')
  //     }
  //
  //   ]
  // },

    //左侧菜单栏页面
  {
    path: '/',
    name: 'Manage',
    meta: {requireAuth: true},
    component: () => import('../views/Manage'),
    redirect: "home",
    children:[
      // {path: '/home', name: 'Home', component: () => import('../views/Home.vue'), meta: {name:"首页", requireAuth: false, visible: true, userType: ["管理员：",'教师：','学生：']}},
      // {path: '/student', name: 'Student', component: () => import('../views/Student.vue'), meta: {name:"学生管理", requireAuth: false, visible: true, userType: ['管理员：','教师：','学生：']}},
      // {path: '/teacher', name: 'Teacher', component: () => import('../views/Teacher.vue'), meta: {name:"教师管理", requireAuth: false, visible: true, userType: ['管理员：','教师：']}},
      // {path: '/clazz', name: 'Clazz', component: () => import('../views/Clazz.vue'), meta: {name:"班级管理", requireAuth: false, visible: true, userType: ['管理员：']}},
      // {path: '/grade', name: 'Grade', component: () => import('../views/Grade.vue'), meta: {name:"年级管理", requireAuth: false, visible: true, userType: ['管理员：']}},
      // {path: '/score', name: 'Score', component: () => import('../views/Score.vue'), meta: {name:"成绩管理", requireAuth: false, visible: true, userType: ['管理员：','教师：','学生：']}},
      // {path: '/admin', name: 'Admin', component: () => import('../views/Admin.vue'), meta: {name:"管理员管理", requireAuth: false, visible: true, userType: ['管理员：']}},
      // {path: '/info', name: 'Info', component: () => import('../views/Info.vue'), meta: {name:"个人信息管理", requireAuth: false, visible: true, userType: ['管理员：','教师：','学生：']}},
      //
      {path: '/home', name: 'Home', component: () => import('../views/Home.vue'), meta: {name:"首页", requireAuth: false, visible: true, userType: [1,2,3]}, icon: "el-icon-s-home"},
      {path: '/student', name: 'Student', component: () => import('../views/Student.vue'), meta: {name:"学生管理", requireAuth: false, visible: true, userType: [1,2,3]}, icon: "el-icon-user"},
      {path: '/teacher', name: 'Teacher', component: () => import('../views/Teacher.vue'), meta: {name:"教师管理", requireAuth: false, visible: true, userType: [1,2]}, icon: "el-icon-s-custom"},
      {path: '/clazz', name: 'Clazz', component: () => import('../views/Clazz.vue'), meta: {name:"班级管理", requireAuth: false, visible: true, userType: [1]}, icon: "el-icon-s-finance"},
      {path: '/grade', name: 'Grade', component: () => import('../views/Grade.vue'), meta: {name:"年级管理", requireAuth: false, visible: true, userType: [1]}, icon: "el-icon-s-flag"},
      {path: '/score', name: 'Score', component: () => import('../views/Score.vue'), meta: {name:"成绩管理", requireAuth: false, visible: true, userType: [1,2,3]}, icon: "el-icon-s-grid"},
      {path: '/admin', name: 'Admin', component: () => import('../views/Admin.vue'), meta: {name:"管理员管理", requireAuth: false, visible: true, userType: [1]}, icon: "el-icon-s-check"},
      {path: '/info', name: 'Info', component: () => import('../views/Info.vue'), meta: {name:"个人信息管理", requireAuth: false, visible: true, userType: [1,2,3]}, icon: "el-icon-c-scale-to-original"},

    ],
  },









    //登录页面
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
