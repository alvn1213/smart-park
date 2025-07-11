import { axios } from '@/utils/request'

const api = {
  serviceSupplier: '/admin/supplier'
}

export function getServiceSupplierList (parameter) {
  return axios({
    url: api.serviceSupplier + '/list',
    method: 'get',
    params: parameter
  })
}

export function getAllServiceSupplierList (parameter) {
  return axios({
    url: api.serviceSupplier + '/allList',
    method: 'get',
    params: parameter
  })
}

export function saveServiceSupplier (parameter) {
  return axios({
    url: api.serviceSupplier + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delServiceSupplier (parameter) {
  return axios({
    url: api.serviceSupplier + '/remove',
    method: 'post',
    params: parameter
  })
}

export function getServiceSupplier (parameter) {
  return axios({
    url: api.serviceSupplier + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export const serviceSupplierExport = api.serviceSupplier + '/export'
