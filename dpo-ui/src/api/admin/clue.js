import { axios } from '@/utils/request'

const api = {
  clue: '/admin/clue'
}

export function getClueList (parameter) {
  return axios({
    url: api.clue + '/list',
    method: 'get',
    params: parameter
  })
}

export function getClueActivationList (parameter) {
  return axios({
    url: api.clue + '/ClueActivationList',
    method: 'get',
    params: parameter
  })
}

export function getClueMyList (parameter) {
  return axios({
    url: api.clue + '/ClueMyList',
    method: 'get',
    params: parameter
  })
}

export function getClueAssignmentList (parameter) {
  return axios({
    url: api.clue + '/ClueAssignmentList',
    method: 'get',
    params: parameter
  })
}

export function saveClue (parameter) {
  return axios({
    url: api.clue + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getClue (parameter) {
  return axios({
    url: api.clue + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export function delClue (parameter) {
  return axios({
    url: api.clue + '/remove',
    method: 'post',
    params: parameter
  })
}

export function review (parameter) {
  return axios({
    url: api.clue + '/review',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function closeClue (parameter) {
  return axios({
    url: api.clue + '/closeClue',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export const excelClue = (params) => {
  return axios({
    method: 'get',
    url: api.clue + '/excelClue',
    params: params,
    responseType: 'blob',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export const clueExport = api.clue + '/export'
