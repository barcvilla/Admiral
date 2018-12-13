/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.admiral.exception;

import java.util.Arrays;

/**
 *
 * @author PC
 */
public class DBException extends AdempiereException{
    
    String m_sql = null;
    Object[] m_params = null;
    
    public DBException(Exception e)
    {
        super(e);
        e.printStackTrace();
    }
    
    public DBException(Exception e, String sql)
    {
        this(e, sql, (Object[]) null);
    }
    
    public DBException(Exception e, String sql, Object[] params)
    {
        this(e);
        m_sql = sql;
        if(params != null)
        {
            m_params = Arrays.copyOf(params, params.length);
        }
    }
}
