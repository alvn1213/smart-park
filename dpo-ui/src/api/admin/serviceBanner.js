import { axios } from '@/utils/request'

const api = {
  serviceBanner: '/admin/sBanner'
}

export function getServiceBannerList (parameter) {
  return axios({
    url: api.serviceBanner + '/list',
    method: 'get',
    params: parameter
  })
}

export function getServiceBanner (parameter) {
  return axios({
    url: api.serviceBanner + '/get/' + parameter,
    method: 'get',
    params: ''
  })
}

export function saveServiceBanner (parameter) {
  return axios({
    url: api.serviceBanner + (parameter.id > 0 ? '/update' : '/save'),
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function delServiceBanner (parameter) {
  return axios({
    url: api.serviceBanner + '/remove',
    method: 'post',
    params: parameter
  })
}

export function changMarketable (parameter) {
  return axios({
    url: api.serviceBanner + '/changeMarketable',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export function changTop (parameter) {
  return axios({
    url: api.serviceBanner + '/changeTop',
    method: 'post',
    data: parameter,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}

export const serviceBannerExport = api.serviceBanner + '/export'
