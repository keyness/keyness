package com.keyness.test;

import com.keyness.util.DBUtil;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Keyness on 2017/11/14.
 */
public class TestDBUtil {

    @Test
    public void testgetConnection() throws SQLException{
        System.out.println(DBUtil.getConnection());
    }

}
