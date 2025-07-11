<template>
  <a-modal
    title="操作"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="合计应收费用"
      >
        <span>{{ list.totalFee }}</span>
      </a-form-item>
    </a-form>
    <a-table
      rowKey="type"
      :pagination="false"
      :columns="columns"
      :dataSource="customerContractExpenses"
    >
    </a-table>
    <a-form :form="form">
      <a-form-item
        :labelCol="{ sm: { span: 2 } }"
        :wrapperCol="{ sm: { span: 22 } }"
        label="备注"
        style="margin-top:20px;"
      >
        <a-textarea :rows="5" placeholder="请填写备注" v-decorator="['remark']"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { confirmReceiveAmount, customerContractBillConfirmDetail } from '@/api/admin/customerContractBill'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
export default {
  name: 'CustomerContractBillModal',
  props: {
  },
  components: {
  },
  data () {
    return {
      list: {},
      customerContractExpenses: [],
      columns: [
        {
          title: '收费类型',
          dataIndex: 'type'
        },
        {
          title: '应收（元）',
          dataIndex: 'receivable'
        },
        {
          title: '已收（元）',
          dataIndex: 'received'
        },
        {
          title: '待收（元）',
          dataIndex: 'toBeCollected'
        },
        {
          title: '收到的金额（元）',
          dataIndex: 'amount',
          customRender: (text, record) => <a-input value={text} onChange={(e) => this.handleChange({ amount: e.target.value }, record)}/>
        }
      ],
      waitPay: 0,
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      confirmLoading: false,
      roleAll: [],
      mdl: {},
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        token: storage.get(ACCESS_TOKEN)
      },
      parkData: [],
      form: this.$form.createForm(this)
    }
  },
  beforeCreate () {
  },
  created () {
  },
  methods: {
    handleChange (e, data) {
      if (data.type === '租金') this.receiveRent = e.amount
      if (data.type === '物业管理费') this.receiveManagementTotalFee = e.amount
      if (data.type === '电费') this.receivePowerFee = e.amount
      if (data.type === '水费') this.receiveWaterFee = e.amount
    },
    edit (record) {
      customerContractBillConfirmDetail({ billId: record.id }).then(res => {
        if (res.code === 0) {
          this.list = res
          this.form.setFieldsValue({
            'remark': res.remark
          })
          this.customerContractExpenses = [
            { type: '租金', receivable: res.rent, received: res.receiveRent, toBeCollected: res.waitRent },
            { type: '物业管理费', receivable: res.managementTotalFee, received: res.receiveManagementTotalFee, toBeCollected: res.waitManagementTotalFee },
            { type: '电费', receivable: res.powerFee, received: res.receivePowerFee, toBeCollected: res.waitPowerFee },
            { type: '水费', receivable: res.waterFee, received: res.receiveWaterFee, toBeCollected: res.waitWaterFee }
          ]
        }
      })
      this.billId = record.id
      this.visible = true
    },
    changeReceiveRent (value) {
      // var waitPay = this.form.getFieldValue('totalBill') - value
      this.form.setFieldsValue('waitPay', value)
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          this.confirmLoading = true
          var list = this.list
          if (this.receiveRent > list.waitRent) {
            this.$message.success('收到租金不能大于待收租金')
            this.confirmLoading = false
            return false
          }
          if (this.receiveManagementTotalFee > list.waitManagementTotalFee) {
            this.$message.success('收到物业管理费不能大于待收物业管理费')
            this.confirmLoading = false
            return false
          }
          if (this.receivePowerFee > list.waitPowerFee) {
            this.$message.success('收到电费不能大于待收电费')
            this.confirmLoading = false
            return false
          }
          if (this.receiveWaterFee > list.waitWaterFee) {
            this.$message.success('收到水费不能大于待收水费')
            this.confirmLoading = false
            return false
          }
          confirmReceiveAmount({
            billId: this.billId,
            receiveRent: this.receiveRent,
            receiveManagementTotalFee: this.receiveManagementTotalFee,
            receivePowerFee: this.receivePowerFee,
            receiveWaterFee: this.receiveWaterFee,
            remark: values.remark
          }).then(res => {
            if (res.code === 0) {
              this.$message.success('保存成功')
              this.$emit('ok')
              this.visible = false
            } else {
              this.$message.success(res.msg)
            }
          }).catch(() => {
            this.$message.error('系统错误，请稍后再试')
          }).finally(() => {
            this.confirmLoading = false
          })
        }
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
