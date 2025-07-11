import { axios } from '@/utils/request'

const api = {
  serviceOrder: '/admin/order'
}

export function getServiceOrderList (parameter) {
  return axios({
    url: api.serviceOrder + '/list',
    method: 'get',
    data: parameter
  })
}

export function saveServiceOrder (parameter) {
  return axios({
    url: api.serviceOrder + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delServiceOrder (parameter) {
  return axios({
    url: api.serviceOrder + '/remove',
    method: 'post',
    params: parameter
  })
}

export const serviceOrderExport = api.serviceOrder + '/export'
