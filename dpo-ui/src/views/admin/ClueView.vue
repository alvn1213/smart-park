<template>
  <div>
    <!-- 主要信息 -->
    <a-card style="margin-top: 10px" :bordered="false" title="主要信息">
      <a-button v-if="operation" @click="handleView(mdl.id)" type="primary" style="margin-right:80px; float: right">编辑</a-button>
      <a-descriptions>
        <a-descriptions-item label="主题名称">{{ mdl.clueName }}</a-descriptions-item>
        <a-descriptions-item label="线索来源">{{ mdl.source }}</a-descriptions-item>
        <a-descriptions-item label="状态">{{ mdl.customerStatus }}</a-descriptions-item>
        <a-descriptions-item label="对接人">{{ mdl.userId===null?'---': mdl.user.username }}</a-descriptions-item>
        <a-descriptions-item label="渠道分类">{{ mdl.channelCategory }}</a-descriptions-item>
        <a-descriptions-item v-if="operationReason" label="关闭原因">{{ mdl.closeReason }}</a-descriptions-item>
        <a-descriptions-item label="渠道名称">{{ mdl.channelName===null?'---':mdl.channelName }}</a-descriptions-item>
        <a-descriptions-item label="详细描述">{{ mdl.remark===null?'---':mdl.remark }}</a-descriptions-item>
        <a-descriptions-item v-if="operationReason" label="关闭说明">{{ mdl.closeExplain===null?'---':mdl.closeExplain }}
        </a-descriptions-item>
      </a-descriptions>
    </a-card>
    <!--客户信息-->
    <a-card style="margin-top: 10px" :bordered="false" title="客户信息">
      <a-descriptions>
        <a-descriptions-item label="客户名称">{{ mdl.customerName===null?'---':mdl.customerName }}</a-descriptions-item>
        <a-descriptions-item label="客户类型">{{ mdl.customerType }}</a-descriptions-item>
        <a-descriptions-item label="证件号">{{ mdl.creditNo===null?'---':mdl.creditNo }}</a-descriptions-item>
        <a-descriptions-item label="所属行业">{{ mdl.sector===null?'---':mdl.sector }}</a-descriptions-item>
        <a-descriptions-item label="联系人">{{ mdl.contacts===null?'---':mdl.contacts }}</a-descriptions-item>
        <a-descriptions-item label="联系电话">{{ mdl.phone }}</a-descriptions-item>
        <a-descriptions-item label="邮箱">{{ mdl.email===null?'---':mdl.email }}</a-descriptions-item>
        <a-descriptions-item label="通讯地址">{{ mdl.mailAddress===null?'---':mdl.mailAddress }}</a-descriptions-item>
        <a-descriptions-item label="邮编">{{ mdl.postalCode===null?'---':mdl.postalCode }}</a-descriptions-item>
      </a-descriptions>
    </a-card>
    <!--操作-->
    <a-card v-if="operation" style="margin-top: 10px" :bordered="false" title="">
      <a-button type="primary" v-if="followLeads" @click="ClueInvestigationEdit(mdl.id)" style="margin-left: 8px">跟进线索</a-button>
      <a-button v-if="customer" @click="handleEdit(mdl.id)" style="margin-left: 8px">转为客户</a-button>
      <a-button type="primary" v-if="contract" @click="handleAdd()" style="margin-left: 8px">签订合同</a-button>
      <a-button v-if="editEnabel" @click="CloseModal(mdl)" style="margin-left: 8px">关闭线索</a-button>
    </a-card>
    <clue-close-modal ref="clue"></clue-close-modal>
  </div>
</template>

<script>
import { getClue } from '@/api/admin/clue'
import { STable } from '@/components'
import { checkPermission } from '@/utils/permissions'
import ClueCloseModal from './modules/ClueCloseModal.vue'

export default {
  name: 'BaseForm',
  components: {
    STable,
    ClueCloseModal
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      edit: {},
      mdl: { user: '', username: '' },
      operation: false,
      operationReason: false,
      followLeads: false,
      contract: false,
      customer: false,
      editEnabel: checkPermission('admin:clue:edit'),
      editCustomer: checkPermission('admin:clue:edit'),
      // form
      form: this.$form.createForm(this)
    }
  },
  filters: {},
  created () {
    this.handleInit()
  },
  methods: {
    handleAdd () {
      this.$router.push({ name: 'CustomerIntentionContractEdit', query: { id: '', url: 'clue' } })
    },
    clueOperation () {
      if (this.mdl.customerStatus === '0') {
        this.operation = true
      }
      if (this.mdl.customerStatus === '1') {
        this.operationReason = true
      }
      if (this.mdl.userId != null) {
        this.followLeads = true
      }
      if (this.mdl.customerType === '个人') {
        this.contract = true
      } else {
        this.customer = true
      }
    },
    handleView (id) {
      this.$router.push({ name: 'ClueEdit', query: { id: id, status: id } })
    },
    handleEdit (customerId) {
      this.$router.push({ name: 'customerEdit', query: { customerId: customerId } })
    },
    CloseModal (record) {
      this.$refs.clue.edit(record)
    },
    ClueInvestigationEdit (id) {
      this.$router.push({ name: 'ClueEdit', query: { id: id } })
    },
    // 线索来源
    getSource (sourceValue) {
      if (sourceValue === '0') {
        return '现场接待'
      } else if (sourceValue === '1') {
        return '主动电访'
      } else if (sourceValue === '2') {
        return '邮件'
      } else if (sourceValue === '3') {
        return '客户来电'
      } else if (sourceValue === '4') {
        return '短信'
      } else if (sourceValue === '5') {
        return '上门拜访'
      } else if (sourceValue === '6') {
        return '小程序申请'
      }
    },
    // 状态
    getCustomerStatus (customerStatusValue) {
      if (customerStatusValue === '0') {
        return '激活'
      } else if (customerStatusValue === '1') {
        return '已关闭'
      } else if (customerStatusValue === '2') {
        return '已转客户'
      }
    },
    // 渠道分类
    getChannelCategory (channelCategoryValue) {
      if (channelCategoryValue === '0') {
        return '中介公司'
      } else if (channelCategoryValue === '1') {
        return '广告'
      } else if (channelCategoryValue === '2') {
        return '合作推荐'
      } else if (channelCategoryValue === '3') {
        return '自开发渠道'
      }
    },
    // 客户类型
    getCustomerType (customerTypeValue) {
      if (customerTypeValue === '0') {
        return '公司'
      } else if (customerTypeValue === '1') {
        return '个人'
      }
    },
    getCloseReason (CloseReasonValue) {
      if (CloseReasonValue === '0') {
        return '电话打不通'
      } else if (CloseReasonValue === '1') {
        return '客户暂无需求'
      } else if (CloseReasonValue === '2') {
        return '客户需求已经满足'
      } else if (CloseReasonValue === '3') {
        return '重复线索'
      } else if (CloseReasonValue === '4') {
        return '其他'
      }
    },
    // 获取数据
    handleInit () {
      const { id } = this.$route.query
      if (id) {
        getClue(id).then(record => {
          this.mdl = Object.assign(record)
          this.mdl.source = this.getSource(this.mdl.source)
          this.mdl.channelCategory = this.getChannelCategory(this.mdl.channelCategory)
          this.mdl.customerType = this.getCustomerType(this.mdl.customerType)
          this.clueOperation()
          this.mdl.customerStatus = this.getCustomerStatus(this.mdl.customerStatus)
          this.mdl.closeReason = this.getCloseReason(this.mdl.closeReason)
        })
      }
    }
  }
}
</script>

<style>
  /* you can make up upload button and sample style by using stylesheets */
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>
