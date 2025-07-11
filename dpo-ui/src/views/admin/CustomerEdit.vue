<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-tabs :defaultActiveKey="defaultActiveKey" @change="callback">
        <a-tab-pane tab="客户详情" key="1">
          <a-form-item style="display:none">
            <a-input v-decorator="['id']"/>
          </a-form-item>
          <a-row>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="客户名称">
                <a-input placeholder="客户名称" v-decorator="['name', {rules: [{required: true, message: '请输入客户名称'}]}]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="简称">
                <a-input placeholder="简称" v-decorator="['shortName']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="客户类型">
                <a-select v-decorator="['type', {initialValue:'COMPANY',rules: [{required: true, message: '请选择客户类型'}]}]">
                  <a-select-option value="COMPANY">公司</a-select-option>
                  <a-select-option value="PERSON">个人</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="客户状态">
                <a-select v-decorator="['customerStatus', {initialValue:'DEAL',rules: [{required: true, message: '请选择客户状态'}]}]">
                  <a-select-option value="POTENTIAL">潜在客户</a-select-option>
                  <a-select-option value="PURPOSE">意向客户</a-select-option>
                  <a-select-option value="DEAL">成交客户</a-select-option>
                  <a-select-option value="LOSE">流失客户</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="所属行业">
                <a-input placeholder="所属行业" v-decorator="['sector']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="所属国家">
                <a-input placeholder="所属国家" v-decorator="['country']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="过程管理">
                <a-select v-decorator="['process']">
                  <a-select-option value="FIRST_VISIT">初次拜访</a-select-option>
                  <a-select-option value="NEED_GUID">需求引导分析</a-select-option>
                  <a-select-option value="COMPETE">竞争阶段</a-select-option>
                  <a-select-option value="PREDETERMINE">预定阶段</a-select-option>
                  <a-select-option value="CONTRACT">合同阶段</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="联系人">
                <a-input placeholder="联系人" v-decorator="['contacts']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="联系电话">
                <a-input placeholder="联系电话" v-decorator="['phone']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="邮箱">
                <a-input placeholder="邮箱" v-decorator="['email']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="统一信用代码">
                <a-input placeholder="统一信用代码" v-decorator="['creditNo']" />
                <a-button type="primary" @click="getQiXinBasicInfo">同步工商数据</a-button>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="邮编">
                <a-input placeholder="邮编" v-decorator="['postalCode']" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="通讯地址">
                <a-input placeholder="通讯地址" v-decorator="['mailAddress']" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传公司头像" extra="尺寸建议：宽160px，高160px">
                <a-upload
                  v-decorator="['headImg']"
                  list-type="picture-card"
                  class="avatar-uploader"
                  :show-upload-list="false"
                  :action="uploadUrl"
                  :headers="headers"
                  :before-upload="beforeUpload"
                  @change="handleChange"
                >
                  <img v-if="imageUrl" :src="imageUrl" style="width: 102px; height: 102px" />
                  <div v-else>
                    <a-icon :type="loading ? 'loading' : 'plus'" />
                    <div class="ant-upload-text"> 上传 </div>
                  </div>
                </a-upload>
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传照片墙">
                <a-upload
                  v-decorator="['bannerImages', {rules: [{ required: false, message: '上传照片墙' }]}]"
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
            </a-col>
            <a-col :span="8">
              <a-form-item :labelCol="labelColOneThird" :wrapperCol="wrapperColOneThird" label="渠道类型">
                <a-select v-decorator="['channelType']">
                  <a-select-option value="AGENT">中介公司</a-select-option>
                  <a-select-option value="AD">广告</a-select-option>
                  <a-select-option value="COOPERATE">合作推荐</a-select-option>
                  <a-select-option value="MY_CHANNEL">自开发渠道</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item :labelCol="labelColOneThird" :wrapperCol="wrapperColOneThird" label="渠道名称">
                <a-input placeholder="渠道名称" v-decorator="['channelName']" />
              </a-form-item>
            </a-col>
            <a-col :span="8">
              <a-form-item :labelCol="labelColOneThird" :wrapperCol="wrapperColOneThird" label="是否黑名单">
                <a-switch :checked="customerForm.isBlacklist" @change="onChangeBlacklist" v-decorator="['isBlacklist']" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperColOneThird" label="客户备注">
                <a-input placeholder="客户备注" v-decorator="['remark']" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工商注册号">
                <a-input placeholder="工商注册号" v-decorator="['regNo']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="组织机构代码">
                <a-input placeholder="组织机构代码" v-decorator="['orgNo']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="法人名">
                <a-input placeholder="法人名" v-decorator="['operName']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="经营状态">
                <a-input placeholder="经营状态" v-decorator="['status']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="公司类型(企业性质)">
                <a-input placeholder="公司类型(企业性质)" v-decorator="['econKind']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="注册资金">
                <a-input placeholder="注册资金" v-decorator="['registCapi']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="成立日期">
                <a-date-picker @change="onChange" v-decorator="['startDate']" placeholder="成立日期" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="营业结束日期">
                <a-date-picker @change="onChange" v-decorator="['endDate']" placeholder="营业结束日期" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="登记机关">
                <a-input placeholder="登记机关" v-decorator="['belongOrg']" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="注册地址">
                <a-input placeholder="注册地址" v-decorator="['address']" />
              </a-form-item>
            </a-col>
            <a-col :span="24">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="经营范围">
                <!-- <a-input placeholder="经营范围" v-decorator="['scope']" /> -->
                <a-textarea placeholder="经营范围" v-decorator="['scope']" :autoSize="{ minRows: 3, maxRows: 5 }"></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
          <a-form-item :wrapperCol="{ span: 24 }" style="text-align: center">
            <a-button htmlType="submit" type="primary">提交</a-button>
            <a-button icon="rollback" style="margin-left: 8px" @click="rollback">关闭</a-button>
          </a-form-item>
        </a-tab-pane>
        <a-tab-pane tab="关联合同" key="2" forceRender v-if="$route.query.customerId">
          <div style="margin: 10px">
            <a-button type="primary" @click="navContract" >新增合同</a-button>
          </div>
          <a-table
            :pagination="false"
            :columns="contractColumns"
            :dataSource="contractData"
            rowKey="id"
          >
            <div slot="sn" slot-scope="text, record" style="cursor:pointer;color:#1890ff" @click="handleDetail(record.id)">
              <span>{{ text }}</span>
            </div>
            <span slot="serial" slot-scope="text, record, index">
              {{ index + 1 }}
            </span>
            <span slot="status" slot-scope="text">
              {{ text | statusFilter }}
            </span>
          </a-table>
        </a-tab-pane>
        <a-tab-pane tab="联系人" key="3" v-if="$route.query.customerId">
          <div style="margin: 10px">
            <a-button type="primary" @click="$refs.contactModal.add({ customerId: $route.query.customerId })">新建联系人</a-button>
          </div>
          <a-table :pagination="false" :columns="contactsItemColumns" :dataSource="contactsItemData" rowKey="id">
            <template slot="operation" slot-scope="text, record">
              <a-button type="link" @click="$refs.contactModal.add(record)">编辑</a-button>
              <a-divider type="vertical" />
              <a-popconfirm @confirm="handleDeleteContact(record)">
                <template slot="title">
                  <span>确定删除 {{ record.name }} 吗？</span>
                </template>
                <a-icon slot="icon" type="question-circle-o" style="color: red" />
                <a-button type="link">删除</a-button>
              </a-popconfirm>
            </template>
          </a-table>
        </a-tab-pane>
        <a-tab-pane tab="相关文件" key="4" v-if="$route.query.customerId">
          <customer-attachment-list :customer-id="$route.query.customerId"></customer-attachment-list>
        </a-tab-pane>
      </a-tabs>
    </a-form>
    <customer-contacts-modal ref="contactModal" @ok="handleContactSaved"/>
  </a-card>
