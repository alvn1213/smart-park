<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="合同名称">
              <a-input placeholder="请输入合同名称" v-model="queryParam.name"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="合同编号">
              <a-input placeholder="请输入合同编号" v-model="queryParam.sn"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.customerName"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="合同状态">
              <a-select v-model="queryParam.status" @change="statusChange" placeholder="请选择合同状态">
                <a-select-option value="DRAFT">草稿</a-select-option>
                <a-select-option value="APPROVING">待审批</a-select-option>
                <a-select-option value="APPROVED">已审批</a-select-option>
                <a-select-option value="REFUSE">已拒绝</a-select-option>
                <a-select-option value="VOIDED">已作废</a-select-option>
                <a-select-option value="OVERDUE">已到期</a-select-option>
                <a-select-option value="END">已结束</a-select-option>
              </a-select>
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
      <a-button v-if="addEnable" type="primary" icon="plus" @click="handleAdd()">新建</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-button type="primary" @click="showModal(0, selectedRowKeys)">提交</a-button>
      </a-dropdown>
      <a-dropdown v-if="removeEnable&&selectedRowKeys.length > 0">
        <a-button type="danger" icon="delete" @click="showModal(1, selectedRowKeys)">删除</a-button>
      </a-dropdown>
    </div>
    <s-table
      size="default"
      ref="table"
      rowKey="id"
      showPagination="true"
      :pagination="pagination"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, getCheckboxProps: getCheckboxProps}"
      :columns="columns"
      :data="loadData"
      :scroll="{x:130}"
      bordered
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
        <a v-if="record.status === 'APPROVED'" @click="billEdit(record)">生成账单</a>
        <div v-if="record.status === 'DRAFT' || record.status === 'REFUSE'">
          <a v-if="editEnabel" @click="handleEdit(record.id)">编辑</a>
          <a-divider type="vertical" />
          <a @click="commitContract([record.id])">提交</a>
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
        </div>
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
  delCustomerContract,
  customerContractCommit,
  customerContractApprove,
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
          width: '220px',
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
          width: '140px'
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },
          fixed: 'right'
        }
      ],
      // 分页对象
       pagination: {
        current: 1, // 当前页码
        pageSize: 50, // 每页显示条数
        total: 0, // 总条数，后端返回
        showSizeChanger: true, // 是否允许改变每页显示条数
        showQuickJumper: true, // 是否显示跳转到某页的功能
      },
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
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
    billEdit (e) {
      this.$router.push({ name: 'customerContractBillEdit', query: { id: e.id, url: 'bill' } })
    },
    getCheckboxProps (record) {
      return ({
        props: {
          disabled: !(record.status === 'DRAFT' || record.status === 'REFUSE')
        }
      })
    },
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
    handleAdd () {
      this.handleEdit()
    },
    handleEdit (id) {
      this.$router.push({ name: 'customerContractEdit', query: { id: id, url: 'contract' } })
    },
    handleDetail (id) {
      this.$router.push({ name: 'customerContractDetailModal', query: { id: id, url: 'contract' } })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleOk () {
      this.$refs.table.refresh()
      console.log('handleSaveOk')
    },
    // 删除合同
    delByIds (ids) {
      delCustomerContract({ ids: ids ? ids.join(',') : this.selectedRowKeys.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error(res.msg)
          this.visible = false
        }
        this.selectedRowKeys = []
      })
    },
    // 提交合同
    commitContract (ids) {
      customerContractCommit({ ids: ids ? ids.join(',') : this.selectedRowKeys.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success('提交成功')
          this.visible = false
          this.handleOk()
        } else {
          this.$message.error(res.msg)
          this.visible = false
        }
        this.selectedRowKeys = []
      })
    },
    // 审批合同
    approveContract () {
      customerContractApprove({
        ids: this.selectedRowKeys.join(','),
        status: this.approvetype,
        remark: this.remark
      }).then(res => {
        if (res.code === 0) {
          this.$message.success('提交审批成功')
          this.visible = false
          this.handleOk()
        } else {
          this.$message.error(res.msg)
          this.visible = false
        }
        this.selectedRowKeys = []
      })
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
