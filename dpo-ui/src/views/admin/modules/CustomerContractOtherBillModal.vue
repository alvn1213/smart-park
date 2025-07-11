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
        label="合计应收其他费用"
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
import { confirmReceiveOtherAmount, customerContractBillConfirmDetail } from '@/api/admin/customerContractBill'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
export default {
  name: 'CustomerContractOtherBillModal',
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
          title: '收费名称',
          dataIndex: 'name'
        },
        {
          title: '应收（元）',
          dataIndex: 'otherFee'
        },
        {
          title: '已收（元）',
          dataIndex: 'receiveOtherFee'
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
      this.receiveOtherFee = e.amount
    },
    edit (record) {
      customerContractBillConfirmDetail({ billId: record.id }).then(res => {
        if (res.code === 0) {
          this.list = res
          console.log('list', this.list)
          this.form.setFieldsValue({
            'remark': res.remark
          })
          const toBeCollected = res.otherFee - res.receiveOtherFee
          this.customerContractExpenses = [
            { name: res.name, otherFee: res.otherFee, receiveOtherFee: res.receiveOtherFee, toBeCollected: toBeCollected }
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
          console.log('--------', list)
          if (this.receiveOtherFee > list.waitOtherFee) {
            this.$message.success('收到费用不能大于待收费用')
            this.confirmLoading = false
            return false
          }
          confirmReceiveOtherAmount({
            billId: this.billId,
            otherFee: list.otherFee,
            receiveOtherFee: this.receiveOtherFee,
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
  watch: {}
}

</script>
