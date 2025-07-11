<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display:none">
        <a-input v-decorator="['id']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="服务名称" extra="不要超过50个字符">
        <a-input style="width: 60%" placeholder="请输入服务名称" v-decorator="['serviceName', {rules: [{ required: true, message: '请输入服务名称' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="服务简介（可选）" extra="不要超过50个字符">
        <a-textarea placeholder="请输入服务简介" :rows="5" v-decorator="['serviceDesc']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="供应商">
        <a-tree-select
          v-decorator="['supplierId', {rules: [{ required: true, message: '请选择' }]}]"
          :dropdownStyle="{ maxHeight: '300px', overflow: 'auto' }"
          :treeData="supplierData"
          placeholder="请选择"
          treeDefaultExpandAll
        ></a-tree-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="菜单选择">
        <a-tree-select
          v-decorator="['menuId', {rules: [{ required: true, message: '请选择菜单' }]}]"
          :dropdownStyle="{ maxHeight: '300px', overflow: 'auto' }"
          :treeData="menus"
          placeholder="请选择"
          treeDefaultExpandAll
        ></a-tree-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="价格(元)">
        <a-input-number :min="1" style="width: 20%" placeholder="请输入" v-decorator="['price']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="价格单位" extra="同价格字段组合用于客户端显示，例：免费，面议，元/次，元/月">
        <a-input style="width: 60%" placeholder="请输入" v-decorator="['priceUnit', {rules: [{ required: true, message: '请输入价格单位' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="咨询电话（可选）" extra="填写后服务详情页会有电话按钮直接拨打此号码">
        <a-input style="width: 60%" placeholder="请输入" v-decorator="['contract']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="咨询电话服务时间段" style="z-index: 10">
        <a-time-picker
          :minute-step="15"
          :second-step="10"
          :value="moment(this.mdl.contractTimeStart ? this.mdl.contractTimeStart : '09:00', 'HH:mm:ss')"
          @change="contractTimeStartChange"
          style="margin-right: 10px"
          format="HH:mm"
        />
        ~
        <a-time-picker
          :minute-step="15"
          :second-step="10"
          :value="moment(this.mdl.contractTimeEnd ? this.mdl.contractTimeEnd : '18:00', 'HH:mm:ss')"
          :defaultValue="moment('18:00', 'HH:mm')"
          @change="contractTimeEndChange"
          format="HH:mm"
          style="margin-left: 10px"
        />
        <div><a-checkbox :checked="mdl.isWorkDay" @change="onChangeIsWorkDay" v-decorator="['isWorkDay']">仅工作日</a-checkbox></div>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传详情" style="z-index: 1">
        <WangEditor v-decorator="['serviceContent']"></WangEditor>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="服务协议(可选)">
        <a-checkbox :checked="mdl.agreementStatus" @change="onChangeAgreementStatus" v-decorator="['agreementStatus']"></a-checkbox>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="服务协议名称" v-if="mdl.agreementStatus">
        <a-input style="width: 60%" placeholder="请输入服务协议名称" v-decorator="['agreementName', {rules: [{ required: true, message: '请输入服务协议名称' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="服务协议内容" v-if="mdl.agreementStatus">
        <a-textarea placeholder="请输入服务协议内容" :rows="5" v-decorator="['agreementContent']"/>
      </a-form-item>
      <a-form-item :wrapper-col="{ span: 12, offset: 12 }">
        <a-button htmlType="submit" type="primary">提交</a-button>
        <a-button icon="rollback" style="margin-left: 8px" @click="rollback">关闭</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
import WangEditor from '@/components/Editor/WangEditor.vue'
import { getDistList } from '@/api/system'
import { saveServiceManage, getServiceManage } from '@/api/admin/serviceManage'
import { getServiceSupplierList } from '@/api/admin/serviceSupplier'
import pick from 'lodash.pick'
import moment from 'moment'
import { getServiceMenuList } from '@/api/admin/serviceMenu'

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
      loading: false,
      imageUrl: '',
      previewVisible: false,
      previewImage: '',
      fileList: [],
      options: [],
      city: [],
      mdl: {},
      supplier: {},
      dataSource: [],
      menus: [{ key: 0, value: '0', title: '请选择' }],
      supplierData: [{ key: 0, value: '0', title: '请选择' }],
      // form
      form: this.$form.createForm(this)

    }
  },
  created () {
    this.loadMenus()
    this.loadSuppliers()
    this.handleInit()
    getDistList(Object.assign({ deep: 0 })).then(res => {
      this.options = res.rows.map(d => {
        return { value: d.id, label: d.name, isLeaf: false }
      })
    })
  },
  methods: {
    contractTimeStartChange (date, dateString) {
      this.mdl.contractTimeStart = dateString
    },
    contractTimeEndChange (time, timeString) {
      this.mdl.contractTimeEnd = timeString
    },
    onChange (supplier) {
      console.log('onChange', supplier)
    },
    onChangeIsWorkDay (e) {
      this.mdl.isWorkDay = e.target.checked
    },
    onChangeAgreementStatus (e) {
      this.mdl.agreementStatus = e.target.checked
    },
    loadMenus () {
      getServiceMenuList().then(res => {
        this.buildtree(res.rows, this.menus, 0)
      })
    },
    buildtree (list, arr, parentId) {
      list.forEach(item => {
        if (item.parentId === parentId) {
          var child = {
            key: item.id,
            value: item.id + '',
            title: item.menuName,
            children: []
          }
          this.buildtree(list, child.children, item.id)
          arr.push(child)
        }
      })
    },
    loadSuppliers () {
      getServiceSupplierList().then(res => {
        this.BrandTree(res.rows, this.supplierData, 0)
      })
    },
    BrandTree (list, arr) {
      list.forEach(item => {
        var child = {
          key: item.id,
          value: item.id + '',
          title: item.supplierName,
          childrens: []
        }
        this.buildtree(list, child.childrens, item.id)
        arr.push(child)
      })
    },
    moment,
    handleInit () {
      const { id } = this.$route.query
      if (id) {
        getServiceManage(id).then(record => {
          this.mdl = Object.assign(record)
          this.visible = true
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.mdl, 'id', 'serviceName', 'serviceDesc', 'supplierId', 'serviceSupplier',
              'menuId', 'price', 'priceUnit', 'contract', 'contractTimeStart', 'contractTimeEnd',
              'isWorkDay', 'serviceContent', 'agreementStatus', 'agreementName', 'agreementContent',
              'isMarketable', 'marketableTime', 'parkId'))
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
          values.banner = []
          values.contractTimeStart = this.mdl.contractTimeStart
          values.contractTimeEnd = this.mdl.contractTimeEnd
          this.confirmLoading = true
          saveServiceManage(values).then(res => {
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
      this.$router.push('/admin/service/serviceManage')
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
