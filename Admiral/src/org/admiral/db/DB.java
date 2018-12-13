package org.admiral.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.admiral.exception.DBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DB {

    private static Logger log = LogManager.getRootLogger();

    private static CConnection s_cc = null;

    private static Object s_ccLock = new Object();

    public static void closeTarget() {
        boolean closed = false;
        if (s_cc != null) {
            closed = true;
            s_cc.setDataSource();
        }
        s_cc = null;
        if (closed) {
            log.debug("closed");
        }
    }

    //set connection
    public static void setDBTarget(CConnection cc) {
        if (cc == null) {
            throw new IllegalArgumentException("Connection is Null");
        }

        if (s_cc != null && s_cc.equals(cc)) {
            return;
        }

        DB.closeTarget();

        synchronized (s_ccLock) {
            s_cc = cc;
        }

        s_cc.setDataSource();
    }

    public static boolean isConnected() {
        return isConnected(true);
    }

    public static boolean isConnected(boolean createNew) {
        if (s_cc == null) {
            return false;
        }
        boolean success = false;
        try {
            Connection conn = getConnectionRW(createNew);
            if (conn != null) {
                conn.close();
            }
            success = (conn != null);
        } catch (Exception e) {
            success = false;
        }
        return success;

    }

    public static Connection getConnectionRW(boolean createNew) {
        return createConnection(true, false, Connection.TRANSACTION_READ_COMMITTED);
    }

    /**
     * Create new Connection
     */
    public static Connection createConnection(boolean autoCommit, boolean readOnly, int trxLevel) {
        Connection conn = s_cc.getConnection(autoCommit, trxLevel);
        if (conn == null) {
            throw new IllegalStateException("failed");
        }
        try {
            if (conn.getAutoCommit() != autoCommit) {
                throw new IllegalStateException("failed");
            }
        } catch (SQLException e) {

        }
        return conn;

    }
    
    /**
     * Obtenemos int Value desde sql
     * @param trxName - transaction
     * @param sql - sql
     * @param params - array de parametros
     * @return primer valor o -1 si no se encuentra
     */
    public static int getSQLValueEx(String trxName, String sql, Object... params) throws DBException
    {
        int retValue = -1;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            pstmt = prepareStatement(sql, trxName);
            setParameters(pstmt, params);
            rs = pstmt.executeQuery();
            if(rs.next())
            {
                retValue = rs.getInt(1);
            }
        }
        catch(SQLException e)
        {
            throw new DBException(e, sql);
        }
        finally
        {
            close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
        return retValue;
    }
}
