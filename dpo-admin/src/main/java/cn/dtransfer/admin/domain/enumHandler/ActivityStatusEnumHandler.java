package cn.dtransfer.admin.domain.enumHandler;

import cn.dtransfer.admin.domain.Activity;
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
@MappedTypes({Activity.Status.class})
public class ActivityStatusEnumHandler extends BaseTypeHandler<Activity.Status> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Activity.Status status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,status.getValue());
    }

    @Override
    public Activity.Status getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        Activity.Status instance = Activity.Status.parse(value);
        return instance;
    }

    @Override
    public Activity.Status getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        Activity.Status instance = Activity.Status.parse(value);
        return instance;
    }

    @Override
    public Activity.Status getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
