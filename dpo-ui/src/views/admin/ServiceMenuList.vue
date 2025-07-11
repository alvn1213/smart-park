<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="菜单名称">
              <a-input placeholder="请输入菜单名称" v-model="queryParam.menuName"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="this.fetch">查询</a-button>
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
    <a-table
      ref="table"
      rowKey="id"
      :pagination="pagination"
      :loading="loading"
      :columns="columns"
      :dataSource="data"
    >
      <span slot="isMarketable" slot-scope="text, record">
        <a-switch :checked="record.isMarketable==true" @change="onChangeStatus(record)"/>
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleEdit(record.id)">编辑</a>
        <a-divider type="vertical" />
        <a v-if="addEnable" @click="handleAdd(record.id)">新增</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
      </span>
    </a-table>
    <djBanner-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { Table as T } from 'ant-design-vue'
import { getServiceMenuList, delServiceMenu, changMarketable } from '@/api/admin/serviceMenu'
import { checkPermission } from '@/utils/permissions'
import pick from 'lodash.pick'
import { treeData } from '@/utils/treeutil'

export default {
  name: 'TableList',
  components: {
    T
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
          title: '菜单名称',
          dataIndex: 'menuName'
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
      loading: false,
      data: [],
      selectedRowKeys: [],
      selectedRows: [],
      pagination: false,
      addEnable: checkPermission('admin:ServiceMenu:add'),
      editEnabel: checkPermission('admin:ServiceMenu:edit'),
      removeEnable: checkPermission('admin:ServiceMenu:remove')
    }
  },
  filters: {
  },
  created () {
    this.fetch()
  },
  methods: {
    onChangeStatus (record) {
      const _this = this
      console.log('record : ', record)
      const text = record.isMarketable === false ? '上架' : '下架'
      this.$confirm({
        title: '警告',
        content: '确认要' + text + '【' + record.menuName + '】' + '吗?',
        okText: '是',
        okType: 'warning',
        cancelText: '否',
        onOk () {
          record.isMarketable = !record.isMarketable
          changMarketable(pick(record, 'id', 'isMarketable')).then(res => {
            if (res.code === 0) {
              _this.$message.success('操作成功')
              _this.handleOk()
            } else {
              _this.$message.error(res.msg)
            }
          })
        },
        onCancel () {}
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd (parentId) {
      this.handleEdit(null, parentId)
    },
    handleEdit (id, parentId) {
      let path = ''
      if (id) {
        path = '/admin/service/ServiceMenuEdit?id=' + id
      } else if (parentId) {
        path = '/admin/service/ServiceMenuEdit?parentId=' + parentId
      } else {
        path = '/admin/service/ServiceMenuEdit'
      }
      this.$router.push({ path: path })
    },
    handleOk () {
      // this.$refs.table.refresh(true)
      this.fetch()
      console.log('handleSaveOk')
    },
    fetch () {
      this.loading = true
      getServiceMenuList(Object.assign(this.queryParam)).then(res => {
        console.log(res.rows)
        this.data = treeData(res.rows, 'id')
        this.loading = false
      })
    },
    delByIds (ids) {
      delServiceMenu({ ids: ids.join(',') }).then(res => {
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
