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
@MappedTypes({Customer.Process.class})
public class ProcessEnumHandler extends BaseTypeHandler<Customer.Process> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Customer.Process process, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,process.getValue());
    }

    @Override
    public Customer.Process getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        Customer.Process instance = Customer.Process.parse(value);
        return instance;
    }

    @Override
    public Customer.Process getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        Customer.Process instance = Customer.Process.parse(value);
        return instance;
    }

    @Override
    public Customer.Process getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
