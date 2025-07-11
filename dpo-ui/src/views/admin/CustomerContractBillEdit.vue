<template>
  <a-card :body-style="{padding: '24px 32px'}" :bordered="false">
    <a-form @submit="handleSubmit" :form="billForm" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="合同名称">
        <a-input
          style="width: 60%"
          v-decorator="['contractName']"
          disabled='true'
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="租赁期限：">
        <a-date-picker style='width: 19%' v-decorator="['startDate']" disabled='true' />
        <span> ~ </span>
        <a-date-picker style='width: 19%' v-decorator="['endDate']" disabled='true' />
      </a-form-item>
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="付款周期："
        style="margin: 0; padding: 10px 0"
      >
        <a-select ref="paymentCycle" style='width: 100px' v-decorator="['paymentCycle', {initialValue: 'MONTH', rules: [{ required: true, message: '请选择付款周期' }]}]">
          <a-select-option v-for="item in paymentPeriodMap" :key="item" :value="item.id">
            {{ item.name }}
          </a-select-option
          >
        </a-select>
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="账单时间：">
        <a-date-picker  ref='billStartDate' style="width: 19%" @change="onChangeBillStartDate"  v-decorator="['billStartDate',{ rules: [{ required: true, message: '请选择账单开始日期' }] }]" format= "YYYY-MM-DD"/>
        <span> ~ </span>
        <a-date-picker ref='billEndDate' style="width: 19%" @change="onChangeBillEndDate" v-decorator="['billEndDate',{ rules: [{ required: true, message: '请选择账单结束日期' }] }]" format= "YYYY-MM-DD"/>
      </a-form-item>
      <div style="border: 1px solid #dddddd; width: 70%; margin: 0 auto">
        <div>
          <div>
            <a-row>
              <a-col :span="48">
                <a-button type="primary" @click="showModal">新建费项</a-button>
              </a-col>
            </a-row>
            <a-row style="width:100%;margin: 30px auto;">
              <a-col>
                <a-table
                  :pagination="false"
                  :columns="otherFeeColumns"
                  :dataSource="formData.customerContractOthers"
                >
                    <span slot="action" slot-scope="text, record">
              <a-popconfirm
                v-if="formData.customerContractOthers.length"
                cancelText="取消"
                okText="确定"
                title="确定删除这条数据吗?"
                @confirm="() => onDelete(record.key)"
              >
                <a style="color:#ff4d4f">删除</a>
              </a-popconfirm>
            </span>
                </a-table>
              </a-col>
            </a-row>
          </div>
          <a-modal v-model="otherFeeVisible" cancelText="取消" okText="添加" title="新建费项目" @ok="handleOk">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="费项名称:">
              <a-select style='width: 90%' ref="expenseSettingsId" v-decorator="['expenseSettingsId', {rules: [{ required: true, message: '请选择费项' }]}]" @change="onSelectExpenses">
                <a-select-option v-for="item in expensesList" :key="item.value">{{ item.text }}</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="费项类型：">
              <span>{{otherFeeFormData.expenseSettings.type.name}}</span>
            </a-form-item>
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="款项类型：">
              <a-select :value="otherFeeFormData.moneyType" style="width: 100%" @change="moneyTypeChange">
                <a-select-option value="IN">应收</a-select-option>
                <a-select-option value="OUT">应退</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="开始时间：">
              <a-date-picker style="width: 40%" @change="otherFeeStartDateChange" v-decorator="['otherFeeFormData.startDate',{ rules: [{ required: true, message: '请选择开始日期' }] }]" />
            </a-form-item>
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结束时间：">
              <a-date-picker style="width: 40%" @change="otherFeeEndDateChange" v-decorator="['otherFeeFormData.endDate',{ rules: [{ required: true, message: '请选择结束日期' }] }]" />
            </a-form-item>
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="金额（元）:">
              <a-input-number style="width: 100%" :min="1" :value="otherFeeFormData.otherFee" @change="amountChange"/>
            </a-form-item>
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注（可选）">
              <a-textarea placeholder="请输入备注" v-model="otherFeeFormData.remark"/>
            </a-form-item>
          </a-modal>
        </div>
      </div>
      <div style="width:100%;text-align:center;margin-top:30px;">
        <a-tabs default-active-key="1">
          <a-tab-pane key="1" tab="固定账单">
            <a-table :columns="billColumns" :data-source="formData.customerContractBills" style="margin: 60px auto;width: 70%;">
              <template
                slot="receiveRentDate"
                slot-scope="text, record"
              >
                <div>
                  <a-date-picker
                    v-if="record.editable"
                    style="width:100%"
                    :defaultValue="text"
                    @change="(data, dateString) => handleChanges(data, dateString, record.key)" />
                  <template v-else>
                    {{ text }}
                  </template>
                </div>
              </template>
              <template
                v-for="col in ['rent', 'managementTotalFee']"
                :slot="col"
                slot-scope="text, record"
              >
                <div :key="col">
                  <a-input
                    v-if="record.editable"
                    style="margin: -5px 0"
                    :value="text"
                    @change="e => handleChange(e.target.value, record.key, col)"
                  />
                  <template v-else>
                    {{ text }}
                  </template>
                </div>
              </template>
              <template slot="operation" slot-scope="text, record">
                <div class="editable-row-operations">
                    <span v-if="record.editable">
                      <a @click="() => save(record.key)">保存</a>
                    </span>
                    <span v-else>
                      <a @click="() => editTable(record.key)">编辑</a>
                    </span>
                </div>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane key="2" tab="其他账单" force-render>
            <a-table :columns="otherBillColumn" :data-source="formData.customerContractOtherBills" style="margin: 60px auto;width: 70%;">
            </a-table>
          </a-tab-pane>
        </a-tabs>
      </div>

      <a-form-item :wrapper-col="{ span: 12, offset: 12 }">
        <a-button style="" @click="createContractBills">生成付款计划</a-button>
        <a-button style="margin-left: 8px" v-if="formData.customerContractBills.length > 0" htmlType="submit" type="primary">提交</a-button>
        <a-button icon="rollback" style="margin-left: 8px" @click="rollback">关闭</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>

