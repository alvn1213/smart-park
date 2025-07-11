<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="服务名称">
              <a-input placeholder="请输入服务名称" v-model="queryParam.serviceName"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="供应商名称">
              <a-input placeholder="请输入供应商名称" v-model="queryParam.supplierName"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="菜单选择">
              <a-tree-select
                v-model="queryParam.menuId"
                :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                :treeData="menus"
                placeholder="请选择菜单"
                treeDefaultExpandAll
              ></a-tree-select>
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
      <a-button v-if="addEnable" type="primary" icon="plus" @click="handleAdd">新建</a-button>
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
    <serviceManage-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getServiceManageList, delServiceManage, changMarketable } from '@/api/admin/serviceManage'
import { checkPermission } from '@/utils/permissions'
import pick from 'lodash.pick'
import { getServiceMenuList } from '@/api/admin/serviceMenu'

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
      menus: [{ key: 0, value: '0', title: '请选择' }],
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '服务名称',
          dataIndex: 'serviceName'
        },
        {
          title: '所属菜单',
          dataIndex: 'serviceMenu.menuName'
        },
        {
          title: '供应商名称',
          dataIndex: 'serviceSupplier.supplierName'
        },
        {
          title: '上架时间',
          dataIndex: 'marketableTime',
          sorter: true
        },
        {
          title: '是否上架',
          dataIndex: 'isMarketable',
          scopedSlots: { customRender: 'isMarketable' }
        },
        {
          title: '申请次数',
          dataIndex: 'supplierId'
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
        return getServiceManageList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:ServiceManage:add'),
      editEnabel: checkPermission('admin:ServiceManage:edit'),
      removeEnable: checkPermission('admin:ServiceManage:remove')
    }
  },
  filters: {
  },
  created () {
    this.loadMenus()
  },
  methods: {
    loadMenus () {
      getServiceMenuList().then(res => {
        console.log(res.rows)
        this.buildtree(res.rows, this.menus, 0)
      })
    },
    buildtree (list, arr, parentId) {
      list.forEach(item => {
        if (item.parentId === parentId) {
          var child = {
            key: item.id,
            value: item.id + '',
            title: item.menuName,
            children: []
          }
          this.buildtree(list, child.children, item.id)
          arr.push(child)
        }
      })
    },
    onChangeStatus (record) {
      const _this = this
      console.log('record : ', record)
      const text = record.isMarketable === false ? '上架' : '下架'
      this.$confirm({
        title: '警告',
        content: '确认要' + text + '【' + record.serviceName + '】' + '吗?',
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
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd () {
      this.handleEdit()
    },
    handleEdit (id) {
      this.$router.push({ name: 'serviceManageEdit', query: { id: id } })
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    delByIds (ids) {
      delServiceManage({ ids: ids.join(',') }).then(res => {
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
