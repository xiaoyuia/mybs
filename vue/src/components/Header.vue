<template >

    <el-dropdown sytle="width: 70px" >
      <div>
        <img :src="user.portraitPath" alt=""
             style="width: 40px;border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{userType +  user.name}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item ><span style="text-decoration: none" @click="logout">退出</span></el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>


</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      userType: localStorage.getItem("userType") ? JSON.parse(localStorage.getItem("userType")) : {},

    }
  },
  created() {

  },
  methods: {
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("user")
      localStorage.removeItem("userType")
      localStorage.removeItem("permissions")
      localStorage.removeItem("typeNum")
      this.$message.success("退出成功")
    }
  }
}
</script>

<style scoped>

</style>