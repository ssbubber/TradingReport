/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sarabjeet.task;

import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import java.io.File;
/**
 *
 * @author Sarabjeet_Singh
 */
public class Main 
{
    static Logger logger = Logger.getLogger(Main.class);
    
    public static void main(String[] args) 
    {
        String log4jConfigFile = System.getProperty("user.dir") + File.separator +"resources"+ File.separator + "log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        
        if(logger.isDebugEnabled()) 
            logger.debug("Log4j Property file in use: "+log4jConfigFile);
        
        //if input properties file not provided
        if(args.length==0)
        {
            System.out.println("Failure: Input Property file not provided \n\n"
                    + "Instructions to generate the report:\n"
                    + "1. The report will generate daily Transaction Amount Report or Daily Summary Report for the user as required\n"
                    + "2. In order to generate the report, an input properties file needs to be passed as a parameter.\n"
                    + "3. The input properties file should contain the following information:\n"
                    + "\t1. inputTransactionsFile = Path to Input file which contains today's transactions\n"
                    + "\t2. fileSpecificationConfig = Path to File Specification Config\n"
                    + "\t3. typeOfReport = Type of Report(S for Daily Summary Report, T for Total Transaction Amount Report)\n"
                    + "\t4. ReportDestinationPath = Path at which desired Report should be created, please ensure file/directory permissions\n\n"
                    + "Example:\n"
                    + "java TradingReportApplication /users/BusinessUser/input.properties\n\n");
            
            logger.fatal("FATAL Error: Input file not provided");
            
            return;
        }
        
        //Step1 - Loading the input Properties file
        Properties properties = new Properties();
	InputStream input = null;
        String inputTransactionsFile="";
        String fileSpecificationConfig="";
        String typeOfReport="";
        String reportDestinationPath="";
        
        try 
        {
            input = new FileInputStream(args[0]);
            
            //Verifying the size of input.properties
            if(input.read(new byte[1])==-1)
            {
                logger.fatal("ERROR: The input.properties file is empty. Please review the file and try again.");
                throw new IOException("The input.properties file is empty");
            }
            
            properties.load(input);

            inputTransactionsFile = properties.getProperty("inputTransactionsFile");
            fileSpecificationConfig=properties.getProperty("fileSpecificationConfig");
            typeOfReport=properties.getProperty("typeOfReport");
            reportDestinationPath=properties.getProperty("ReportDestinationPath");
            
            if(logger.isDebugEnabled()) 
            {
                logger.debug("inputTransactionsFile: "+inputTransactionsFile);
                logger.debug("fileSpecificationConfig: "+fileSpecificationConfig);
                logger.debug("typeOfReport: "+typeOfReport);
                logger.debug("reportDestinationPath: "+reportDestinationPath);
            }
	} 
        catch (IOException ex) 
        {
            logger.fatal("ERROR: Input Properties doesn't exist or is inaccesible. Please try again with valid Input Properties file.");
            ex.printStackTrace();
            return;
	}
        finally
        {
            if (input != null) 
            {
                try 
                {
                    input.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
	}
        
        FileSpecification fileSpecification = new FileSpecification();
        InputFile inputFile = new InputFile();
        List<InputFileTransaction> file;
        Report report = new Report();
        
        //Step2: Loading the File Specification Configuration file
        fileSpecification.loadConfigFile(fileSpecificationConfig);
        
        //Step3: Load the input file in the file variable as per the file Specification
        file = inputFile.loadInputFile(fileSpecification,inputTransactionsFile); 
        
        //Step 4: Generating the report as per properties file
        if(typeOfReport.equals("S"))
           report.generateDailySummaryReport(file,reportDestinationPath);
               
        if(typeOfReport.equals("T"))
            report.generateTotalTransactionAmountReport(file,reportDestinationPath);
    }
}