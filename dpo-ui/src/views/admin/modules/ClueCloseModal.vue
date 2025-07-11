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
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="关闭原因">
        <a-select v-decorator="['closeReason', {initialValue:'',rules: [{ required: true, message: '请选择' }]}]">
          <a-select-option value="0">电话打不通</a-select-option>
          <a-select-option value="1">客户暂无需求</a-select-option>
          <a-select-option value="2">客户需求已经满足</a-select-option>
          <a-select-option value="3">重复线索</a-select-option>
          <a-select-option value="4">其他</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="关闭说明">
        <a-textarea :rows="5" placeholder="请详细说明关闭原因..." v-decorator="['closeExplain']"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { closeClue } from '@/api/admin/clue'
import pick from 'lodash.pick'
export default {
  name: 'ClueCloseModal',
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
      form: this.$form.createForm(this)
    }
  },
  beforeCreate () {
  },
  created () {
  },
  methods: {
    add () {
      this.form.resetFields()
      this.edit({ id: 0 })
    },
    edit (record) {
      this.mdl = Object.assign(record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'id', 'closeReason', 'closeExplain'))
      })
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          this.confirmLoading = true
          closeClue(values).then(res => {
            if (res.code === 0) {
              this.$message.success('保存成功')
              this.$emit('ok')
              this.visible = false
              this.$router.push({ name: 'clue' })
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
  }
}
</script>
