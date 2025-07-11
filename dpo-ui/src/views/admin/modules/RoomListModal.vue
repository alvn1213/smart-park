<template>
  <a-modal
    title="操作"
    style="top: 20px;"
    :width="1200"
    v-model="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
  >
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="15">
              <a-form-item label="房间名称">
                <a-input placeholder="请输入房间名称" v-model="queryParam.name"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="15">
              <a-form-item label="所属楼宇">
                <a-input placeholder="请输入所属楼宇名称" v-model="queryParam.buildingName"/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <span class="table-page-search-submitButtons">
                <a-button type="primary" @click="search">查询</a-button>
                <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>
      <a-table
        size="default"
        ref="table"
        :rowKey="record => record.id"
        showPagination="true"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :columns="columns"
        :data-source="loadData"
      >
        <span slot="rentType" slot-scope="text">
          {{ text | rentTypeFilter }}
        </span>
      </a-table>
    </a-card>
  </a-modal>
</template>
<script>
import { STable } from '@/components'
import { getRoomList } from '@/api/admin/room'

export default {
  name: 'RoomListModal',
  components: {
    STable
  },
  data: function () {
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
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '房间名称',
          dataIndex: 'name'
        },
        {
          title: '所属园区',
          dataIndex: 'park.name'
        },
        {
          title: '所属楼宇',
          dataIndex: 'building.buildingName'
        },
        {
          title: '所属楼层',
          dataIndex: 'buildingDetail.floorName'
        },
        {
          title: '出租面积（平方米）',
          dataIndex: 'rentArea'
        },
        {
          title: '租金类型',
          dataIndex: 'rentType',
          scopedSlots: { customRender: 'rentType' }
        },
        {
          title: '租金（元）',
          dataIndex: 'rent'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: {},
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  filters: {
    rentTypeFilter (rentType) {
      const rentTypeMap = {
        1: '元/㎡/天',
        2: '元/㎡/月',
        3: '元/天',
        4: '元/月'
      }
      return rentTypeMap[rentType]
    }
  },
  created () {
  },
  methods: {
    search () {
      this.queryParam.status = 'NO'
      getRoomList(this.queryParam).then(res => {
        this.loadData = res.rows
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
      this.$router.push({ name: 'roomEdit', query: { id: id } })
    },
    handleSubmit () {
      this.$parent.$parent.fatherMethod(this.selectedRows)
      this.visible = false
      this.selectedRows = []
    },
    show () {
      this.visible = true
      this.queryParam.status = 'NO'
      getRoomList(this.queryParam).then(res => {
        this.loadData = res.rows
      })
    }
  },
  watch: {

  }
}
</script>
