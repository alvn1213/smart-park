import { axios } from '@/utils/request'

const api = {
  customerContractRefund: '/admin/customerContractRefund'
}

export function getCustomerContractRefundList (parameter) {
  return axios({
    url: api.customerContractRefund + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveCustomerContractRefund (parameter) {
  return axios({
    url: api.customerContractRefund + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function customerContractRefundSave (parameter) {
  return axios({
    url: api.customerContractRefund + (parameter.createBy === undefined ? '/save' : '/update'),
    method: 'post',
    data: parameter
  })
}

export function getCustomerContractRefund (parameter) {
  return axios({
    url: api.customerContractRefund + '/get',
    method: 'get',
    params: parameter
  })
}

export function customerContractCancel (parameter) {
  return axios({
    url: api.customerContractRefund + '/determine',
    method: 'POST',
    params: parameter
  })
}

export function delCustomerContractRefund (parameter) {
  return axios({
    url: api.customerContractRefund + '/remove',
    method: 'post',
    params: parameter
  })
}

export const customerContractRefundExport = api.customerContractRefund + '/export'
