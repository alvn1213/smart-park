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
        <a-input v-decorator="['buildingId']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="楼层">
        <a-input-number :min="1" style="width: 20%" placeholder="请输入楼层" v-decorator="['floorNum', {rules: [{ required: true, message: '请输入楼层' }]}]"/>
      </a-form-item>
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="楼层名称"
      >
        <a-input placeholder="楼层名称" v-decorator="['floorName', {rules: [{ required: true, message: '请输入楼层名称' }]}]" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { saveBuildingDetail } from '@/api/admin/buildingDetail'
import pick from 'lodash.pick'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
export default {
  name: 'BuildingFloorModal',
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
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        token: storage.get(ACCESS_TOKEN)
      },
      form: this.$form.createForm(this)
    }
  },
  beforeCreate () {
  },
  created () {
  },
  methods: {
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('Image must smaller than 2MB!')
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
        console.log('result', result)
        // 设置值
        this.$nextTick(() => {
          setFieldsValue({
            floorPic: result.fileName
          })
        })
        // Get this url from response in real world.
        this.getBase64(info.file.originFileObj, imageUrl => {
          this.imageUrl = imageUrl
          this.loading = false
        })
      }
    },
    getBase64 (img, callback) {
      const reader = new FileReader()
      reader.addEventListener('load', () => callback(reader.result))
      reader.readAsDataURL(img)
    },
    add (buildingId) {
      this.form.resetFields()
      console.log('addBuildingId', buildingId)
      this.edit({ buildingId: buildingId })
    },
    edit (record) {
      console.log('record', record)
      this.mdl = Object.assign({}, record)
      console.log('mdl', this.mdl)
      this.visible = true
      if (this.mdl.floorPic) {
        this.imageUrl = process.env.VUE_APP_API_BASE_URL + this.mdl.floorPic
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'id', 'floorNum', 'floorName', 'floorPic', 'buildingId'))
      })
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values)
          this.confirmLoading = true
          saveBuildingDetail(values).then(res => {
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
