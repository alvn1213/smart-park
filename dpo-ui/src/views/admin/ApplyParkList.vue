<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="入园企业名称">
              <a-input placeholder="请输入入园企业名称" v-model="queryParam.name"/>
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
       :scroll="{ x: 2600 }"
      :size="small"
      bordered
    >
      <div slot="name" slot-scope="text, record" style="cursor:pointer;color:#1890ff" @click="binNav(record)">
        <span>{{ text }}</span>
      </div>
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="type" slot-scope="text">
        {{ text | typeFilter }}
      </span>
      <span slot="isCompliance" slot-scope="text">
        {{ text | isComplianceFilter }}
      </span>
      <span slot="isRegister" slot-scope="text">
        {{ text | isRegisterFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="approveByOne(record)">审批</a>
        <a-divider type="vertical" />
        <a v-if="editEnabel" @click="cancel(record.id)">取消审批</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
      </span>
    </s-table>
    <apply-park-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getApplyParkList, delApplyPark, approveApplyPark, cancelApplyPark } from '@/api/admin/applyPark'
import { checkPermission } from '@/utils/permissions'
import ApplyParkModal from '../admin/modules/ApplyParkModal'

export default {
  name: 'TableList',
  components: {
    STable,
    ApplyParkModal
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
          title: '入园企业名称',
          dataIndex: 'name',
          scopedSlots: { customRender: 'name' },
          fixed: 'left',
          width: 250,
          ellipsis: true,
        },
        {
          title: '公司原办公地址',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' },
        },
        {
          title: '是否注册',
          dataIndex: 'isRegister',
          scopedSlots: { customRender: 'isRegister' }
        },
        {
          title: '法人代表',
          dataIndex: 'operName'
        },
        {
          title: '注册资本',
          dataIndex: 'registCapi'
        },
        {
          title: '公司类型(企业性质)',
          dataIndex: 'econKind'
        },
        {
          title: '姓名',
          dataIndex: 'userName'
        },
        {
          title: '职务',
          dataIndex: 'position'
        },
        {
          title: '联系电话',
          dataIndex: 'phone'
        },
        {
          title: '是否合法合规',
          dataIndex: 'isCompliance',
          scopedSlots: { customRender: 'isCompliance' }
        },
        {
          title: '期望入驻开始时间',
          dataIndex: 'startDate'
        },
        {
          title: '期望入驻结束时间',
          dataIndex: 'endDate'
        },
        {
          title: '申请办公面积',
          dataIndex: 'applyArea'
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
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getApplyParkList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:applyPark:add'),
      editEnabel: checkPermission('admin:applyPark:edit'),
      removeEnable: checkPermission('admin:applyPark:remove')
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
    },
    typeFilter (type) {
      const typeMap = {
        0: '总公司搬迁至设计城',
        1: '在园区开设分公司',
        2: '成立分公司',
        3: '其他'
      }
      return typeMap[type]
    },
    isComplianceFilter (isCompliance) {
      const isComplianceMap = {
        false: '否',
        true: '是'
      }
      return isComplianceMap[isCompliance]
    },
    isRegisterFilter (isRegister) {
      const isRegisterMap = {
        false: '否',
        true: '是'
      }
      return isRegisterMap[isRegister]
    }
  },
  created () {
  },
  methods: {
    binNav (e) {
      this.$router.push({ name: 'applyParkEdit', query: { id: e.id } })
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
      delApplyPark({ ids: ids.join(',') }).then(res => {
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
      approveApplyPark({ ids: ids, status: applyStatus }).then(res => {
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
      cancelApplyPark({ id: id }).then(res => {
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
