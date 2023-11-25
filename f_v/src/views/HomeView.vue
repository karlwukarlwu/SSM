<template>
<!--  他这里前端逻辑跟屎一样  看看得了-->
  <div style="margin: 10px 5px">
    <!--      老韩解读
      1. @click="add" 表单点击 新增，就会触发 add方法
    -->
    <div style="margin: 10px 5px">
      <!--      老韩解读
        1. @click="add" 表单点击 新增，就会触发 add方法
      -->
      <el-button type="primary" @click="add">新增</el-button>
      <el-button>其它</el-button>
    </div>

    <div style="margin: 10px 5px">
      <el-input v-model="search" style="width: 30%;" placeholder="请输入关键字"/>
      <el-button style="margin-left: 10px" type="primary" @click="list">检索</el-button>
    </div>


    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column
          prop="id"
          label="ID"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="name"
          label="家居名"
      >
      </el-table-column>
      <el-table-column
          prop="maker"
          label="厂家">
      </el-table-column>

      <el-table-column
          prop="price"
          label="价格">
      </el-table-column>

      <el-table-column
          prop="sales"
          label="销量">
      </el-table-column>

      <el-table-column
          prop="stock"
          label="库存">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <!--        老师说明：
              1、这里通过 handleEdit(scope.row)
              2, 可以将当前行数据传递给handleEdit
        -->
        <template #default="scope">
          <!--          scope.row 拿到行数据的方式 等学vue再说 -->
          <!--          知道这样拿到了行数据就行 -->
          <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <!--      老韩说明
              1. 如果你点击的确定，就会触发handleDel
              2. 如果你点击的是取消，就不会触发handleDel
          -->
          <!--          这里直接从文档来-->
          <el-popconfirm title="确认删除吗?" @confirm="handleDel(scope.row.id)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--当点击添加的时候跳出的弹窗-->
    <!--el-input v-model="form.name" 表示表单的input控件，名字为name 需要和后台Javabean[Furn] 属性名称一致-->
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules" ref="form" label-width="120px">
        <el-form-item label="家居名" prop="name">
          <el-input v-model="form.name" style="width: 50%"></el-input>
          {{serverValidErrors.name}}
        </el-form-item>
        <el-form-item label="厂商" prop="maker">
          <el-input v-model="form.maker" style="width: 50%"></el-input>
          {{serverValidErrors.maker}}
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" style="width: 50%"></el-input>
          {{serverValidErrors.price}}
        </el-form-item>
        <el-form-item label="销量" prop="sales">
          <el-input v-model="form.sales" style="width: 50%"></el-input>
          {{serverValidErrors.sales}}
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="form.stock" style="width: 50%"></el-input>
          {{serverValidErrors.stock}}
        </el-form-item>
      </el-form>
      <template #footer>
<span class="dialog-footer">
<el-button @click="dialogVisible = false">取消</el-button>
  <!--  修改和添加是一个弹窗 如何区别？-->
<el-button type="primary" @click="save">确定</el-button>
</span>
      </template>
      <!--      引入分页导航条-->
    </el-dialog>
    <div style="margin: 10px 0">
      <!--          下面的值要从后端拿过来-->
      <el-pagination
          @size-change="handlePageSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[2,5,10,15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import request from "@/utils/request";

