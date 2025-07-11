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
@MappedTypes({Room.Layout.class})
public class RoomLayoutEnumHandler extends BaseTypeHandler<Room.Layout> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Room.Layout layout, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, layout.getValue());
    }

    @Override
    public Room.Layout getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        Room.Layout instance = Room.Layout.parse(value);
        return instance;
    }

    @Override
    public Room.Layout getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        Room.Layout instance = Room.Layout.parse(value);
        return instance;
    }

    @Override
    public Room.Layout getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
