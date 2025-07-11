<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="15">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.queryCustomerName"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="15">
            <a-form-item label="账单编号">
              <a-input placeholder="请输入账单编号" v-model="queryParam.sn"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="15">
            <a-form-item label="合同编号">
              <a-input placeholder="请输入合同编号" v-model="queryParam.searchValue"/>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="15">
            <a-form-item label="账单月份">
              <a-month-picker placeholder="请选择日期" v-model="queryParam.queryMonth" @change="onChange" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="15">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="账单状态">
              <a-radio-group button-style="solid" @change="radiogroupChange" v-model="queryParam.status">
                <a-radio-button value="UNRECEIVED">未收</a-radio-button>
                <a-radio-button value="RECEIVED">已收</a-radio-button>
                <a-radio-button value="PART">部分</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="15">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="账单类型">
              <a-radio-group button-style="solid" @change="radiogroupChange" v-model="queryParam.billType">
                <a-radio-button value="GENERAL">固定账单</a-radio-button>
                <a-radio-button value="OTHER">其他账单</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="15">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="是否逾期">
              <a-radio-group button-style="solid" @change="isOverdueChange" v-model="queryParam.isOverdue">
                <a-radio-button value="0">否</a-radio-button>
                <a-radio-button value="1">是</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :md="24" :sm="24" style="text-align:center;">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              <a-button style="margin-left: 8px" @click="download">导出</a-button>
              <a-button style="margin-left: 8px" @click="visible = true, exportType = '1'">租金导入</a-button>
              <a-button style="margin-left: 8px" @click="visible = true, exportType = '0'">水电费导入</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <div class="table-operator">
<!--      <a-button type="primary" icon="plus" @click="billEdit">新建</a-button>-->
      <a-dropdown>
        <a-popconfirm
          cancelText="取消"
          okText="确定"
          title="确定批量确认收款吗?"
          @confirm="cancelContract"
        >
          <a-button type="primary">确认收款</a-button>
        </a-popconfirm>
      </a-dropdown>
      <a-dropdown v-if="removeEnable&&selectedRowKeys.length > 0">
        <a-button type="danger" icon="delete" @click="delByIds(selectedRowKeys)">删除</a-button>
      </a-dropdown>
    </div>
    <s-table
      size="default"
      ref="table"
      rowKey="id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, getCheckboxProps: getCheckboxProps}"
      :columns="columns"
      :data="loadData"
      :scroll="{x:true}"
            bordered
      showPagination="true"
    >
      <span slot="isOverdue" slot-scope="text">
        {{ text === 0? "否":"是" }}
      </span>
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="billType" slot-scope="text">
        {{ text | billTypeFilter }}
      </span>
      <div slot="customerContract.sn" slot-scope="text, record" style="cursor:pointer;color:#1890ff" @click="binNav(record)">
        <span>{{ text }}</span>
      </div>
      <span slot="action" slot-scope="text, record">
