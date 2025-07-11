<template>
  <a-card :bordered="false">
    <a-tabs default-active-key="1" @change="callback">
      <a-tab-pane key="1" tab="合同信息">
        <div>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin-bottom:20px;color:#A5AAB7;font-size:16px;">主要信息</a-col>
            <a-col :span="6">
              <span>合同编号：</span>
              <span>{{ detailList.sn? detailList.sn:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>意向编号：</span>
              <span>{{ detailList.manageSn? detailList.manageSn:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>合同名称：</span>
              <span>{{ detailList.name? detailList.name:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>意向状态：</span>
              <span v-if="detailList.status === 'DRAFT'">草稿</span>
              <span v-if="detailList.status === 'APPROVING'">审批中</span>
              <span v-if="detailList.status === 'APPROVED'">已审批</span>
              <span v-if="detailList.status === 'REFUSE'">已拒绝</span>
              <span v-if="detailList.status === 'EXECUTE'">执行中</span>
              <span v-if="detailList.status === 'VOIDED'">已作废</span>
              <span v-if="detailList.status === 'EXECUTING'">执行中</span>
              <span v-if="detailList.status === 'OVERDUE'">已到期</span>
              <span v-if="detailList.status === 'END'">已结束</span>
            </a-col>
            <a-col :span="6">
              <span>意向时间：</span>
              <span>{{ detailList.needStartDate }}-{{ detailList.needEndDate }}</span>
            </a-col>
            <a-col :span="6">
              <span>租赁期限：</span>
              <span>{{ detailList.startDate }}-{{ detailList.endDate }}</span>
            </a-col>
            <a-col :span="6">
              <span>签订日期：</span>
              <span>{{ detailList.signDate? detailList.signDate:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>合同类型：</span>
              <span v-if="detailList.type === 'OFFICIAL'" >正式合同</span>
              <span v-if="detailList.type === 'INTENTION'" >意向合同</span>
            </a-col>
            <a-col :span="6">
              <span>意向金：</span>
              <span>{{ detailList.intentionMoney? detailList.intentionMoney:'--' }}元</span>
            </a-col>
            <a-col :span="6">
              <span>经办人：</span>
              <span>{{ detailList.createBy? detailList.createBy:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>合同版本：</span>
              <span>{{ detailList.version? detailList.version:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>备注：</span>
              <span>{{ detailList.remark? detailList.remark:'--' }}</span>
            </a-col>
          </a-row>
          <a-divider orientation="left"></a-divider>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin-bottom:20px;color:#A5AAB7;font-size:16px;">乙方</a-col>
            <a-col :span="24">
              <span>客户名称：</span>
              <span>{{ detailList.customerName? detailList.customerName:'--' }}</span>
            </a-col>
            <a-col :span="12">
              <span>租赁方：</span>
              <span>{{ detailList.lessorType === 0? '公司租赁':'个人租赁' }}</span>
            </a-col>
            <a-col :span="12">
              <span>所属行业：</span>
              <span>{{ detailList.sector? detailList.sector:'--' }}</span>
            </a-col>
            <a-col :span="12">
              <span>纳税人识别号：</span>
              <span>{{ detailList.creditNo? detailList.creditNo:'--' }}</span>
            </a-col>
            <a-col :span="12">
              <span>公司邮箱：</span>
              <span>{{ detailList.email? detailList.email:'--' }}</span>
            </a-col>
            <a-col :span="12">
              <span>法人：</span>
              <span>{{ detailList.operName? detailList.operName:'--' }}</span>
            </a-col>
            <a-col :span="12">
              <span>联系电话：</span>
              <span>{{ detailList.phone? detailList.phone:'--' }}</span>
            </a-col>
            <a-col :span="12">
              <span>渠道名称：</span>
              <span>{{ detailList.channelName? detailList.channelName:'--' }}</span>
            </a-col>
          </a-row>
          <a-divider orientation="left"></a-divider>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin-bottom:20px;color:#A5AAB7;font-size:16px;">租赁场地</a-col>
            <a-col :span="24">
              <a-table
                :key="id"
                :pagination="false"
                :columns="columns"
                rowKey="pid"
                :dataSource="customerContractRooms"
              >
              </a-table>
            </a-col>
          </a-row>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin:20px 0;color:#A5AAB7;font-size:16px;">固定租金</a-col>
            <a-col :span="8">
              <span>租金单价：</span>
              <span>{{ detailList.rentPrice? detailList.rentPrice:'--' }}{{ detailList.rentPriceUnit === '0'? '/元/平米/天':'/元/平米/月' }}</span>
            </a-col>
            <a-col :span="8">
              <span>日租金：</span>
              <span>{{ detailList.rentIncreaseDay? detailList.rentIncreaseDay:'--' }}元</span>
            </a-col>
            <a-col :span="8">
              <span>月租金：</span>
              <span>{{ detailList.rentIncreaseMonth? detailList.rentIncreaseMonth:'--' }}元</span>
            </a-col>
          </a-row>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin:20px 0;color:#A5AAB7;font-size:16px;">押金</a-col>
            <a-col :span="8">
              <span>金额：</span>
              <span>{{ detailList.deposit? detailList.deposit:'--' }}元</span>
            </a-col>
          </a-row>
        </div>
      </a-tab-pane>
      <a-tab-pane key="2" tab="账单信息" force-render>
        <a-row style="width:80%;margin: 0 auto;">
          <a-col :span="24" style="margin:20px 0;color:#A5AAB7;font-size:16px;">付款计划</a-col>
          <a-col :span="24">
            <a-table
              :columns="customer"
              rowKey="pid"
              :dataSource="customerContractBills"
            >
              <template slot="status" slot-scope="text">
                <div v-if="text === 'UNRECEIVED'">未收</div>
                <div v-if="text === 'RECEIVED'">已收</div>
                <div v-if="text === 'PART'">部分</div>
              </template>
            </a-table>
          </a-col>
        </a-row>
      </a-tab-pane>
    </a-tabs>
    <div style="width:100%;text-align:center;margin-top: 15px">
      <!--<a-button v-if="contractStatus" @click="toVoid(detailList.id)" type="primary" style="margin-left: 8px">作废</a-button>-->
      <a-button v-if="contractStatus" type="primary" @click="handleEdit(detailList.id)" style="margin-left: 8px">转成合同</a-button>
      <a-button key="buy" @click="navBack" style="margin-left: 8px">返回</a-button>
    </div>
  </a-card>
</template>

<script>
import { getCustomerContract, customerUpdateStatus } from '@/api/admin/customerIntentionContract'

export default {
  name: 'TableList',
  components: {},
  data () {
    return {
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
          title: '收租面积（平方米）',
          dataIndex: 'rentArea'
        }
      ],
      customer: [
        {
          title: '账单编号',
          dataIndex: 'sn'
        },
        {
          title: '账单开始时间',
          dataIndex: 'billStartDate'
        },
        {
          title: '账单结束时间',
          dataIndex: 'billEndDate'
        },
        {
          title: '账单收款日',
          dataIndex: 'receiveRentDate'
        },
        {
          title: '租金',
          dataIndex: 'rent'
        },
        {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        }
      ],
      detailList: {},
      contractStatus: true,
      customerContractRooms: []
    }
  },
  created () {
    getCustomerContract({ id: this.$route.query.id }).then(res => {
      const arr = []
      res.customerContractRooms.forEach((item) => {
        arr.push(item)
      })
      this.detailList = res
      this.customerContractRooms = arr
      this.customerContractBills = res.customerContractBills
      this.voidContract()
    })
  },
  methods: {
    voidContract () {
      if (this.detailList.status === 'VOIDED') {
        this.contractStatus = false
      }
    },
    toVoid () {
      customerUpdateStatus({ id: this.$route.query.id }).then(res => {
        if (res.code === 0) {
          this.$message.success('作废成功')
          this.navBack()
        }
      })
    },
    handleEdit (id) {
      this.$router.push({ name: 'customerContractEdit', query: { id: id, url: 'intentionContract' } })
    },
    navBack () {
      this.$router.push({ name: this.$route.query.url })
    }
  }
}
</script>

<style>
  .ant-col{
    margin-bottom: 10px;
  }
</style>
