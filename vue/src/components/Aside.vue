<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x: hidden;margin"
           background-color="rgb(48, 65, 86)" text-color="#fff"
           :collapse-transition="false"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center;">
      <img src="../assets/logo.png" alt="" style="width: 20px;position: relative; top:3px;margin-right: 5px;">
      <b style="color: white;font-size: 20px;font-family: 华文宋体">校园信息管理系统</b>
    </div>
    <br/>

<!--    <el-menu-item index="/home" style="font-size: 18px;font-family: 新宋体" ><i class="el-icon-s-home" style="margin-right: 10px;"></i>主页</el-menu-item>-->
<!--    <el-menu-item index="/student" style="font-size: 18px;font-family: 新宋体"><i class="el-icon-user" style="margin-right: 10px;" ></i>学生管理</el-menu-item>-->
<!--    <el-menu-item index="/teacher" style="font-size: 18px;font-family: 新宋体"><i class="el-icon-s-custom" style="margin-right: 10px;"></i>教师管理</el-menu-item>-->
<!--    <el-menu-item index="/clazz" style="font-size: 18px;font-family: 新宋体"><i class="el-icon-s-finance" style="margin-right: 10px;"></i>班级管理</el-menu-item>-->
<!--    <el-menu-item index="/grade" style="font-size: 18px;font-family: 新宋体"><i class="el-icon-s-flag" style="margin-right: 10px;"></i>年级管理</el-menu-item>-->
<!--    <el-menu-item index="/score" style="font-size: 18px;font-family: 新宋体"><i class="el-icon-s-grid" style="margin-right: 10px;"></i>成绩管理</el-menu-item>-->
<!--    <el-menu-item index="/admin" style="font-size: 18px;font-family: 新宋体"><i class="el-icon-s-check" style="margin-right: 10px;"></i>管理员管理</el-menu-item>-->
<!--    <el-menu-item index="/info" style="font-size: 18px;font-family: 新宋体"><i class="el-icon-c-scale-to-original" style="margin-right: 10px;"></i>个人信息管理</el-menu-item>-->


    <template v-for="item in menuList">
      <template v-for="child in item.children">
        <!--一级菜单 -->
        <el-sub-menu :key="item.path" v-if="item.children != undefined && item.meta.visible">
          <p slot="title">
            <span><a-icon :type="item.meta.icon"/></span>
            <span>{{item.meta.name}}}</span>
          </p>
        </el-sub-menu>
        <!-- 二级菜单-->
        <el-menu-item :key="child.path" v-if="child.meta.visible">
          <p class="ciclebox">
            <span>{{child.name}}}</span>
          </p>
        </el-menu-item>
      </template>
      <!--普通一级菜单 -->
      <el-menu-item :index="item.path" v-if="item.meta.visible" style="font-size: 18px;font-family: 新宋体">
        <i :class="item.icon"></i>
        <span>{{item.meta.name}}</span>
      </el-menu-item>

    </template>


  </el-menu>
</template>

<script>
import routes from "../router";
export default {
  name: "Aside",

  created() {
    let that = this
    this.menuList = routes.options.routes[0].children
    console.log(this.menuList,"一级菜单");
    this.rootSubmenuKeys = this.menuList
    this.typeNum = localStorage.getItem('typeNum')
    //1- 循环一级菜单
    //2- 找出角色所在的角色数组（判断某个值在不在数组中）
    //3-然后所在数组visible改为true，其他为false
    this.menuList.forEach(element => {
      for (this.i = 0,this.count = 0;this.i<element.meta.userType.length;this.i++) {
        if(element.meta.userType[this.i] == this.typeNum){
          this.count++;
        }
        if(this.count){
          element.meta.visible = true
        }else {
          element.meta.visible = false
        }
      }
    })

    //1- 循环一级菜单
    //2- 找出角色所在的角色数组（判断某个值在不在数组中）
    //3-然后所在数组visible改为true，其他为false
    // this.menuList.forEach(element => {
    //   element.meta.userType.forEach(item => {
    //     if(item.includes(this.userType)) {   //
    //       //存在
    //       element.meta.visible = true
    //     }else {
    //       //不存在
    //       element.meta.visible = false
    //     }
    //   })
    // })
  },

  data() {
    return {
      menuList: [], //菜单循环列表
      roles: '',
      type: '', // 请求参数type类型
      count: 0,
      i:0,
    }
  }
}
</script>

<style>
  .ciclebox {
    position: relative;
  }
</style>