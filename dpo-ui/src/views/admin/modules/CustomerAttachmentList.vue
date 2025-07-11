<template>
  <div>
    <div style="margin: 10px">
      <a-upload
        list-type="text"
        :show-upload-list="false"
        :action="uploadUrl"
        :headers="headers"
        :before-upload="beforeUpload"
        @change="handleChange"
      >
        <a-button type="primary">
          <a-icon type="upload" />
          上传
        </a-button>
      </a-upload>
    </div>
    <s-table
      size="default"
      ref="attachmentTable"
      rowKey="id"
      :columns="columns"
      :data="loadData"
    >
      <div slot="name" slot-scope="text, record">
        <a-icon type="file"></a-icon>
        <span> {{ record.name }} </span>
      </div>
      <span slot="action" slot-scope="text, record">
        <a-popconfirm @confirm="handleDeleteAttachment(record)">
          <template slot="title">
            <span>确定删除 {{ record.name }} 吗？</span>
          </template>
          <a-icon slot="icon" type="question-circle-o" style="color: red" />
          <a-button type="link">
            <a-icon type="delete"></a-icon> 删除
          </a-button>
        </a-popconfirm>
        <a-divider type="vertical" />
        <a-button slot="actions" type="link" @click="handlePreviewAttachment(record.url)">
          <a-icon type="file-search"></a-icon>预览
        </a-button>
        <a-divider type="vertical" />
        <a-button slot="actions" type="link" @click="handleDownlaodAttachment(record)">
          <a-icon type="download"></a-icon>下载
        </a-button>
      </span>
    </s-table>
    <a-modal v-model="previewVisible" title="预览" centered :footer="null">
      <img style="width: 100%" :src="previewImage" />
    </a-modal>
  </div>
</template>

<script>
import { STable } from '@/components'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { getCustomerAttachmentsList, saveCustomerAttachments, delCustomerAttachments } from '@/api/admin/customer'

export default {
  name: 'CustomerAttachmentList',
  components: {
    STable
  },
  props: {
    customerId: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      // 表头
      columns: [
        {
          title: '文件名',
          dataIndex: 'name',
          scopedSlots: { customRender: 'name' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      attachmentList: [
        {
          name: 'hahaha.jpg'
        },
        {
          name: 'hahaha.jpg'
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getCustomerAttachmentsList(Object.assign(parameter, { customerId: this.customerId }))
      },
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      uploadFileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  created () {
    // this.getAttachmentsList()
  },
  methods: {
    getAttachmentsList () {
      getCustomerAttachmentsList({ customerId: this.customerId }).then(res => {
        if (res.code === 0) {
          console.log('attachment list done')
          console.log(res)
        }
      })
    },
    saveAttachments (info) {
      if (this.customerId > 0) {
        const parameter = {
          name: info.name,
          url: info.response.url,
          customerId: this.customerId
        }
        saveCustomerAttachments(parameter).then(res => {
          if (res.code === 0) {
            this.$message.success(`上传成功`)
            console.log(this.$refs['attachmentTable'])
            this.$refs['attachmentTable'].refresh(true)
          } else {
            this.$message.error(`上传失败`)
          }
        }).catch(() => {
          this.$message.error(`上传失败`)
        }).finally(() => {

        })
      }
    },
    beforeUpload () {
      return true
    },
    handleChange (info) {
      if (info.file.status !== 'uploading') {
        console.log(info.file, info.fileList)
      }
      if (info.file.status === 'done') {
        // this.$message.success(`${info.file.name} 上传成功`)
        console.log('upload done')
        console.log(info)
        if (info.file.response.code === 0) {
          this.saveAttachments(info.file)
        }
      } else if (info.file.status === 'error') {
        // this.$message.error(`${info.file.name} 上传失败`)
      }
    },
    handlePreviewAttachment (url) {
      this.previewImage = url
      this.previewVisible = true
    },
    handleDeleteAttachment (fileInfo) {
      delCustomerAttachments({ ids: fileInfo.id }).then(res => {
        if (res.code === 0) {
          this.$message.success(`删除成功`)
          this.$refs.attachmentTable.refresh(true)
        } else {
          this.$message.error(`删除失败`)
        }
      }).catch(() => {
        this.$message.error(`删除失败`)
      })
    },
    handleDownlaodAttachment (info) {
      const a = document.createElement('a')
      a.href = info.url
      document.body.appendChild(a)
      a.click()
      URL.revokeObjectURL(a.href)
      document.body.removeChild(a)
    }
  }
}
</script>

<style scoped>
</style>