<script>
import { getCustomerContract, getCustomerContractSearchList, initNewBill } from '@/api/admin/customerContract'
import { getExpenseSettingsAllList } from '@/api/admin/expenseSettings'
import { saveCustomerContractBill } from '@/api/admin/customerContractBill'
import moment from 'moment'
const dateFormat = 'YYYY-MM-DD'

export default {
  name: 'TableList',
  components: {},
  data () {
    return {
      cacheData: [],
      waterFee: 0,
      powerFee: 0,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      contractList: [],
      expensesList: [],
      otherFeeVisible: false,
      billForm: this.$form.createForm(this),
      formData: {
        contractId: null,
        customerContractBills: [],
        customerContractOthers: [],
        customerContractOtherBills: []
      },
      paymentPeriodMap: [
        {
          id: 'YEAR',
          name: '年付'
        }, {
          id: 'MONTH',
          name: '月付'
        }, {
          id: 'HALF_YEAR',
          name: '半年付'
        }, {
          id: 'SEASON',
          name: '季付'
        }
      ],
      // 其他费项
      otherFeeFormData: {
        expenseName: null,
        expenseSettingsId: null,
        expenseTypeName: null,
        otherFee: null,
        startDate: null,
        endDate: null,
        remark: null,
        billType: null,
        moneyType: null,
        moneyTypeName: null,
        expenseSettings: {
          name: null,
          type: { name: null, value: null }
        }
      },
      billColumns: [
        {
          title: '账单开始时间',
          dataIndex: 'billStartDate',
          scopedSlots: { customRender: 'billStartDate' }
        },
        {
          title: '账单结束时间',
          dataIndex: 'billEndDate',
          scopedSlots: { customRender: 'billEndDate' }
        },
        {
          title: '账单收款日',
          dataIndex: 'receiveRentDate',
          scopedSlots: { customRender: 'receiveRentDate' }
        },
        {
          title: '租金（元）',
          dataIndex: 'rent',
          scopedSlots: { customRender: 'rent' }
        },
        {
          title: '物业管理费（元）',
          dataIndex: 'managementTotalFee',
          scopedSlots: { customRender: 'managementTotalFee' }
        },
        {
          title: '操作',
          dataIndex: 'operation',
          scopedSlots: { customRender: 'operation' }
        }
      ],
      // 表头
      otherFeeColumns: [
        {
          title: '费项名称',
          dataIndex: 'expenseSettings.name'
        },
        {
          title: '费项类型',
          dataIndex: 'expenseSettings.type.name'
        },
        {
          title: '款项类型',
          dataIndex: 'moneyTypeName'
        },
        {
          title: '开始时间',
          dataIndex: 'startDate'
        },
        {
          title: '结束时间',
          dataIndex: 'endDate'
        },
        {
          title: '金额（元）',
          dataIndex: 'otherFee'
        },
        {
          title: '备注',
          dataIndex: 'remark'
        },
        {
          title: '操作',
          width: '130px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
      otherBillColumn: [
        {
          title: '费项名称',
          dataIndex: 'name'
        },
        {
          title: '款项类型',
          dataIndex: 'moneyTypeName'
        },
        {
          title: '账单开始时间',
          dataIndex: 'billStartDate',
          scopedSlots: { customRender: 'billStartDate' }
        },
        {
          title: '账单结束时间',
          dataIndex: 'billEndDate',
          scopedSlots: { customRender: 'billEndDate' }
        },
        {
          title: '账单收款日',
          dataIndex: 'receiveRentDate',
          scopedSlots: { customRender: 'receiveRentDate' }
        },
        {
          title: '费用（元）',
          dataIndex: 'otherFee',
          scopedSlots: { customRender: 'otherFee' }
        }
      ],
      visible: false,
      detailList: { customerContract: {} },
      customerContractExpenses: []

    }
  },
  created () {
    this.onSelectExpenses()
  },
  mounted () {
    if (this.$route.query.id) {
      this.formData.contractId = this.$route.query.id
      getCustomerContract({ id: this.formData.contractId }).then((record) => {
        const contract = Object.assign(record)
        this.billForm.setFieldsValue(
          { 'contractName': contract.name, 'startDate': contract.startDate, 'endDate': contract.endDate })
      })
    }
  },
  methods: {
    handleChange (value, key, column) {
      const newData = [...this.formData.customerContractBills]
      const target = newData.filter(item => key === item.key)[0]
      if (target) {
        target[column] = value
        this.formData.customerContractBills = newData
      }
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.billForm.validateFields((err, values) => {
        if (!err) {
          const values = Object.assign({}, this.formData)
          values.billStartDate = moment(this.billForm.getFieldValue('billStartDate')).format(dateFormat)
          values.billEndDate = moment(this.billForm.getFieldValue('billEndDate')).format(dateFormat)
          values.paymentCycle = this.billForm.getFieldValue('paymentCycle')
          saveCustomerContractBill(values).then(res => {
            console.log(res)
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
    onChangeBillStartDate (date, dateString) {
      const startDate = new Date(dateString)
      const oStartDate = new Date(this.billForm.getFieldValue('startDate'))
      const oEndDate = new Date(this.billForm.getFieldValue('endDate'))
      if (startDate < oStartDate) {
        this.$message.error('账单开始日期不能小于租赁开始日期')
      } else if (startDate > oEndDate) {
        this.$message.error('账单开始日期不能大于租赁结束日期')
      }
    },
    onChangeBillEndDate (date, dateString) {
      const endDate = new Date(dateString)
      const oStartDate = new Date(this.billForm.getFieldValue('startDate'))
      const oEndDate = new Date(this.billForm.getFieldValue('endDate'))
      if (endDate < oStartDate) {
        this.$message.error('账单结束日期不能小于租赁开始日期')
      } else if (endDate > oEndDate) {
        this.$message.error('账单结束日期不能大于租赁结束日期')
      }
    },
    onSelect (input, option) {
      const data = option.key
      this.formData.contractId = data.id
      this.billForm.setFieldsValue({ 'startDate': data.startDate, 'endDate': data.endDate })
    },
    onSearch (searchText) {
      this.contractList = []
      getCustomerContractSearchList({ name: searchText }).then((res) => {
        this.contractList = res.data
      })
    },
    // 选择费项
    onSelectExpenses (e) {
      this.expensesList = []
      // this.otherFeeFormData.setFieldsValue({ expenseSettingsId: '' })
      getExpenseSettingsAllList().then(res => {
        if (res.code === 0) {
          const result = res.rows
          result.forEach(r => {
            this.expensesList.push({ value: r.id, text: r.name })
            if (r.id === e) {
              this.otherFeeFormData.expenseSettings.type.name = r.type.name
              this.otherFeeFormData.expenseSettings.name = r.name
              this.otherFeeFormData.expenseSettingsId = r.id
            }
          })
        }
      })
    },
    handleOk (e) {
      this.formData.customerContractOthers.push(this.otherFeeFormData)
      this.formData.customerContractOthers.forEach((item, index) => {
        item.key = index
      })
      this.otherFeeVisible = false
    },
    onDelete (key) {
      const customerContractOthers = [...this.formData.customerContractOthers]
      this.formData.customerContractOthers = customerContractOthers.filter(item => item.key !== key)
      const customerContractOtherBills = [...this.formData.customerContractOtherBills]
      this.formData.customerContractOtherBills = customerContractOtherBills.filter(item => item.key !== key)
    },
    // 显示其他费项
    showModal () {
      this.otherFeeVisible = true
    },
    moneyTypeChange (e) {
      this.otherFeeFormData.moneyType = e
      if (e === 'IN') {
        this.otherFeeFormData.moneyTypeName = '应收'
      } else {
        this.otherFeeFormData.moneyTypeName = '应退'
      }
    },
    otherFeeStartDateChange (date, dateString) {
      this.otherFeeFormData.startDate = dateString
    },
    otherFeeEndDateChange (date, dateString) {
      this.otherFeeFormData.endDate = dateString
    },
    amountChange (e) {
      this.otherFeeFormData.otherFee = e
    },
    createContractBills () {
      this.billForm.validateFields((err, values) => {
        if (!err) {
          values.contractId = this.formData.contractId
          values.billStartDate = moment(values.billStartDate).format(dateFormat)
          values.billEndDate = moment(values.billEndDate).format(dateFormat)
          console.log('values:', values)
          // 生成固定账单
          initNewBill(values).then(res => {
            console.log('res:', res)
            res.data.forEach((item, index) => {
              item.key = index
            })
            this.formData.customerContractBills = res.data
            this.cacheData = this.formData.customerContractBills.map(item => ({ ...item }))
          })
        }
      })
      // 生成其他账单
      this.formData.customerContractOthers.forEach(r => {
        const otherBill = {}
        otherBill.billStartDate = r.startDate
        otherBill.billEndDate = r.endDate
        otherBill.receiveRentDate = r.startDate
        otherBill.otherFee = r.otherFee
        otherBill.name = r.expenseSettings.name
        otherBill.moneyType = r.moneyType
        otherBill.moneyTypeName = r.moneyTypeName
        otherBill.key = r.key
        this.formData.customerContractOtherBills.push(otherBill)
      })
    },

    save (key) {
      const newData = [...this.formData.customerContractBills]
      const newCacheData = [...this.cacheData]
      const target = newData.filter(item => key === item.key)[0]
      const targetCache = newCacheData.filter(item => key === item.key)[0]
      if (target && targetCache) {
        delete target.editable
        this.formData.customerContractBills = newData
        Object.assign(targetCache, target)
        this.cacheData = newCacheData
      }
    },
    editTable (key) {
      const newData = [...this.formData.customerContractBills]
      newData[key].editable = true
      this.formData.customerContractBills = newData
    },
    handleChanges (data, dateString, key) {
      const newData = [...this.formData.customerContractBills]
      const target = newData.filter(item => key === item.key)[0]
      if (target) {
        target['receiveRentDate'] = dateString
        this.formData.customerContractBills = newData
      }
    },
    rollback () {
      this.$router.push('/admin/fund/contract')
    }
  }
}
</script>

<style>
  .ant-col{
    margin-bottom: 10px;
  }
</style>
