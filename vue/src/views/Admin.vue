<template>
  <div>


    <div style="margin: 10px  0 ">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>

      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='取消'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      &nbsp;&nbsp;&nbsp;&nbsp;管理员姓名：<el-input style="width:200px" suffix-icon="el-icon-search" placeholder="请输入..." v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>

    </div>



    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" ></el-table-column>
      <el-table-column prop="name" label="姓名">
      </el-table-column>
      <el-table-column prop="gender" label="性别">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column prop="telephone" label="电话">
      </el-table-column>
      <el-table-column prop="address" label="住址">
      </el-table-column>
      <el-table-column prop="portraitPath" label="头像" >
        <temlate slot-scope="form">
          <img :src="form.row.portraitPath" width="100px" height="100px">
        </temlate>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>

          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>

    <!--分页 -->
    <div style="padding: 10px 0 ">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <!--新增对话框 -->
    <el-dialog title="添加管理员信息" :visible.sync="dialogFormVisible" width="25%">
      <el-form label-width="100px" size="small">
        <el-form-item label="姓名:" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="性别:" prop="gender">
          <template>
            <el-select v-model="form.gender" placeholder="请选择">
              <el-option
                  v-for="item in options1"
                  :key="item.gender"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="邮箱:" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话:" prop="telephone">
          <el-input v-model="form.telephone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="住址:" prop="address">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="头像:" prop="portraitPath" >
          <!--          <el-input v-model="form.portraitPath" autocomplete="off"></el-input>-->
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8085/system/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess">
            <img v-if="form.portraitPath" :src="form.portraitPath" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>

        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Admin",

  data() {
    return {
      //分页
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      //信息
      name: "",
      clazzName: "",
      sno: "",
      telephone: "",
      gender: "",
      gradeName: "",

      //性别选择器
      options1: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      },
      ],

      multipleSelection: [],

      //新增对话框
      dialogFormVisible: false,
      form: {},

    }
  },

  created() {
    this.load()
  },

  methods: {
    //启动
    load() {
      request.get("/admin/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records
        this.total = res.total
      })

      // fetch("http://localhost:8085/student/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize).then(res => res.json()).then(res =>{
      //   console.log(res)
      //   this.tableData = res.data
      //   this.total = res.total
      // })
    },

    //分页
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },

    //编辑按钮
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },

    //删除按钮
    del(id) {
      request.delete("/admin/" + id).then(res => {
        if(res) {
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },


    //批量删除
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  //[{},{},{},] => [1,2,3]
      request.post("/admin/del/batch",ids).then(res => {
        if(res) {
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },

    //重置按钮
    reset() {
      this.name = ""
      this.load()
    },

    //新增对话框
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    save() {
      request.post("/admin",this.form).then(res =>{
        if(res) {
          // //头像
          // localStorage.removeItem("user")
          // localStorage.setItem("user",JSON.stringify(this.form))
          // location.reload()

          this.$message.success("添加成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error("添加失败")
        }
      })
    },

    //头像上传
    handleAvatarSuccess(res) {
      this.form.portraitPath = res
    },

  },
}
</script>

<style>
  .avatar-uploader {
    text-align: left;
  }
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