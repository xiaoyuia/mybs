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
      &nbsp;&nbsp;&nbsp;年级：
      <!--下拉菜单-->
      <template>
        <el-select v-model="name" clearable placeholder="请选择" @focus="getGrade">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.name"
              :value="item.name">
          </el-option>
        </el-select>
      </template>

      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>

    </div>



    <el-table :data="tableData" border stripe @selection-change="handleSelectionChange" >
      <el-table-column type="selection" width="40" ></el-table-column>
      <el-table-column prop="name" label="年级名称" width="140">
      </el-table-column>
      <el-table-column prop="manager" label="年级主任" width="140">
      </el-table-column>
      <el-table-column prop="email" label="主任邮箱">
      </el-table-column>
      <el-table-column prop="telephone" label="主任电话" >
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
    <el-dialog title="添加年级信息" :visible.sync="dialogFormVisible" width="25%">
      <el-form label-width="100px" size="small">
        <el-form-item label="年级名称:" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="年级主任:" prop="manager">
          <el-input v-model="form.manager" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主任邮箱:" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="主任电话:" prop="telephone">
          <el-input v-model="form.telephone" autocomplete="off"></el-input>
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
  name: "Grade",

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
      telephone: "",
      gender: "",
      gradeName: "",
      headmaster: "",

      multipleSelection: [],

      //新增对话框
      dialogFormVisible: false,
      form: {},

      //班级选择器
      options: [],
      value: "",
    }
  },

  created() {
    this.load()
  },

  methods: {
    //启动
    load() {
      request.get("/grade/page", {
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
      request.delete("/grade/" + id).then(res => {
        if(res) {
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.error("删除失败")
        }
      })
    },

    //年级选择器
    getGrade() {
      request("/grade/").then(res => {
        console.log(res)
        this.options = res
      })
    },

    //批量删除
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  //[{},{},{},] => [1,2,3]
      request.post("/grade/del/batch",ids).then(res => {
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
      request.post("/grade",this.form).then(res =>{
        if(res) {
          this.$message.success("添加成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error("添加失败")
        }
      })
    }

  },
}
</script>

<style scoped>

</style>