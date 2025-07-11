package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.CustomerContractRefundRoom;
import cn.dtransfer.admin.domain.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 退租房间关联Mapper接口
 *
 * @author dtransfer
 * @date 2024-03-31
 */
@Mapper
public interface CustomerContractRefundRoomMapper extends BaseMapper<CustomerContractRefundRoom> {
    /**
     * 查询退租房间关联
     *
     * @param id 退租房间关联ID
     * @return 退租房间关联
     */
    CustomerContractRefundRoom selectCustomerContractRefundRoomById(Long id);

    /**
     * 查询退租房间关联列表
     *
     * @param customerContractRefundRoom 退租房间关联
     * @return 退租房间关联集合
     */
    List<CustomerContractRefundRoom> selectCustomerContractRefundRoomList(CustomerContractRefundRoom customerContractRefundRoom);


    /**
     * 提交合同，更改房间租赁状态
     *
     * @param ids 合同ids,
     * @param status 房间租赁状态
     * @return
     */
    int updateRoomStatus(@Param("ids") String[] ids, @Param("status") Room.Status status);

}
