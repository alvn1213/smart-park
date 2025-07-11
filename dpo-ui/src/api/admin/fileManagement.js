import { axios } from '@/utils/request'

const api = {
  fileManagement: '/admin/fileManagement'
}

export function getFileManagementList (parameter) {
  return axios({
    url: api.fileManagement + '/list',
    method: 'get',
    params: parameter
  })
}

export function saveFileManagement (parameter) {
  return axios({
    url: api.fileManagement + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delFileManagement (parameter) {
  return axios({
    url: api.fileManagement + '/remove',
    method: 'post',
    params: parameter
  })
}

export const fileManagementExport = api.fileManagement + '/export'
