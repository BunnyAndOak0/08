import Vue from 'vue'
import VueRouter from 'vue-router'
import NewsRelease from '../views/NewsRelease.vue'
import NewsReleasex from '../views/NewsReleasex.vue'

// 程晟
import Register from '../views/Register.vue';
import Login from '../views/Login.vue';
import Profile from '../views/Profile.vue';
import Forget from '../views/Forget.vue';
import RegisterTop from '../components/comment/RegisterTop.vue';
import RegisterField from '../components/comment/RegisterField.vue';
import LoginTop from '../components/comment/LoginTop.vue';
import LoginField from '../components/comment/LoginField.vue';
import Reset from '../views/Reset.vue';
import Userinfo from '../views/Userinfo.vue';
import MeField from '../components/geren/MeField.vue';
import MeTop from '../components/geren/MeTop.vue';
import ProfileBanner from '../components/geren/ProfileBanner.vue';
import Edit from '../views/Edit.vue';
import Find from '../views/Find.vue';

// 郭不青
import Riji from '../views/Riji.vue';
import MyRiji from '../views/MyRiji.vue';
import Detail from '../views/Detail.vue';
import Update from '../views/Update.vue';

Vue.use(VueRouter)

const routes = [
  {
    path: '/newsRelease',
    name: 'NewsRelease',
    component: NewsRelease
  },
  {
    path: '/newsReleasex',
    name: 'NewsReleasex',
    component: NewsReleasex
  },
  {
    path:'/',
    component:()=> import('../views/Home.vue'),
  },
  {
    path:'/anoDyn',
    component:()=> import('../views/AnonymousDynamic.vue'),
  },
  {
    path:'/person/:id',
    component:()=> import('../views/Person.vue'),
    props:true,
  },
  {
    path:'/article/:aid',
    component:()=> import('../views/Article.vue'),
    props:true,
  },
  {
    path:'/articleAnonymous/:anonyId',
    component:()=> import('../views/ArticleAnonymous.vue'),
    props:true,
  },



  // 程晟
  {
    path:'/Find',
    component:Find
  },

  {
    path:'/Edit',
    component:Edit
  },

  {
    path:'/ProfileBanner',
    component:ProfileBanner
  },

  {
    path:'/MeTop',
    component:MeTop
  },

  {
    path:'/MeField',
    component:MeField
  },

  {
    path:'/Reset',
    component:Reset
  },

  {
    path:'/Forget',
    component:Forget
  },

  {
    path:'/LoginField',
    component:LoginField
  },

  {
    path:'/LoginTop',
    component:LoginTop
  },

  {
    path:'/RegisterField',
    component:RegisterField
  },

  {
    path:'/RegisterTop',
    component:RegisterTop
  },

  {
    path:'/profile',
    component:Profile
  },
  {
    path:'/Userinfo',
    name:'Userinfo',
    component:Userinfo
  },
  {
    path:'/register',
    component:Register
  },
  {
    path:'/login',
    component:Login
  },

  // 郭步青
  {
    path: '/update',
    name: 'Update',
    component: Update
  },
  {
    path: '/detail',
    name: 'Detail',
    component: Detail
  },
  {
    path: '/myriji',
    name: 'MyRiji',
    component: MyRiji
  },
  {
    path: '/riji',
    name: 'Riji',
    component: Riji
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
