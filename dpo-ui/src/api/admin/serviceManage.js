import { axios } from '@/utils/request'

const api = {
  serviceManage: '/admin/manage'
}

export function getServiceManageList (parameter) {
  return axios({
    url: api.serviceManage + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveServiceManage (parameter) {
  return axios({
    url: api.serviceManage + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delServiceManage (parameter) {
  return axios({
    url: api.serviceManage + '/remove',
    method: 'post',
    params: parameter
  })
}

export function getServiceManage (parameter) {
  return axios({
    url: api.serviceManage + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export function changMarketable (parameter) {
  return axios({
    url: api.serviceManage + '/changeMarketable',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export const serviceManageExport = api.serviceManage + '/export'
