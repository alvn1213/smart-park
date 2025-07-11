<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="意向名称">
              <a-input placeholder="请输入合同名称" v-model="queryParam.name"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="意向编号">
              <a-input placeholder="请输入合同编号" v-model="queryParam.sn"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="客户名称">
              <a-input placeholder="请输入客户名称" v-model="queryParam.customerName"/>
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
      :pagination="pagination"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      :columns="columns"
      :data="loadData"
      :size="small"
      bordered
    >
      <span slot="type" slot-scope="text">
        {{ text | typeFilter }}
      </span>
      <span slot="lessorType" slot-scope="text">
        {{ text | lessorTypeFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleEdit(record.id)">编辑</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
        <a-divider type="vertical" />
        <a @click="handleDetail(record.id)">详情</a>
      </span>
    </s-table>
    <customerContract-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getCustomerIntententionContractList, delCustomerContract } from '@/api/admin/customerIntentionContract'
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
          title: '意向名称',
          dataIndex: 'name'
        },
        {
          title: '意向类型',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '意向编号',
          dataIndex: 'manageSn'
        },
        {
          title: '签订日期',
          dataIndex: 'signDate',
          sorter: true
        },
        {
          title: '租赁开始期限',
          dataIndex: 'startDate',
          sorter: true
        },
        {
          title: '租赁结束期限',
          dataIndex: 'endDate',
          sorter: true
        },
        {
          title: '租赁方',
          dataIndex: 'lessorType',
          scopedSlots: { customRender: 'lessorType' }
        },
        {
          title: '客户名称',
          dataIndex: 'customerName'
        },
        {
          title: '意向金(元)',
          dataIndex: 'intentionMoney'
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
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
        return getCustomerIntententionContractList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:intentionContract:add'),
      editEnabel: checkPermission('admin:intentionContract:edit'),
      removeEnable: checkPermission('admin:intentionContract:remove')
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
    }
  },
  created () {
  },
  methods: {
    handleAdd () {
      this.handleEdit()
    },
    handleEdit (id) {
      this.$router.push({ name: 'CustomerIntentionContractEdit', query: { id: id, url: 'intentionContract' } })
    },
    handleDetail (id) {
      this.$router.push({ name: 'CustomerIntentionDetailModal', query: { id: id, url: 'intentionContract' } })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    delByIds (ids) {
      delCustomerContract({ ids: ids.join(',') }).then(res => {
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
