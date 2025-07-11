import { axios } from '@/utils/request'

const api = {
  applyPark: '/admin/applyPark'
}
// 详情
export function getApplyPark (parameter) {
  return axios({
    url: api.applyPark + '/get/' + parameter,
    method: 'get'
  })
}

export function getApplyParkList (parameter) {
  return axios({
    url: api.applyPark + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveApplyPark (parameter) {
  return axios({
    url: api.applyPark + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delApplyPark (parameter) {
  return axios({
    url: api.applyPark + '/remove',
    method: 'post',
    params: parameter
  })
}

// 批量审批
export function approveApplyPark (parameter) {
  return axios({
    url: api.applyPark + '/approve',
    method: 'post',
    params: parameter
  })
}

// 取消审批
export function cancelApplyPark (parameter) {
  return axios({
    url: api.applyPark + '/cancelApprove',
    method: 'post',
    params: parameter
  })
}

export const applyParkExport = api.applyPark + '/export'
