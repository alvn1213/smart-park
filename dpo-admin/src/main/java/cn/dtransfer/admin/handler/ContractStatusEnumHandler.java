package cn.dtransfer.admin.handler;

import cn.dtransfer.admin.domain.CustomerContract;
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
@MappedTypes({CustomerContract.Status.class})
public class ContractStatusEnumHandler extends BaseTypeHandler<CustomerContract.Status> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CustomerContract.Status instance, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,instance.getValue());
    }

    @Override
    public CustomerContract.Status getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        CustomerContract.Status instance = CustomerContract.Status.parse(value);
        return instance;
    }

    @Override
    public CustomerContract.Status getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        CustomerContract.Status instance = CustomerContract.Status.parse(value);
        return instance;
    }

    @Override
    public CustomerContract.Status getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
