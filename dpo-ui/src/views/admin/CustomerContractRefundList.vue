<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="信息检索">
              <a-input placeholder="请输入客户名称\合同名称\退租单号" v-model="queryParam.searchValue"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="退租状态">
              <a-select v-model="queryParam.status" placeholder="请选择合同状态">
                <a-select-option value="1">已完成</a-select-option>
                <a-select-option value="0">待结算</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <div class="table-operator">
      <a-dropdown v-if="removeEnable&&selectedRowKeys.length > 0">
        <a-button type="danger" icon="delete" @click="delByIds(selectedRowKeys)">删除</a-button>
      </a-dropdown>
    </div>
    <s-table
      size="default"
      ref="table"
      rowKey="id"
      showPagination="true"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, getCheckboxProps: getCheckboxProps}"
      :columns="columns"
      :data="loadData"
      bordered
    >
      <div slot="sn" slot-scope="text, record" style="cursor:pointer;color:#1890ff" v-if="editEnabel" @click="handleEdit(record.id, record.contractId)">
        <span>{{ text }}</span>
      </div>
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="!record.status" @click="approval(record.contractId, record.id)" type="primary">确定退租</a>
        <a-divider type="vertical" v-if="!record.status"/>
        <a @click="refundEdit(record.id,record.contractId)" v-if="!record.status">编辑</a>
        <a-divider type="vertical" v-if="!record.status"/>
        <a @click="delByIds([record.id])" v-if="!record.status">删除</a>
      </span>
    </s-table>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import {
  getCustomerContractRefundList,
  delCustomerContractRefund,
  customerContractCancel
} from '@/api/admin/customerContractRefund'
import CustomerContractRefundModal from './modules/CustomerContractRefundModal.vue'
import { checkPermission } from '@/utils/permissions'

export default {
  name: 'TableList',
  components: {
    STable,
    CustomerContractRefundModal
  },
  data () {
    return {
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
          title: '退租时间',
          dataIndex: 'refundDate'
        },
        {
          title: '退租单号',
          dataIndex: 'sn',
          scopedSlots: { customRender: 'sn' }
        },
        {
          title: '客户名称',
          dataIndex: 'customerContract.customerName'
        },
        {
          title: '应收金额(元)',
          dataIndex: 'receiveFee'
        },
        {
          title: '应退金额(元)',
          dataIndex: 'refundFee'
        },
        {
          title: '合计金额(元)',
          dataIndex: 'totalFee'
        },
        {
          title: '退租状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },
          fixed: 'right'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getCustomerContractRefundList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:refund:add'),
      editEnabel: checkPermission('admin:refund:edit'),
      removeEnable: checkPermission('admin:refund:remove')
    }
  },
  filters: {
    statusFilter (status) {
      const statusTypeMap = {
        'false': '待结算',
        'true': '已完成'
      }
      return statusTypeMap[status]
    }
  },
  created () {
  },
  methods: {
    getCheckboxProps (record) {
      return ({
        props: {
          disabled: record.status === true
        }
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd () {
      this.$refs.modal.add()
    },
    handleEdit (id, contractId) {
      this.$router.push({ name: 'CustomerContractRefundModal', query: { id: id, contractId: contractId, url: 'refund' } })
    },
    refundEdit (id, contractId) {
      this.$router.push({ name: 'CustomerContractRefundEdit', query: { id: id, contractId: contractId, url: 'refund' } })
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    delByIds (id) {
      delCustomerContractRefund({ ids: id.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error(res.msg)
        }
        this.selectedRowKeys = []
      })
    },
    approval (ids, id) {
      // ids合同id， id退租id
      customerContractCancel({ ids, id }).then(res => {
        if (res.code === 0) {
          this.$message.success('操作成功')
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
