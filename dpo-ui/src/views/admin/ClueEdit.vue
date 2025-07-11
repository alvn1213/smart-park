<template>
  <a-card :body-style="{ padding: '24px 32px' }" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display: none">
        <a-input v-decorator="['id']" />
      </a-form-item>
      <a-divider orientation="left">主要信息</a-divider>
      <a-row>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="主题名称">
            <a-input
              placeholder="主题名称"
              v-decorator="['clueName', { rules: [{ required: true, message: '请输入主题' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="线索来源">
            <a-select v-decorator="['source', { initialValue: '', rules: [{ required: true, message: '请选择' }] }]">
              <a-select-option value="0">现场接待</a-select-option>
              <a-select-option value="1">主动电访</a-select-option>
              <a-select-option value="2">邮件</a-select-option>
              <a-select-option value="3">客户来电</a-select-option>
              <a-select-option value="4">短信</a-select-option>
              <a-select-option value="5">上门拜访</a-select-option>
              <a-select-option value="6">小程序申请</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="渠道分类">
            <a-select
              v-decorator="['channelCategory', { initialValue: '', rules: [{ required: true, message: '请选择' }] }]"
            >
              <a-select-option value="0">中介公司</a-select-option>
              <a-select-option value="1">广告</a-select-option>
              <a-select-option value="2">合作推荐</a-select-option>
              <a-select-option value="3">自开发渠道</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="渠道名称">
            <a-input
              placeholder="渠道名称"
              v-decorator="['channelName', { rules: [{ required: true, message: '请输入渠道名称' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="详细描述">
            <a-textarea :rows="5" placeholder="..." v-decorator="['remark']" />
          </a-form-item>
        </a-col>
        <a-divider orientation="left">客户信息</a-divider>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户名称">
            <a-input
              placeholder="客户名称"
              v-decorator="['customerName', { rules: [{ required: true, message: '请输入客户名称' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户类型">
            <a-select
              v-decorator="['customerType', { initialValue: '', rules: [{ required: true, message: '请选择' }] }]"
            >
              <a-select-option value="0">公司</a-select-option>
              <a-select-option value="1">个人</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="证件号">
            <a-input placeholder="（企业）纳税识别号 /（个人）身份证" v-decorator="['creditNo']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="所属行业">
            <a-input placeholder="所属行业" v-decorator="['sector']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系人">
            <a-input
              placeholder="联系姓名"
              v-decorator="['contacts', { rules: [{ required: true, message: '请输入联系姓名' }] }]"
            />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系电话">
            <a-input
              placeholder="联系电话"
              v-decorator="['phone', { rules: [{ required: true, message: '请输入联系电话' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="邮箱">
            <a-input placeholder="邮箱" v-decorator="['email']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="通讯地址">
            <a-input placeholder="通讯地址" v-decorator="['mailAddress']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="邮编">
            <a-input placeholder="邮编" v-decorator="['postalCode']" />
          </a-form-item>
        </a-col>

        <a-col v-if="clueInvestigation">
          <a-divider orientation="left">跟进信息</a-divider>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="意向状态">
            <a-select
              v-decorator="['intentionState', { initialValue: '', rules: [{ required: true, message: '请选择' }] }]"
            >
              <a-select-option value="0">强烈</a-select-option>
              <a-select-option value="1">一般</a-select-option>
              <a-select-option value="2">无</a-select-option>
              <a-select-option value="3">未知</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperCol" label="跟进日期">
            <a-date-picker
              @change="onChange"
              v-decorator="['investigationTime', { rules: [{ required: true, message: '请输入跟进日期' }] }]"
              placeholder="跟进日期"
            />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="跟进方式">
            <a-select
              v-decorator="['investigationMode', { initialValue: '', rules: [{ required: true, message: '请选择' }] }]"
            >
              <a-select-option value="0">客户上门</a-select-option>
              <a-select-option value="1">主动拜访</a-select-option>
              <a-select-option value="2">客户来电</a-select-option>
              <a-select-option value="3">主动电访</a-select-option>
              <a-select-option value="4">短信</a-select-option>
              <a-select-option value="5">微信</a-select-option>
              <a-select-option value="6">邮件</a-select-option>
              <a-select-option value="7">其他</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="跟进记录">
            <a-textarea :rows="10" placeholder="..." v-decorator="['investigationRecord']" />
          </a-form-item>
          <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperCol" label="计划跟进时间">
            <a-date-picker
              @change="onChange"
              v-decorator="['planTime', { rules: [{ required: true, message: '请输入日期' }] }]"
              placeholder="计划跟进时间"
            />
          </a-form-item>
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="计划跟进内容">
            <a-textarea :rows="5" placeholder="..." v-decorator="['planDetailed']" />
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item :wrapper-col="{ span: 12, offset: 11 }">
            <a-button htmlType="submit" type="primary">提交</a-button>
            <a-button icon="rollback" style="margin-left: 8px" @click="rollback">关闭</a-button>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-card>
