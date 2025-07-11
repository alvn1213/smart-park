<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="房间名称">
              <a-input placeholder="请输入房间名称" v-model="queryParam.name"/>
            </a-form-item>
          </a-col>
          <a-col :md="5" :sm="15">
            <a-form-item label="所属楼宇">
              <a-input placeholder="请输入所属楼宇名称" v-model="queryParam.buildingName"/>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="15">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="租赁状态">
              <a-radio-group button-style="solid" @change="statusChange" v-model="queryParam.status">
                <a-radio-button value="NO">未租</a-radio-button>
                <a-radio-button value="YES">已租</a-radio-button>
              </a-radio-group>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="search">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
            </span>
          </a-col>
          <a-col :md="4" :sm="15">
            <a-radio-group button-style="solid" @change="roomStyleChange" v-model="roomStyle">
              <a-radio-button value="YES">列表模式</a-radio-button>
              <a-radio-button value="NO">房态模式</a-radio-button>
            </a-radio-group>
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
      v-if="roomStyle=='YES'"
      :scroll="{ x: true }"
      :size="small"
      bordered
    >
      <span slot="isMarketable" slot-scope="text, record">
        <a-switch :checked="record.isMarketable==true" @change="onChangeStatus(record)"/>
      </span>
      <span slot="status" slot-scope="text">
        {{ text | statusFilter }}
      </span>
      <span slot="rentType" slot-scope="text">
        {{ text | rentTypeFilter }}
      </span>
      <span slot="action" slot-scope="text, record">
        <a v-if="editEnabel" @click="handleEdit(record.id)">编辑</a>
        <a-divider type="vertical" />
        <a v-if="removeEnable" @click="delByIds([record.id])">删除</a>
      </span>
    </s-table>
    <div v-else>
      <div v-for="(item, index) in roomAllData" :key="index">
        <h2>{{ item.parkName }}</h2>
        <div v-for="(datas, indexs) in item.buildingVOList" :key="indexs">
          <div v-for="(list, keys) in datas.buildingDetailVOList" :key="keys">
            <div v-if="list.roomVOList.length > 0" class="roomtitle">{{ datas.buildingName }}{{ list.FloorName }} {{ list.floorNum }}楼</div>
            <a-list :grid="{ gutter: 16, column: 6 }" :data-source="list.roomVOList" v-if="list.roomVOList.length > 0">
              <a-list-item slot="renderItem" slot-scope="items" @click="handleEdit(items.roomId, 'NO')">
                <a-card class="roomcard" :style="{borderLeft:(items.status === 'NO' ? '2px solid red':'2px solid #03c683')}">
                  <div style="color: black;font-size: 14px">{{ items.roomName }}</div>
                  <div style="margin: 2px;">{{ items.area }}㎡ - ￥{{ items.rent }} {{ items.rentType | rentTypeFilter }}</div>
                  <div v-if="items.status === 'NO'">
                    <i>
                      <svg
                        t="1613978550873"
                        class="icon"
                        viewBox="0 0 1024 1024"
                        version="1.1"
                        xmlns="http://www.w3.org/2000/svg"
                        p-id="2457"
                        width="14"
                        height="14">
                        <path d="M512 962.56c-180.98176 0-327.68-146.69824-327.68-327.68 0-96.62464 32.01024-174.63296 96.01024-234.02496a40.96 40.96 0 0 1 68.3008 36.59776c-10.46528 64.36864 9.29792 96.5632 59.26912 96.5632 130.9696 0-142.52032-405.504 186.01984-472.576-81.92 139.96032 124.8256 299.6224 184.32 382.3616 38.66624 53.78048 61.44 119.76704 61.44 191.0784 0 180.98176-146.69824 327.68-327.68 327.68z m40.96-122.88a163.84 163.84 0 0 0 163.84-163.84 40.96 40.96 0 1 0-81.92 0 81.92 81.92 0 0 1-81.92 81.92 40.96 40.96 0 1 0 0 81.92z" p-id="2458" fill="#FB494D"></path>
                      </svg>
                    </i>
                    <span> 空置 </span>
                    <span style="color: #FB4246">{{ items.expireDate }} 天</span>
                  </div>
                </a-card>
              </a-list-item>
            </a-list>
          </div>
        </div>
        <a-divider />
      </div>
    </div>
    <park-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { getRoomList, delRoom, changMarketable, getRoomAll } from '@/api/admin/room'
import { checkPermission } from '@/utils/permissions'
import pick from 'lodash.pick'

export default {
  name: 'TableList',
  components: {
    STable
  },
  data: function () {
    return {
      roomAllData: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      roomStyle: 'YES',
      form: this.$form.createForm(this),
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
      {
          title: '所属楼宇',
          dataIndex: 'building.buildingName',
          fixed: 'left'
        },
        {
          title: '房间名称',
          dataIndex: 'name',
           fixed: 'left'
        },

        {
          title: '所属楼层',
          dataIndex: 'buildingDetail.floorName'
        },
        {
          title: '面积（平方米）',
          dataIndex: 'area'
        },
        {
          title: '收租面积（平方米）',
          dataIndex: 'rentArea'
        },
        {
          title: '公摊面积（平方米）',
          dataIndex: 'commonArea'
        },
        {
          title: '租赁状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '租金（元）',
          dataIndex: 'rent'
        },
        {
          title: '租金类型',
          dataIndex: 'rentType',
          scopedSlots: { customRender: 'rentType' }
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
          title: '所属园区',
          dataIndex: 'park.name',
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
        return getRoomList(Object.assign(parameter, this.queryParam))
      },
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:room:add'),
      editEnabel: checkPermission('admin:room:edit'),
      removeEnable: checkPermission('admin:room:remove')
    }
  },
  filters: {
    statusFilter (status) {
      const statusMap = {
        'NO': '未租',
        'YES': '已租'
      }
      return statusMap[status]
    },
    isMarketableFilter (isMarketable) {
      const isMarketableMap = {
        true: '是',
        false: '否'
      }
      return isMarketableMap[isMarketable]
    },
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
    if (this.$route.query.type) {
      this.roomStyle = 'NO'
      this.roomAllList()
    }
  },
  methods: {
    search () {
      if (this.roomStyle === 'YES') {
        this.$refs.table.refresh(true)
      } else {
        this.roomAllList()
      }
    },
    roomAllList () {
      getRoomAll(this.queryParam).then(res => {
        if (res.code === 0) {
          console.log(2)
          this.roomAllData = res.data
        }
      })
    },
    statusChange (e) {
      this.queryParam.status = e.target.value
      this.roomAllList()
      this.$refs.table.refresh(true)
    },
    roomStyleChange (e) {
      this.roomStyle = e.target.value
      if (e.target.value === 'NO') {
        this.roomAllList()
      } else {
        this.$router.push({ name: 'room' })
      }
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleAdd () {
      this.handleEdit()
    },
    handleEdit (id, type) {
      this.$router.push({ name: 'roomEdit', query: { id: id, type } })
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    onChangeStatus (record) {
      const _this = this
      console.log('record : ', record)
      const text = record.isMarketable ? '下架' : '上架'
      this.$confirm({
        title: '警告',
        content: '确认要' + text + '【房间' + record.name + '】' + '吗?',
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
      delRoom({ ids: ids.join(',') }).then(res => {
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
<style>
  .roomtitle{font-size: 16px;background:#FAFBFC;width: 100%;padding: 6px 10px;margin-top: 10px;margin-bottom:8px;color: #111}
  .roomcard{color:#777;cursor:pointer;height: 100px;border-radius: 12px;}
</style>
