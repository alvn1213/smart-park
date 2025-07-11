<template>
  <a-card :body-style="{ padding: '24px 32px' }" :bordered="false">
    <a-form :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-tabs :defaultActiveKey="true">
        <a-tab-pane tab="入园申请" key="1">
          <a-form-item style="display: none">
            <a-input v-decorator="['id']" />
          </a-form-item>
          <a-row>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="入园企业名称">
                <a-input
                  placeholder="入园企业名称"
                  v-decorator="['name', { rules: [{ required: true, message: '请输入入园企业名称' }] }]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="公司原办公地址">
                <a-select
                  v-decorator="[
                    'type',
                    { initialValue: '', rules: [{ required: true, message: '请选择公司原办公地址' }] },
                  ]"
                >
                  <a-select-option value="0">总公司搬迁至设计城</a-select-option>
                  <a-select-option value="1">在园区开设分公司</a-select-option>
                  <a-select-option value="2">成立分公司</a-select-option>
                  <a-select-option value="3">其他</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColOneThird" :wrapperCol="wrapperColOneThird" label="是否注册">
                <a-switch :checked="applyParkForm.isRegister" v-decorator="['isRegister', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="法人代表">
                <a-input placeholder="法人代表" v-decorator="['operName', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="注册资本">
                <a-input placeholder="注册资本" v-decorator="['registCapi', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="公司类型">
                <a-input placeholder="公司类型" v-decorator="['econKind']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="国家">
                <a-input placeholder="国家" v-decorator="['nation']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="姓名">
                <a-input placeholder="姓名" v-decorator="['userName']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="职务">
                <a-input placeholder="职务" v-decorator="['position']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="联系电话">
                <a-input placeholder="联系电话" v-decorator="['phone', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="联系QQ">
                <a-input placeholder="联系QQ" v-decorator="['qq']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="邮箱">
                <a-input placeholder="邮箱" v-decorator="['email']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="网址">
                <a-input placeholder="网址" v-decorator="['site']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="公司人数">
                <a-input placeholder="公司人数" v-decorator="['persons']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColOneThird" :wrapperCol="wrapperColOneThird" label="是否合法合规">
                <a-switch :checked="applyParkForm.isCompliance" v-decorator="['isCompliance']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="预估年产值">
                <a-input placeholder="预估年产值" v-decorator="['preYearValue']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="期望入驻开始时间">
                <a-input placeholder="期望入驻开始时间" v-decorator="['startDate', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="期望入驻结束时间">
                <a-input placeholder="期望入驻结束时间" v-decorator="['endDate', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="申请办公面积">
                <a-input placeholder="申请办公面积" v-decorator="['applyArea', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="简介">
                <a-input placeholder="简介" v-decorator="['profile']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="主要业务范围">
                <a-input placeholder="主要业务范围" v-decorator="['scope']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="企业优势">
                <a-input placeholder="企业优势" v-decorator="['advantage']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="申请入驻原因">
                <a-input placeholder="申请入驻原因" v-decorator="['reason', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="备注">
                <a-input placeholder="备注" v-decorator="['memo']" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-tab-pane>
        <a-tab-pane tab="相关文件" key="2">
          <!-- <a href="http://buyer.zhichubao.com/template/申领单导入申领物料明细模板.xlsx">申领单导入申领物料明细模板</a> -->
          <a-table :pagination="false" :columns="fileColumns" :dataSource="fileList" rowKey="id">
            <template slot="action" slot-scope="text, record">
              <a-button slot="actions" type="link" @click="handleDownlaodAttachment(record.filePath)">
                <a-icon type="download"></a-icon>下载
              </a-button>
            </template>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </a-card>
</template>

<script>
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { getApplyPark } from '@/api/admin/applyPark'
import pick from 'lodash.pick'

export default {
  name: 'BaseForm',
  components: {},
  data () {
    return {
      defaultActiveKey: '1',
      // 相关文件
      fileColumns: [
        {
          title: '文件名',
          dataIndex: 'fileName'
        },
        {
          title: '操作',
          width: '200px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
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
      applyParkForm: {},
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      fileList: [],
      loading: false,
      // form
      form: this.$form.createForm(this)
    }
  },
  filters: {},
  created () {
    this.handleInit()
  },
  methods: {
    handleDownlaodAttachment (info) {
      const a = document.createElement('a')
      a.href = process.env.VUE_APP_API_BASE_URL + info
      document.body.appendChild(a)
      a.click()
      URL.revokeObjectURL(a.href)
      document.body.removeChild(a)
    },
    handleInit () {
      const { id } = this.$route.query
      if (id) {
        getApplyPark(id).then((res) => {
          this.applyParkForm = res
          console.log(this.applyParkForm)
          this.fileList = res.applyParkFileList
          this.$nextTick(() => {
            this.form.setFieldsValue(
              pick(
                this.applyParkForm,
                'id',
                'name',
                'type',
                'isRegister',
                'operName',
                'registCapi',
                'econKind',
                'nation',
                'phone',
                'email',
                'userName',
                'position',
                'qq',
                'site',
                'persons',
                'isCompliance',
                'preYearValue',
                'startDate',
                'endDate',
                'applyArea',
                'profile',
                'scope',
                'advantage',
                'reason',
                'memo'
              )
            )
          })
        })
      }
    }
  }
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
