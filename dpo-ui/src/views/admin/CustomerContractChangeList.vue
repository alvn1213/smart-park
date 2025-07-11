<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="7" :sm="15">
            <a-form-item label="合同名称">
              <a-input placeholder="请输入合同名称" v-model="queryParam.name"/>
            </a-form-item>
          </a-col>
          <a-col :md="7" :sm="15">
            <a-form-item label="合同编号">
              <a-input placeholder="请输入合同编号" v-model="queryParam.sn"/>
            </a-form-item>
          </a-col>
          <a-col :md="7" :sm="15">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.customerName"/>
            </a-form-item>
          </a-col>
          <a-col :md="24" :sm="24" style="text-align:center;">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <div class="table-operator">
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-button type="primary" @click="showModal(0, selectedRowKeys)">提交</a-button>
      </a-dropdown>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-button type="primary" @click="showModal(2, selectedRowKeys)">审批</a-button>
      </a-dropdown>
      <a-dropdown v-if="removeEnable&&selectedRowKeys.length > 0">
        <a-button type="danger" icon="delete" @click="showModal(1, selectedRowKeys)">删除</a-button>
      </a-dropdown>
    </div>
    <s-table
      size="default"
      ref="table"
      rowKey="id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      :columns="columns"
      :data="loadData"
      :scroll="{x:100}"
      bordered
      showPagination="true"
    >
      <div slot="sn" slot-scope="text, record" style="cursor:pointer;color:#1890ff" @click="handleDetail(record.id)">
        <span>{{ text }}</span>
      </div>
      <span slot="type" slot-scope="text">
        {{ text | typeFilter }}
      </span>
      <span slot="lessorType" slot-scope="text">
        {{ text | lessorTypeFilter }}
      </span>
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit('change', record.id)">变更</a>
        <a-divider type="vertical" />
        <a-popconfirm
          cancelText="取消"
          okText="确定"
          title="确定作废这条数据吗?"
          @confirm="cancelContract([record.id])"
        >
          <a>作废</a>
        </a-popconfirm>
        <a-divider type="vertical" />
        <a @click="handleRefundEdit(record.id)">退租</a>
      </span>
    </s-table>
    <customerContract-modal ref="modal" @ok="handleOk"/>
    <a-modal
      :title="modaltype === 2? '审批合同' : '操作'"
      v-model="visible"
      @ok="handleSubmit"
      @cancel="modalclose"
    >
      <div style="font-size:16px;margin:20px 0">
        <div style="padding-left:20px" v-if="modaltype === 0">确定批量提交所选合同吗？</div>
        <div style="padding-left:20px" v-if="modaltype === 1">确定批量删除所选合同吗？</div>
        <div style="padding-left:20px" v-if="modaltype === 2">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="审批意见">
            <a-select :value="approvetype" style="width: 90%" @change="approvetypeChange">
              <a-select-option value="APPROVED">审批通过</a-select-option>
              <a-select-option value="REFUSE">拒绝通过</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="原因">
            <a-textarea v-model="remark" placeholder="请输入原因" style="width: 90%" :rows="5" />
          </a-form-item>
        </div>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import {
  getCustomerContractList,
  customerContractCancel
} from '@/api/admin/customerContract'
import { checkPermission } from '@/utils/permissions'

export default {
  name: 'TableList',
  components: {
    STable
  },
  data () {
    return {
      approvetype: 'APPROVED',
      visible: false,
      remark: '',
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
          title: '合同名称',
          dataIndex: 'name',
          width: '240px',
          ellipsis: true,
          fixed: 'left'
        },
        {
          title: '合同类型',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' },
          width: '100px'
        },
        {
          title: '合同状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' },
          width: '100px'
        },
        {
          title: '合同编号',
          dataIndex: 'sn',
          scopedSlots: { customRender: 'sn' },
          width: '200px',
          ellipsis: true
        },
        {
          title: '物管合同号',
          dataIndex: 'pmSn',
          width: '200px',
          ellipsis: true
        },
        {
          title: '签订日期',
          dataIndex: 'signDate',
          sorter: true,
          width: '120px'
        },
        {
          title: '租赁开始期限',
          dataIndex: 'startDate',
          sorter: true,
          width: '140px'
        },
        {
          title: '租赁结束期限',
          dataIndex: 'endDate',
          sorter: true,
          width: '140px'
        },
        {
          title: '租赁方',
          dataIndex: 'lessorType',
          scopedSlots: { customRender: 'lessorType' },
          width: '100px'
        },
        {
          title: '客户名称',
          dataIndex: 'customerName',
          width: '200px',
          ellipsis: true
        },
        {
          title: '联系电话',
          dataIndex: 'phone',
          width: '120px'
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
        this.queryParam.status = 'APPROVED'
        return getCustomerContractList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:contract:add'),
      editEnabel: checkPermission('admin:contract:edit'),
      removeEnable: checkPermission('admin:contract:remove')
    }
  },
  filters: {
    lessorTypeFilter (lessorType) {
      const lessorTypeMap = {
        0: '公司租赁',
        1: '个人租赁'
      }
      return lessorTypeMap[lessorType]
    },
    typeFilter (type) {
      const typeMap = {
        'OFFICIAL': '正式合同',
        'INTENTION': '意向合同'
      }
      return typeMap[type]
    },
    statusFilter (type) {
      const statusMap = {
        'DRAFT': '草稿',
        'APPROVING': '待审批',
        'APPROVED': '已审批',
        'REFUSE': '已拒绝',
        'EXECUTING': '执行中',
        'VOIDED': '已作废',
        'OVERDUE': '已到期',
        'END': '已结束'
      }
      return statusMap[type]
    }
  },
  created () {
  },
  methods: {
    statusChange (e) {
      this.queryParam.status = e
    },
    modalclose () {
      this.selectedRowKeys = []
    },
    handleSubmit () {
      if (this.modaltype === 0) {
        this.commitContract()
      } else if (this.modaltype === 1) {
        this.delByIds()
      } else if (this.modaltype === 2) {
        this.approveContract()
      }
    },
    approvetypeChange (e) {
      this.approvetype = e
    },
    showModal (type, ids) {
      this.modaltype = type
      this.visible = true
      this.selectedRowKeys = ids
    },
    handleEdit (change, id) {
      this.$router.push({ name: 'customerContractEdit', query: { id: id, url: 'contractChange', change: 'change' } })
    },
    handleDetail (id) {
      this.$router.push({ name: 'customerContractDetailModal', query: { id: id, url: 'contractChange' } })
    },
    handleRefundEdit (id) {
      this.$router.push({ name: 'CustomerContractRefundEdit', query: { id: id, url: 'contractChange' } })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    // 作废合同
    cancelContract (ids) {
      customerContractCancel({ ids: ids.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success('操作已成功')
          this.visible = false
          this.handleOk()
        } else {
          this.$message.error(res.msg)
          this.visible = false
        }
        this.selectedRowKeys = []
      })
    }
  }
}
</script>
