package com.keyness.util;

import com.keyness.pojo.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Keyness on 2017/11/14.
 */
public class TokenUtil {

    /**
     * 从数据库里面获取token
     * @return
     */
    public static Map<String, Object> getToken(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "select * from t_token order by createTime desc limit 0,1";
        String access_token = "";
        Map<String, Object> map = new HashMap<String, Object>();
        Integer expires_in = 0;
        try{
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()){
                access_token = rs.getString("access_token");
                expires_in = rs.getInt("expires_in");
                map.put("access_token",access_token);
                map.put("expires_in",expires_in);
            }
        }catch(SQLException e){
            System.out.println("数据库操作异常: "+ e.getMessage());
        }finally{
            DBUtil.closeConnection(conn);
        }
        return map;
    }

    public static void saveToken(Token token){
        Connection conn = null;
        PreparedStatement pst = null;
        try{
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement("insert into t_token(access_token,expires_in,createTime)values(?,?,?)");
            pst.setString(1,token.getAccessToken());
            pst.setInt(2,token.getExpiressIn());
            long now = new Date().getTime();
            pst.setTimestamp(3,new java.sql.Timestamp(now));
            pst.execute();
        }catch(SQLException e){
            System.out.println("数据库操作异常: "+ e.getMessage());
        }finally{
            DBUtil.closeConnection(conn);
        }
    }

}
