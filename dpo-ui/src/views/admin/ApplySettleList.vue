<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="入驻企业名称">
              <a-input placeholder="请输入入驻企业名称" v-model="queryParam.name"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="联系电话">
              <a-input placeholder="请输入联系电话" v-model="queryParam.phone"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="联系人">
              <a-input placeholder="请输入联系人" v-model="queryParam.contacts"/>
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
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-popconfirm
          cancelText="取消"
          okText="确定"
          title="确定批量审批通过吗?"
          @confirm="approve(selectedRowKeys.join(','), 'PASS')"
        ><a-button type="primary">一键通过</a-button>
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
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      :columns="columns"
      :data="loadData"
      showPagination="true"
      :size="small"
      bordered
    >
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <div slot="name" slot-scope="text, record" style="cursor:pointer;color:#1890ff" @click="binNav(record)">
        <span>{{ text }}</span>
      </div>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="approveByOne(record)">审批</a>
        <a-divider type="vertical" />
        <a v-if="editEnabel" @click="cancel(record.id)">取消审批</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
      </span>
    </s-table>
    <apply-settle-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getApplySettleList, delApplySettle, approveApplySettle, cancelApplySettle } from '@/api/admin/applySettle'
import { checkPermission } from '@/utils/permissions'
import ApplySettleModal from '@/views/admin/modules/ApplySettleModal'

export default {
  name: 'TableList',
  components: {
    ApplySettleModal,
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
          title: '入驻企业名称',
          dataIndex: 'name',
          scopedSlots: { customRender: 'name' }
        },
        {
          title: '所需办公面积',
          dataIndex: 'needArea'
        },
        {
          title: '申请入驻日期',
          dataIndex: 'startTime'
        },
        {
          title: '联系电话',
          dataIndex: 'phone'
        },
        {
          title: '姓名',
          dataIndex: 'userName'
        },
        {
          title: '审批状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          // fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getApplySettleList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:settle:add'),
      editEnabel: checkPermission('admin:settle:edit'),
      removeEnable: checkPermission('admin:settle:remove')
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        'APPROVING': '待审批',
        'PASS': '已审批',
        'REFUSE': '已拒绝'
      }
      return statusMap[status]
    }
  },
  created () {
  },
  methods: {
    binNav (e) {
      this.$router.push({ name: 'applySettleEdit', query: { id: e.id } })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd () {
      this.$refs.modal.add()
    },
    approveByOne (record) {
      this.$refs.modal.edit(record)
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    delByIds (ids) {
      delApplySettle({ ids: ids.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success('删除成功')
          this.handleOk()
        } else {
          this.$message.error(res.msg)
        }
        this.selectedRowKeys = []
      })
    },
    // 批量审批
    approve (ids, applyStatus) {
      approveApplySettle({ ids: ids, status: applyStatus }).then(res => {
        if (res.code === 0) {
          this.$message.success('操作已成功')
          this.$refs.table.refresh(true)
        } else {
          this.$message.error(res.msg)
        }
        this.selectedRowKeys = []
      })
    },
    // 批量审批
    cancel (id) {
      cancelApplySettle({ id: id }).then(res => {
        if (res.code === 0) {
          this.$message.success('操作已成功')
          this.$refs.table.refresh(true)
        } else {
          this.$message.error(res.msg)
        }
      })
    }
  }
}
</script>
