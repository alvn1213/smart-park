<template>
  <a-card :body-style="{ padding: '24px 32px' }" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display: none">
        <a-input v-decorator="['id']" />
      </a-form-item>
      <a-divider orientation="left">基本信息</a-divider>
      <a-row style="width:80%;margin: 0 auto;">
        <a-col :span="6">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合同编号">
            <a @click="handleDetail()">{{ mdldata.sn }}</a>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="管理编号">
            <span>{{ mdldata.manageSn }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合同名称">
            <span>{{ mdldata.name }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="租户名称">
            <span>{{ mdldata.customerName }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-divider orientation="left">租赁场地</a-divider>
      <a-row style="width:80%;margin: 40px auto;">
        <a-col :span="24">
          <a-form-item :wrapper-col="{ span: 24 }">
            <a-table
              :pagination="false"
              :columns="columns"
              rowKey="pid"
              :dataSource="formData.customerContractRefundRooms">
            </a-table>
          </a-form-item>
        </a-col>
      </a-row>
      <a-divider orientation="left">退租信息</a-divider>
      <a-form-item style="display: none">
        <a-input v-decorator="['createBy']" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="退租日期：">
        <a-date-picker style="width: 60%" @change="signDateChange" v-decorator="['refundDate',{ rules: [{ required: true, message: '请选择退租日期' }] }]" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="退租原因">
        <a-select style="width: 60%" v-decorator="['refundReason', {initialValue:'',rules: [{ required: true, message: '请选择' }]}]">
          <a-select-option value="NORMAL">正常到期</a-select-option>
          <a-select-option value="CONSULT">协商提前退租</a-select-option>
          <a-select-option value="VIOLATION">违约清退</a-select-option>
          <a-select-option value="OTHER">其他</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="应收费用">
        <a-input-number
          style="width: 60%"
          v-decorator="['receiveFee', { rules: [{ required: true, message: '请填写应收费用' }] }]"
          :min="0"
          @change="receiveFeeChange"
        />
        <span class="ant-form-text">元</span>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="应退费用">
        <a-input-number
          style="width: 60%"
          v-decorator="['refundFee', { rules: [{ required: true, message: '请填写应退费用' }] }]"
          :min="0"
          @change="refundFeeChange"
        />
        <span class="ant-form-text">元</span>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="费用合计" extra="正数为应收费用 — — 负数为应退费用">
        <a-input-number style="width: 60%" :disabled="disabled" v-decorator="['totalFee', { rules: [{ required: true, message: '请填写费用合计' }] }]"/>
        <span class="ant-form-text">元</span>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注（可选）">
        <a-textarea style="width: 60%" placeholder="请输入备注" :rows="5" v-decorator="['remark']" />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="存档图片" extra="存档图片最多上传5张">
        <a-upload
          v-decorator="['archiveImageList']"
          class="avatar-uploader"
          list-type="picture-card"
          :file-list="fileList"
          :action="uploadUrl"
          :headers="headers"
          @preview="handlePreview"
          @change="handleChange"
        >
          <div v-if="fileList.length < 5">
            <a-icon type="plus" />
            <div class="ant-upload-text">
              上传
            </div>
          </div>
        </a-upload>
        <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
          <img alt="example" style="width: 100%" :src="previewImage" />
        </a-modal>
      </a-form-item>
      <div style="width:100%;text-align:center;">
        <a-button style="margin-right: 25px" key="buy" @click="navBack">返回</a-button>
        <a-button style="margin-right: 25px" html-type="submit" type="primary" >提交</a-button>
      </div>
    </a-form>
  </a-card>
</template>

<script>
import pick from 'lodash.pick'
import { getCustomerallList, getCustomerContract } from '@/api/admin/customerIntentionContract'
import { customerContractRefundSave, getCustomerContractRefund } from '@/api/admin/customerContractRefund'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import moment from 'moment'

export default {
  name: 'BaseForm',
  components: {},
  data () {
    return {
      mdldata: {},
      disabled: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      // form
      form: this.$form.createForm(this),
      // 表头
      columns: [
        {
          title: '所属园区',
          dataIndex: 'park.name'
        },
        {
          title: '所属楼宇',
          dataIndex: 'building.buildingName'
        },
        {
          title: '所属楼层',
          dataIndex: 'buildingDetail.floorName'
        },
        {
          title: '房间名称',
          dataIndex: 'name'
        },
        {
          title: '收租面积（平方米）',
          dataIndex: 'rentArea'
        }
      ],
      id: '',
      url: '',
      customerId: '', // 客户
      contractId: '', // 合同
      dataSource: [],
      formData: {
        customerContractRefundRooms: []
      },
      receiveFee: 0,
      refundFee: 0,
      // 上传
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      previewVisible: false,
      previewImage: '',
      fileList: []
    }
  },
  mounted () {
    this.form.setFieldsValue({ id: this.$route.query.id, contractId: this.$route.query.id })
    this.form.setFieldsValue({ depositPeriod: 1, deposit: 2000 })
    if (this.$route.query.id) {
      this.disabled = true
      this.id = this.$route.query.id
    }
    if (this.$route.query.customerId) {
      this.disabled = true
    }
    if (this.$route.query.url) {
      this.url = this.$route.query.url
    }
    getCustomerallList().then((res) => {
      this.dataSource = res.data
    })
    // 添加
    this.handleInit()
    // 编辑
    getCustomerContractRefund({ id: this.$route.query.id }).then((res) => {
      this.$nextTick(() => {
        this.mdl = Object.assign(res)
        if (this.mdl.archiveImageList != null) {
          this.mdl.archiveImageList.forEach((item, i) => {
            item.url = process.env.VUE_APP_API_BASE_URL + item.url
          })
          this.fileList = this.mdl.archiveImageList
        }
        this.form.setFieldsValue(
          pick(this.mdl, 'id', 'createBy', 'customerId', 'refundDate', 'refundReason', 'receiveFee', 'refundFee', 'totalFee', 'remark', 'archiveImageList')
        )
      })
    })
  },
  methods: {
    receiveFeeChange (e) {
      this.receiveFee = e
      this.$nextTick(() => {
        this.form.setFieldsValue({ totalFee: (e - this.refundFee) })
      })
    },
    refundFeeChange (e) {
      this.$nextTick(() => {
        this.form.setFieldsValue({ totalFee: (this.receiveFee - e) })
      })
      this.refundFee = e
    },
    signDateChange (date, dateString) {
      this.formData.signDate = dateString
    },
    handleDetail () {
      this.$router.push({ name: 'customerContractDetailModal', query: { id: this.$route.query.id, url: 'contractChange' } })
    },
    fatherMethod (res) {
      this.formData.customerContractRefundRooms = res
      let rentArea = 0
      res.forEach(item => {
        rentArea += item.rentArea
      })
      this.formData.rentArea = rentArea
      if (this.$route.query.id) {
        this.default = true
      }
    },
    navBack (e) {
      this.$router.push({ name: this.url, query: { types: '1', customerId: this.$route.query.customerId } })
    },
    handleInit () {
      if (this.id) {
        getCustomerContract({ id: this.$route.query.contractId == null ? this.$route.query.id : this.$route.query.contractId }).then((record) => {
          this.mdldata = record
          this.mdl = Object.assign(record)
          this.formData.customerId = record.customerId
          const arr = []
          record.customerContractRooms.forEach((item) => {
            arr.push(item)
          })
          let rentArea = 0
          arr.forEach(item => {
            rentArea += item.rentArea
          })
          this.formData.rentArea = rentArea
          this.formData.customerContractRefundRooms = arr
          this.$nextTick(() => {
            this.form.setFieldsValue(
              pick(this.mdl, 'name', 'sn', 'customerName', 'creditNo', 'manageSn')
            )
          })
        })
      }
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          if (this.formData.customerContractRefundRooms.length > 0) {
            const arr = []
            this.formData.customerContractRefundRooms.forEach((item) => {
              arr.push({ roomId: item.id })
            })
            this.formData.customerContractRefundRooms = arr
            Object.assign(this.formData, values)
          } else {
            this.$message.warning('租赁场地不能为空')
          }
        }
        if (!err) {
          /* 上传 */
          const archiveImageList = []
          this.fileList.forEach((file, index) => {
            // 添加原来的
            if (file.hasOwnProperty('name') && file.hasOwnProperty('url') && file.hasOwnProperty('uid')) {
              archiveImageList.push({ 'name': file.name, 'url': file.name, 'uid': index })
            }
            if (file.status === 'done' && file.response.code === 0) {
              const data = file.response
              archiveImageList.push({ 'name': data.fileName, 'url': data.fileName, 'uid': index })
            }
          })
          values.availableFrom = moment(values.availableFrom).format('YYYY-MM-DD')
          /* 分割线 */
          const formData = this.formData
          const data = Object.assign({}, formData, values)
          data.startDate = formData.startDate
          data.endDate = formData.endDate
          // 存档图片
          data.archiveImageList = archiveImageList
          data.contractId = this.mdldata.id
          customerContractRefundSave(data).then((res) => {
            console.log(data)
            if (res.code === 0) {
              this.$message.success('已经提交')
              this.navBack()
            } else {
              this.$message.warning(res.msg)
            }
          })
        }
      })
    },
    rollback () {
      this.$router.push('/admin/dj/djBanner')
    },
    // 上传
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64FileList(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    handleChange ({ fileList }) {
      this.fileList = fileList
    }
  }
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
