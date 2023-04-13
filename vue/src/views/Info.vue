<template>

  <el-card style="width: 700px; height: 800px;">
    <h2 style="text-align: center">个人信息</h2>
    <el-divider></el-divider>


<!--      头像-->
      <div style="text-align: center">
        <el-upload

            action="http://localhost:8085/system/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess1">
          <img v-if="form.portraitPath" :src="form.portraitPath" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
    <el-form lable-width="100px" size="small">
      <el-form-item label="姓名:" >
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别:">
        <el-input v-model="form.gender" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码:">
        <el-input v-model="form.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话:">
        <el-input v-model="form.telephone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址:">
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div style="text-align: right"><el-button type="primary" @click="save">修 改</el-button></div>

  </el-card>
</template>


<script>

import request from "@/utils/request";
export default {
  name: "info",

  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      userType: localStorage.getItem("userType") ? JSON.parse(localStorage.getItem("userType")) : {},
      typeNum: localStorage.getItem("typeNum") ? JSON.parse(localStorage.getItem("typeNum")) : {},
      form: {},
    }
  },
  created() {

    this.form = this.user

  },
  methods: {
    handleAvatarSuccess1(res) {
      this.form.portraitPath = res

      localStorage.removeItem("user")
      localStorage.setItem("user",JSON.stringify(this.form))
      location.reload()
    },
    save() {
      if(this.userType === "管理员：") {
        request.post("/admin", this.form).then(res => {
          console.log(res)
          this.$message.success("修改成功")
        })
      }else if(this.userType === "教师：") {
        request.post("/teacher",this.form).then(res => {
          console.log(res)
          this.$message.success("修改成功")
        })
      }else if(this.userType === "学生：") {
        request.post("/student",this.form).then(res => {
          console.log(res);
          this.$message.success("修改成功")
        })
      }else {
        this.$message.error("修改失败，请重试")
      }

    },

  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>