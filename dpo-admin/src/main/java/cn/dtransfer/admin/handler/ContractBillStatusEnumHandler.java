package cn.dtransfer.admin.handler;

import cn.dtransfer.admin.domain.CustomerContractBill;
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
@MappedTypes({CustomerContractBill.Status.class})
public class ContractBillStatusEnumHandler extends BaseTypeHandler<CustomerContractBill.Status> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CustomerContractBill.Status instance, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,instance.getValue());
    }

    @Override
    public CustomerContractBill.Status getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        CustomerContractBill.Status instance = CustomerContractBill.Status.parse(value);
        return instance;
    }

    @Override
    public CustomerContractBill.Status getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        CustomerContractBill.Status instance = CustomerContractBill.Status.parse(value);
        return instance;
    }

    @Override
    public CustomerContractBill.Status getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
