package cn.dtransfer.admin.domain.enumHandler;

import cn.dtransfer.admin.domain.Repair;
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
@MappedTypes({Repair.Status.class})
public class RepairStatusEnumHandler extends BaseTypeHandler<Repair.Status> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Repair.Status status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,status.getValue());
    }

    @Override
    public Repair.Status getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        Repair.Status instance = Repair.Status.parse(value);
        return instance;
    }

    @Override
    public Repair.Status getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        Repair.Status instance = Repair.Status.parse(value);
        return instance;
    }

    @Override
    public Repair.Status getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
