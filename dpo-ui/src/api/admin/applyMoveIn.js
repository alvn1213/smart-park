import { axios } from '@/utils/request'

const api = {
  applyMoveIn: '/admin/applyMoveIn'
}

// 详情
export function getApplyMoveIn (parameter) {
  return axios({
    url: api.applyMoveIn + '/get/' + parameter,
    method: 'get'
  })
}

export function getApplyMoveInList (parameter) {
  return axios({
    url: api.applyMoveIn + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveApplyMoveIn (parameter) {
  return axios({
    url: api.applyMoveIn + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delApplyMoveIn (parameter) {
  return axios({
    url: api.applyMoveIn + '/remove',
    method: 'post',
    params: parameter
  })
}

// 批量审批
export function approveApplyMoveIn (parameter) {
  return axios({
    url: api.applyMoveIn + '/approve',
    method: 'post',
    params: parameter
  })
}

// 取消审批
export function cancelApplyMoveIn (parameter) {
  return axios({
    url: api.applyMoveIn + '/cancelApprove',
    method: 'post',
    params: parameter
  })
}

export const applyMoveInExport = api.applyMoveIn + '/export'
