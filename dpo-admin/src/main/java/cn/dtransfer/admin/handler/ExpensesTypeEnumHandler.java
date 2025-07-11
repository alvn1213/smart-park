package cn.dtransfer.admin.handler;

import cn.dtransfer.admin.domain.CustomerContractExpenses;
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
@MappedTypes({CustomerContractExpenses.ExpenseType.class})
public class ExpensesTypeEnumHandler extends BaseTypeHandler<CustomerContractExpenses.ExpenseType> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CustomerContractExpenses.ExpenseType instance, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,instance.getValue());
    }

    @Override
    public CustomerContractExpenses.ExpenseType getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        CustomerContractExpenses.ExpenseType instance = CustomerContractExpenses.ExpenseType.parse(value);
        return instance;
    }

    @Override
    public CustomerContractExpenses.ExpenseType getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        CustomerContractExpenses.ExpenseType instance = CustomerContractExpenses.ExpenseType.parse(value);
        return instance;
    }

    @Override
    public CustomerContractExpenses.ExpenseType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
