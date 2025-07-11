<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="申请人">
              <a-input placeholder="请输入申请人" v-model="queryParam.userName"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="手机号">
              <a-input placeholder="请输入手机号" v-model="queryParam.phone"/>
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
    </s-table>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getActivityDetailList, delActivityDetail } from '@/api/admin/activityDetail'
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
      // 表头
      columns: [
        {
          title: '活动名称',
          dataIndex: 'activity.name'
        },
        {
          title: '申请人',
          dataIndex: 'userName'
        },
        {
          title: '手机号',
          dataIndex: 'phone'
        },
        {
          title: '报名时间',
          dataIndex: 'signDate',
          sorter: true
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getActivityDetailList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:activityDetail:add'),
      editEnabel: checkPermission('admin:activityDetail:edit'),
      removeEnable: checkPermission('admin:activityDetail:remove')
    }
  },
  filters: {
  },
  created () {
  },
  methods: {
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    delByIds (ids) {
      delActivityDetail({ ids: ids.join(',') }).then(res => {
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
