package cn.dtransfer.admin.controller;

import cn.dtransfer.admin.service.IServiceOrderService;
import cn.dtransfer.admin.domain.ServiceOrder;
import cn.dtransfer.common.core.controller.BaseController;
import cn.dtransfer.common.core.domain.R;
import cn.dtransfer.common.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wf.jwtp.annotation.RequiresPermissions;

/**
 * 服务订单 提供者
 *
 * @author dtransfer
 * @date 2024-03-26
 */
@RestController
@RequestMapping("/admin/order")
public class ServiceOrderController extends BaseController {

    @Autowired
    private IServiceOrderService serviceOrderService;

    /**
     * 查询服务订单
     */
    @GetMapping("get/{id}")
    public ServiceOrder get(@PathVariable("id") Long id) {
        return serviceOrderService.selectServiceOrderById(id);
    }

    /**
     * 查询服务订单列表
     */
    @RequiresPermissions("admin:ServiceOrder:list")
    @GetMapping("list")
    public R list(ServiceOrder serviceOrder) {
        startPage();
        return result(serviceOrderService.selectServiceOrderList(serviceOrder));
    }

    /**
     * 新增保存服务订单
     */
    @RequiresPermissions("admin:ServiceOrder:add")
    @PostMapping("save")
    public R addSave(@RequestBody ServiceOrder serviceOrder) {
        ValidatorUtils.validateEntity(serviceOrder);
        return toAjax(serviceOrderService.insertServiceOrder(serviceOrder));
    }

    /**
     * 修改保存服务订单
     */
    @RequiresPermissions("admin:ServiceOrder:edit")
    @PostMapping("update")
    public R editSave(@RequestBody ServiceOrder serviceOrder) {
        return toAjax(serviceOrderService.updateServiceOrder(serviceOrder));
    }

    /**
     * 删除服务订单
     */
    @RequiresPermissions("admin:ServiceOrder:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(serviceOrderService.deleteServiceOrderByIds(ids));
    }

}
