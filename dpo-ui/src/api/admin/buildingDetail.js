import { axios } from '@/utils/request'

const api = {
  buildingDetail: '/admin/buildingDetail'
}

export function getBuildingDetailList (parameter) {
  return axios({
    url: api.buildingDetail + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveBuildingDetail (parameter) {
  return axios({
    url: api.buildingDetail + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delBuildingDetail (parameter) {
  return axios({
    url: api.buildingDetail + '/remove',
    method: 'post',
    params: parameter
  })
}

export const buildingDetailExport = api.buildingDetail + '/export'
