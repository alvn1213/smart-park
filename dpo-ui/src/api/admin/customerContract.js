import { axios } from '@/utils/request'

const api = {
  customerContract: '/admin/contract'
}

export function getCustomerContractList (parameter) {
  return axios({
    url: api.customerContract + '/list',
    method: 'post',
    data: parameter
  })
}

export function getCustomerContractSearchList (parameter) {
  return axios({
    url: api.customerContract + '/searchList',
    method: 'get',
    params: parameter
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

export function initNewBill (parameter) {
  return axios({
    url: '/admin/contract/bill/init_new_bill',
    method: 'POST',
    params: parameter
  })
}

export function customerContractCommit (parameter) {
  return axios({
    url: api.customerContract + '/commit',
    method: 'POST',
    params: parameter
  })
}

export function customerContractApprove (parameter) {
  return axios({
    url: api.customerContract + '/approve',
    method: 'POST',
    params: parameter
  })
}

export function customerContractCancel (parameter) {
  return axios({
    url: api.customerContract + '/cancel',
    method: 'POST',
    params: parameter
  })
}

export function customerContractChange (parameter) {
  return axios({
    url: api.customerContract + '/change',
    method: 'POST',
    data: parameter
  })
}

export const customerContractExport = api.customerContract + '/export'
