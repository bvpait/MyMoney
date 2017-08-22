package ciccio.mymoney.costant;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TipoTransazioneTypeHandler implements TypeHandler<TipoTransazione> {

    @Override
    public TipoTransazione getResult(ResultSet rs, String param) throws SQLException {
	return TipoTransazione.create(rs.getInt(param));
    }

    @Override
    public TipoTransazione getResult(ResultSet rs, int col) throws SQLException {
	return TipoTransazione.create(rs.getInt(col));
    }

    @Override
    public TipoTransazione getResult(CallableStatement cs, int col) throws SQLException {
	return TipoTransazione.create(cs.getInt(col));
    }

    @Override
    public void setParameter(PreparedStatement ps, int paramInt, TipoTransazione paramType, JdbcType jdbctype) throws SQLException {
	ps.setInt(paramInt, paramType.getValue());
    }

}