export default {
  name: 'HomeView',
  components: {},

  data() {
    return {
      serverValidErrors: {}, //后端校验的错误信息

      // 增加分页相应的数据绑定
      currentPage: 1, //当前页
      pageSize: 5,//每页显示记录数
      total: 10,//共有多少记录

      search: '',
      dialogVisible: false,
      // 这个form是添加的时候蹦出来的那个弹窗
      form: {},
      // 这个tableData是前端展示的数据
      tableData: [],
      //定义添加表单的校验规则
      rules: {
        name: [
          //这里我们可以写多个针对name属性的校验规则
          {required: true, message: "请输入家居名", trigger: "blur"}
        ],
        maker: [
          {required: true, message: "请输入制造商名", trigger: "blur"}
        ],
        price: [
          {required: true, message: "请输入价格", trigger: "blur"},
          //使用正则表达式输入的数据进行校验=> java基础我们讲过正则表达式
          {pattern: /^([1-9]\d*|0)(\.\d+)?$/, message: "请输入数字", trigger: "blur"}
        ],
        sales: [
          {required: true, message: "请输入销量", trigger: "blur"},
          {pattern: /^([1-9]\d*|0)$/, message: "请输入数字", trigger: "blur"}
        ],
        stock: [
          {required: true, message: "请输入库存", trigger: "blur"},
          {pattern: /^([1-9]\d*|0)$/, message: "请输入数字", trigger: "blur"}
        ]
      }

    }
  },
  created() {
    // 这个时候我们拿到了extend的数据  然后我们可以对数据开始操作
    this.list()
  },
  methods: {
    add() {
      this.dialogVisible = true
      this.form = {}
      // 等学vue再说了
      // 清空前端
      if(this.$refs['form']) {
        this.$refs['form'].resetFields()
      }
      // 清空后端
      this.serverValidErrors = {}
    },
    handleEdit(row) {
      // this.form = JSON.parse(JSON.stringify(row))
      // this.dialogVisible = true
      request.get("/api/find/" + row.id).then(res => {
        // console.log("家居信息-", res.extend.furn)
        this.form = res.extend.furn
        this.dialogVisible = true
      })
    },
    save() {
      // 他只验证了添加的逻辑
      // 通过id 判断是新增还是修改
      // 因为设置了回显 所以修改是有id的，新增是没有id的 id要去mysql自增
      if (this.form.id) {
        // 修改
        request.put("/api/update", this.form).then(res => {
          console.log(res)
          if (res.code === 200) {
            // 涉及到了vue的知识点 等我学了vue再说
            // 他这里新建了一个组件 但是我没看懂
            this.$message({
              message: '修改成功',
              type: 'success'
            });
          }
          this.dialogVisible = false
          this.list()
        })
        return
      }else {
        //表单验证是否通过
        this.$refs['form'].validate((valid) => {
          //这里我们让前端校验放行,测试后端校验的效果,测试完毕，再修改回来
          // valid = true  //注销测试
          //valid就是表单校验后返回的结果
          if (valid) {//如果校验通过
            //老师解读
            //1. url: http://localhost:8080/ssm/save
            //2. this.form : 携带的数据
            request.post("/api/save", this.form).then(res => {
              console.log("res-", res)
              if(res.code === 200) {
              //   //关闭对话框，更新数据
                this.dialogVisible = false
                //调用list方法, 刷新数据
                this.list()
              } else if(res.code === 400) {//后端校验失败
                //取出校验失败的信息，赋给 serverValidErrors
                this.serverValidErrors.name = res.extend.errorMsg.name
                this.serverValidErrors.maker = res.extend.errorMsg.maker
                this.serverValidErrors.price = res.extend.errorMsg.price
                this.serverValidErrors.sales = res.extend.errorMsg.sales
                this.serverValidErrors.stock = res.extend.errorMsg.stock
              }
            })
          } else {//校验没有通过

            //提示一个错误的消息框
            this.$message(
                {
                  type: "error",
                  message: "验证失败, 不提交"
                }
            )
          }
          return false //放弃提交

        })
      }

      // // 跨域拦截方是浏览器 不是后端
      // request.post("/api/save", this.form).then(res => {
      //   console.log(res)
      //   this.dialogVisible = false
      //   this.list()
      //
      // })
    },
    // 这个方法是在页面加载完成后，自动调用
    // 用vue的生命周期
    // 本质上跳页 查找 都是list 只是传入的参数不一样
    // 查找的时候传入的是search 跳页的时候传入的是currentPage和pageSize
    // 查找其他的参数是requestParam 后台默认的
    list() {
      // request.get("/api/furns").then(res => {
      //   // 这个res是经过拦截器处理的res 直接就是response.data 且被转换成了json
      //   console.log(res)
      //   // 这里就是你后端配置的 data.extend.furns
      //   // this.tableData = res.data.extend.furns
      //   this.tableData = res.extend.furns
      // })

      // 变成有分页的需求
      // request.get("/api/furnsByPage", {
      request.get("/api/furnsByConditionPage", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          // 他这里是双向绑定 你甚至不用点击检索按钮 他就会自动检索
          // 你只要是输入框输入了内容 他就会自动检索 而且只要内容不消失就是一直绑定
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.extend.pageInfo.list
        this.total = res.extend.pageInfo.total
      })

    },
    handleDel(id) {
      request.delete("/api/del/" + id).then(res => {
        if (res.code === 200) {//删除成功
          //提示一个成功的消息框
          this.$message(
              {
                type: "success",
                message: res.msg
              }
          )
        } else { //删除失败
          //提示一个错误的消息框
          this.$message(
              {
                type: "error",
                message: res.msg
              }
          )
        }
        this.list()
      })
    },
    // 分页导航条控制大小的方法
    handlePageSizeChange(pageSize) {
      this.pageSize = pageSize
      this.list()
    },
    // 分页导航条控制页数的方法
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage
      this.list()
    }
  }
}
</script>
