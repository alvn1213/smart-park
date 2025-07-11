<template>
  <a-modal
    title="操作"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
  >
    <a-spin :spinning="spinning">
      <a-form :form="form">
        <a-form-item style="display:none">
          <a-input v-decorator="['id']"/>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="用户名" >
          <a-input placeholder="用户名" v-decorator="['username', {rules: [{ required: true, message: '请输入用户名' }]}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="昵称">
          <a-input v-decorator="['nickname', {rules: [{ required: true, message: '请输入昵称' }]}] "placeholder="起一个名字"/>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="手机" >
          <a-input placeholder="手机" v-decorator="['mobile', {rules: [{ required: true, message: '请输入手机' }]}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="状态">
          <a-select v-decorator="['status', {initialValue:'0',rules: [{ required: true, message: '请选择状态' }]}]">
            <a-select-option :value="'0'">正常</a-select-option>
            <a-select-option :value="'1'">禁用</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item v-if="tenantId !== null" :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门">
          <a-tree-select
            v-decorator="['deptId', {rules: [{ required: true, message: '请选择部门' }]}]"
            :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
            :treeData="deptTree"
            @change="onChange"
            placeholder="部门"
            treeDefaultExpandAll
          >
          </a-tree-select>
        </a-form-item>
        <a-form-item v-if="tenantId === null" :labelCol="labelCol" :wrapperCol="wrapperCol" label="所属租户">
          <a-select v-decorator="['tenantId', {rules: [{ required: true, message: '请选择所属租户' }]}]">
            <a-select-option v-for="item in tenantData" :key="item.value">{{ item.text }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item v-if="tenantId !== null" :labelCol="labelCol" :wrapperCol="wrapperCol" label="所属园区">
          <a-select v-decorator="['parkId', {rules: [{ required: true, message: '请选择所属园区' }]}]">
            <a-select-option v-for="item in parkData" :key="item.value">{{ item.text }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="拥有角色" hasFeedback>
          <a-select
            style="width: 100%"
            mode="multiple"
            v-decorator="['roleIds', {rules: [{ required: true, message: '请选择角色' }]}]"
            :allowClear="true"
          >
            <a-select-option v-for="(action) in roleAll" :key="action.id" >{{ action.roleName }}</a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="描述">
          <a-textarea :rows="3" placeholder="..." v-decorator="['remark']"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { getRoleAll, saveUser, getUser } from '@/api/system'
import { getTenantList } from '@/api/tenant'
import { getParkList } from '@/api/admin/park'
import pick from 'lodash.pick'
import { mapGetters } from 'vuex'
export default {
  name: 'UserModal',
  props: {
    deptTree: {
      type: Array,
      required: true
    }
  },
  computed: {
    ...mapGetters(['parkId', 'tenantId'])
  },
  data () {
    return {
      description: '列表使用场景：后台管理中的权限管理以及角色管理，可用于基于 RBAC 设计的角色权限控制，颗粒度细到每一个操作类型。',
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      confirmLoading: false,
      roleAll: [],
      mdl: {},
      deptCheck: true,
      spinning: false,
      tenantData: [],
      parkData: [],
      form: this.$form.createForm(this)
    }
  },
  beforeCreate () {
  },
  created () {
    // 租户
    getTenantList().then(res => {
      if (res.code === 0) {
        res.rows.forEach(r => {
          this.tenantData.push({ value: r.id, text: r.name })
        })
      }
    })
    // 获取园区
    getParkList().then(res => {
      if (res.code === 0) {
        res.rows.forEach(r => {
          this.parkData.push({ value: r.id, text: r.name })
        })
      }
    })
    this.loadRoleAll()
  },
  methods: {
    add () {
      this.form.resetFields()
      this.mdl = Object.assign({}, { id: 0, deptId: '' })
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'id', 'username', 'nickname', 'status', 'mobile', 'roleIds', 'remark', 'deptId', 'parkId', 'tenantId'))
      })
    },
    edit (record) {
      if (record.id > 0) {
        this.spinning = true
        getUser(record.id).then(res => {
          this.mdl = Object.assign({}, res)
          this.visible = true
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.mdl, 'id', 'username', 'nickname', 'status', 'mobile', 'roleIds', 'remark', 'deptId', 'parkId', 'tenantId'))
            this.spinning = false
            // this.form.setFieldsValue({ ...record })
          })
        })
      }
    },
    onChange (value, label, extra) {
      if (extra.triggerNode.$children.length > 0) {
        this.$message.error('不能选择父节点' + `${label}`)
        this.deptCheck = false
      } else {
        this.deptCheck = true
      }
    },
    loadRoleAll () {
      getRoleAll().then(res => {
        this.roleAll = res.rows
      })
    },
    handleSubmit (e) {
      e.preventDefault()
      if (!this.deptCheck) {
        this.$message.error('不能选择父节点')
        return
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values)
          this.confirmLoading = true
          saveUser(values).then(res => {
            if (res.code === 0) {
              this.$message.success('保存成功')
              this.$emit('ok')
              this.visible = false
            } else {
              this.$message.success(res.msg)
            }
          }).catch(() => {
            this.$message.error('系统错误，请稍后再试')
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      })
    }
  }
}
</script>
