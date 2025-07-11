package cn.dtransfer.admin.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import cn.dtransfer.common.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 数组与整型转换
 *
 * @author dtransfer
 */
@MappedJdbcTypes({ JdbcType.LONGVARCHAR })
public class ListIntHandler extends BaseTypeHandler<List<Integer>> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null) {
			ps.setString(i, JsonUtils.toJson(parameter));
		}
	}

	@Override
	public List<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String dbData = rs.getString(columnName);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<Integer>>() { });
	}

	@Override
	public List<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String dbData = rs.getString(columnIndex);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<Integer>>() { });
	}

	@Override
	public List<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String dbData = cs.getString(columnIndex);
		if (StringUtils.isEmpty(dbData)) {
			return null;
		}
        return JsonUtils.toObject(dbData, new TypeReference<List<Integer>>() { });
	}

}
