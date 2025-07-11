<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.name"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="联系人">
              <a-input placeholder="请输入联系人" v-model="queryParam.contacts"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="联系电话">
              <a-input placeholder="请输入联系电话" v-model="queryParam.phone"/>
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
      <a-button v-if="addEnable" type="primary" icon="plus" @click="handleAdd()">新建</a-button>
      <a-dropdown v-if="removeEnable&&selectedRowKeys.length > 0">
        <a-button type="danger" icon="delete" @click="delByIds(selectedRowKeys)">删除</a-button>
      </a-dropdown>
    </div>
    <s-table
      size="default"
      ref="table"
      rowKey="id"
      showPagination="true"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      :columns="columns"
      :data="loadData"
      :scroll="{ x: true }"
      :size="small"
      bordered
    >
      <span slot="type" slot-scope="text">
        {{ text | typeFilter }}
      </span>
      <span slot="customerStatus" slot-scope="text">
        {{ text | customerStatusFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleEdit(record.id)">编辑</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
      </span>
    </s-table>
    <customer-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getCustomerList, delCustomer } from '@/api/admin/customer'
import { checkPermission } from '@/utils/permissions'

export default {
  name: 'TableList',
  components: {
    STable
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
          title: '所属园区',
          dataIndex: 'park.name',
          fixed: 'left'
        },
        {
          title: '客户名称',
          dataIndex: 'name',
          fixed: 'left'
        },
        {
          title: '客户类型',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '客户状态',
          dataIndex: 'customerStatus',
          scopedSlots: { customRender: 'customerStatus' }
        },
        {
          title: '所属行业',
          dataIndex: 'sector'
        },
        {
          title: '联系人',
          dataIndex: 'contacts'
        },
        {
          title: '联系电话',
          dataIndex: 'phone'
        },
        {
          title: '邮箱',
          dataIndex: 'email'
        },
        {
          title: '统一信用代码',
          dataIndex: 'creditNo'
        },
        {
          title: '工商注册号',
          dataIndex: 'regNo'
        },
        {
          title: '经营状态',
          dataIndex: 'status'
        },
        {
          title: '组织机构代码',
          dataIndex: 'orgNo'
        },
        {
          title: '法人名',
          dataIndex: 'operName'
        },
        {
          title: '公司类型(企业性质)',
          dataIndex: 'econKind'
        },
        {
          title: '成立日期',
          dataIndex: 'startDate',
          sorter: true
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getCustomerList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:customer:add'),
      editEnabel: checkPermission('admin:customer:edit'),
      removeEnable: checkPermission('admin:customer:remove')
    }
  },
  filters: {
    typeFilter (type) {
      const typeMap = {
        'COMPANY': '公司',
        'PERSON': '个人'
      }
      return typeMap[type]
    },
    customerStatusFilter (customerStatus) {
      const customerStatusMap = {
        'POTENTIAL': '潜在客户',
        'PURPOSE': '意向客户',
        'DEAL': '成交客户',
        'LOSE': '流失客户'
      }
      return customerStatusMap[customerStatus]
    }
  },
  created () {
  },
  methods: {
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd () {
      this.handleEdit()
    },
    handleEdit (customerId) {
      this.$router.push({ name: 'customerEdit', query: { customerId: customerId } })
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    delByIds (ids) {
      delCustomer({ ids: ids.join(',') }).then(res => {
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

<style scoped>
  .ant-table td {
    white-space: nowrap;
  }
</style>
