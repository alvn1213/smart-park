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
@MappedTypes({Room.DecorationType.class})
public class RoomDecorationTypeEnumHandler extends BaseTypeHandler<Room.DecorationType> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Room.DecorationType decorationType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,decorationType.getValue());
    }

    @Override
    public Room.DecorationType getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        int value = resultSet.getInt(columnName);
        Room.DecorationType instance = Room.DecorationType.parse(value);
        return instance;
    }

    @Override
    public Room.DecorationType getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        int value = resultSet.getInt(columnIndex);
        Room.DecorationType instance = Room.DecorationType.parse(value);
        return instance;
    }

    @Override
    public Room.DecorationType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
