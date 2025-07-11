import { axios } from '@/utils/request'

const api = {
  room: '/admin/room'
}

export function getRoomById (id) {
  return axios({
    url: api.room + '/get/' + id,
    method: 'get'
  })
}

export function getRoomList (parameter) {
  return axios({
    url: api.room + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveRoom (parameter) {
  return axios({
    url: api.room + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delRoom (parameter) {
  return axios({
    url: api.room + '/remove',
    method: 'post',
    params: parameter
  })
}

export function changMarketable (parameter) {
  return axios({
    url: api.room + '/changeMarketable',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function getRoomAll (parameter) {
  return axios({
    url: api.room + '/map',
    method: 'get',
    params: parameter
  })
}

export const parkExport = api.room + '/export'
