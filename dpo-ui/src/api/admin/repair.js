import { axios } from '@/utils/request'

const api = {
  repair: '/admin/repair'
}

export function getRepair (parameter) {
  return axios({
    url: api.repair + '/get/' + parameter,
    method: 'get'
  })
}

export function getRepairList (parameter) {
  return axios({
    url: api.repair + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveRepair (parameter) {
  return axios({
    url: api.repair + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function review (parameter) {
  return axios({
    url: api.repair + '/review',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function complete (parameter) {
  return axios({
    url: api.repair + '/complete',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delRepair (parameter) {
  return axios({
    url: api.repair + '/remove',
    method: 'post',
    params: parameter
  })
}

export const repairExport = api.repair + '/export'
