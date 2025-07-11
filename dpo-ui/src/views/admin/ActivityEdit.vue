<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display:none">
        <a-input v-decorator="['id']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="活动名称" extra="请输入活动名称, 尽量不要超过20个字">
        <a-input style="width: 60%" placeholder="请输入活动名称" v-decorator="['name', {rules: [{ required: true, message: '请输入活动名称' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="报名起止时间">
        <a-range-picker
          :ranges="{ Today: [moment(), moment()], 'This Month': [moment(), moment().endOf('month')] }"
          show-time
          format="YYYY-MM-DD HH:mm"
          @change="onRangeChangeSign"
          v-decorator="['signDate', {rules: [{required: true, message: '请输入报名起止时间'}]}]"
          placeholder="报名起止时间"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="活动起止时间">
        <a-range-picker
          :ranges="{ Today: [moment(), moment()], 'This Month': [moment(), moment().endOf('month')] }"
          show-time
          format="YYYY-MM-DD HH:mm"
          @change="onRangeChangeAct"
          v-decorator="['actDate', {rules: [{required: true, message: '请输入活动起止时间'}]}]"
          placeholder="活动起止时间"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="活动地点" extra="尽量不要超过30个字">
        <a-input style="width: 60%" placeholder="请输入活动地点" v-decorator="['location', {rules: [{ required: true, message: '请输入活动地点' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传活动头像" extra="尺寸建议：宽750px，高300px">
        <a-upload
          v-decorator="['headImg']"
          list-type="picture-card"
          class="avatar-uploader"
          :show-upload-list="false"
          :action="uploadUrl"
          :headers="headers"
          :before-upload="beforeUpload"
          @change="handleChangeHeadImg"
        >
          <img v-if="headImageUrl" :src="headImageUrl" style="width: 102px; height: 102px"/>
          <div v-else>
            <a-icon :type="loading ? 'loading' : 'plus'" />
            <div class="ant-upload-text"> 上传 </div>
          </div>
        </a-upload>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传活动小图" extra="尺寸建议：宽160px，高160px">
        <a-upload
          v-decorator="['smallImg']"
          list-type="picture-card"
          class="avatar-uploader"
          :show-upload-list="false"
          :action="uploadUrl"
          :headers="headers"
          :before-upload="beforeUpload"
          @change="handleChange"
        >
          <img v-if="imageUrl" :src="imageUrl" style="width: 102px; height: 102px"/>
          <div v-else>
            <a-icon :type="loading ? 'loading' : 'plus'" />
            <div class="ant-upload-text"> 上传 </div>
          </div>
        </a-upload>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传详情">
        <WangEditor v-decorator="['content']"></WangEditor>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系电话" extra="只能输入1个手机号或固定电话，无联系电话时，前端电话按钮灰掉，不可联络。">
        <a-input style="width: 60%" placeholder="请输入联系电话" v-decorator="['contract', {rules: [{ required: true, message: '请输入联系电话' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="活动金额（元）" extra="0元为免费，均为线下付费。">
        <a-input-number :min="0" style="width: 150px; text-align: center" placeholder="请输入活动金额" v-decorator="['price', {rules: [{ required: true, message: '请输入活动金额' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="限制人数" extra="0为不限制人数。">
        <a-input-number :min="0" style="width: 150px; text-align: center" placeholder="请输入限制人数" v-decorator="['fullNum', {rules: [{ required: true, message: '请输入限制人数' }]}]"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 12, offset: 12 }">
        <a-button htmlType="submit" type="primary">提交</a-button>
        <a-button icon="rollback" style="margin-left: 8px" @click="rollback">关闭</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>

import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import WangEditor from '@/components/Editor/WangEditor.vue'
import { getActivity, saveActivity } from '@/api/admin/activity'
import pick from 'lodash.pick'
import moment from 'moment'

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
        sm: { span: 16 }
      },
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      loading: false,
      imageUrl: '',
      headImageUrl: '',
      previewVisible: false,
      previewImage: '',
      fileList: [],
      options: [],
      parkData: [],
      mdl: {},
      // form
      form: this.$form.createForm(this),
      dateFormat: 'YYYY-MM-DD HH:mm',
      monthFormat: 'YYYY-MM'

    }
  },
  created () {
    // 初始化form
    this.handleInit()
  },
  methods: {
    moment,
    onRangeChangeSign (dates, dateStrings) {
      this.mdl.signBegin = dateStrings[0]
      this.mdl.signEnd = dateStrings[1]
    },
    onRangeChangeAct (dates, dateStrings) {
      this.mdl.actBegin = dateStrings[0]
      this.mdl.actEnd = dateStrings[1]
    },
    handleInit () {
      const { id } = this.$route.query
      console.log('id', id)
      if (id) {
        getActivity(id).then(record => {
          this.mdl = Object.assign(record)
          console.log('mdl', this.mdl)
          // 报名时间回显
          const signDate = []
          signDate.push(this.mdl.signBegin)
          signDate.push(this.mdl.signEnd)
          this.mdl.signDate = signDate
          // 活动时间回显
          const actDate = []
          actDate.push(this.mdl.actBegin)
          actDate.push(this.mdl.actEnd)
          this.mdl.actDate = actDate
          this.visible = true
          // 房间图片
          if (this.mdl.smallImg) {
            this.imageUrl = process.env.VUE_APP_API_BASE_URL + this.mdl.smallImg
          }
          if (this.mdl.headImg) {
            this.headImageUrl = process.env.VUE_APP_API_BASE_URL + this.mdl.headImg
          }
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.mdl, 'id', 'name', 'signBegin', 'signEnd', 'actBegin', 'actEnd', 'location',
              'headImg', 'smallImg', 'content', 'contract', 'price', 'fullNum', 'status',
              'isMarketable', 'marketableTime', 'signDate', 'actDate'))
          })
        })
      }
    },
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('图片必须小于 2MB!')
      }
      return isJpgOrPng && isLt2M
    },
    // 活动小图上传
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
        return
      }
      if (info.file.status === 'done') {
        const { form: { setFieldsValue } } = this
        const result = info.file.response
        // 设置值
        this.$nextTick(() => {
          setFieldsValue({
            smallImg: result.fileName
          })
        })
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, imageUrl => {
          this.imageUrl = imageUrl
          this.loading = false
        })
      }
    },
    // 活动头像上传
    handleChangeHeadImg (info) {
      if (info.file.status === 'uploading') {
        this.loading = true
        return
      }
      if (info.file.status === 'done') {
        const { form: { setFieldsValue } } = this
        const result = info.file.response
        // 设置值
        this.$nextTick(() => {
          setFieldsValue({
            headImg: result.fileName
          })
        })
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, imageUrl => {
          this.headImageUrl = imageUrl
          this.loading = false
        })
      }
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64FileList(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    handleCancel () {
      this.previewVisible = false
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          // eslint-disable-next-line no-console
          values.availableFrom = moment(values.availableFrom).format('YYYY-MM-DD')
          console.log('Received values of form: ', values)
          values.signBegin = this.mdl.signBegin
          values.signEnd = this.mdl.signEnd
          values.actBegin = this.mdl.actBegin
          values.actEnd = this.mdl.actEnd
          this.confirmLoading = true
          saveActivity(values).then(res => {
            if (res.code === 0) {
              this.$message.success('保存成功')
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
      })
    },
    rollback () {
      this.$router.push('/admin/activity/activity')
    }
  }
}

// 上传园区小图
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}

// 上传园区banner图
function getBase64FileList (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
</script>

<style>
.avatar-uploader > .ant-upload {
  width: 102px;
  height: 102px;
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
