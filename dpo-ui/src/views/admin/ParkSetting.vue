<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display:none">
        <a-input v-decorator="['id']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="园区名称" extra="不要超过15个字符">
        <a-input style="width: 60%" placeholder="请输入园区名称" v-decorator="['name', {rules: [{ required: true, message: '请输入园区名称' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="园区总面积" extra="不要超过15个字符">
        <a-input-number :min="1" style="width: 20%" placeholder="请输入园区总面积" v-decorator="['area', {rules: [{ required: true, message: '请输入园区总面积' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="园区总房间数" extra="不要超过15个字符">
        <a-input-number :min="1" style="width: 20%" placeholder="请输入园区总房间数" v-decorator="['roomNumbers']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传园区小图" extra="尺寸建议：宽160px，高160px">
        <a-upload
          v-decorator="['smallPic', {rules: [{ required: true, message: '请上传园区小图' }]}]"
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
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传园区banner图" extra="尺寸建议：宽750px，高300px">
        <a-upload
          v-decorator="['bannerImages', {rules: [{ required: true, message: '上传园区banner图' }]}]"
          list-type="picture-card"
          class="avatar-uploader"
          :file-list="fileList"
          :action="uploadUrl"
          :headers="headers"
          @preview="handlePreview"
          @change="handleChangeFileList"
        >
          <div v-if="fileList.length < 4">
            <a-icon type="plus" />
            <div class="ant-upload-text"> 上传 </div>
          </div>
        </a-upload>
        <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
          <img style="width: 100%" :src="previewImage" />
        </a-modal>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="园区简介（可选）" extra="不要超过50个字符">
        <a-textarea placeholder="请输入园区简介" :rows="5" v-decorator="['briefIntro']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="纬度">
        <a-input-group compact>
          <a-input-number style="width: 150px; text-align: center" placeholder="纬度" v-decorator="['lat', {rules: [{ required: true, message: '请输入纬度' }]}]"/>
        </a-input-group>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经度">
        <a-input-group compact>
          <a-input-number style="width: 150px; text-align: center" placeholder="经度" v-decorator="['lng', {rules: [{ required: true, message: '请输入经度' }]}]"/>
        </a-input-group>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="租金范围(起)">
        <a-input-group compact>
          <a-input-number :min="1" style="width: 150px; text-align: center" placeholder="租金范围" v-decorator="['lowRent', {rules: [{ required: true, message: '请输入租金范围' }]}]"/>
          <a-input style="width: 30px; border-left: 0; pointer-events: none; backgroundColor: #fff" placeholder="~" disabled />
          <a-input-number :min="1" style="width: 150px; text-align: center; border-left: 0" placeholder="租金范围" v-decorator="['highRent', {rules: [{ required: true, message: '请输入租金范围' }]}]"/>
          <a-select style="width: 10%" v-decorator="['rentType', {initialValue: '4', rules: [{ required: true, message: '请选择租金类型' }]}]">
            <a-select-option value="1"> 元/㎡/天 </a-select-option>
            <a-select-option value="2"> 元/㎡/月 </a-select-option>
            <a-select-option value="3"> 元/天 </a-select-option>
            <a-select-option value="4"> 元/月 </a-select-option>
          </a-select>
        </a-input-group>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="园区地址">
        <a-input-group compact>
          <a-cascader :options="options" placeholder="选择地区" v-model="city" changeOnSelect/>
          <a-input style="width: 40%; margin-left: 8px" placeholder="详细地址" v-decorator="['address', {rules: [{ required: true, message: '请输入详细地址' }]}]"/>
        </a-input-group>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="配套服务" extra="不要超过50个字符">
        <a-textarea placeholder="请输入配套服务" :rows="5" v-decorator="['facilities', {rules: [{ required: true, message: '请输入配套服务' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="交通" extra="不要超过50个字符">
        <a-textarea placeholder="请输入配套服务" :rows="5" v-decorator="['traffic']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="停车费（元/小时）" extra="不要超过15个字符">
        <a-input placeholder="请输入停车费" v-decorator="['parkingFee']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="园区图文介绍">
        <WangEditor v-decorator="['introduction']"></WangEditor>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 12, offset: 12 }">
        <a-button htmlType="submit" type="primary">提交</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>

import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import WangEditor from '@/components/Editor/WangEditor.vue'
import { getDistVOList } from '@/api/system'
import { getCurrentPark, savePark } from '@/api/admin/park'
import pick from 'lodash.pick'

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
      previewVisible: false,
      previewImage: '',
      fileList: [],
      options: [],
      city: [],
      // form
      form: this.$form.createForm(this)

    }
  },
  created () {
    // 初始化地区
    getDistVOList().then(res => {
      this.options = res.data
    })
    // 初始化form
    this.handleInit()
  },
  methods: {
    handleInit () {
      getCurrentPark().then(record => {
        if (record) {
          this.mdl = Object.assign(record)
          this.visible = true
          // 省，市，区
          this.city = this.mdl.city
          // 园区图片
          this.imageUrl = process.env.VUE_APP_API_BASE_URL + this.mdl.smallPic
          // 园区banner图片
          console.log(this.mdl.bannerImages)
          if (this.mdl.bannerImages) {
            this.mdl.bannerImages.forEach((item, i) => {
              item.url = process.env.VUE_APP_API_BASE_URL + item.url
            })
          }
          this.fileList = this.mdl.bannerImages
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.mdl, 'id', 'name', 'area', 'smallPic', 'bannerImages', 'roomNumbers', 'briefIntro', 'lowRent', 'highRent', 'rentType', 'address', 'facilities', 'traffic', 'parkingFee', 'introduction', 'lat', 'lng'))
          })
        }
      })
    },
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt2M = file.size / 1024 < 500
      if (!isLt2M) {
        this.$message.error('图片必须小于 500kb!')
      }
      return isJpgOrPng && isLt2M
    },
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
            // 设置相对路径
            smallPic: result.fileName
          })
        })
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, imageUrl => {
          this.imageUrl = imageUrl
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
    // 上传banner
    handleChangeFileList ({ fileList }) {
      this.fileList = fileList
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          // eslint-disable-next-line no-console
          const bannerImages = []
          this.fileList.forEach((file, index) => {
            // 添加原来的
            if (file.hasOwnProperty('name') && file.hasOwnProperty('url') && file.hasOwnProperty('uid')) {
              bannerImages.push({ 'name': file.name, 'url': file.name, 'uid': index })
            }
            if (file.status === 'done' && file.response.code === 0) {
              const data = file.response
              bannerImages.push({ 'name': data.fileName, 'url': data.fileName, 'uid': index })
            }
          })
          // 园区banner图片
          values.bannerImages = bannerImages
          values.city = this.city
          console.log('Received values of form: ', values)
          this.confirmLoading = true
          savePark(values).then(res => {
            console.log(res)
            if (res.code === 0) {
              this.$message.success('保存成功')
              this.$emit('ok')
              this.visible = false
              this.form.setFieldsValue({ id: res.id })
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
      this.$router.push('/admin/park/parkList')
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
