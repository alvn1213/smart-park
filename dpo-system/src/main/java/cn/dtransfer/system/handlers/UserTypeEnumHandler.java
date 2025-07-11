package cn.dtransfer.system.handlers;

import cn.dtransfer.system.domain.User;
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
@MappedJdbcTypes({JdbcType.VARCHAR})
@MappedTypes({User.Type.class})
public class UserTypeEnumHandler extends BaseTypeHandler<User.Type> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, User.Type instance, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,instance.getValue());
    }

    @Override
    public User.Type getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        User.Type instance = User.Type.parse(value);
        return instance;
    }

    @Override
    public User.Type getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        User.Type instance = User.Type.parse(value);
        return instance;
    }

    @Override
    public User.Type getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
