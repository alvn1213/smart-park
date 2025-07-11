<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="15">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.customerName"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="15">
            <a-form-item label="账单月份">
              <a-month-picker style="display:inline-block" placeholder="请选择开始日期" v-model="queryParam.queryStartMonth" @change="onStartChange" />
              <span> - </span>
              <a-month-picker style="display:inline-block" placeholder="请选择结束日期" v-model="queryParam.queryEndMonth" @change="onEndChange" />
            </a-form-item>
          </a-col>
          <a-col :md="24" :sm="24" style="text-align:center;">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              <a-button style="margin-left: 8px" @click="download">导出</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table
      size="default"
      ref="table"
      rowKey="billId"
      :columns="columns"
      :data="loadData"
    >
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="powerFee" slot-scope="text">
        {{ text? text : '0' }}
      </span>
      <span slot="waterFee" slot-scope="text">
        {{ text? text : '0' }}
      </span>
      <span slot="otherFee" slot-scope="text">
        {{ text? text : '0' }}
      </span>
      <span slot="refund" slot-scope="text">
        {{ text? text : '0' }}
      </span>
    </s-table>
    <customerContractBill-modal ref="modal" @ok="handleOk" />
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { reportCustomerContractBill, delCustomerContractBill, reportExportFileRequest, cancelConfirm } from '@/api/admin/customerContractBill'
import { checkPermission } from '@/utils/permissions'
import CustomerContractBillModal from '@/views/admin/modules/CustomerContractBillModal'
import moment from 'moment'
export default {
  name: 'TableList',
  components: {
    CustomerContractBillModal,
    STable
  },
  data () {
    return {
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
          title: '客户名称',
          width: '200px',
          dataIndex: 'customer.name'
        },
        {
          title: '应收租金(元)',
          width: '100px',
          dataIndex: 'totalRent'
        },
        {
          title: '已收租金(元)',
          width: '100px',
          dataIndex: 'receiveTotalRent'
        },
        {
          title: '应收物业费(元)',
          width: '100px',
          dataIndex: 'managementTotalFee'
        },
        {
          title: '已收物业费(元)',
          width: '100px',
          dataIndex: 'receiveManagementTotalFee'
        },
        {
          title: '应收电费(元)',
          width: '100px',
          dataIndex: 'powerFee'
        },
        {
          title: '已收电费(元)',
          width: '100px',
          dataIndex: 'receivePowerFee'
        },
        {
          title: '应收水费(元)',
          width: '100px',
          dataIndex: 'waterFee'
        },
        {
          title: '已收水费(元)',
          width: '100px',
          dataIndex: 'receiveWaterFee'
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
          dataIndex: 'receiveTotalRent'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        var params = Object.assign(parameter, this.queryParam)
        return reportCustomerContractBill(params)
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
  },
  methods: {
    billEdit (e) {
      this.$router.push({ name: 'customerContractBillEdit', query: { id: e.billId, url: 'bill' } })
    },
    radiogroupChange (e) {
      this.queryParam = {}
      this.queryParam.status = e.target.value
      this.$refs.table.refresh(true)
    },
    binNav (e) {
      this.$router.push({ name: 'customerContactsDetailModal', query: { id: e.contractId, url: 'bill' } })
    },
    download (parameter) {
      reportExportFileRequest(Object.assign(parameter, this.queryParam)).then(response => {
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
      var monthStart = moment(this.queryParam.queryStartMonth).format('YYYY年MM月')
      var monthEnd = moment(this.queryParam.queryEndMonth).format('YYYY年MM月')
      if (this.queryParam.queryStartMonth && !this.queryParam.queryEndMonth) {
        link.setAttribute('download', monthStart + '账单报表.xls')
      } else if (this.queryParam.queryEndMonth && !this.queryParam.queryStartMonth) {
        link.setAttribute('download', monthEnd + '账单报表.xls')
      } else if (this.queryParam.queryStartMonth && this.queryParam.queryEndMonth) {
        link.setAttribute('download', monthStart + '至' + monthEnd + '账单报表.xls')
      } else {
        link.setAttribute('download', '账单报表.xls')
      }
      document.body.appendChild(link)
      link.click()
    },
    onStartChange (data, datastring) {
      this.queryParam.queryStartMonth = datastring
    },
    onEndChange (data, datastring) {
      this.queryParam.queryEndMonth = datastring
    },
    handleAdd () {
      this.$refs.modal.add()
    },
    handleEdit (record) {
      this.$refs.modal.edit(record)
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
  }
}
</script>
