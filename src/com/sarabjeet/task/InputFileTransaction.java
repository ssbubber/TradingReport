/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarabjeet.task;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Sarabjeet_Singh
 */
public class InputFileTransaction {
    
    private ArrayList<InputFileField> transaction;
    
    public String getFieldValueByFieldName(String fieldName)
    {
        InputFileField currentInputFileField = new InputFileField();
        
        Iterator fieldIterator = transaction.iterator();
        
        while(fieldIterator.hasNext())
        {
            currentInputFileField = (InputFileField)fieldIterator.next();
            if(currentInputFileField.getFieldName().equals(fieldName))
                return currentInputFileField.getFieldValue();;
        }
        
        return null;
    }
    
    /**
     * Get the value of transaction
     *
     * @return the value of transaction
     */
    public ArrayList getTransaction() {
        return transaction;
    }

    /**
     * Set the value of transaction
     *
     * @param transaction new value of transaction
     */
    public void setTransaction(ArrayList transaction) {
        this.transaction = transaction;
    }

}
