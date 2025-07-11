<template>
  <div>
    <a-card :bordered="false" title="处理进度">
      <a-steps :current="mdl.status" progressDot>
        <a-step>
          <template v-slot:title>
            <span>指派中</span>
          </template>
          <template v-slot:description v-if="mdl.status >= 1">
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ mdl.isAnonymous !=0 ? '（匿名）' :mdl.createBy }}
              <div>{{ mdl.createTime }}</div>
            </div>
          </template>
        </a-step>
        <a-step>
          <template v-slot:title>
            <span>处理中</span>
          </template>
          <template v-slot:description v-if="mdl.status >= 2">
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ mdl.user.username }}
              <div>{{ mdl.processingTime }}</div>
            </div>
          </template>
        </a-step>
        <a-step>
          <template v-slot:title>
            <span>已完成</span>
          </template>
          <template v-slot:description v-if="mdl.status >= 3">
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ mdl.user.username }}
              <div>{{ mdl.updateTime }}</div>
            </div>
          </template>
        </a-step>
      </a-steps>
    </a-card>
    <a-card style="margin-top: 10px" :bordered="false" title="投诉信息">
      <a-descriptions>
        <a-descriptions-item label="所属园区">{{ mdl.park.name }}</a-descriptions-item>
        <a-descriptions-item label="投诉单号">{{ mdl.sn }}</a-descriptions-item>
        <a-descriptions-item label="投诉人">{{ mdl.isAnonymous !=0 ? '（匿名）' :mdl.createBy }}</a-descriptions-item>
        <a-descriptions-item label="投诉时间">{{ mdl.createTime }}</a-descriptions-item>
        <a-descriptions-item label="描述">{{ mdl.content }}</a-descriptions-item>
        <br/>
        <div>
          <img v-for="(item, index) in mdl.images" :key="index" style="width:150px;heigth:150px;cursor:pointer;margin-right: 20px;" :src="imgheard + item.name" @click="handlePreview(imgheard + item.name)"/>
        </div>
      </a-descriptions>
      <a-divider />
    </a-card>
    <a-modal :visible="previewVisible" :footer="null" @cancel="previewCancel">
      <img style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>

<script>
import { getSuggestion } from '@/api/admin/suggestion'
export default {
  name: 'BaseForm',
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
      imgheard: process.env.VUE_APP_API_BASE_URL,
      // 表头
      columns: [
        {
          title: '操作类型',
          dataIndex: 'type',
          key: 'type',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '操作人',
          dataIndex: 'updateBy',
          key: 'updateBy'
        },
        {
          title: '操作详情',
          dataIndex: 'detail',
          key: 'detail'
        },
        {
          title: '操作时间',
          dataIndex: 'updateTime',
          key: 'updateTime'
        }
      ],
      dataSource: [],
      mdl: {
        park: { name: '' }
      },
      previewVisible: false,
      previewImage: '',
      fileList: [],
      // form
      form: this.$form.createForm(this)
    }
  },
  filters: {
    typeFilter (type) {
      const typeMap = {
        'PENDING_ASSIGN': '待分配',
        'PENDING_PROCESS': '待处理',
        'COMPLETED': '已完成',
        'CANCELED': '已取消',
        'SCORE': '已评价'
      }
      return typeMap[type]
    }
  },
  created () {
    this.handleInit()
  },
  methods: {
    previewCancel () {
      this.previewVisible = false
    },
    handlePreview (url) {
      this.previewImage = url
      this.previewVisible = true
    },
    handleInit () {
      const { id } = this.$route.query
      if (id) {
        getSuggestion(id).then(record => {
          this.mdl = Object.assign(record)
          // 报修图片
          this.fileList = this.mdl.repairImages
        })
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    handleChange ({ fileList }) {
      this.fileList = fileList
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          // eslint-disable-next-line no-console
          console.log('Received values of form: ', values)
        }
      })
    },
    rollback () {
      this.$router.push('/repair/repairList')
    }
  }
}
</script>

<style>
/* you can make up upload button and sample style by using stylesheets */
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
