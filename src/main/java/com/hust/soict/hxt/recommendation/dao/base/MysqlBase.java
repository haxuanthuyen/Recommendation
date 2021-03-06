package com.hust.soict.hxt.recommendation.dao.base;

import com.hust.soict.hxt.recommendation.global.SystemInfo;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by thuyenhx on 3/15/16.
 */
public class MysqlBase {

    private static String user;
    private static String password;
    private static String url;
    private static Logger logger = LoggerFactory.getLogger("warringLog");

    static {
        init();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            logger.error("can't load mysql driver ", e);
        }
    }

    private static synchronized void init() {
        final Configuration conf = SystemInfo.getConfiguration();
        user = conf.getString("mysql.user");
        password = conf.getString("mysql.pass");
        url = conf.getString("mysql.url");
    }

    public synchronized static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
