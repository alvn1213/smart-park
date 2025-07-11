import { axios } from '@/utils/request'

const api = {
  clueInvestigation: '/admin/clueInvestigation'
}

export function getClueInvestigationList (parameter) {
  return axios({
    url: api.clueInvestigation + '/list',
    method: 'get',
    params: parameter
  })
}

export function getClueInvestigationMyList (parameter) {
  return axios({
    url: api.clueInvestigation + '/myList',
    method: 'get',
    params: parameter
  })
}

export function saveClueInvestigation (parameter) {
  return axios({
    url: api.clueInvestigation + '/save',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delClueInvestigation (parameter) {
  return axios({
    url: api.clueInvestigation + '/remove',
    method: 'post',
    params: parameter
  })
}

export const clueInvestigationExport = api.clueInvestigation + '/export'