</template>
<script>
import moment from 'moment'
import pick from 'lodash.pick'
import WangEditor from '@/components/Editor/WangEditor.vue'
import { saveClue, getClue } from '@/api/admin/clue'
import { saveClueInvestigation } from '@/api/admin/clueInvestigation'
import { getDistList } from '@/api/system'

export default {
  name: 'BaseForm',
  components: {
    WangEditor
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      loading: false,
      previewVisible: false,
      clueInvestigation: false,
      options: [],
      // form
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.handleInit()
    getDistList(Object.assign({ deep: 0 })).then((res) => {
      this.options = res.rows.map((d) => {
        return { value: d.id, label: d.name, isLeaf: false }
      })
    })
  },
  methods: {
    clueOpen () {
      if (this.mdl.assignmentStatus === '1') {
        this.clueInvestigation = true
      }
      if (this.$route.query.status === this.$route.query.id) {
        this.clueInvestigation = false
      }
    },
    onChange (date, dateString) {
      console.log(date, dateString)
    },
    handleInit () {
      const { id } = this.$route.query
      if (id) {
        getClue(id).then((record) => {
          this.mdl = Object.assign(record)
          this.clueOpen()
          this.mdl.investigationTime = moment(this.mdl.investigationTime, 'YYYY-MM-DD HH:mm:ss')
          this.mdl.planTime = moment(this.mdl.planTime, 'YYYY-MM-DD HH:mm:ss')
          this.visible = true
          this.$nextTick(() => {
            this.form.setFieldsValue(
              pick(
                this.mdl,
                'id',
                'clueName',
                'source',
                'channelCategory',
                'channelName',
                'remark',
                'customerName',
                'customerType',
                'creditNo',
                'sector',
                'contacts',
                'phone',
                'email',
                'mailAddress',
                'postalCode'
              )
            )
          })
        })
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          this.confirmLoading = true
          saveClue(values)
            .then((res) => {
              if (res.code === 0) {
                this.$message.success('保存成功')
                this.$emit('ok')
                this.visible = false
                this.rollback()
              } else {
                this.$message.error(res.msg)
              }
            })
            .catch(() => {
              this.$message.error('系统错误，请稍后再试')
            })
            .finally(() => {
              this.confirmLoading = false
            })
          if (this.$route.query.id !== null && this.$route.query.status !== this.$route.query.id) {
            saveClueInvestigation(values).then((res) => {
              if (res.code === 0) {
                this.$message.success('跟进信息保存成功')
                this.$emit('ok')
                this.visible = false
                this.rollback()
              } else {
                this.$message.error(res.msg)
              }
            }).catch(() => {
              this.$message.error('系统错误，请稍后再试')
            }).finally(() => {
              this.confirmLoading = false
            })
          }
        }
      })
    },
    rollback () {
      this.$router.push('/merchants/clue')
    }
  }
}
</script>

<style>
.avatar-uploader > .ant-upload {
  width: 102px;
  height: 102px;
}
.avatar-banner-uploader > .ant-upload {
  width: 260px;
  height: 104px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
