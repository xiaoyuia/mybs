<template>
  <div class="wrapper">
    <div style="margin:200px auto; background-color: #fff; width: 350px; height: 300px; padding:20px; border-radius: 10px">
      <div style="margin:20px 0; text-align:center; font-size: 24px;font-family: 华文宋体"><b>校园信息管理系统</b></div>
      <el-input size="medium" style="margin:10px 0 " prefix-icon="el-icon-user" v-model="user.username"></el-input>
      <el-input size="medium" style="margin:10px 0 " prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
      <div style="margin-top: 5px;font-family: 新宋体">
        <el-radio-group v-model="user.userType">
          <el-radio :label="1">管理员</el-radio>
          <el-radio :label="2">教师</el-radio>
          <el-radio :label="3">学生</el-radio>
        </el-radio-group>
      </div>
      <div style="margin:10px 0; text-align: right">
        <el-button type="primary" size="small" autocomlete="off" @click="login">登录</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      userType: 1,
      user: {}
    }
  },
  methods: {
    login() {
      this.request.post("/system/login",this.user).then(res=>{
        if(res.code == '200') {
          localStorage.setItem("typeNum",JSON.stringify(res.data.typeNum))  //存储用户类型（数字）到浏览器
          localStorage.setItem("userType",JSON.stringify(res.data.userType)) //存储用户类型（id）到浏览器
          localStorage.setItem("user",JSON.stringify(res.data.user))  //存储用户信息到浏览器
          localStorage.setItem("permissions","edit")  //存储按钮控制信息到浏览器
          this.$router.push("/")
          this.$message.success("登录成功")
        } else {
          this.$message.error(res.message)
        }
      })
    }
  }
}
</script>

<style>
  .wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right,#FC466B , #3F5EFB);
    OVERFLOW: HIDDEN;
  }

</style>