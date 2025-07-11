import { axios } from '@/utils/request'

const api = {
  applySettle: '/admin/settle'
}

// 详情
export function getApplySettle (parameter) {
  return axios({
    url: api.applySettle + '/get/' + parameter,
    method: 'get'
  })
}

export function getApplySettleList (parameter) {
  return axios({
    url: api.applySettle + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveApplySettle (parameter) {
  return axios({
    url: api.applySettle + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delApplySettle (parameter) {
  return axios({
    url: api.applySettle + '/remove',
    method: 'post',
    params: parameter
  })
}
// 批量审批
export function approveApplySettle (parameter) {
  return axios({
    url: api.applySettle + '/approve',
    method: 'post',
    params: parameter
  })
}

// 取消审批
export function cancelApplySettle (parameter) {
  return axios({
    url: api.applySettle + '/cancelApprove',
    method: 'post',
    params: parameter
  })
}
export const applySettleExport = api.applySettle + '/export'
