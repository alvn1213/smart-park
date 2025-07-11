package cn.dtransfer.admin.handler;

import cn.dtransfer.admin.domain.ApplySettle;
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
@MappedTypes({ApplySettle.Status.class})
public class ApplySettleStatusEnumHandler extends BaseTypeHandler<ApplySettle.Status> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ApplySettle.Status instance, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,instance.getValue());
    }

    @Override
    public ApplySettle.Status getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        ApplySettle.Status instance = ApplySettle.Status.parse(value);
        return instance;
    }

    @Override
    public ApplySettle.Status getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        ApplySettle.Status instance = ApplySettle.Status.parse(value);
        return instance;
    }

    @Override
    public ApplySettle.Status getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
