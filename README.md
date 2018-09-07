# TradingReport

About
The task is accomplished in the following logical steps:
1. The The File Specifications have been loaded into a FileSpecification object. 

Note: The File specifications have not been statically used in the application, rather a Configurable & Reusable approach has been taken(parameter fileSpecificationConfig in input.properties file). Each field has been loaded as a (fieldname,start position,end position) pair to enable to read each field from the input transactions file.

This means that this section of the code can be reused even if the file specification changes; or it can be reused to load flat files for varied specifications, for which the layout needs to be defined/altered in csv format in the configuration file.

2. This FileSpecification has been used to load the input transactions file into a InputFIle object. 

3. Thereafter, based upon the desired report(as configured in input.properties), a Daily Summary Report or Transaction Amount report will be generated. The same codebase(classes,methods) have been used to generate both of these report, thereby displaying code reusability.  
How to Build the software
1. Create a new TradingReportApplication directory 
2. Create the following sub-directories(no capitals in directory names): 
    1. code 
    2. build 
    3. resources 
    4. reports 
    5. Libraries 
    6. inputfile 
3. Copy all the java files(from com/sarabjeet/task) & Manifest.mf into code 
4. Copy the input transaction file into inputfile 
5. Copy the log4j.jar into the directory - libraries 
6. Copy the following files in the resources directory: 
    1. FileSpecification.config 
    2. Input.properties 
    3. log4j.properties 
7. Compile the java files: 
javac -cp ./libraries/log4j-1.2.17.jar ./code/'*.java -d ./build/
8. Navigate to the build directory: cd build 
9. Execute:  
tar xf ../libraries/log4j-1.2.17.jar
10. Navigate out of the build directory: cd .. 
11. Create the jar file: 
jar cvfm TradingReport.jar ./Code/Manifest.mf -C ./build/com/sarabjeet/task/ .

How to Use the software
1. Create an executable file(or a .bat file for windows) and copy the following into the file: 
java -jar TradingReport.jar ./resources/input.properties
2. In order to use the software, please execute the file created in the previous step. Alternatively, the following command can be executed directly on command line: 
java -jar TradingReport.jar ./resources/input.properties
3. The report would be generated in the reports directory. 

Note: Following things should be in order before the application can be used:
1. In order to generate the report, an input properties file must be passed as a parameter.
2. The input properties file should contain the following information:
	1. inputTransactionsFile = Path to Input file which contains today's transactions
	2. fileSpecificationConfig = Path to File Specification Config
	3. typeOfReport = Type of Report(S for Daily Summary Report, T for Total Transaction Amount Report)
	4. ReportDestinationPath = Path at which desired Report is created.

Example:
java TradingReportApplication ./resources/input.properties

Troubleshooting 

S.No	Error/Failure	Remedy
1	ERROR: Input Properties doesn't exist or is inaccesible. Please try again with valid Input Properties file.	The input.properties file does not exist in the resources directory. Either an incorrect path has been provided, or the file path is inaccessible.
2	ERROR: The input.properties file is empty. Please review the file and try again.	The input.properties file is an empty file. The file needs to be fixed. Sample is attached with the submitted code.
3	ERROR: The FileSpecification.config file is empty. Please review the file and try again.	The input.properties file is an empty file. The file needs to be fixed. Sample is attached with the submitted code.
4	FATAL ERROR: File Specification Config file doesn't exist or is inaccesible. Please try again with a valid file.	The file does not exist in the resources directory. Either an incorrect path has been provided for fileSpecificationConfig parameter   in the input.properties file, or the file path is inaccessible.
5	ERROR: The Input Transactions file is empty. Please review the file and try again.	The input.properties file is an empty file. The file needs to be fixed. Sample is attached with the submitted code.
6	FATAL ERROR: Input Transactions file doesn't exist or is inaccesible. Please try again with a valid file.\"	The file does not exist in the resources directory. Either an incorrect path has been provided for inputTransactionsFile
 parameter   in the input.properties file, or the file path is inaccessible.
