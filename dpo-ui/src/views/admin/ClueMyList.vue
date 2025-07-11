<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="信息搜索">
              <a-input placeholder="主题/客户/联系人/电话/创建人" v-model="queryParam.retrieval"/>
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
      showPagination="true"
      :columns="columns"
      :data="loadData"
      :scroll="{ x: true }"
      :size="small"
      bordered
    >
      <span slot="intentionState" slot-scope="text">
        {{ text | intentionStateType }}
      </span>
      <span slot="investigationMode" slot-scope="text">
        {{ text | investigationModeType }}
      </span>
      <span slot="source" slot-scope="text">
        {{ text | sourceType }}
      </span>
      <span slot="customerStatus" slot-scope="text">
        {{ text | statuType }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleView(record.clue.id)">详情</a>
      </span>
    </s-table>
    <clue-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getClueInvestigationMyList } from '@/api/admin/clueInvestigation'
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
          title: '跟进人',
          dataIndex: 'user.username'
        },
        {
          title: '跟进时间',
          dataIndex: 'investigationTime',
          sorter: true
        },
        {
          title: '意向水平',
          dataIndex: 'intentionState',
          scopedSlots: { customRender: 'intentionState' }
        },
        {
          title: '跟进方式',
          dataIndex: 'investigationMode',
          scopedSlots: { customRender: 'investigationMode' }
        },
        {
          title: '跟进记录',
          dataIndex: 'investigationRecord'
        },
        {
          title: '计划跟进时间',
          dataIndex: 'planTime',
          sorter: true
        },
        {
          title: '客户名称',
          dataIndex: 'clue.customerName'
        },
        {
          title: '主题',
          dataIndex: 'clue.clueName'
        },
        {
          title: '联系电话',
          dataIndex: 'clue.phone'
        },
        {
          title: '线索来源',
          dataIndex: 'clue.source',
          scopedSlots: { customRender: 'source' }
        },
        {
          title: '创建时间',
          dataIndex: 'clue.createTime',
          sorter: true
        },
        {
          title: '分派时间',
          dataIndex: 'clue.assignmentTime'
        },
        {
          title: '更新时间',
          dataIndex: 'clue.updateTime'
        },
        {
          title: '创建人',
          dataIndex: 'clue.createBy'
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
        return getClueInvestigationMyList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:investigation:add'),
      editEnabel: checkPermission('admin:investigation:edit'),
      removeEnable: checkPermission('admin:investigation:remove')
    }
  },
  filters: {
    intentionStateType (intentionState) {
      const typeMap = {
        0: '强烈',
        1: '一般',
        2: '无',
        3: '未知'
      }
      return typeMap[intentionState]
    },
    investigationModeType (investigationMode) {
      const typeMap = {
        0: '客户上门',
        1: '主动拜访',
        2: '客户来电',
        3: '主动电访',
        4: '短信',
        5: '微信',
        6: '邮件',
        7: '其他'
      }
      return typeMap[investigationMode]
    },
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
