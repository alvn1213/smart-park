<template>
  <a-card :body-style="{ padding: '24px 32px' }" :bordered="false">
    <a-steps :current="step" style="margin-bottom: 20px">
      <a-step title="填写基础信息" />
      <a-step title="填写费项信息" />
      <a-step title="完成" />
    </a-steps>
    <a-form @submit="handleSubmit" :form="form" :label-col="{ span: 4 }" :wrapper-col="{ span: 6 }">
      <a-form-item style="display: none">
        <a-input v-decorator="['id']" />
      </a-form-item>
      <div v-if="step == 0">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="意向名称">
          <a-input
            style="width: 60%"
            placeholder="请输入意向名称"
            v-decorator="['name', { rules: [{ required: true, message: '请输入意向名称' }] }]"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="备注（可选）">
          <a-textarea placeholder="请输入备注" :rows="5" v-decorator="['remark']" />
        </a-form-item>
        <a-divider orientation="left">乙方</a-divider>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="租赁方">
          <a-radio-group name="lessorType" :default-value="formData.lessorType" @change="lessorTypeChange">
            <a-radio :value="0"> 公司租赁 </a-radio>
            <a-radio :value="1"> 个人租赁 </a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="客户名称" extra="不要超过30个字符">
          <a-auto-complete
            v-decorator="['customerName', { rules: [{ required: true, message: '请输入客户名称' }] }]"
            :data-source="dataSource"
            style="width: 60%"
            placeholder="请输入客户名称"
            optionLabelProp="value"
            @select="onSelect"
            @search="onSearch"
            :disabled="disabled"
          >
            <template slot="dataSource">
              <a-select-option v-for="item in dataSource" :key="item" :value="item.name">
                {{ item.name }}</a-select-option
              >
            </template>
          </a-auto-complete>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="所属行业">
          <a-input
            style="width: 60%"
            placeholder="请输入所属行业"
            v-decorator="['sector', { rules: [{ required: true, message: '请输入所属行业' }] }]"
          />
        </a-form-item>
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          :label="formData.lessorType === 1 ? '身份证号' : '企业信用代码'"
        >
          <a-input
            style="width: 60%"
            :placeholder="formData.lessorType === 1 ? '身份证号' : '企业信用代码'"
            v-decorator="['creditNo', { rules: [{ required: true }] }]"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="个人邮箱">
          <a-input
            style="width: 60%"
            placeholder="请输入个人邮箱"
            v-decorator="['email', { rules: [{ message: '请输入个人邮箱' }] }]"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="法人">
          <a-input
            style="width: 60%"
            placeholder="请输入法人"
            v-decorator="['operName', { rules: [{ required: true, message: '请输入法人' }] }]"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="联系电话">
          <a-input
            style="width: 60%"
            placeholder="请输入联系电话"
            v-decorator="['phone', { rules: [{ message: '请输入联系电话' }] }]"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="通讯地址">
          <a-input
            style="width: 60%"
            placeholder="请输入通讯地址"
            v-decorator="['address', { rules: [{ required: true, message: '请输入通讯地址' }] }]"
          />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="渠道名称">
          <a-input
            style="width: 60%"
            placeholder="请输入渠道名称"
            v-decorator="['channelName', { rules: [{ message: '请输入渠道名称' }] }]"
          />
        </a-form-item>
        <a-divider orientation="left">选择租赁场地</a-divider>
        <div class="table-operator">
          <a-button type="primary" icon="plus" @click="$refs.child1Ref.show()">选择房间</a-button>
          <a-dropdown v-if="removeEnable && selectedRowKeys.length > 0">
            <a-button type="danger" icon="delete" @click="delByIds(selectedRowKeys)">删除</a-button>
          </a-dropdown>
        </div>
        <a-form-item :wrapper-col="{ span: 24 }">
          <a-table
            :pagination="false"
            :columns="columns"
            rowKey="pid"
            :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
            :dataSource="formData.customerContractRooms"
          >
          </a-table>
        </a-form-item>
        <div style="width:100%;text-align:center;">
          <a-button key="buy" @click="navBack">返回</a-button>
          <a-button type="primary" style="margin-left: 8px" html-type="submit">下一步</a-button>
        </div>
      </div>
      <div v-if="step == 1">
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="签订日期：">
          <a-date-picker style="width: 40%" @change="signDateChange" v-decorator="['sign',{ rules: [{ required: true, message: '请选择签订日期' }] }]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="意向需求日期：">
          <a-date-picker style="width: 19%" v-decorator="['needStartDate',{ rules: [{ required: true, message: '请选择意向需求开始日期' }] }]" />
          <span> ~ </span>
          <a-date-picker style="width: 19%" v-decorator="['needEndDate',{ rules: [{ required: true, message: '请选择意向需求结束日期' }] }]" />
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="意向金">
          <a-input-number
            v-decorator="['intentionMoney', { rules: [{ required: true, message: '请填写意向金' }] }]"
            :min="0"
          /><span class="ant-form-text">元</span>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="租赁期限：">
          <a-date-picker style="width: 19%" @change="startDateChange" v-decorator="['startDate',{ rules: [{ required: true, message: '请选择租赁开始日期' }] }]" />
          <span> ~ </span>
          <a-date-picker style="width: 19%" @change="endDateChange" v-decorator="['endDate',{ rules: [{ required: true, message: '请选择租赁结束日期' }] }]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="快捷选择">
          <a-radio-group default-value="0" button-style="solid" @change="radiogroupChange">
            <a-radio-button v-for="(item, index) in chose" :key="index" :value="item.value">{{ item.text }}</a-radio-button>
          </a-radio-group>
        </a-form-item>

        <div>固定租金</div>
        <div style="border: 1px solid #dddddd; width: 70%; margin: 0 auto">
          <div
            style="
              text-align: center;
              border-bottom: 1px solid #dddddd;
              padding: 20px 0;
              background-image: linear-gradient(to top, #ededed, #fbfbfb);
            "
          >
            <span style="margin-left: -40px">计算方式：<span>按单元面积</span></span>
            <span style="margin-left: 20px">租赁面积：<span>{{ formData.rentArea }}</span></span>
          </div>

          <div style="border-bottom: 1px solid #dddddd; display: flex">
            <div style="flex: 1; border-right: 1px solid #dddddd">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="租金单价："
                style="margin: 0; padding: 10px 0"
              >
                <a-input-number
                  v-decorator="['rentPrice', { rules: [{ required: true, message: '请填写租金单价' }] }]"
                  @change="getRentPrice"
                  :min="1"
                />
                <a-select :value="formData.rentPriceUnit" style="width: 120px" @change="handleChange">
                  <a-select-option value="0">元/平米/天</a-select-option>
                  <a-select-option value="1">元/平米/月</a-select-option>
                </a-select>
              </a-form-item>
            </div>
            <div style="flex: 1">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="日租金："
                style="margin: 0; padding: 10px 0"
              >
                <a-input-number
                  v-decorator="['rentIncreaseDay', { rules: [{ required: true, message: '请填写日租金' }] }]"
                  :min="1"
                />
                <span class="ant-form-text">元/天</span>
              </a-form-item>
            </div>
          </div>

          <div style="border-bottom: 1px solid #dddddd; display: flex">
            <div style="flex: 1">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="月租金计算方式："
                style="margin: 0; padding: 10px 0"
              >
                <a-radio-group v-model="formData.rentIncreaseWay" @change="radioChange">
                  <a-radio :value="0">按固定租金</a-radio>
                  <a-radio :value="1">按实际天数</a-radio>
                </a-radio-group>
              </a-form-item>
            </div>
            <div style="flex: 1">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="月租金："
                style="margin: 0; padding: 10px 0"
              >
                <a-input-number
                  v-decorator="['rentIncreaseMonth', { rules: [{ required: true, message: '请填写月租金' }] }]"
                  :min="1"
                />
                <span class="ant-form-text">元/月</span>
              </a-form-item>
            </div>
          </div>

          <div style="border-bottom: 1px solid #dddddd; display: flex">
            <div style="flex: 1">
              <a-form-item
                :labelCol="{ xs: { span: 24 }, sm: { span: 10 } }"
                :wrapperCol="{ xs: { span: 24 }, sm: { span: 12 } }"
                label="管理费单价："
                style="margin: 0; padding: 10px 0"
              >
                <a-input-number
                  v-decorator="['managementFee', { initialValue: 3, rules: [{ required: true, message: '请填写管理费单价' }] }]"
                  @change="getManagementFee"
                  :min="1"
                />
                <a-select :value="formData.managementFeeUnit" style="width: 120px" @change="managementFeeUnitChange">
                  <a-select-option value="0">元/平米/天</a-select-option>
                  <a-select-option value="1">元/平米/月</a-select-option>
                </a-select>
              </a-form-item>
            </div>
          </div>

          <div style="border-bottom: 1px solid #dddddd; display: flex">
            <div style="flex: 1;">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="日管理费："
                style="margin: 0; padding: 10px 0"
              >
                <a-input-number
                  v-decorator="['managementFeeIncreaseDay', { initialValue: ((3 * formData.rentArea) / 30).toFixed(2), rules: [{ required: true, message: '请填写日管理费' }] }]"
                  :min="1"
                />
                <span class="ant-form-text">元/天</span>
              </a-form-item>
            </div>
            <div style="flex: 1">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="月管理费："
                style="margin: 0; padding: 10px 0"
              >
                <a-input-number
                  v-decorator="['managementFeeIncreaseMonth', { initialValue: (3 * formData.rentArea).toFixed(2), rules: [{ required: true, message: '请填写月管理费：' }] }]"
                  :min="1"
                />
                <span class="ant-form-text">元/月</span>
              </a-form-item>
            </div>
          </div>

          <div style="display: flex">
            <div style="flex: 1; border-right: 1px solid #dddddd">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="付款周期："
                style="margin: 0; padding: 10px 0"
              >
                <a-select ref="paymentPeriod" v-decorator="['paymentPeriod', {rules: [{ required: true, message: '请选择付款周期' }]}]">
                  <a-select-option v-for="item in paymentPeriodMap" :key="item" :value="item.id">
                    {{ item.name }}
                  </a-select-option
                  >
                </a-select>
              </a-form-item>
            </div>
            <div style="flex: 1;">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="押付方式："
                style="margin: 0; padding: 10px 0"
              >
                <span class="ant-form-text">押</span>
                <a-input-number v-decorator="['depositPeriod', { initialValue: 1, rules: [{ required: true }] }]" :min="1" />
                <span class="ant-form-text">月</span>
                <a-input-number v-decorator="['deposit', { initialValue: 2000, rules: [{ required: true }] }]" :min="2000" />
                <span class="ant-form-text">元</span>
              </a-form-item>
            </div>
          </div>
        </div>
        <a-table v-if="formData.customerContractBills.length > 0" :columns="columnss" :data-source="formData.customerContractBills" :loading="tableloading" style="margin: 60px auto;width: 70%;">
          <template
            slot="billEndDate"
            slot-scope="text, record"
          >
            <div :key="col">
              <a-date-picker
                v-if="record.editable"
                style="width:100%"
                :defaultValue="text"
                @change="(data, dateString) => handleChangesa(data, dateString, record.key)" />
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
                <a @click="() => edittable(record.key)">编辑</a>
              </span>
            </div>
          </template>
        </a-table>

        <div style="width:100%;text-align:center;margin-top:30px;">
          <a-button key="buy" @click="navBack">返回</a-button>
          <a-button style="margin-left: 8px" html-type="submit" type="primary" v-if="formData.customerContractBills.length > 0">提交</a-button>
          <a-button style="margin-left: 8px" @click="back">上一步</a-button>
          <a-button style="margin-left: 8px" @click="createcustomerContractBills">生成付款计划</a-button>
        </div>
      </div>
      <div v-if="step == 2">
        <a-result status="success" title="操作成功">
          <template #extra>
            <a-button key="buy" @click="navBack">返回</a-button>
          </template>
        </a-result>
      </div>
    </a-form>
    <RoomListModal ref="child1Ref" @fatherMethod="fatherMethod"></RoomListModal>
  </a-card>
</template>

<script>
import moment from 'moment'
import storage from 'store'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import WangEditor from '@/components/Editor/WangEditor.vue'
import pick from 'lodash.pick'
import RoomListModal from '@/views/admin/modules/RoomListModal'
import {
  getCustomerallList,
  customerContractSave,
  getCustomerContract,
  customerContractUpdate,
  findByCustomerId,
  initBill
} from '@/api/admin/customerIntentionContract'
import { customerContractChange } from '@/api/admin/customerContract'

export default {
  name: 'BaseForm',
  components: {
    RoomListModal,
    WangEditor
  },
  data () {
    return {
      tableloading: false,
      updateDate: '',
      chose: [
        { text: '1年', value: '0' },
        { text: '2年', value: '1' },
        { text: '3年', value: '2' },
        { text: '4年', value: '3' },
        { text: '5年', value: '4' }
      ],
      columnss: [
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
          title: '租金',
          dataIndex: 'rent',
          scopedSlots: { customRender: 'rent' }
        },
        {
          title: '物业管理费',
          dataIndex: 'managementTotalFee'
        },
        {
          title: '操作',
          dataIndex: 'operation',
          scopedSlots: { customRender: 'operation' }
        }
      ],
      disabled: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      uploadUrl: process.env.VUE_APP_API_BASE_URL + '/dfs/upload',
      headers: {
        token: storage.get(ACCESS_TOKEN)
      },
      loading: false,
      imageUrl: '',
      previewVisible: false,
      previewImage: '',
      fileList: [],
      options: [],
      city: [],
      step: 0,
      // form
      form: this.$form.createForm(this),
      itemsColumns: [
        {
          titles: '房间名称',
          dataIndex: 'name',
          x: '3'
        },
        {
          titles: '所属园区',
          dataIndex: 'park.name',
          x: '8'
        }
      ],
      // 表头
      columns: [
        {
          title: '房间名称',
          dataIndex: 'name'
        },
        {
          title: '所属园区',
          dataIndex: 'park.name'
        },
        {
          title: '所属楼宇',
          dataIndex: 'building.buildingName'
        },
        {
          title: '所属楼层',
          dataIndex: 'buildingDetail.floorName'
        },
        {
          title: '收租面积（平方米）',
          dataIndex: 'rentArea'
        }
      ],
      id: '',
      url: '',
      customerId: '',
      dataSource: [],
      paymentPeriodMap: [
        {
          id: 1,
          name: '年付'
        }, {
          id: 2,
          name: '月付'
        }, {
          id: 3,
          name: '半年付'
        }, {
          id: 4,
          name: '季付'
        }
      ],
      formData: {
        type: 0,
        lessorType: 0,
        rentPriceUnit: '1',
        managementFeeUnit: '1',
        rentIncreaseWay: 0,
        dateString: '',
        startDate: '',
        endDate: '',
        customerContractRooms: [],
        customerContractBills: []
      }
    }
  },
  mounted () {
    this.form.setFieldsValue({ depositPeriod: 1, deposit: 2000 })
    if (this.$route.query.id) {
      this.disabled = true
      this.id = this.$route.query.id
    }
    if (this.$route.query.customerId) {
      this.disabled = true
      findByCustomerId({ customerId: this.$route.query.customerId }).then((res) => {
        console.log(res)
        this.mdl = Object.assign(res.data)
        this.mdl.customerName = this.mdl.name
        this.mdl.email = this.mdl.mail
        this.formData.customerId = this.mdl.id
        this.$nextTick(() => {
          this.form.setFieldsValue(
            pick(
              this.mdl,
              'customerId',
              'customerName',
              'sector',
              'creditNo',
              'email',
              'operName',
              'phone',
              'address',
              'channelName'
            )
          )
        })
      })
    }
    if (this.$route.query.url) {
      this.url = this.$route.query.url
    }
    getCustomerallList().then((res) => {
      this.dataSource = res.data
    })
    this.handleInit()
  },
  methods: {
    moment,
    radiogroupChange (e) {
      var d2 = new Date(this.formData.startDate)
      d2.setFullYear(d2.getFullYear() + (parseInt(e.target.value) + 1))
      d2.setDate(d2.getDate() - 1)
      var d = new Date(d2)
      d = d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate()
      this.form.setFieldsValue({ endDate: d })
      this.formData.endDate = d
    },
    handleChangesa (data, dateString, key) {
      this.updateDate = dateString
    },
    edittable (key) {
      this.colseall()
      const newData = [...this.formData.customerContractBills]
      newData[key].editable = true
      this.formData.customerContractBills = newData
    },
    colseall () {
      this.updateDate = ''
      const newData = [...this.formData.customerContractBills]
      newData.forEach((item, index) => {
        item.editable = false
      })
      this.formData.customerContractBills = newData
    },
    save (key) {
      const newData = [...this.formData.customerContractBills]
      const target = newData.filter(item => key === item.key)[0]
      this.editingKey = key
      if (target) {
        target.editable = false
        target.billEndDate = this.updateDate
        this.formData.customerContractBills = newData
      }
      this.createcustomerContractBills()
    },
    createcustomerContractBills () {
      this.tableloading = true
      var data = {
        deposit: this.form.getFieldValue('deposit'),
        rentIncreaseMonth: this.form.getFieldValue('rentIncreaseMonth'),
        rentIncreaseDay: this.form.getFieldValue('rentIncreaseDay'),
        managementFeeIncreaseMonth: this.form.getFieldValue('managementFeeIncreaseMonth'),
        managementFeeIncreaseDay: this.form.getFieldValue('managementFeeIncreaseDay'),
        receiveRentDay: '1',
        startDate: this.formData.startDate,
        endDate: this.formData.endDate,
        updateDate: this.updateDate
      }
      if (data.startDate === '') {
        this.$message.warning('请先选择租赁期限开始时间')
        return false
      }
      if (data.endDate === '') {
        this.$message.warning('请先选择租赁期限结束时间')
        return false
      }
      if (!data.receiveRentDay) {
        this.$message.warning('请先填写收款日')
        return false
      }
      if (!data.rentIncreaseMonth) {
        this.$message.warning('请先填月租金')
        return false
      }
      if (!data.rentIncreaseDay) {
        this.$message.warning('请先填写日租金')
        return false
      }
      if (!data.deposit) {
        this.$message.warning('请先填写押金')
        return false
      }
      initBill(data).then(res => {
        res.data.forEach((item, index) => {
          item.key = index
        })
        this.formData.customerContractBills = res.data
        this.tableloading = false
      })
    },
    getRentPrice (e) {
      if (this.formData.rentPriceUnit === '0') {
        const rentArea = this.formData.rentArea
        const num = (e * rentArea) * 30
        this.form.setFieldsValue({
          rentIncreaseMonth: num.toFixed(2),
          deposit: num.toFixed(2),
          rentIncreaseDay: (e * rentArea).toFixed(2)
        })
      } else {
        const rentArea = this.formData.rentArea
        const num = (e * rentArea) / 30
        this.form.setFieldsValue({
          rentIncreaseMonth: (e * rentArea).toFixed(2),
          deposit: (e * rentArea).toFixed(2),
          rentIncreaseDay: num.toFixed(2)
        })
      }
    },
    getManagementFee (e) {
      if (this.formData.managementFeeUnit === '0') {
        const rentArea = this.formData.rentArea
        const num = (e * rentArea) * 30
        this.form.setFieldsValue({
          managementFeeIncreaseMonth: num.toFixed(2),
          managementFeeIncreaseDay: (e * rentArea).toFixed(2)
        })
      } else {
        const rentArea = this.formData.rentArea
        const num = (e * rentArea) / 30
        this.form.setFieldsValue({
          managementFeeIncreaseMonth: (e * rentArea).toFixed(2),
          managementFeeIncreaseDay: num.toFixed(2)
        })
      }
    },
    navBack (e) {
      this.$router.push({ name: this.url, query: { types: '1', customerId: this.$route.query.customerId } })
    },
    onSearch (searchText) {
      console.log(searchText)
      getCustomerallList({ name: searchText }).then((res) => {
        this.dataSource = res.data
      })
    },
    onSelect (input, option) {
      const data = option.key
      this.formData.customerId = data.id
      this.form.setFieldsValue({
        sector: data.sector,
        creditNo: data.creditNo,
        email: data.mail,
        operName: data.operName,
        phone: data.phone,
        address: data.address,
        channelName: data.channelName
      })
    },
    signDateChange (date, dateString) {
      this.formData.signDate = dateString
    },
    startDateChange (date, dateString) {
      this.formData.startDate = dateString
    },
    endDateChange (date, dateString) {
      console.log(dateString)
      this.formData.endDate = dateString
    },
    lessorTypeChange (e) {
      this.formData.lessorType = e.target.value
    },
    typeChange (e) {
      this.formData.type = e.target.value
    },
    radioChange (e) {
      this.formData.rentIncreaseWay = e.target.value
    },
    fatherMethod (res) {
      this.formData.customerContractRooms = res
      let rentArea = 0
      res.forEach(item => {
        rentArea += item.rentArea
      })
      this.formData.rentArea = rentArea
    },
    next () {
      this.step++
    },
    back () {
      this.step--
      if (this.id) {
        this.handleInit()
      }
    },
    handleInit () {
      if (this.id) {
        getCustomerContract({ id: this.id }).then((record) => {
          this.mdl = Object.assign(record)
          if (this.step === 0) {
            this.formData.lessorType = record.lessorType
            this.formData.type = record.type
            this.formData.customerId = record.customerId
            const arr = []
            record.customerContractRooms.forEach((item) => {
              arr.push(item)
            })
            let rentArea = 0
            arr.forEach(item => {
              rentArea += item.rentArea
            })
            this.formData.rentArea = rentArea
            this.formData.customerContractRooms = arr
            this.$nextTick(() => {
              this.form.setFieldsValue(
                pick(
                  this.mdl,
                  'id',
                  'customerId',
                  'name',
                  'sn',
                  'customerName',
                  'sector',
                  'creditNo',
                  'email',
                  'operName',
                  'phone',
                  'address',
                  'channelName',
                  'remark'
                )
              )
            })
          } else if (this.step === 1) {
            record.customerContractBills.forEach((item, index) => {
              item.key = index
            })
            this.$nextTick(() => {
              this.formData.rentPriceUnit = record.rentPriceUnit
              this.formData.managementFeeUnit = record.managementFeeUnit
              this.formData.rentIncreaseWay = record.rentIncreaseWay
              this.formData.signDate = record.signDate
              this.formData.startDate = record.startDate
              this.formData.needEndDate = record.needEndDate
              this.formData.needStartDate = record.needStartDate
              this.formData.endDate = record.endDate
              this.formData.customerContractBills = record.customerContractBills
              this.mdl.sign = record.signDate
              this.form.setFieldsValue(
                pick(
                  this.mdl,
                  'rentPrice',
                  'rentIncreaseDay',
                  'rentIncreaseMonth',
                  'managementFee',
                  'managementFeeIncreaseDay',
                  'managementFeeIncreaseMonth',
                  'paymentPeriod',
                  'rentOverduePercent',
                  'depositPeriod',
                  'deposit',
                  'depositOverduePercent',
                  'receiveRentDay',
                  'sign',
                  'startDate',
                  'needEndDate',
                  'needStartDate',
                  'endDate',
                  'intentionMoney'
                )
              )
            })
          }
        })
      }
    },
    handleChange (value) {
      this.formData.rentPriceUnit = value
      this.form.setFieldsValue({ rentPrice: '' })
      this.form.setFieldsValue({ rentIncreaseDay: '' })
      this.form.setFieldsValue({ rentIncreaseMonth: '' })
    },
    managementFeeUnitChange (value) {
      this.formData.managementFeeUnit = value
      this.form.setFieldsValue({ managementFee: '' })
      this.form.setFieldsValue({ managementFeeIncreaseDay: '' })
      this.form.setFieldsValue({ managementFeeIncreaseMonth: '' })
    },
    handleCancel () {
      this.previewVisible = false
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (this.step === 0) {
          if (!err) {
            if (this.formData.customerContractRooms.length > 0) {
              const arr = []
              this.formData.customerContractRooms.forEach((item) => {
                arr.push({ id: item.id })
              })
              this.formData.customerContractRooms = arr
              Object.assign(this.formData, values)
              this.step++
              this.handleInit()
            } else {
              this.$message.warning('请先选择租赁场地')
            }
          }
        } else if (this.step === 1) {
          if (!err) {
            const formData = this.formData
            const data = Object.assign({}, formData, values)
            data.startDate = formData.startDate
            data.endDate = formData.endDate
            if (this.id) {
              if (this.$route.query.change) {
                customerContractChange(data).then((res) => {
                  if (res.code === 0) {
                    this.step++
                  } else {
                    this.$message.warning(res.msg)
                  }
                })
              } else {
                customerContractUpdate(data).then((res) => {
                  if (res.code === 0) {
                    this.step++
                  } else {
                    this.$message.warning(res.msg)
                  }
                })
              }
            } else {
              customerContractSave(data).then((res) => {
                if (res.code === 0) {
                  this.step++
                } else {
                  this.$message.warning(res.msg)
                }
              })
            }
          }
        }
      })
    },
    rollback () {
      this.$router.push('/admin/dj/djBanner')
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
