<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="信息搜索">
              <a-input placeholder="主题/客户/联系人/电话/创建人" v-model="queryParam.clueName"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="客户状态">
              <a-tree-select
                v-model="queryParam.customerStatus"
                :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                :treeData="state"
                placeholder="请选择"
                treeDefaultExpandAll
              ></a-tree-select>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="线索来源">
              <a-tree-select
                v-model="queryParam.source"
                :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
                :treeData="menus"
                placeholder="请选择"
                treeDefaultExpandAll
              ></a-tree-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button type="primary" style="margin-left: 8px"><a @click="parameter =>excelClue(parameter)">线索导出</a></a-button>
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
      showPagination="true"
      :columns="columns"
      :data="loadData"
      :scroll="{ x: true }"
      :size="small"
      bordered
    >
      <span slot="source" slot-scope="text">
        {{ text | sourceType }}
      </span>
      <span slot="customerStatus" slot-scope="text">
        {{ text | statuType }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleView(record.id)">详情</a>
      </span>
    </s-table>
    <clue-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getClueList, delClue, excelClue } from '@/api/admin/clue'
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
      // 下拉框赋值
      menus: [
        { key: 0, value: '0', title: '现场接待' },
        { key: 1, value: '1', title: '主动电访' },
        { key: 2, value: '2', title: '邮件' },
        { key: 3, value: '3', title: '客户来电' },
        { key: 4, value: '4', title: '短信' },
        { key: 5, value: '5', title: '上门拜访' },
        { key: 6, value: '6', title: '小程序申请' }],
      state: [
        { key: 0, value: '0', title: '激活' },
        { key: 1, value: '1', title: '已经关闭' },
        { key: 2, value: '2', title: '已经转客户' }
      ],
      // 表头
      columns: [
        {
          title: '客户名称',
          dataIndex: 'customerName',
          fixed: 'left',
        },
        {
          title: '主题',
          dataIndex: 'clueName'
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
          title: '线索来源',
          dataIndex: 'source',
          scopedSlots: { customRender: 'source' }
        },
        {
          title: '创建时间',
          dataIndex: 'createTime',
          sorter: true
        },
        {
          title: '更新时间',
          dataIndex: 'updateTime',
          sorter: true
        },
        {
          title: '创建人',
          dataIndex: 'createBy'
        },
        {
          title: '对接人',
          dataIndex: 'user.username'
        },
        {
          title: '状态',
          dataIndex: 'customerStatus',
          scopedSlots: { customRender: 'customerStatus' }
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
        return getClueList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:clue:add'),
      editEnabel: checkPermission('admin:clue:edit'),
      removeEnable: checkPermission('admin:clue:remove')
    }
  },
  filters: {
    sourceType (source) {
      const typeMap = {
        0: '现场接待',
        1: '主动电访',
        2: '邮件',
        3: '客户来电',
        4: '短信',
        5: '上门拜访',
        6: '小程序申请'
      }
      return typeMap[source]
    },
    statuType (customerStatus) {
      const typeMap = {
        0: '激活',
        1: '已经关闭',
        2: '已转客户'
      }
      return typeMap[customerStatus]
    }
  },
  created () {
  },
  methods: {
    excelClue (parameter) {
      excelClue(Object.assign(parameter, this.queryParam)).then(response => {
        this.exportPowerWaterFile(response)
      })
    },
    exportPowerWaterFile (data) {
      var link = document.createElement('a')
      // 文件导出
      if (!data) {
        return
      }
      link.style.display = 'none'
      link.href = window.URL.createObjectURL(new Blob([data]))
      // var month = moment(this.queryParam.queryMonth).format('YYYY年MM月')
      link.setAttribute('download', '招商线索账单.xls')
      document.body.appendChild(link)
      link.click()
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd () {
      this.handleEdit()
    },
    handleEdit (id) {
      this.$router.push({ name: 'ClueEdit', query: { id: id } })
    },
    handleView (id) {
      this.$router.push({ name: 'ClueView', query: { id: id } })
    },
    handleOk () {
      this.$refs.table.refresh(true)
    },
    delByIds (ids) {
      delClue({ ids: ids.join(',') }).then(res => {
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
