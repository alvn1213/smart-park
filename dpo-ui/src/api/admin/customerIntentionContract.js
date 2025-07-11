import { axios } from '@/utils/request'

const api = {
  customerContract: '/admin/intentionContract'
}
export function getCustomerIntententionContractList (parameter) {
  return axios({
    url: api.customerContract + '/list',
    method: 'post',
    data: parameter
  })
}

export function saveCustomerContract (parameter) {
  return axios({
    url: api.customerContract + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delCustomerContract (parameter) {
  return axios({
    url: api.customerContract + '/remove',
    method: 'post',
    params: parameter
  })
}

export function getCustomerContract (parameter) {
  return axios({
    url: api.customerContract + '/get',
    method: 'get',
    params: parameter
  })
}

export function getCustomerallList (parameter) {
  return axios({
    url: '/admin/customer/allList',
    method: 'GET',
    params: parameter
  })
}

export function customerContractSave (parameter) {
  return axios({
    url: api.customerContract + '/save',
    method: 'post',
    data: parameter
  })
}

export function customerContractUpdate (parameter) {
  return axios({
    url: api.customerContract + '/update',
    method: 'post',
    data: parameter
  })
}

export function customerUpdateStatus (parameter) {
  return axios({
    url: api.customerContract + '/updateStatus',
    method: 'post',
    data: parameter
  })
}

export function findByCustomerId (parameter) {
  return axios({
    url: '/admin/customer/findByCustomerId',
    method: 'GET',
    params: parameter
  })
}

export function initBill (parameter) {
  return axios({
    url: '/admin/contract/bill/init_bill',
    method: 'GET',
    params: parameter
  })
}

export const customerContractExport = api.customerContract + '/export'
