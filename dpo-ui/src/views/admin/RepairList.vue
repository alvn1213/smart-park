<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="报修单号">
              <a-input placeholder="请输入报修单号" v-model="queryParam.sn"/>
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
    <s-table
      size="default"
      ref="table"
      rowKey="id"
      :columns="columns"
      :data="loadData"
    >
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleView(record.id)">详情</a>
        <a-divider type="vertical" />
        <a v-if="editEnabel" @click="handleEdit(record)">指派</a>
        <a-divider type="vertical" />
        <a v-if="editEnabel" @click="handleComplete(record)">完成</a>
      </span>
    </s-table>
    <repair-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getRepairList, complete } from '@/api/admin/repair'
import RepairModal from './modules/RepairModal.vue'
import { checkPermission } from '@/utils/permissions'

export default {
  name: 'TableList',
  components: {
    STable,
    RepairModal
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
          title: '维修单号',
          dataIndex: 'sn'
        },
        {
          title: '报修园区',
          dataIndex: 'park.name'
        },
        {
          title: '报修人',
          dataIndex: 'name'
        },
        {
          title: '报修人手机',
          dataIndex: 'phone'
        },
        {
          title: '维修工人',
          dataIndex: 'user.userName'
        },
        {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '报修时间',
          dataIndex: 'repairTime',
          sorter: true
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getRepairList(Object.assign(parameter, this.queryParam))
      },
      addEnable: checkPermission('admin:repair:add'),
      editEnabel: checkPermission('admin:repair:edit'),
      removeEnable: checkPermission('admin:repair:list')
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        'PENDING_ASSIGN': '待分配',
        'PENDING_PROCESS': '待处理',
        'COMPLETED': '已完成',
        'CANCELED': '已取消',
        'SCORE': '已评价'
      }
      return statusMap[status]
    }
  },
  created () {
  },
  methods: {
    handleAdd () {
      this.$refs.modal.add()
    },
    handleView (repairId) {
      this.$router.push({ name: 'repairView', query: { repairId: repairId } })
    },
    handleEdit (record) {
      this.$refs.modal.edit(record)
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    handleComplete (id) {
      const _this = this
      this.$confirm({
        title: '警告',
        content: '确认要完成工单吗?',
        okText: '是',
        okType: 'warning',
        cancelText: '否',
        onOk () {
          complete(id).then(res => {
            if (res.code === 0) {
              _this.$message.success('操作成功')
              _this.$refs.table.refresh(true)
            } else {
              _this.$message.error(res.msg)
            }
          }).catch(() => {
            this.$message.error('系统错误，请稍后再试')
          })
        },
        onCancel () {}
      })
    }
  },
  watch: {
  }
}
</script>
