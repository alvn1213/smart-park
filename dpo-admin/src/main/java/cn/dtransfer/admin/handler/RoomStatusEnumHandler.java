package cn.dtransfer.admin.handler;

import cn.dtransfer.admin.domain.Room;
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
@MappedTypes({Room.Status.class})
public class RoomStatusEnumHandler extends BaseTypeHandler<Room.Status> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Room.Status status, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,status.getValue());
    }

    @Override
    public Room.Status getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        Room.Status instance = Room.Status.parse(value);
        return instance;
    }

    @Override
    public Room.Status getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        Room.Status instance = Room.Status.parse(value);
        return instance;
    }

    @Override
    public Room.Status getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
