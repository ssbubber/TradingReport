/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarabjeet.task;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import org.apache.log4j.Logger;
import java.math.BigDecimal;

/**
 *
 * @author Sarabjeet_Singh
 */
public class Report 
{
    static Logger logger = Logger.getLogger(Report.class);
    
    List<ReportLine> report = new ArrayList<ReportLine>();
   
   public void generateTotalTransactionAmountReport(List<InputFileTransaction> file, String reportDestinationPath)//Option 'T'
   {
       /*
       Steps:
       1) Get all the distinct product names by traversing the file
       2) Scan all the transactions in the file and write to the report instance variable
       3) Invoke writeReport to generate the physical report.
       */
       
       if(logger.isDebugEnabled()) 
            logger.debug("Initiate Total Amount Transactions Report generation.");
       
       ReportLine reportLine;
       Iterator fileIterator = file.iterator();
       Iterator productNamesIterator;
       List<String> productNames = new ArrayList<String>();
       String currentProductName;
       BigDecimal transactionAmountForProductName;
       BigDecimal transactionAmountForCurrentTransaction;
       String transactionAmount;
       InputFileTransaction currentInputFileTransaction = new InputFileTransaction();
       String fieldValue;
       
       //Loop to fetch all the distict Product Names
       while(fileIterator.hasNext())
        {
            //Getting the next Transaction from  the file
            currentInputFileTransaction = (InputFileTransaction)fileIterator.next();
            
            fieldValue = currentInputFileTransaction.getFieldValueByFieldName("PRODUCT_INFORMATION");
            
            if(!productNames.contains(fieldValue))
                productNames.add(fieldValue);
        }
       
        if(logger.isDebugEnabled()) 
           logger.debug("Calculated the distinct Product Names of all transactions.");
       
        productNamesIterator = productNames.iterator();
        
        //Loop to traverse all the Product Names 
        while(productNamesIterator.hasNext())
        {
            reportLine = new ReportLine();
            transactionAmountForProductName = new BigDecimal("0.0000000");
                    
            currentProductName = (String)productNamesIterator.next();
            
            fileIterator = file.iterator();
            //Loop to traverse the entire file and add the transaction amount for that product information.
            while(fileIterator.hasNext())
            {
                currentInputFileTransaction = (InputFileTransaction)fileIterator.next();

                //Calculating the Total Transaction Amount
                if(currentProductName.equals(currentInputFileTransaction.getFieldValueByFieldName("PRODUCT_INFORMATION")))
                transactionAmountForProductName=transactionAmountForProductName.add(new BigDecimal(currentInputFileTransaction.getFieldValueByFieldName("TRANSACTION_PRICE_/_DEC")));
                
            }
            
            transactionAmountForProductName=transactionAmountForProductName.divide(new BigDecimal("10000000"));
             
            reportLine.setProductInformation(currentProductName);
            reportLine.setTotalTransactionAmount(transactionAmountForProductName);
            report.add(reportLine);
            
            if(logger.isDebugEnabled()) 
                logger.debug("Total amount found for Product |"+ currentProductName+"| is: "+transactionAmountForProductName);
        }
        
        writeReport('T',reportDestinationPath);
   }
    
