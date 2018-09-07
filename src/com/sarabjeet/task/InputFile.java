/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarabjeet.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author java_developer
 */
public class InputFile {
    
    private List<InputFileTransaction> file;
    
    static Logger logger = Logger.getLogger(InputFile.class);

    public List<InputFileTransaction> loadInputFile(FileSpecification fileSpecification, String inputTransactionsFile)
    {
        if(logger.isDebugEnabled()) 
            logger.debug("Initiate Load Daily Transactions File.");
        
        BufferedReader mbr = null;
        file = new ArrayList<InputFileTransaction>();
        InputFileTransaction inputFileTransaction = new InputFileTransaction();
        int rowsCount=0;
        
        try 
        {
            mbr = new BufferedReader(new FileReader(inputTransactionsFile));
            
            String readLine = mbr.readLine();

            while(readLine != null)
            {
                inputFileTransaction = new InputFileTransaction(); // Flushing the previous transaction

                inputFileTransaction = loadTransactionFromInputFile(readLine,fileSpecification);

                //Load the transaction onto the file instance variable
                file.add(inputFileTransaction);

                //read the next transaction from the file
                readLine = mbr.readLine();
                rowsCount++;
            }
            
            if(logger.isDebugEnabled()) 
                logger.debug("Daily Transactions File loaded successfully: "+ rowsCount+" rows loaded");
        }
        catch (FileNotFoundException e) 
        {
            logger.fatal("FATAL ERROR: Input Transactions file doesn't exist or is inaccesible. Please try again with a valid file.");
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
           try 
           {
                mbr.close();
           } 
           catch (IOException e) 
           {
                e.printStackTrace();
           }
        }
     
        return file;
    }
    
    public InputFileTransaction loadTransactionFromInputFile(String readLine,FileSpecification fileSpecification)
    {
        FieldSpecification currentFieldSpecification = new FieldSpecification ();
        InputFileField inputFileField;// = new InputFileField();
        ArrayList<InputFileField> transaction = new ArrayList<InputFileField>();
        InputFileTransaction inputFileTransaction = new InputFileTransaction();
        int fileSpecificationIndex=0;
        
        char fieldValueArray[];
        String fieldValue;
        //Traversing through the fileSpeficiation for each line of inputFile to load the inputFileField, inputTransaction into file
        Iterator iterator = fileSpecification.getFileFormat().iterator();
        while(iterator.hasNext())
        {
            inputFileField = new InputFileField(); // Flushing the pevious field

            currentFieldSpecification = (FieldSpecification)fileSpecification.getFileFormat().get(fileSpecificationIndex);

            if(Integer.valueOf(currentFieldSpecification.getStartPosition())>readLine.length())
                break;
            
            inputFileField.setFieldName(currentFieldSpecification.getFieldName());

            //Initialise a char array of the length of the current field
            fieldValueArray = new char[Integer.valueOf(currentFieldSpecification.getEndPosition()) - 
                                    Integer.valueOf(currentFieldSpecification.getStartPosition())
                                    + 1
                                ];
            //Read the chars into the fieldValueArray variable
                    readLine.getChars(
                            Integer.valueOf(currentFieldSpecification.getStartPosition())-1, 
                            Integer.valueOf(currentFieldSpecification.getEndPosition()), 
                            fieldValueArray, 
                            0)
            ;

            fieldValue = new String(fieldValueArray);
            inputFileField.setFieldValue(fieldValue);

            transaction.add(inputFileField);
            
            fileSpecificationIndex++;
        }
        
        inputFileTransaction.setTransaction(transaction);
        
        return inputFileTransaction;
    }
    /**
     * Get the value of file
     *
     * @return the value of file
     */
    public List<InputFileTransaction> getFile() {
        return file;
    }

    /**
     * Set the value of file
     *
     * @param file new value of file
     */
    public void setFile(ArrayList file) {
        this.file = file;
    }

}
