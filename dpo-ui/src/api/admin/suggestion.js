import { axios } from '@/utils/request'

const api = {
  suggestion: '/admin/suggestion'
}

export function getSuggestion (parameter) {
  return axios({
    url: api.suggestion + '/get/' + parameter,
    method: 'get'
  })
}

export function getSuggestionList (parameter) {
  return axios({
    url: api.suggestion + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveSuggestion (parameter) {
  return axios({
    url: api.suggestion + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delSuggestion (parameter) {
  return axios({
    url: api.suggestion + '/remove',
    method: 'post',
    params: parameter
  })
}

export function review (parameter) {
  return axios({
    url: api.suggestion + '/review',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function complete (parameter) {
  return axios({
    url: api.suggestion + '/complete',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export const suggestionExport = api.suggestion + '/export'