</template>

<script>
import moment from 'moment'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { saveCustomer, getCustomer, getQiXinBasicInfo, getCustomerContactsList, delCustomerContacts } from '@/api/admin/customer'
import { getCustomerContractList } from '@/api/admin/customerContract'
import CustomerContactsModal from './modules/CustomerContactsModal.vue'
import CustomerAttachmentList from './modules/CustomerAttachmentList.vue'
import pick from 'lodash.pick'

export default {
  name: 'BaseForm',
  components: {
    CustomerContactsModal,
    CustomerAttachmentList
  },
  data () {
    return {
      id: '',
      defaultActiveKey: '1',
      // 合同行
      contractColumns: [
        {
          title: '合同编号',
          dataIndex: 'sn',
          scopedSlots: { customRender: 'sn' }
        },
        {
          title: '物管合同号',
          dataIndex: 'pmSn'
        },
        {
          title: '合同名称',
          dataIndex: 'name'
        },
        {
          title: '签订时间',
          dataIndex: 'signDate'
        },
        {
          title: '记租时间',
          dataIndex: 'startDate'
        },
        {
          title: '到期时间',
          dataIndex: 'endDate'
        },
        {
          title: '状态',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '签订人',
          dataIndex: 'operName'
        }
      ],
      // 联系人列表表头
      contactsItemColumns: [
        {
          title: '签订人',
          dataIndex: 'name'
        },
        {
          title: '联系电话',
          dataIndex: 'phone'
        },
        {
          title: '备注',
          dataIndex: 'remark'
        },
        {
          title: '操作',
          key: 'operation',
          scopedSlots: { customRender: 'operation' }
        }
      ],
      labelColHalf: {
        span: 6
      },
      wrapperColHalf: {
        span: 18
      },
      labelColOneThird: {
        span: 9
      },
      wrapperColOneThird: {
        span: 15
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 3 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 21 }
      },
      contractData: [],
      orderPaymentData: [],
      orderRefundsData: [],
      orderShippingData: [],
      orderReturnsData: [],
      contactsItemData: [],
      customerForm: {},
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      fileList: [],
      previewVisible: false,
      previewImage: '',
      loading: false,
      imageUrl: '',
      // form
      form: this.$form.createForm(this)
    }
  },
  filters: {
    statusFilter (type) {
      const statusMap = {
        'DRAFT': '草稿',
        'APPROVING': '待审批',
        'APPROVED': '已审批',
        'REFUSE': '已拒绝',
        'EXECUTING': '执行中',
        'VOIDED': '已作废',
        'OVERDUE': '已到期',
        'END': '已结束'
      }
      return statusMap[type]
    }
  },
  created () {
    this.id = this.$route.query.customerId
    if (this.$route.query.types) {
      this.defaultActiveKey = '2'
    }
    this.handleInit()
  },
  methods: {
    handleDetail (id) {
      this.$router.push({ name: 'customerContractDetailModal', query: { id: id, customerId: this.$route.query.customerId, url: 'customerEdit' } })
    },
    navContract (e) {
      this.$router.push({ name: 'customerContractEdit', query: { customerId: this.id, url: 'customerEdit' } })
    },
    onChangeBlacklist (checked) {
      this.customerForm.isBlacklist = checked
    },
    getQiXinBasicInfo () {
      var keyword = this.form.getFieldValue('creditNo')
      getQiXinBasicInfo({ 'keyword': keyword }).then(res => {
        if (res.code === 0) {
          var qiXinResult = JSON.parse(res.data)
          if (!(isNaN(qiXinResult) && !isNaN(Date.parse(qiXinResult.endDate)))) {
            qiXinResult.endDate = null
          }
          this.form.setFieldsValue(pick(qiXinResult, 'creditNo', 'regNo', 'status', 'orgNo', 'operName', 'econKind', 'startDate', 'registCapi', 'endDate', 'address', 'scope', 'delFlag', 'belongOrg', 'remark'))
        } else {
          this.$message.error('同步工商信息失败：' + res.msg)
        }
      })
    },
    handleInit () {
      const { customerId } = this.$route.query
      if (customerId) {
        getCustomer(customerId).then(res => {
          this.customerForm = res
          // 头像
          if (this.customerForm.headImg) {
            this.imageUrl = process.env.VUE_APP_API_BASE_URL + this.customerForm.headImg
          }
          // 照片墙
          this.customerForm.bannerImages.forEach((item, i) => {
            item.url = process.env.VUE_APP_API_BASE_URL + item.url
          })
          this.fileList = this.customerForm.bannerImages
          this.form.setFieldsValue(pick(this.customerForm, 'id', 'name', 'shortName', 'type', 'customerStatus', 'sector', 'country', 'process', 'contacts', 'phone', 'email', 'creditNo', 'mailAddress',
            'postalCode', 'channelType', 'channelName', 'isBlacklist', 'regNo', 'status', 'orgNo', 'operName', 'econKind', 'startDate', 'registCapi', 'endDate'
            , 'address', 'scope', 'delFlag', 'belongOrg', 'remark', 'headImg', 'bannerImages'))
        })
      }
      this.getContastsList()
      this.getContractList()
    },
    onChange (date, dateString) {
      console.log(date, dateString)
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
          // 照片墙
          values.bannerImages = bannerImages
          console.log('Received values of form: ', values)
          values.startDate = moment(values.startDate).format('YYYY-MM-DD')
          console.log(values.startDate)
          this.confirmLoading = true
          saveCustomer(values).then(res => {
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
      this.$router.push('/admin/service/customer')
    },
    // 联系人列表
    getContastsList () {
      getCustomerContactsList({ customerId: this.$route.query.customerId }).then(res => {
        // console.log(res)
        if (res.code === 0) {
          this.contactsItemData = res.rows
        }
      })
    },
    // 合同列表
    getContractList () {
      getCustomerContractList({ customerId: this.$route.query.customerId }).then(res => {
        // console.log(res)
        if (res.code === 0) {
          this.contractData = res.rows
        }
      })
    },
    // 新增联系人回调
    handleContactSaved () {
      this.getContastsList()
    },
    // 删除联系人
    handleDeleteContact (contactInfo) {
      delCustomerContacts({ ids: contactInfo.id }).then(res => {
        if (res.code === 0) {
          this.$message.success('删除成功')
          this.deleteVisible = false
          this.deletingContact = {}
          this.getContastsList()
        } else {
          this.$message.error(res.msg)
        }
      }).catch(() => {
        this.$message.error('系统错误，请稍后再试')
      }).finally(() => {
      })
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
            headImg: result.fileName
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
    // 上传照片墙
    handleChangeFileList ({ fileList }) {
      this.fileList = fileList
    }
  }
}

// 上传头像
function getBase64 (img, callback) {
  const reader = new FileReader()
  reader.addEventListener('load', () => callback(reader.result))
  reader.readAsDataURL(img)
}

// 上传照片墙
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
  .avatar-uploader>.ant-upload {
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
