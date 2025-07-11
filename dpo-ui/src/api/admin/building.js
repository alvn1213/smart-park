import { axios } from '@/utils/request'

const api = {
  building: '/admin/building'
}

export function getBuildingList (parameter) {
  return axios({
    url: api.building + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveBuilding (parameter) {
  return axios({
    url: api.building + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delBuilding (parameter) {
  return axios({
    url: api.building + '/remove',
    method: 'post',
    params: parameter
  })
}

export const buildingExport = api.building + '/export'
