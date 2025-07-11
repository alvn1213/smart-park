<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display:none">
        <a-input v-decorator="['id']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="所属楼宇">
        <a-select ref="buildingId" v-decorator="['buildingId', {rules: [{ required: true, message: '请选择所属楼宇' }]}]" @change="onSelectBuilding">
          <a-select-option v-for="item in buildingData" :key="item.value">{{ item.text }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="所属楼层">
        <a-select ref="buildingDetailId" v-decorator="['buildingDetailId', {rules: [{ required: true, message: '请选择所属楼层' }]}]">
          <a-select-option v-for="item in buildingDetailData" :key="item.value">{{ item.text }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="房间名称">
        <a-input style="width: 20%" placeholder="请输入房间名称" v-decorator="['name']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="租金">
        <a-input-group compact>
          <a-input-number :min="1" style="width: 150px; text-align: center" placeholder="租金" v-decorator="['rent', {rules: [{ required: true, message: '请输入租金' }]}]"/>
          <a-select style="width: 10%" v-decorator="['rentType', {initialValue: '4', rules: [{ required: true, message: '请选择租金类型' }]}]">
            <a-select-option value="1"> 元/㎡/天 </a-select-option>
            <a-select-option value="2"> 元/㎡/月 </a-select-option>
            <a-select-option value="3"> 元/天 </a-select-option>
            <a-select-option value="4"> 元/月 </a-select-option>
          </a-select>
        </a-input-group>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="物业费">
        <a-input-group compact>
          <a-input-number :min="1" style="width: 150px; text-align: center" placeholder="物业费" v-decorator="['managementFee', {rules: [{ required: true, message: '请输入物业费' }]}]"/>
          <a-select style="width: 10%" v-decorator="['managementFeeType', {initialValue: '4', rules: [{ required: true, message: '请选择物业费类型' }]}]">
            <a-select-option value="1"> 元/㎡/天 </a-select-option>
            <a-select-option value="2"> 元/㎡/月 </a-select-option>
            <a-select-option value="3"> 元/天 </a-select-option>
            <a-select-option value="4"> 元/月 </a-select-option>
          </a-select>
        </a-input-group>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="房间面积（平方米）">
        <a-input-number :min="1" style="width: 20%" placeholder="请输入房间面积" v-decorator="['area', {rules: [{ required: true, message: '请输入房间面积' }]}]"/>
        <div><a-checkbox :checked="mdl.canBeDivided" @change="onChangeCheckBox" v-decorator="['canBeDivided']">可分割</a-checkbox></div>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="收租面积（平方米）">
        <a-input-number :min="1" style="width: 20%" placeholder="请输入收租面积" v-decorator="['rentArea', {rules: [{ required: true, message: '请输入收租面积' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="公摊面积（平方米）">
        <a-input-number :min="0" style="width: 20%" placeholder="请输入公摊面积" v-decorator="['commonArea', {rules: [{ required: true, message: '请输入公摊面积' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="房型">
        <a-select v-decorator="['layout', {initialValue:'',rules: [{ required: true, message: '请选择房型' }]}]">
          <a-select-option value="LOFT">loft</a-select-option>
          <a-select-option value="BUSINESS">商业配套</a-select-option>
          <a-select-option value="OFFICE">办公场所</a-select-option>
          <a-select-option value="OTHER">其他</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="层高(米)">
        <a-input-number :min="1" style="width: 20%" placeholder="请输入层高" v-decorator="['floorHeight']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="可入住日期">
        <a-date-picker @change="onChange" v-decorator="['availableFrom', {rules: [{required: true, message: '请输入可入住日期'}]}]" placeholder="可入住日期" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="装修类型">
        <a-select v-decorator="['decorationType', {initialValue:'',rules: [{ required: true, message: '请选择装修类型' }]}]">
          <a-select-option value="SIMPLE">简装</a-select-option>
          <a-select-option value="SPECIAL">精装</a-select-option>
          <a-select-option value="ROUGH">毛坯房</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="奖金(元)">
        <a-input-number :min="1" style="width: 150px; text-align: center" placeholder="奖金" v-decorator="['bonus']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传房间小图" extra="尺寸建议：宽160px，高160px">
        <a-upload
          v-decorator="['smallPic']"
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
          v-decorator="['bannerImages']"
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
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="房间简介（可选）" extra="不要超过50个字符">
        <a-textarea placeholder="请输入房间简介" :rows="5" v-decorator="['briefIntro']"/>
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
import { getDistVOList } from '@/api/system'
import { getRoomById, saveRoom } from '@/api/admin/room'
import pick from 'lodash.pick'
import moment from 'moment'
import { getParkList } from '@/api/admin/park'
import { getBuildingList } from '@/api/admin/building'
import { getBuildingDetailList } from '@/api/admin/buildingDetail'

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
      parkData: [],
      buildingData: [],
      buildingDetailData: [],
      mdl: {},
      // form
      form: this.$form.createForm(this)

    }
  },
  created () {
    this.onSelectPark()
    // 获取园区
    getParkList().then(res => {
      if (res.code === 0) {
        const result = res.rows
        result.forEach(r => {
          this.parkData.push({ value: r.id, text: r.name })
        })
      }
    })
    // 初始化地区
    getDistVOList().then(res => {
      this.options = res.data
    })
    // 初始化form
    this.handleInit()
  },
  methods: {
    // 选择园区
    onSelectPark () {
      this.buildingData = []
      this.form.setFieldsValue({ buildingId: '' })
      getBuildingList().then(res => {
        if (res.code === 0) {
          const result = res.rows
          result.forEach(r => {
            this.buildingData.push({ value: r.id, text: r.buildingName })
          })
        }
      })
    },
    // 选择楼宇
    onSelectBuilding (buildingId) {
      this.buildingDetailData = []
      this.form.setFieldsValue({ buildingDetailId: '' })
      if (buildingId) {
        getBuildingDetailList({ buildingId: buildingId }).then(res => {
          if (res.code === 0) {
            const result = res.rows
            result.forEach(r => {
              this.buildingDetailData.push({ value: r.id, text: r.floorName })
            })
          }
        })
      }
    },
    onChangeCheckBox (e) {
      this.mdl.canBeDivided = e.target.checked
    },
    onChange (date, dateString) {
    },
    handleInit () {
      const { id } = this.$route.query
      if (id) {
        getRoomById(id).then(record => {
          this.mdl = Object.assign(record)
          this.visible = true
          // 房间图片
          if (this.mdl.smallPic) {
            this.imageUrl = process.env.VUE_APP_API_BASE_URL + this.mdl.smallPic
          }
          // banner图片
          this.mdl.bannerImages.forEach((item, i) => {
            item.url = process.env.VUE_APP_API_BASE_URL + item.url
          })
          this.fileList = this.mdl.bannerImages
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.mdl, 'id', 'name', 'area', 'smallPic', 'bannerImages', 'rent', 'briefIntro', 'bonus',
              'rentType', 'managementFee', 'managementFeeType', 'parkingFee', 'layout', 'floorHeight', 'availableFrom',
              'decorationType', 'isMarketable', 'marketableTime', 'canBeDivided', 'parkId', 'buildingId', 'buildingDetailId', 'rentArea', 'commonArea'))
          })
          if (this.mdl.parkId) {
            this.onSelectPark(this.mdl.parkId)
            if (this.mdl.buildingId) {
              this.onSelectBuilding(this.mdl.buildingId)
            }
          }
        })
      }
    },
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt500KB = file.size / 1024 < 500
      if (!isLt500KB) {
        this.$message.error('图片必须小于 500KB!')
      }
      return isJpgOrPng && isLt500KB
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
          values.availableFrom = moment(values.availableFrom).format('YYYY-MM-DD')
          // 园区banner图片
          values.bannerImages = bannerImages
          console.log('Received values of form: ', values)
          this.confirmLoading = true
          saveRoom(values).then(res => {
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
      this.$router.push({ name: 'room', query: { type: this.$route.query.type } })
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