<!--        <a @click="billEdit(record)" style="margin-right:10px;">编辑</a>
        -->
        <a v-if="editEnabel && record.status != 'RECEIVED'" @click="handleEdit(record)">确认收款</a>
        <a v-if="editEnabel && record.status == 'RECEIVED'" @click="cancel(record.id)">取消确认</a>
        <a-divider type="vertical" />
        <a-popconfirm
            v-if="removeEnable"
            cancelText="取消"
            okText="确定"
            title="确定删除这条数据吗?"
            @confirm="delByIds([record.id])"
          >
            <a>删除</a>
          </a-popconfirm>
      </span>
    </s-table>
    <customerContractBill-modal ref="modal" @ok="handleOk" />
    <customerContractOtherBill-modal ref="otherModal" @ok="handleOk" />
    <a-modal
      title="账单导入"
      :width="600"
      v-model="visible"
      :confirmLoading="confirmLoading"
      @ok="handleSubmit"
    >
      <div style="line-height:30px;">
        <div>操作步骤：</div>
        <div v-if="exportType === '0'">1、下载《<a @click="parameter =>exportPowerWaterFee(parameter, 0)">水电费账单模版</a>》</div>
        <div v-else>1、下载《<a @click="parameter =>exportPowerWaterFee(parameter, 1)">租金账单模版</a>》</div>
        <div>2、打开下载表，将对应字段信息输入或粘贴进本表，为保障粘贴信息被有效导入，请使用纯文本或数字</div>
        <div>3、信息输入完毕，在本页面点击“上传”按钮，选择excel文档</div>
        <div>4、点击“开始导入”，即可完成批量导入操作</div>
        <a-upload
          name="file"
          :multiple="true"
          :file-list="fileList"
          :action="uploadUrl"
          :headers="headers"
          @change="handleChange"
        >
          <a-button> <a-icon type="upload" />上传</a-button>
        </a-upload>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import {
  getCustomerContractBillList,
  batchConfirmReceiveAmount,
  delCustomerContractBill,
  exportFileRequest,
  exportPowerWaterFee,
  exportRent,
  importPowerWaterFee,
  importRent,
  cancelConfirm
} from '@/api/admin/customerContractBill'
import storage from 'store'
import { checkPermission } from '@/utils/permissions'
import CustomerContractBillModal from '@/views/admin/modules/CustomerContractBillModal'
import CustomerContractOtherBillModal from '@/views/admin/modules/CustomerContractOtherBillModal'
import moment from 'moment'
export default {
  name: 'TableList',
  components: {
    CustomerContractBillModal,
    CustomerContractOtherBillModal,
    STable
  },
  data () {
    return {
      exportType: '0', // 账单导入类型
      fileName: '',
      fileList: [],
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      confirmLoading: false,
      visible: false,
      allAmount: 0,
      notPayBill: 0,
      receiveRent: 0,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: this.$form.createForm(this),
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '账期',
          width: '220px',
          dataIndex: 'billDate',
          fixed: 'left'
        },
        {
          title: '客户名称',
          width: '260px',
          dataIndex: 'customerContract.customerName',
          fixed: 'left'
        },
        {
          title: '合同名称',
          width: '210px',
          dataIndex: 'customerContract.name'
        },
        {
          title: '账单编号',
          width: '220px',
          dataIndex: 'sn'
        },
        {
          title: '合同编号',
          width: '260px',
          dataIndex: 'customerContract.sn',
          scopedSlots: { customRender: 'customerContract.sn' }
        },
        {
          title: '物管合同号',
          dataIndex: 'customerContract.pmSn',
          width: '200px',
          ellipsis: true
        },
        {
          title: '账单类型',
          width: '90px',
          dataIndex: 'billType',
          scopedSlots: { customRender: 'billType' }
        },
        {
          title: '账单状态',
          width: '90px',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '是否逾期',
          width: '90px',
          dataIndex: 'isOverdue',
          scopedSlots: { customRender: 'isOverdue' }
        },
        {
          title: '收款截止日',
          width: '130px',
          dataIndex: 'receiveRentDate',
          sorter: true
        },
        {
          title: '应收租金(元)',
          width: '100px',
          dataIndex: 'rent'
        },
        {
          title: '押金(元)',
          width: '100px',
          dataIndex: 'deposit'
        },
        {
          title: '已收租金(元)',
          width: '100px',
          dataIndex: 'receiveRent'
        },
        {
          title: '水费(元)',
          width: '100px',
          dataIndex: 'waterFee'
        },
        {
          title: '电费(元)',
          width: '100px',
          dataIndex: 'powerFee'
        },
        {
          title: '其它费用(元)',
          width: '130px',
          dataIndex: 'otherFee'
        },
        {
          title: '退费(元)',
          width: '100px',
          dataIndex: 'refundFee'
        },
        {
          title: '物业管理费(元)',
          width: '130px',
          dataIndex: 'managementTotalFee'
        },
        {
          title: '合计应收(元)',
          width: '100px',
          dataIndex: 'totalBill'
        },
        {
          title: '合计未收(元)',
          width: '100px',
          dataIndex: 'waitPay'
        },
        {
          title: '合计已收(元)',
          width: '100px',
          dataIndex: 'receiveTotalBill'
        },
        {
          title: '备注',
          width: '100px',
          dataIndex: 'remark'
        },
        {
          title: '操作',
          width: '160px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },
          fixed: 'right'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        var params = Object.assign(parameter, this.queryParam)
        return getCustomerContractBillList(params)
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:bill:add'),
      editEnabel: checkPermission('admin:bill:edit'),
      removeEnable: checkPermission('admin:bill:remove')
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        'UNRECEIVED': '未收',
        'PART': '部分',
        'RECEIVED': '已收'
      }
      return statusMap[status]
    },
    billTypeFilter (billType) {
      const billTypeMap = {
        'GENERAL': '固定账单',
        'OTHER': '其他账单'
      }
      return billTypeMap[billType]
    },
    NumFormat: function (value) {
      if (!value) return '0.00'
      value = value.toFixed(2)
      var intPart = Math.trunc(value)// 获取整数部分
      var intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,') // 将整数部分逢三一断
      var floatPart = '.00' // 预定义小数部分
      var value2Array = value.split('.')
      // =2表示数据有小数位
      if (value2Array.length === 2) {
        floatPart = value2Array[1].toString() // 拿到小数部分
        if (floatPart.length === 1) { // 补0,实际上用不着
          return intPartFormat + '.' + floatPart + '0'
        } else {
          return intPartFormat + '.' + floatPart
        }
      } else {
        return intPartFormat + floatPart
      }
    }
  },
  created () {
    if (this.$route.query.Date) {
      this.queryParam.queryMonth = this.$route.query.Date
      this.$refs.table.refresh(true)
    } else if (this.$route.query.isOverdue) {
      this.queryParam.isOverdue = '1'
      this.$refs.table.refresh(true)
    }
  },
  methods: {
    handleSubmit () {
      this.confirmLoading = true
      if (this.fileList.length > 0) {
        if (this.exportType === '0') {
          importPowerWaterFee({ filePath: this.fileName }).then(res => {
            if (res.code === 0) {
              this.fileList = []
              this.visible = false
              this.confirmLoading = false
              this.$message.success(res.msg)
            } else {
              this.confirmLoading = false
              this.$message.error(res.msg)
            }
          })
        } else {
          importRent({ filePath: this.fileName }).then(res => {
            if (res.code === 0) {
              this.fileList = []
              this.visible = false
              this.confirmLoading = false
              this.$message.success(res.msg)
            } else {
              this.confirmLoading = false
              this.$message.error(res.msg)
            }
          })
        }
      } else {
        this.confirmLoading = false
        this.$message.success('请先上传文件')
      }
    },
    handleChange (info) {
      this.fileList = info.fileList
      if (info.file.status === 'done') {
        const result = info.file.response
        this.fileName = result.fileName
      }
    },
    exportPowerWaterFee (parameter, type) {
      if (!this.queryParam.queryMonth) {
        this.$message.error('账单月份不能为空!')
        return
      }
      if (type === 0) {
        exportPowerWaterFee(Object.assign(parameter, this.queryParam)).then(response => {
          this.exportPowerWaterFile(response, type)
        })
      } else {
        exportRent(Object.assign(parameter, this.queryParam)).then(response => {
          this.exportPowerWaterFile(response, type)
        })
      }
    },
    exportPowerWaterFile (data, type) {
      var link = document.createElement('a')
      // 文件导出
      if (!data) {
        return
      }
      link.style.display = 'none'
      link.href = window.URL.createObjectURL(new Blob([data]))
      // var month = moment(this.queryParam.queryMonth).format('YYYY年MM月')
      if (type === 0) {
        link.setAttribute('download', '水电费账单模版.xls')
      } else {
        link.setAttribute('download', '租金账单模版.xls')
      }
      document.body.appendChild(link)
      link.click()
    },
    getCheckboxProps (record) {
      return ({
        props: {
          disabled: record.status === 'RECEIVED'
        }
      })
    },
    cancelContract () {
      batchConfirmReceiveAmount({ billIds: this.selectedRowKeys.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success('操作已成功')
          this.$refs.table.refresh(true)
        } else {
          this.$message.error(res.msg)
        }
        this.selectedRowKeys = []
      })
    },
    billEdit (e) {
      this.$router.push({ name: 'customerContractBillEdit', query: { id: e.id, url: 'bill' } })
    },
    radiogroupChange (e) {
      this.$refs.table.refresh(true)
    },
    isOverdueChange (e) {
      var data = this.queryParam
      this.queryParam = {}
      delete data.isOverdue
      this.queryParam = data
      this.queryParam.isOverdue = e.target.value
      this.$refs.table.refresh(true)
    },
    exportTypeChange (e) {
      this.exportType = e.target.value
    },
    binNav (e) {
      this.$router.push({ name: 'customerContractDetailModal', query: { id: e.contractId, url: 'bill' } })
    },
    download (parameter) {
      if (!this.queryParam.queryMonth) {
        this.$message.error('账单月份不能为空!')
        return
      }
      exportFileRequest(Object.assign(parameter, this.queryParam)).then(response => {
        this.downloadFile(response)
      })
    },
    downloadFile (data) {
      var link = document.createElement('a')
      console.log(data)
      // 文件导出
      if (!data) {
        return
      }
      link.style.display = 'none'
      link.href = window.URL.createObjectURL(new Blob([data]))
      var month = moment(this.queryParam.queryMonth).format('YYYY年MM月')
      link.setAttribute('download', month + '办公场地租金一览表.xls')
      document.body.appendChild(link)
      link.click()
    },
    onChange (data, datastring) {
      var datas = this.queryParam
      this.queryParam = {}
      delete datas.queryMonth
      this.queryParam = datas
      this.queryParam.queryMonth = datastring
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd () {
      this.$refs.modal.add()
    },
    handleEdit (record) {
      if (record.billType === 'OTHER') {
        this.$refs.otherModal.edit(record)
      } else {
        this.$refs.modal.edit(record)
      }
    },
    cancel (billId) {
      cancelConfirm({ billId: billId }).then(res => {
        if (res.code === 0) {
          this.$message.success('取消成功')
          this.handleOk()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    delByIds (ids) {
      delCustomerContractBill({ ids: ids.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error(res.msg)
        }
        this.selectedRowKeys = []
      })
    }
  },
  watch: {
    /*
      'selectedRows': function (selectedRows) {
        this.needTotalList = this.needTotalList.map(item => {
          return {
            ...item,
            total: selectedRows.reduce( (sum, val) => {
              return sum + val[item.dataIndex]
            }, 0)
          }
        })
      }
      */
  }
}
</script>
