<template>
  <a-card :bordered="false">
    <a-tabs default-active-key="1" @change="callback">
      <a-tab-pane key="1" tab="退租合同">
        <div>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin-bottom:20px;color:#A5AAB7;font-size:16px;">基本信息</a-col>
            <a-col :span="6">
              <span>退租日期：</span>
              <span>{{ detailList.refundDate? detailList.refundDate:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>合同编号：</span>
              <span>{{ detailList.customerContract.sn? detailList.customerContract.sn:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>管理编号：</span>
              <span>{{ detailList.customerContract.manageSn? detailList.customerContract.manageSn:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>退租单号：</span>
              <span>{{ detailList.sn? detailList.sn:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>合同名称：</span>
              <span>{{ detailList.customerContract.name? detailList.customerContract.name:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>租户名称：</span>
              <span>{{ detailList.customerContract.customerName? detailList.customerContract.customerName:'--' }}</span>
            </a-col>
            <a-col :span="6">
              <span>退租状态：</span>
              <span v-if="detailList.status === false">待结算</span>
              <span v-if="detailList.status === true">已完成</span>
            </a-col>
          </a-row>
          <a-divider orientation="left"></a-divider>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin-bottom:20px;color:#A5AAB7;font-size:16px;">退租场地</a-col>
            <a-col :span="24">
              <a-table
                :key="id"
                :pagination="false"
                :columns="columns"
                rowKey="pid"
                :dataSource="formData.customerContractRefundRooms"
              >
              </a-table>
            </a-col>
          </a-row>
          <a-divider orientation="left"></a-divider>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin:20px 0;color:#A5AAB7;font-size:16px;">费用详细</a-col>
            <a-col :span="6">
              <span>应收部分：</span>
              <span>{{ detailList.receiveFee? detailList.receiveFee:'0' }}元</span>
            </a-col>
            <a-col :span="6">
              <span>应退部分：</span>
              <span>{{ detailList.refundFee? detailList.refundFee:'0' }}元</span>
            </a-col>
            <a-col :span="6">
              <span>总计金额：</span>
              <span>{{ detailList.totalFee? detailList.totalFee:'0' }}元</span>
            </a-col>
          </a-row>
          <a-divider orientation="left"></a-divider>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin-bottom:20px;color:#A5AAB7;font-size:16px;">其他信息</a-col>
            <a-col :span="6">
              <span>退租原因：</span>
              <span v-if="detailList.refundReason === 'NORMAL'">正常到期</span>
              <span v-if="detailList.refundReason === 'CONSULT'">协商提前退</span>
              <span v-if="detailList.refundReason === 'VIOLATION'">违约退租</span>
              <span v-if="detailList.refundReason === 'OTHER'">其他</span>
            </a-col>
            <a-col :span="6">
              <span>备注信息：</span>
              <span>{{ detailList.remark? detailList.remark:'--' }}</span>
            </a-col>
          </a-row>
          <a-divider orientation="left"></a-divider>
          <a-row style="width:80%;margin: 0 auto;">
            <a-col :span="24" style="margin-bottom:20px;color:#A5AAB7;font-size:16px;">存档图片</a-col>
            <a-col :span="24">
              <div>
                <img v-for="(item, index) in detailList.archiveImageList" :key="index" style="width:150px;heigth:150px;cursor:pointer;margin-right: 20px;" :src="imgheard + item.name" @click="handlePreview(imgheard + item.name)"/>
              </div>
            </a-col>
          </a-row>
        </div>
      </a-tab-pane>
    </a-tabs>
    <a-modal :visible="previewVisible" :footer="null" @cancel="previewCancel">
      <img style="width: 100%" :src="previewImage" />
    </a-modal>
    <div style="width:100%;text-align:center;margin-top: 15px">
      <a-button style="margin-left: 8px" @click="approval([detailList.customerContract.id])" type="primary" v-if="detailList.status === false" >确定退租</a-button>
      <a-button key="buy" @click="navBack" style="margin-left: 8px">返回</a-button>
    </div>
  </a-card>
</template>

<script>
import { getCustomerContractRefund, customerContractCancel } from '@/api/admin/customerContractRefund'
import { getCustomerContract } from '@/api/admin/customerIntentionContract'

export default {
  name: 'TableList',
  components: {},
  data () {
    return {
      imgheard: process.env.VUE_APP_API_BASE_URL,
      previewVisible: false,
      formData: {
        customerContractRefundRooms: []
      },
      // 表头
      columns: [
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
          title: '房间名称',
          dataIndex: 'name'
        },
        {
          title: '收租面积（平方米）',
          dataIndex: 'rentArea'
        }
      ],
      detailList: { customerContract: { sn: '' } },
      contractStatus: true,
      customerContractRefundRooms: []
    }
  },
  created () {
    getCustomerContractRefund({ id: this.$route.query.id }).then(res => {
      this.detailList = res
      this.formData.customerContractRefundRooms = res.customerContractRooms
    })
    // 显示退租的房子
    getCustomerContract({ id: this.$route.query.contractId }).then(res => {
      const arr = []
      res.customerContractRooms.forEach((item) => {
        arr.push(item)
      })
      this.formData.customerContractRefundRooms = arr
    })
  },
  methods: {
    previewCancel () {
      this.previewVisible = false
    },
    handlePreview (url) {
      this.previewImage = url
      this.previewVisible = true
    },
    navBack () {
      this.$router.push({ name: this.$route.query.url })
    },
    approval (ids) {
      customerContractCancel({ ids: ids.join(','), id: this.detailList.id }).then(res => {
        if (res.code === 0) {
          this.$message.success('操作已成功')
          this.navBack()
        } else {
          this.$message.error(res.msg)
        }
        this.selectedRowKeys = []
      })
    }
  }
}
</script>

<style>
  .ant-col{
    margin-bottom: 10px;
  }
</style>
