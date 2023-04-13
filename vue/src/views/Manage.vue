<template>

  <div>
    <el-container style="height: 100%;min-height: 100vh; border: 0px solid #eee;">
      <!--Aside-->
      <el-aside width="250px" style="background-color: rgb(0 21 41)" >
        <Aside />
      </el-aside>

      <el-container>
        <!--Header -->
        <el-header style="text-align: right; font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px">
          <Header />
        </el-header>

        <!--router-view -->
        <el-main>
          <!-- 表示当前页面的子路由会在router-view 里面展示-->
          <router-view />
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'HomeView',
  data() {
    return {}
  },
  //引入组件
  components: {
    Aside,Header
  },

  methods: {

  }
}
</script>



