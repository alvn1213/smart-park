import { axios } from '@/utils/request'

const api = {
  serviceMenu: '/admin/menu'
}

export function getServiceMenuList (parameter) {
  return axios({
    url: api.serviceMenu + '/list',
    method: 'get',
    params: parameter
  })
}

export function getServiceMenu (parameter) {
  return axios({
    url: api.serviceMenu + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export function saveServiceMenu (parameter) {
  return axios({
    url: api.serviceMenu + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delServiceMenu (parameter) {
  return axios({
    url: api.serviceMenu + '/remove',
    method: 'post',
    params: parameter
  })
}

export function changMarketable (parameter) {
  return axios({
    url: api.serviceMenu + '/changeMarketable',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export const serviceMenuExport = api.serviceMenu + '/export'
