package cn.dtransfer.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.dtransfer.service.IWxAppLoginService;
import org.springframework.stereotype.Service;

@Service("wxAppLoginService")
public class WxAppLoginServiceImpl implements IWxAppLoginService {

    @Override
    public WxMaService getWxMaService() {

        return null;
    }
}
