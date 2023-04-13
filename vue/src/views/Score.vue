<template>
  <div>


    <div style="margin: 10px  0 ">
      年级：
      <!--下拉菜单-->
      <template>
        <el-select v-model="gradeName" clearable placeholder="请选择" @focus="getGrade">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.name"
              :value="item.name">
          </el-option>
        </el-select>
      </template>

      &nbsp;&nbsp;&nbsp;班级：
      <!--班级下拉菜单-->
      <template>
        <el-select v-model="clazzName" clearable placeholder="请选择" @focus="getClazz">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.name"
              :value="item.name">
          </el-option>
        </el-select>
      </template>
      科目：
      <!--科目下拉菜单-->
      <template>
        <el-select v-model="subject" clearable placeholder="请选择" @focus="getSubject">
          <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.cname"
              :value="item.ename">
          </el-option>
        </el-select>
      </template>
      排名：
      <!--科目下拉菜单-->
      <template>
        <el-select v-model="rank" clearable placeholder="请选择">
          <el-option
              v-for="item in optionsRank"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </template>
      &nbsp;&nbsp;学生姓名：<el-input style="width:200px" suffix-icon="el-icon-search" placeholder="请输入..." v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>

    </div>



    <el-table :data="tableData" >
      <el-table-column prop="sno" label="学号" width="140" >
      </el-table-column>
      <el-table-column prop="clazzName" label="所属班级">
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="140">
      </el-table-column>

      <el-table-column prop="chinese" label="语文" :formatter="caseChinese">
      </el-table-column>
      <el-table-column prop="math" label="数学" :formatter="caseMath">
      </el-table-column>
      <el-table-column prop="english" label="英语" :formatter="caseEnglish">
      </el-table-column>
      <el-table-column prop="geography" label="地理" :formatter="caseGeography">
      </el-table-column>
      <el-table-column prop="history" label="历史" :formatter="caseHistory">
      </el-table-column>
      <el-table-column prop="politics" label="政治" :formatter="casePolitics">
      </el-table-column>
      <el-table-column prop="physics" label="物理" :formatter="casePhysics">
      </el-table-column>
      <el-table-column prop="chemistry" label="化学" :formatter="caseChemistry">
      </el-table-column>
      <el-table-column prop="biology" label="生物" :formatter="caseBiology">
      </el-table-column>
      <el-table-column prop="gross_score" label="总分" :formatter="caseGrossScore">
      </el-table-column>



      <el-table-column label="操作" v-if="typeNum != 3">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)" >编辑<i class="el-icon-edit"></i></el-button>
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

    <!--修改对话框 -->
    <el-dialog title="修改成绩信息" :visible.sync="dialogFormVisible" width="25%">
      <el-form label-width="100px" size="small">
        <el-form-item label="语文:" prop="chinese">
          <el-input v-model="form.chinese" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="数学:" prop="math">
          <el-input v-model="form.math" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="英语:" prop="english">
          <el-input v-model="form.english" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地理:" prop="geography">
          <el-input v-model="form.geography" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="历史:" prop="history">
          <el-input v-model="form.history" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="政治:" prop="politics">
          <el-input v-model="form.politics" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="物理:" prop="physics">
          <el-input v-model="form.physics" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="化学:" prop="chemistry">
          <el-input v-model="form.chemistry" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="生物:" prop="biology">
          <el-input v-model="form.biology" autocomplete="off"></el-input>
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
  name: "Score",


  data() {
    return {
      //分页
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 15,
      //信息
      name: "",
      clazzName: "",
      gradeName: "",
      chinese: '',
      math: '',
      english: '',
      geography: '',
      history: '',
      politics: '',
      physics: '',
      chemistry: '',
      biology: '',
      grossScore: '',
      subject: "",
      rank: "",

      //角色（数字）
      typeNum: localStorage.getItem('typeNum'),

      multipleSelection: [],

      //新增对话框
      dialogFormVisible: false,
      form: {},

      //班级选择器
      options: [],
      value: "",

      //科目选择器
      options1: [],

      //排名选择器
      optionsRank: [{
        label: "前五",
        value: 5
      }, {
        label: "前十",
        value: 10
      }, {
        label: "前十五",
        value: 15
      }, {
        label: "前二十",
        value: 20
      }, {
        label: "前二十五",
        value: 25
      }, {
        label: "前三十",
        value: 30
      }, {
        label: "前五十",
        value: 50
      }, {
        label: "前一百",
        value: 100
      }],
    }
  },

  created() {
    this.load()
    console.log(this.typeNum,"typeNum");
  },

  methods: {
    //启动
    load() {
      request.get("/score/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          clazzName: this.clazzName,
          name: this.name,
          subject: this.subject,
          rank: this.rank,
          gradeName: this.gradeName,
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

    //班级选择器
    getClazz() {
      request("/clazz/getByGradeName", {
        params: {
          gradeName: this.gradeName
        }
      }).then(res => {
        console.log(res)
        this.options = res
      })
    },

    //年级选择器
    getGrade() {
      request("/grade/").then(res => {
        console.log(res)
        this.options = res
      })
    },

    //重置按钮
    reset() {
      this.clazzName = ""
      this.name = ""
      this.subject = ""
      this.gradeName = ""
      this.rank = ""
      this.load()
    },

    save() {
      request.post("/score",this.form).then(res =>{
        if(res) {
          this.$message.success("修改成功")
          this.dialogFormVisible = false
          this.load()
        }else {
          this.$message.error("修改失败")
        }
      })
    },

    //科目选择器
    getSubject() {
      request.get("/subject/").then(res => {
        console.log((res));
        this.options1 = res;

      })
    },

    //类型格式化
    //语文默认值为null
    caseChinese(row) {
      let showProp = null
      row.chinese ? showProp = row.chinese : showProp = null
      return showProp
    },
    //数学默认值为null
    caseMath(row) {
      let showProp = null
      row.math ? showProp = row.math : showProp = null
      return showProp
    },
    //英语默认值为null
    caseEnglish(row) {
      let showProp = null
      row.english ? showProp = row.english : showProp = null
      return showProp
    },
    //地理默认值为null
    caseGeography(row) {
      let showProp = null
      row.geography ? showProp = row.geography : showProp = null
      return showProp
    },
    //历史默认值为null
    caseHistory(row) {
      let showProp = null
      row.history ? showProp = row.history : showProp = null
      return showProp
    },
    //政治默认值为null
    casePolitics(row) {
      let showProp = null
      row.politics ? showProp = row.politics : showProp = null
      return showProp
    },
    //物理默认值为null
    casePhysics(row) {
      let showProp = null
      row.physics ? showProp = row.physics : showProp = null
      return showProp
    },
    //化学默认值为null
    caseChemistry(row) {
      let showProp = null
      row.chemistry ? showProp = row.chemistry : showProp = null
      return showProp
    },
    //物理默认值为null
    caseBiology(row) {
      let showProp = null
      row.biology ? showProp = row.biology : showProp = null
      return showProp
    },
    //总分默认值为null
    caseGrossScore(row) {
      let showProp = null
      row.grossScore ? showProp = row.grossScore : showProp = null
      return showProp
    },

  },
}
</script>

<style scoped>

</style>