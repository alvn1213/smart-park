package cn.dtransfer.controller.mobile;

import com.google.common.collect.Maps;

import cn.dtransfer.admin.domain.Park;
import cn.dtransfer.admin.service.IParkService;
import cn.dtransfer.common.annotation.LoginUser;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.system.domain.Tenant;
import cn.dtransfer.system.domain.User;
import cn.dtransfer.system.service.ITenantService;
import cn.dtransfer.system.service.IUserService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wf.jwtp.annotation.Ignore;
import org.wf.jwtp.annotation.RequiresPermissions;

import java.util.List;
import java.util.Map;


/**
 * 园区选择 APP端
 *
 * @author dtransfer
 * @date 2024-03-01
 */
@RestController
@RequestMapping("/park")
public class ParkAPIController extends BaseController {

    @Autowired
    private IParkService parkService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITenantService tenantService;


    /**
     * 选择租户
     * @param tenantFrom
     * @return
     */
    @Ignore
    @GetMapping("tenantList")
    public R tenantList(Tenant tenantFrom) {
        List<Tenant> tenantList = tenantService.selectTenantList(tenantFrom);
        List<Map> tenantMaps = Lists.newArrayList();
        for (Tenant tenant : tenantList){
            Map<String, Object> tenantMap = Maps.newHashMap();
            tenantMap.put("id", tenant.getId());
            tenantMap.put("name", tenant.getName());
            tenantMaps.add(tenantMap);
        }
        return R.data(tenantMaps);
    }

    /**
     * 选择园区
     * @param parkForm
     * @return
     */
    @Ignore
    @GetMapping("list")
    public R list(Park parkForm) {
        List<Park> parkList = parkService.selectAllParkList(parkForm);
        List<Map> parkMaps = Lists.newArrayList();
        for (Park park : parkList){
            Map<String, Object> parkMap = Maps.newHashMap();
            parkMap.put("id", park.getId());
            parkMap.put("tenantId", park.getTenantId());
            parkMap.put("parkName", park.getName());
            parkMap.put("distance", park.getDistance());
            parkMap.put("address", park.getAddress());
            parkMap.put("pic", park.getSmallPic());
            parkMaps.add(parkMap);
        }
        return R.data(parkMaps);
    }

    /**
     * 根据选择园区绑定注册用户
     * @param deptId
     * @param user
     * @return
     */
    @RequiresPermissions("member:center:view")
    @PostMapping("selectParkByDeptId")
    public R selectParkByDeptId(Long deptId, @LoginUser User user){
        user.setDeptId(deptId);
        return toAjax(userService.updateUserInfo(user));
    }

}
