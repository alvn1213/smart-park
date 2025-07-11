<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="5" :sm="15">
            <a-form-item label="文件名">
              <a-input placeholder="请输入文件名" v-model="queryParam.fileName"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
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
      ref="table"
      rowKey="id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      :columns="columns"
      :data="loadData"
    >
      <div slot="fileName" slot-scope="text, record">
        <a-icon type="file"></a-icon>
        <span> {{ record.fileName }} </span>
      </div>
      <span slot="action" slot-scope="text, record">
        <a-popconfirm @confirm="handleDeleteAttachment(record)">
          <template slot="title">
            <span>确定删除 {{ record.fileName }} 吗？</span>
          </template>
          <a-icon slot="icon" type="question-circle-o" style="color: red" />
          <a-button type="link">
            <a-icon type="delete"></a-icon> 删除
          </a-button>
        </a-popconfirm>
        <a-divider type="vertical" />
        <a-button slot="actions" type="link" @click="handleEdit(record)">
          <a-icon type="file-search"></a-icon>备注编辑
        </a-button>
        <a-divider type="vertical" />
        <a-button slot="actions" type="link" @click="handleDownlaodAttachment(record)">
          <a-icon type="download"></a-icon>下载
        </a-button>
      </span>
    </s-table>
    <fileManagement-modal ref="modal" @ok="handleOk"/>
  </a-card>
</template>

<script>

import { STable } from '@/components'
import { getFileManagementList, delFileManagement, saveFileManagement } from '@/api/admin/fileManagement'
import FileManagementModal from './modules/FileManagementModal.vue'
import { checkPermission } from '@/utils/permissions'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'

export default {
  name: 'TableList',
  components: {
    STable,
    FileManagementModal
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      form: this.$form.createForm(this),
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '文件名',
          dataIndex: 'fileName',
          scopedSlots: { customRender: 'fileName' }
        },
        {
          title: '文件备注',
          dataIndex: 'fileRemarks'
        },
        {
          title: '文件上传时间',
          dataIndex: 'createTime'
        },
        {
          title: '操作',
          dataIndex: 'action',
          fixed: 'right',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getFileManagementList(Object.assign(parameter, this.queryParam))
      },
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        Authorization: 'Bearer ' + storage.get(ACCESS_TOKEN)
      },
      uploadFileList: [],
      previewVisible: false,
      previewImage: '',
      selectedRowKeys: [],
      selectedRows: [],
      addEnable: checkPermission('admin:fileManagement:add'),
      editEnabel: checkPermission('admin:fileManagement:edit'),
      removeEnable: checkPermission('admin:fileManagement:remove')
    }
  },
  filters: {
  },
  created () {
  },
  methods: {
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleEdit (record) {
      this.$refs.modal.edit(record)
    },
    handleOk () {
      this.$refs.table.refresh(true)
      console.log('handleSaveOk')
    },
    // 上传区域
    getAttachmentsList () {
      getFileManagementList().then(res => {
        if (res.code === 0) {
          console.log('attachment list done')
          console.log(res)
        }
      })
    },
    saveAttachments (info) {
      const parameter = {
        fileName: info.name,
        url: info.response.url
      }
      saveFileManagement(parameter).then(res => {
        if (res.code === 0) {
          this.$message.success(`上传成功`)
          this.handleOk()
        } else {
          this.$message.error(`上传失败`)
        }
      }).catch(() => {
        this.$message.error(`上传失败`)
      }).finally(() => {
      })
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
      delFileManagement({ ids: fileInfo.id }).then(res => {
        if (res.code === 0) {
          this.$message.success(`删除成功`)
          this.handleOk()
        } else {
          this.$message.error(`删除失败`)
        }
      }).catch(() => {
        this.$message.error(`删除失败`)
      })
    },
    // 下载图片
    handleDownlaodAttachment (info) {
      const a = document.createElement('a')
      a.href = info.url
      document.body.appendChild(a)
      a.click()
      URL.revokeObjectURL(a.href)
      document.body.removeChild(a)
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
