package cn.dtransfer.admin.domain.enumHandler;

import cn.dtransfer.admin.domain.CustomerContractRefund;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author dtransfer
 */
@MappedJdbcTypes({JdbcType.INTEGER})
@MappedTypes({CustomerContractRefund.RefundReason.class})
public class RefundStatusEnumHandler extends BaseTypeHandler<CustomerContractRefund.RefundReason> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CustomerContractRefund.RefundReason refundReason, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,refundReason.getValue());
    }

    @Override
    public CustomerContractRefund.RefundReason getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int value = resultSet.getInt(s);
        CustomerContractRefund.RefundReason instance = CustomerContractRefund.RefundReason.parse(value);
        return instance;
    }

    @Override
    public CustomerContractRefund.RefundReason getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int value = resultSet.getInt(i);
        CustomerContractRefund.RefundReason instance = CustomerContractRefund.RefundReason.parse(value);
        return instance;
    }

    @Override
    public CustomerContractRefund.RefundReason getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
