package com.hyb.rbac.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedJdbcTypes(JdbcType.TIMESTAMP)
@MappedTypes(value = Date.class)
public class DateTypeHandler extends BaseTypeHandler<Date> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        String d=sdf.format(date);
        preparedStatement.setString(i,d );
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String result=resultSet.getString(s);
        if(result!=null)
            try{
                return sdf.parse(result);
            }catch (ParseException e){
                return null;
            }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String result= resultSet.getString(i);
        if(result!=null)
            try{
                return sdf.parse(result);
            }catch (ParseException e){
                return null;
            }
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String result= callableStatement.getString(i);
        if(result!=null)
            try{
                return sdf.parse(result);
            }catch (ParseException e){
                return null;
            }
        return null;
    }
}
