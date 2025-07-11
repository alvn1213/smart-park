<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-tabs :defaultActiveKey="true">
        <a-tab-pane tab="迁入申请" key="1">
          <a-form-item style="display:none">
            <a-input v-decorator="['id']"/>
          </a-form-item>
          <a-row>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="企业名称">
                <a-input placeholder="拟成立/迁入企业名称" v-decorator="['companyName', {rules: [{required: true, message: '请输入拟成立/迁入企业名称'}]}]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="姓名">
                <a-input placeholder="姓名" v-decorator="['userName', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="申请人（盖章）">
                <a-input placeholder="申请人（盖章）" v-decorator="['applyName', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="承租物业地址">
                <a-input placeholder="承租物业地址" v-decorator="['address', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="租赁物业面积">
                <a-input placeholder="租赁物业面积" v-decorator="['area', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="物业合同编号">
                <a-input placeholder="物业合同编号" v-decorator="['contractNo', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="联系电话">
                <a-input placeholder="联系电话" v-decorator="['phone', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="与申请人关系">
                <a-select v-decorator="['relation', {initialValue:'',rules: [{ required: true, message: '请选择与申请人关系' }]}]">
                  <a-select-option value="0">子公司</a-select-option>
                  <a-select-option value="1">分公司</a-select-option>
                  <a-select-option value="2">其他</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="租赁开始时间">
                <a-input placeholder="租赁开始时间" v-decorator="['rentStartTime', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="租赁结束时间">
                <a-input placeholder="租赁结束时间" v-decorator="['rentEndTime', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelColHalf" :wrapperCol="wrapperColHalf" label="申请人承诺">
                <a-input placeholder="申请人承诺" v-decorator="['promise', { rules: [{ required: true }] }]" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-tab-pane>
        <a-tab-pane tab="相关文件" key="2">
          <a-table
            :pagination="false"
            :columns="fileColumns"
            :dataSource="fileList"
            rowKey="id"
          >
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
import { getApplyMoveIn } from '@/api/admin/applyMoveIn'
import pick from 'lodash.pick'

export default {
  name: 'BaseForm',
  components: {
  },
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
      applyMoveInForm: {},
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      fileList: [],
      loading: false,
      // form
      form: this.$form.createForm(this)
    }
  },
  filters: {
  },
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
        getApplyMoveIn(id).then(res => {
          this.applyMoveInForm = res
          console.log(this.applyMoveInForm)
          this.fileList = res.applyMoveInFileList
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.applyMoveInForm, 'id', 'applyName', 'type', 'userName', 'area', 'phone', 'address', 'contractNo', 'rentStartTime',
              'rentEndTime', 'companyName', 'relation', 'promise'))
          })
        })
      }
    }
  }
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
