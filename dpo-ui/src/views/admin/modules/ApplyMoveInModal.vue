<template>
  <a-modal
    title="审批"
    style="top: 20px;"
    :width="400"
    v-model="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <a-form-item style="display:none">
        <a-input v-decorator="['id']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="审批结果">
        <a-select v-decorator="['status', {initialValue:'',rules: [{ required: true, message: '请选择' }]}]">
          <a-select-option value="APPROVING">待审批</a-select-option>
          <a-select-option value="PASS">审批通过</a-select-option>
          <a-select-option value="REFUSE">已拒绝</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="审批意见"
      >
        <a-textarea :rows="5" placeholder="..." v-decorator="['remark']"/>
      </a-form-item>

    </a-form>
  </a-modal>
</template>
<script>
import { approveApplyMoveIn } from '@/api/admin/applyMoveIn'
import pick from 'lodash.pick'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
export default {
  name: 'ApplyMoveInModal',
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
      roleAll: [],
      mdl: {},
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
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
    edit (record) {
      this.mdl = Object.assign({}, record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'id', 'status', 'remark'))
      })
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values)
          this.confirmLoading = true
          values.ids = values.id
          approveApplyMoveIn(values).then(res => {
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
  }
}

</script>
