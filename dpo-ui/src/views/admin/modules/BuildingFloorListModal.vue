<template>
  <a-modal
    title="楼层列表"
    style="top: 20px;"
    :width="1100"
    v-model="visible"
    :footer="null"
  >
    <div class="table-operator">
      <a-button type="primary" icon="plus" @click="$refs.modal.add(buildingId)">新建</a-button>
      <a-dropdown v-if="selectedRowKeys.length > 0">
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
    >
      <span slot="status" slot-scope="text">
        <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
      </span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit(record)">编辑</a>
        <a-divider type="vertical" />
        <a @click="delByIds([record.id])">删除</a>
      </span>
    </s-table>
    <building-floor-modal ref="modal" @ok="handleOk" />
  </a-modal>
</template>

<script>
import { STable } from '@/components'
import { getBuildingDetailList, delBuildingDetail } from '@/api/admin/buildingDetail'
import BuildingFloorModal from './BuildingFloorModal.vue'
import { checkPermission } from '@/utils/permissions'

const statusMap = {
  0: {
    status: 'success',
    text: '正常'
  },
  1: {
    status: 'default',
    text: '停用'
  }
}

export default {
  name: 'TableList',
  components: {
    STable,
    BuildingFloorModal
  },
  data () {
    return {
      visible: false,
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
      permissions: [],
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: { },
      // 表头
      columns: [
        {
          title: '楼层',
          dataIndex: 'floorNum'
        },
        {
          title: '楼层名称',
          dataIndex: 'floorName'
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          sorter: true
        },
        {
          title: '创建人',
          dataIndex: 'createBy'
        },
        {
          title: '操作',
          width: '150px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getBuildingDetailList(Object.assign(parameter, this.queryParam))
      },
      buildingId: '',
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:buildingDetail:add'),
      editEnabel: checkPermission('admin:buildingDetail:edit'),
      removeEnable: checkPermission('admin:buildingDetail:remove')
    }
  },
  filters: {
    statusFilter (type) {
      return statusMap[type].text
    },
    statusTypeFilter (type) {
      return statusMap[type].status
    }
  },
  created () {
  },
  methods: {
    show (buildingId) {
      this.visible = true
      this.queryParam.buildingId = buildingId
      this.buildingId = buildingId
      this.$refs.table && this.$refs.table.refresh(true)
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    handleEdit (record) {
      console.log('record', record)
      this.$refs.modal.edit(record)
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    delByIds (ids) {
      delBuildingDetail({ ids: ids.join(',') }).then(res => {
        if (res.code === 0) {
          this.$message.success(`删除成功`)
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
