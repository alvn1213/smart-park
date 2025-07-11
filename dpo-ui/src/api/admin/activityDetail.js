import { axios } from '@/utils/request'

const api = {
  activityDetail: '/admin/activityDetail'
}

export function getActivityDetailList (parameter) {
  return axios({
    url: api.activityDetail + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveActivityDetail (parameter) {
  return axios({
    url: api.activityDetail + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delActivityDetail (parameter) {
  return axios({
    url: api.activityDetail + '/remove',
    method: 'post',
    params: parameter
  })
}

export const activityDetailExport = api.activityDetail + '/export'
