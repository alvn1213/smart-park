<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display:none">
        <a-input v-decorator="['id']"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="政策名称" extra="不要超过50个字符">
        <a-input style="width: 60%" placeholder="请输入政策名称" v-decorator="['name', {rules: [{ required: true, message: '请输入政策名称' }]}]"/>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上传详情">
        <WangEditor v-decorator="['content']"></WangEditor>
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
import { savePolicy, getPolicy } from '@/api/admin/policy'
import pick from 'lodash.pick'

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
      previewVisible: false,
      options: [],
      // form
      form: this.$form.createForm(this)

    }
  },
  created () {
    this.handleInit()
    getDistList(Object.assign({ deep: 0 })).then(res => {
      this.options = res.rows.map(d => {
        return { value: d.id, label: d.name, isLeaf: false }
      })
    })
  },
  methods: {
    handleInit () {
      const { id } = this.$route.query
      if (id) {
        getPolicy(id).then(record => {
          this.mdl = Object.assign(record)
          this.visible = true
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.mdl, 'id', 'name', 'content', 'isMarketable'))
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
          // eslint-disable-next-line no-console
          // console.log('city:', this.city)
          // console.log('banner', this.fileList)
          this.confirmLoading = true
          savePolicy(values).then(res => {
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
      this.$router.push('/admin/policy/policy')
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
