<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="园区名称">
              <a-input placeholder="请输入园区名称" v-model="queryParam.name"/>
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
      :size="small"
      bordered
    >
      <span slot="isMarketable" slot-scope="text, record">
        <a-switch :checked="record.isMarketable==true" @change="onChangeStatus(record)"/>
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleEdit(record.id)">编辑</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
      </span>
    </s-table>
    <park-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getParkList, delPark, changMarketable } from '@/api/admin/park'
import { checkPermission } from '@/utils/permissions'
import pick from 'lodash.pick'

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
          title: '园区名称',
          dataIndex: 'name'
        },
        {
          title: '上架时间',
          dataIndex: 'marketableTime'
        },
        {
          title: '是否上架',
          dataIndex: 'isMarketable',
          scopedSlots: { customRender: 'isMarketable' }
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
        return getParkList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:park:add'),
      editEnabel: checkPermission('admin:park:edit'),
      removeEnable: checkPermission('admin:park:remove')
    }
  },
  filters: {
    isMarketableFilter (isMarketable) {
      const isMarketableMap = {
        true: '是',
        false: '否'
      }
      return isMarketableMap[isMarketable]
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
    handleEdit (parkId) {
      this.$router.push({ name: 'parkEdit', query: { parkId: parkId } })
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    onChangeStatus (record) {
      const _this = this
      console.log('record : ', record)
      const text = record.isMarketable === false ? '上架' : '下架'
      this.$confirm({
        title: '警告',
        content: '确认要' + text + record.name + '吗?',
        okText: '是',
        okType: 'warning',
        cancelText: '否',
        onOk () {
          record.isMarketable = !record.isMarketable
          changMarketable(pick(record, 'id', 'isMarketable')).then(res => {
            if (res.code === 0) {
              _this.$message.success('操作成功')
              _this.$refs.table.refresh(true)
            } else {
              _this.$message.error(res.msg)
            }
          })
        },
        onCancel () {}
      })
    },
    delByIds (ids) {
      delPark({ ids: ids.join(',') }).then(res => {
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
