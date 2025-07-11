package cn.dtransfer.admin.mapper;

import cn.dtransfer.admin.domain.CustomerContractRoom;
import cn.dtransfer.admin.domain.Room;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *
 */

@Mapper
public interface CustomerContractRoomMapper extends BaseMapper<CustomerContractRoom> {

    /**
     * 提交合同，更改房间租赁状态
     *
     * @param ids 合同ids,
     * @param status 房间租赁状态
     * @return
     */
    int updateRoomStatus(@Param("ids") String[] ids, @Param("status") Room.Status status);


    /**
     * 根据合同id查询房间合同绑定关系
     * @param contractId
     * @return
     */
    List<CustomerContractRoom> findByContractId(Long contractId);

}
