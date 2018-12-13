/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.admiral.exception;

/**
 * Cualquier excepcion que ocurre dentro del core de Admiral
 * @author PC
 */
public class AdempiereException extends RuntimeException{
    public AdempiereException(String message)
    {
        super(message);
    }
    
    public AdempiereException(Throwable cause)
    {
        super(cause);
    }
    
    public AdempiereException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
