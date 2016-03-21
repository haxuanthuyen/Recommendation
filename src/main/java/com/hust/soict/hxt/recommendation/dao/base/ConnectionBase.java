package com.hust.soict.hxt.recommendation.dao.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by thuyenhx on 3/15/16.
 */
public class ConnectionBase {
    protected static Logger logger = LoggerFactory.getLogger("warringLog");
    protected Connection conn;

    public ConnectionBase() throws SQLException {
        conn = MysqlBase.createConnection();
//        conn.setAutoCommit(true);
    }

    public void dispose() {
        try{
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }catch (Exception e) {
            logger.warn("error when close connection ", e);
        }
    }

}
