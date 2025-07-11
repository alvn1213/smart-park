import { axios } from '@/utils/request'

const api = {
  customer: '/admin/customer', // 客户管理
  customerContacts: 'admin/contacts', // 客户管理-联系人
  customerAttachments: '/admin/attachments' // 客户管理-相关文件
}

export function getCustomerList (parameter) {
  return axios({
    url: api.customer + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveCustomer (parameter) {
  return axios({
    url: api.customer + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delCustomer (parameter) {
  return axios({
    url: api.customer + '/remove',
    method: 'post',
    params: parameter
  })
}

export function getCustomer (parameter) {
  return axios({
    url: api.customer + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export function getQiXinBasicInfo (parameter) {
  return axios({
    url: api.customer + '/getBasicInfo',
    method: 'post',
    params: parameter
  })
}

export function getCustomerContacts (parameter) {
  return axios({
    url: api.customerContacts + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export function getCustomerContactsList (parameter) {
  return axios({
    url: api.customerContacts + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveCustomerContacts (parameter) {
  return axios({
    url: api.customerContacts + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delCustomerContacts (parameter) {
  return axios({
    url: api.customerContacts + '/remove',
    method: 'post',
    params: parameter
  })
}

export function getCustomerAttachmentsList (parameter) {
  return axios({
    url: api.customerAttachments + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveCustomerAttachments (parameter) {
  return axios({
    url: api.customerAttachments + '/save',
    method: 'post',
    data: parameter
  })
}

export function delCustomerAttachments (parameter) {
  return axios({
    url: api.customerAttachments + '/remove',
    method: 'post',
    params: parameter
  })
}

export const customerExport = api.customer + '/export'
