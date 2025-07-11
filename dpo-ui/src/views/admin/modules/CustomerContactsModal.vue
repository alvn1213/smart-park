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
      <a-form-item style="display:none">
        <a-input v-decorator="['id']"/>
      </a-form-item>
      <a-form-item style="display:none">
        <a-input v-decorator="['customerId']" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系人">
        <a-input placeholder="联系人" v-decorator="['name', {rules: [{required: true, message: '请输入联系人'}]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系电话">
        <a-input placeholder="联系电话" v-decorator="['phone']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注">
        <a-input placeholder="备注" v-decorator="['remark']"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { saveCustomerContacts } from '@/api/admin/customer'
import pick from 'lodash.pick'
export default {
  name: 'CustomerContactsModal',
  props: {
  },
  components: {
  },
  data () {
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
      confirmLoading: false,
      mdl: {},
      customerId: undefined,
      contactId: undefined,
      form: this.$form.createForm(this)
    }
  },
  beforeCreate () {
  },
  created () {
  },
  methods: {
    add (contactInfo) {
      this.customerId = undefined
      this.customerId = contactInfo.customerId
      this.contactId = undefined
      this.contactId = contactInfo.id
      this.form.resetFields()
      this.edit(contactInfo)
    },
    edit (record) {
      this.mdl = Object.assign(record)
      console.log(this.mdl)
      // this.mdl = record
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'id', 'customerId', 'name', 'phone', 'remark'))
      })
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          // values.customerId = this.customerId
          console.log('Received values of form: ', values)
          this.confirmLoading = true
          saveCustomerContacts(values).then(res => {
            if (res.code === 0) {
              this.$message.success('保存成功')
              this.$emit('ok')
              this.visible = false
            } else {
              this.$message.error(res.msg)
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
