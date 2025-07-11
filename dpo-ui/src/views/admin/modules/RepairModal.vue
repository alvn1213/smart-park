<template>
  <a-modal
    title="指派维修员"
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
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="维修员">
        <a-select v-decorator="['workerId', {rules: [{ required: true, message: '请选择维修员' }]}]">
          <a-select-option v-for="item in workerData" :key="item.value">{{ item.text }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="维修时间">
        <a-range-picker
          style="width: 100%"
          showTime
          format="YYYY-MM-DD HH:mm:ss"
          :value="dateValue"
          @change="onChange"
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="维修材料">
        <a-input placeholder="维修材料" v-decorator="['repairMateriel']"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { review } from '@/api/admin/repair'
import pick from 'lodash.pick'
import { getUserList } from '@/api/system'
import moment from 'moment'
const dateFormat = 'YYYY-MM-DD HH:mm:ss'

export default {
  name: 'RepairModal',
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
      workerData: [],
      // 开始日期
      beginDate: null,
      // 结束日期
      endDate: null,
      dateValue: [],
      form: this.$form.createForm(this)
    }
  },
  beforeCreate () {
  },
  created () {
    // 工人列表
    getUserList({ dtype: 'Admin' }).then(res => {
      if (res.code === 0) {
        const result = res.rows
        result.forEach(r => {
          this.workerData.push({ value: r.id, text: r.username + '(' + r.mobile + ')' })
        })
      }
    })
  },
  methods: {
    moment,
    add () {
      this.form.resetFields()
      this.edit({ id: 0 })
    },
    edit (record) {
      this.mdl = Object.assign(record)
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'id', 'workerId', 'repairMateriel'))
      })
      // 处理日期
      if (this.mdl.beginDate !== null && this.mdl.beginDate !== undefined && this.mdl.endDate !== null && this.mdl.endDate !== undefined) {
        this.dateValue = [moment(this.mdl.beginDate, dateFormat), moment(this.mdl.endDate, dateFormat)]
      }
    },
    onChange (dates, dateStrings) {
      console.log('From: ', dateStrings[0], ', to: ', dateStrings[1])
      this.beginDate = dateStrings[0]
      this.endDate = dateStrings[1]
      this.dateValue = [moment(this.beginDate, dateFormat), moment(this.endDate, dateFormat)]
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values)
          this.confirmLoading = true
          values.beginDate = this.beginDate
          values.endDate = this.endDate
          review(values).then(res => {
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
  }
}
</script>