   public void generateDailySummaryReport(List<InputFileTransaction> file, String reportDestinationPath)//Option 'S'
   {
        /*
        Steps:
        1) Get the combination of all the distinct clients & product names
        2) Scan all the transactions in the file and write to the report instance variable
        3) Invoke writeReport to generate the physical report.
        */
       
        if(logger.isDebugEnabled()) 
            logger.debug("Initiate Daily Summary Report generation.");
        
        ReportLine reportLine;
        Iterator fileIterator = file.iterator();
        // String currentProductName;

        BigDecimal transactionAmountForClientAndProductName;
        InputFileTransaction currentInputFileTransaction = new InputFileTransaction();
        String productNameValue="";
        String clientValue="";

        //Loop to fetch all the distict Product Names and Client Names from the Imput file
        while(fileIterator.hasNext())
        {
            //Getting the next Transaction from  the file
            reportLine = new ReportLine();
            
            currentInputFileTransaction = (InputFileTransaction)fileIterator.next();
            
            productNameValue = currentInputFileTransaction.getFieldValueByFieldName("PRODUCT_INFORMATION");
            clientValue = currentInputFileTransaction.getFieldValueByFieldName("CLIENT_INFORMATION");
            
            if(!existsProductAndClientCombination(productNameValue,clientValue))
            {
                reportLine.setClientInformation(clientValue);
                reportLine.setProductInformation(productNameValue);
                report.add(reportLine);
            }
        }
       
        if(logger.isDebugEnabled()) 
           logger.debug("Calculated the distinct Client & Product Names of all transactions.");
        
        int reportIndex = 0;

        //Loop to traverse all the Product & Client Names in the report instance variable
        while(reportIndex<report.size())
        {
            reportLine = new ReportLine();
            transactionAmountForClientAndProductName=new BigDecimal("0.0");
            productNameValue="";
            clientValue="";
                    
            reportLine = report.get(reportIndex);
            
            fileIterator = file.iterator();
            //Loop to traverse the entire file and add the transaction amount for that product information.
            while(fileIterator.hasNext())
            {
                currentInputFileTransaction = (InputFileTransaction)fileIterator.next();

                productNameValue=currentInputFileTransaction.getFieldValueByFieldName("PRODUCT_INFORMATION");
                clientValue = currentInputFileTransaction.getFieldValueByFieldName("CLIENT_INFORMATION");
                if(reportLine.getProductInformation().equals(productNameValue) &&
                   reportLine.getClientInformation().equals(clientValue))
                    transactionAmountForClientAndProductName=transactionAmountForClientAndProductName.add(new BigDecimal(currentInputFileTransaction.getFieldValueByFieldName("TRANSACTION_PRICE_/_DEC")));
            }
            
            transactionAmountForClientAndProductName=transactionAmountForClientAndProductName.divide(new BigDecimal("10000000"));
            
            reportLine.setTotalTransactionAmount(transactionAmountForClientAndProductName);
            report.set(reportIndex, reportLine);
            
            reportIndex++;
            
            if(logger.isDebugEnabled()) 
                logger.debug("Total amount found for Product |"+ productNameValue+
                        "| and Client Name |"+clientValue+
                        "| is: "+transactionAmountForClientAndProductName);
        }
       
        writeReport('S', reportDestinationPath);
   }
   
   public Boolean existsProductAndClientCombination(String productNameValue, String clientValue)
   {
       ReportLine currentReportLine = new ReportLine();
       
       Iterator reportIterator = report.iterator();
       
       while(reportIterator.hasNext())
       {
           currentReportLine = (ReportLine)reportIterator.next();
           
           if(currentReportLine.getClientInformation().equals(clientValue) &&
              currentReportLine.getProductInformation().equals(productNameValue))
               return true;
       }
      
       return false;
   }
   
   public void writeReport(char type, String reportDestinationPath)
   {
       if(logger.isDebugEnabled())
           logger.debug("Initiate dumping of report data onto a physical file");
       
        //Logic to write the report in the file.
        String filename="";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMMyyyy");  
        LocalDateTime now = LocalDateTime.now();  
        
        if(type=='S')
           filename="SummaryReportOutput_"+ dtf.format(now)+".csv";
       
        if(type=='T')
           filename="TransactionReportOutput_"+ dtf.format(now)+".csv";
       
        if(logger.isDebugEnabled())
           logger.debug("Report name: "+ filename);
        
        try 
        {
            File file = new File(reportDestinationPath+filename);
        
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            if(type=='S')
                bw.write("Client Information,");
            
            bw.write("Product Information,");
            bw.write("Total Transaction Amount\n");
            
            int reportIndex = 0;
            ReportLine reportLine;

            while(reportIndex<report.size())
            {
                reportLine = new ReportLine();//flushing
                
                reportLine = report.get(reportIndex);

                if(type=='S')
                {
                    bw.write(reportLine.getClientInformation());
                    bw.write(",");
                }
                
                bw.write(reportLine.getProductInformation());
                bw.write(",");
                bw.write(reportLine.getTotalTransactionAmount().toString());
                bw.write("\n");
                
                reportIndex++;
            }
            bw.close();
        
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        System.out.println("\nReport " + filename+" has been generated");
   }
}
