/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarabjeet.task;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author Sarabjeet_Singh
 */
public class FileSpecification
{    
    static Logger logger = Logger.getLogger(FileSpecification.class);
    
    private ArrayList<FieldSpecification> fileFormat;
    /**
     * Get the value of fileSpecification
     *
     * @return the value of fileSpecification
     */
    public ArrayList getFileFormat() {
        return fileFormat;
    }

    /**
     * Set the value of fileSpecification
     *
     * @param fileSpecification new value of fileSpecification
     */
    public void setFileFormat(ArrayList fileFormat) {
        this.fileFormat = fileFormat;
    }

    public void loadConfigFile(String fileSpecificationConfig)
    {
        BufferedReader mbr = null;
        fileFormat = new ArrayList<FieldSpecification>();
        FieldSpecification fieldSpecification = new FieldSpecification();
        
        int index=0;

        if(logger.isDebugEnabled()) 
            logger.debug("Initiate Load File Specification Configuration file.");
        
        try 
        {
            //Load the mapping csv file in a buffered reader
            mbr = new BufferedReader(new FileReader(fileSpecificationConfig));
            
            if(mbr.read()==-1)
            {
                logger.fatal("The FileSpecification.config file is empty. Please review the file and try again.");
                throw new IOException("The FileSpecification.config file is empty");
            }
            
            String readLine = mbr.readLine();

            readLine = mbr.readLine();//skipping the first config definition record

            while(readLine != null)
            {
                fieldSpecification = new FieldSpecification(); // Flushing the previous specification line details
                //Split each row of mapping csv by comma
                String[] parts= readLine.split(",");
                fieldSpecification.setFieldName(parts[0]);
                fieldSpecification.setDecimals(parts[1]);
                fieldSpecification.setIsDate(parts[2]);
                fieldSpecification.setStartPosition(parts[3]);
                fieldSpecification.setEndPosition(parts[4]);

                if(logger.isDebugEnabled()) 
                    logger.debug("File Specification added: "
                    + parts[0]+" | "
                    + parts[1]+" | "
                    + parts[2]+" | "
                    + parts[3]+" | "
                    + parts[4]);
                
                fileFormat.add(index, fieldSpecification);

                readLine = mbr.readLine();
                index++;
            }
            
            if(logger.isDebugEnabled()) 
                logger.debug("File Specification Configuration file loaded successfully");
        }
        catch (FileNotFoundException e) 
        {
            logger.fatal("FATAL ERROR: File Specification Config file doesn't exist or is inaccesible. Please try again with a valid file.");
            e.printStackTrace();
            return;
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
    }
}
