<template>
  <div>
    <!-- 维修进度 -->
    <a-card :bordered="false" title="维修进度">
      <a-steps :current="mdl.status" progressDot>
        <a-step>
          <template v-slot:title>
            <span>待分配</span>
          </template>
          <template v-slot:description v-if="mdl.status >= 0">
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ mdl.createBy }}
              <div>{{ mdl.createTime }}</div>
            </div>
          </template>
        </a-step>
        <a-step>
          <template v-slot:title>
            <span>待处理</span>
          </template>
          <template v-slot:description v-if="mdl.status >= 1">
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ mdl.updateBy }}
              <div>{{ mdl.updateTime }}</div>
            </div>
          </template>
        </a-step>
        <a-step>
          <template v-slot:title>
            <span>已完成</span>
          </template>
          <template v-slot:description v-if="mdl.status >= 2">
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ mdl.updateBy }}
              <div>{{ mdl.updateTime }}</div>
            </div>
          </template>
        </a-step>
        <a-step>
          <template v-slot:title>
            <span>已评价</span>
          </template>
          <template v-slot:description v-if="mdl.status >= 3">
            <div class="antd-pro-pages-profile-advanced-style-stepDescription">
              {{ mdl.updateBy }}
              <div>{{ mdl.updateTime }}</div>
            </div>
          </template>
        </a-step>
      </a-steps>
    </a-card>
    <!-- 维修信息 -->
    <a-card style="margin-top: 10px" :bordered="false" title="报修信息">
      <a-descriptions>
        <a-descriptions-item label="报修人">{{ mdl.name }}</a-descriptions-item>
        <a-descriptions-item label="维修单号">{{ mdl.sn }}</a-descriptions-item>
        <a-descriptions-item label="状态">{{ mdl.statusName }}</a-descriptions-item>
        <a-descriptions-item label="联系方式">{{ mdl.phone }}</a-descriptions-item>
        <a-descriptions-item label="公司">{{ mdl.customerVO===null?'':mdl.customerVO.name }}</a-descriptions-item>
        <a-descriptions-item label="园区">{{ mdl.parkVO===null?'':mdl.parkVO.name }}</a-descriptions-item>
        <a-descriptions-item label="报修时间">{{ mdl.repairTime }}</a-descriptions-item>
        <a-descriptions-item label="报修区域">{{ mdl.area }}</a-descriptions-item>
      </a-descriptions>
      <a-divider />
      <template>
        <div class="clearfix">
          <a-upload list-type="picture-card" :file-list="fileList" @preview="handlePreview">
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </div>
      </template>
    </a-card>
    <a-card style="margin-top: 10px" :bordered="false" title="维修信息">
      <a-descriptions>
        <a-descriptions-item label="维修员">{{ mdl.userVO===null?'':mdl.userVO.userName }}</a-descriptions-item>
        <a-descriptions-item label="联系电话">{{ mdl.userVO===null?'':mdl.userVO.mobile }}</a-descriptions-item>
        <a-descriptions-item label="维修费用">{{ mdl.repairFee===null?'0': mdl.repairFee }} 元</a-descriptions-item>
        <a-descriptions-item label="开始时间">{{ mdl.beginDate }}</a-descriptions-item>
        <a-descriptions-item label="结束时间">{{ mdl.endDate }}</a-descriptions-item>
        <a-descriptions-item label="维修材料">{{ mdl.repairMateriel }}</a-descriptions-item>
      </a-descriptions>
    </a-card>
    <a-card style="margin-top: 10px" :bordered="false" title="评价">
      <a-descriptions >
        <a-descriptions-item label="评价结果">
          <a-rate v-model="mdl.repairScore" disabled/>
        </a-descriptions-item>
        <a-descriptions-item label="评价工人">
          <a-rate v-model="mdl.workerScore" disabled/>
        </a-descriptions-item>
        <a-descriptions-item label="意见建议">
          {{ mdl.remark }}
        </a-descriptions-item>
      </a-descriptions>
    </a-card>

    <!-- 操作 -->
    <a-card style="margin-top: 10px" :bordered="false" title="维修日志">
      <a-table rowKey="id" :columns="columns" :dataSource="dataSource" :pagination="false">
        <span slot="type" slot-scope="text">
          {{ text | typeFilter }}
        </span>
      </a-table>
    </a-card>
  </div>
</template>

<script>
import { getRepair } from '@/api/admin/repair'
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
        customerVO: { name: '' },
        parkVO: { name: '' },
        // 工人信息
        userVO: { userName: '', mobile: '' },
        // 工单评价
        repairScore: 0,
        // 工人评价
        workerScore: 0
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
    handleInit () {
      const { repairId } = this.$route.query
      if (repairId) {
        getRepair(repairId).then(record => {
          this.mdl = Object.assign(record)
          console.log(this.mdl.user)
          // 维修记录
          if (this.mdl.repairLogs.length > 0) {
            this.dataSource = this.mdl.repairLogs
          }
          // 报修图片
          this.fileList = this.mdl.repairImages
        })
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
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

function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
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
