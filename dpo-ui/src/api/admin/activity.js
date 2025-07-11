import { axios } from '@/utils/request'

const api = {
  activity: '/admin/activity'
}

export function getActivityList (parameter) {
  return axios({
    url: api.activity + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveActivity (parameter) {
  return axios({
    url: api.activity + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delActivity (parameter) {
  return axios({
    url: api.activity + '/remove',
    method: 'post',
    params: parameter
  })
}

export function getActivity (parameter) {
  return axios({
    url: api.activity + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export function changMarketable (parameter) {
  return axios({
    url: api.activity + '/changeMarketable',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export const activityExport = api.activity + '/export'
