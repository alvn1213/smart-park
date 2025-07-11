package cn.dtransfer.admin.handler;

import cn.dtransfer.admin.domain.Customer;
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
@MappedTypes({Customer.Type.class})
public class CustomerTypeEnumHandler extends BaseTypeHandler<Customer.Type> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Customer.Type type, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,type.getValue());
    }

    @Override
    public Customer.Type getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        Customer.Type instance = Customer.Type.parse(value);
        return instance;
    }

    @Override
    public Customer.Type getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        Customer.Type instance = Customer.Type.parse(value);
        return instance;
    }

    @Override
    public Customer.Type getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
