import { axios } from '@/utils/request'

const api = {
  customerContractBill: '/admin/contract/bill'
}

export function getCustomerContractBillList (parameter) {
  return axios({
    url: api.customerContractBill + '/list',
    method: 'post',
    data: parameter
  })
}

export function saveCustomerContractBill (parameter) {
  return axios({
    url: api.customerContractBill + (parameter.billId > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function saveOtherBill (parameter) {
  return axios({
    url: api.customerContractBill + '/batchSave',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function confirmReceiveRent (parameter) {
  return axios({
    url: api.customerContractBill + '/confirmReceiveRent',
    method: 'post',
    params: parameter
  })
}

export function batchConfirmReceiveAmount (parameter) {
  return axios({
    url: api.customerContractBill + '/batch_confirm_receive_amount',
    method: 'post',
    params: parameter
  })
}

export function confirmReceiveAmount (parameter) {
  return axios({
    url: api.customerContractBill + '/confirm_receive_amount',
    method: 'post',
    params: parameter
  })
}

// 确认收到其他费用
export function confirmReceiveOtherAmount (parameter) {
  return axios({
    url: api.customerContractBill + '/confirm_receive_other_amount',
    method: 'post',
    params: parameter
  })
}

export function delCustomerContractBill (parameter) {
  return axios({
    url: api.customerContractBill + '/remove',
    method: 'post',
    params: parameter
  })
}

export function cancelConfirm (parameter) {
  return axios({
    url: api.customerContractBill + '/cancelConfirm',
    method: 'post',
    params: parameter
  })
}

export function getCustomerContractBill (parameter) {
  return axios({
    url: api.customerContractBill + '/get',
    method: 'get',
    params: parameter
  })
}

export function updateCustomerContractBill (parameter) {
  return axios({
    url: api.customerContractBill + '/update',
    method: 'POST',
    data: parameter
  })
}

export function reportCustomerContractBill (parameter) {
  return axios({
    url: api.customerContractBill + '/report/list',
    method: 'GET',
    params: parameter
  })
}

export function customerContractBillConfirmDetail (parameter) {
  return axios({
    url: api.customerContractBill + '/confirm_detail',
    method: 'GET',
    params: parameter
  })
}

export function importPowerWaterFee (parameter) {
  return axios({
    url: api.customerContractBill + '/importPowerWaterFee',
    method: 'post',
    params: parameter
  })
}

export function importRent (parameter) {
  return axios({
    url: api.customerContractBill + '/import_rent',
    method: 'post',
    params: parameter
  })
}

export const customerContractBillExport = api.customerContractBill + '/export'

export const exportFileRequest = (params) => {
  return axios({
    method: 'get',
    url: api.customerContractBill + '/export',
    params: params,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export const exportPowerWaterFee = (params) => {
  return axios({
    method: 'get',
    url: api.customerContractBill + '/exportPowerWaterFee',
    params: params,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export const exportRent = (params) => {
  return axios({
    method: 'get',
    url: api.customerContractBill + '/export_rent',
    params: params,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export const reportExportFileRequest = (params) => {
  return axios({
    method: 'get',
    url: api.customerContractBill + '/report/export',
    params: params,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
