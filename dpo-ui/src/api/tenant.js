import { axios } from '@/utils/request'

const api = {
  tenant: '/system/tenant'
}

export function getTenantList (parameter) {
  return axios({
    url: api.tenant + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveTenant (parameter) {
  return axios({
    url: api.tenant + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delTenant (parameter) {
  return axios({
    url: api.tenant + '/remove',
    method: 'post',
    params: parameter
  })
}

export function resetPassword (parameter) {
  return axios({
    url: api.tenant + '/resetPwd',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export const tenantExport = api.tenant + '/export'
