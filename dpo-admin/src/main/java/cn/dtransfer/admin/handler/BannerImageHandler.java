package cn.dtransfer.admin.handler;

import cn.dtransfer.admin.domain.BannerImage;
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
 * 动作示范图
 *
 * @author dtransfer
 */
@MappedJdbcTypes({JdbcType.LONGVARCHAR})
public class BannerImageHandler extends BaseTypeHandler<List<BannerImage>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<BannerImage> parameter, JdbcType jdbcType) throws SQLException {
        if (parameter != null) {
            ps.setString(i, JsonUtils.toJson(parameter));
        }
    }

    @Override
    public List<BannerImage> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dbData = rs.getString(columnName);
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }
        return JsonUtils.toObject(dbData, new TypeReference<List<BannerImage>>() { });
    }

    @Override
    public List<BannerImage> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String dbData = rs.getString(columnIndex);
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }
        return JsonUtils.toObject(dbData, new TypeReference<List<BannerImage>>() { });
    }

    @Override
    public List<BannerImage> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String dbData = cs.getString(columnIndex);
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }
        return JsonUtils.toObject(dbData, new TypeReference<List<BannerImage>>() { });
    }

}
