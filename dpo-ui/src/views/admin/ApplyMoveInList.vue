<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="申请人">
              <a-input placeholder="请输入申请人" v-model="queryParam.applyName"/>
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
      showPagination="true"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      :columns="columns"
      :data="loadData"
      :scroll="{ x: 2000 }"
      :size="small"
      bordered
    >
      <div slot="companyName" slot-scope="text, record" style="cursor:pointer;color:#1890ff" @click="binNav(record)">
        <span>{{ text }}</span>
      </div>
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="type" slot-scope="text">
        {{ text | typeFilter }}
      </span>
      <span slot="relation" slot-scope="text">
        {{ text | relationFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="approveByOne(record)">审批</a>
        <a-divider type="vertical" />
        <a v-if="editEnabel" @click="cancel(record.id)">取消审批</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
      </span>
    </s-table>
    <apply-move-in-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getApplyMoveInList, delApplyMoveIn, approveApplyMoveIn, cancelApplyMoveIn } from '@/api/admin/applyMoveIn'
import { checkPermission } from '@/utils/permissions'
import ApplyMoveInModal from '@/views/admin/modules/ApplyMoveInModal'

export default {
  name: 'TableList',
  components: {
    ApplyMoveInModal,
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
          title: '拟成立/迁入企业名称',
          dataIndex: 'companyName',
          fixed: 'left',
          scopedSlots: { customRender: 'companyName' },
          width: 200,
        },
        {
          title: '申请业务',
          dataIndex: 'type',
          scopedSlots: { customRender: 'type' },
          width: 200,
          ellipsis: true,
        },
        {
          title: '申请人',
          dataIndex: 'applyName',
          width: 200,
          ellipsis: true,
        },
        {
          title: '承租地址',
          dataIndex: 'address',
          ellipsis: true,
        },
        {
          title: '租赁面积',
          dataIndex: 'area'
        },
        {
          title: '姓名',
          dataIndex: 'userName'
        },
        {
          title: '联系电话',
          dataIndex: 'phone'
        },
        {
          title: '物业合同编号',
          dataIndex: 'contractNo'
        },
        {
          title: '租赁开始时间',
          dataIndex: 'rentStartTime'
        },
        {
          title: '租赁结束时间',
          dataIndex: 'rentEndTime'
        },
        {
          title: '与申请人关系',
          dataIndex: 'relation',
          scopedSlots: { customRender: 'relation' }
        },
        {
          title: '审批状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '操作',
          width: '180px',
          dataIndex: 'action',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getApplyMoveInList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:applyMoveIn:add'),
      editEnabel: checkPermission('admin:applyMoveIn:edit'),
      removeEnable: checkPermission('admin:applyMoveIn:remove')
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
    relationFilter (relation) {
      const relationMap = {
        0: '子公司',
        1: '分公司',
        2: '其他'
      }
      return relationMap[relation]
    }
  },
  created () {
  },
  methods: {
    binNav (e) {
      this.$router.push({ name: 'applyMoveInEdit', query: { id: e.id } })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    approveByOne (record) {
      this.$refs.modal.edit(record)
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    delByIds (ids) {
      delApplyMoveIn({ ids: ids.join(',') }).then(res => {
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
      approveApplyMoveIn({ ids: ids, status: applyStatus }).then(res => {
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
      cancelApplyMoveIn({ id: id }).then(res => {
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
