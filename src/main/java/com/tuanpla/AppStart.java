package com.tuanpla;

import com.tuanpla.common.Tool;
import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * *
 * Lop Main Start xu ly
 *
 * @author PLATUAN
 */
public class AppStart extends Thread {

    private static final Logger logger = Logger.getLogger(AppStart.class);
    //--
    public static boolean isRuning = true;
    //--
    public static int TPS_LOG = 100;

    static {
        try {
            // Log4j
            initLog4j();
            // Load Config
            //-----------------------------------------
        } catch (Exception ex) {
            logger.error("Thong so gateway chua du...");
            logger.error(Tool.getLogMessage(ex));
            System.exit(1);
        }
    }

    public static void initLog4j() {
        Tool.debug("-----------START LOAD LOG4j-----------");
        String log4jPath = "D:\\log\\devlog\\log4j.properties";
        //--
        File fileLog4j = new File(log4jPath);
        if (fileLog4j.exists()) {
            Tool.debug("====>Initializing Log4j:" + log4jPath);
            PropertyConfigurator.configure(log4jPath);
        } else {
            System.err.println("=====> *** " + log4jPath + " file not found, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        }
        Tool.debug("-----------END LOAD LOG4j-----------");
    }

    //--------------------------------
    public static void main(String[] args) {
        try {
            //-- LogMsgBrandIncome
            logger.debug("This is a debug message");
            logger.info("This is a information message");
            logger.warn("This is a warning message");
            logger.error("This is an error message");
            logger.fatal("This is a fatal message");
            
            logger.debug("This is another debug message");
            logger.info("This is another information message");
            logger.warn("This is another warning message");
            logger.error("This is another error message");
            logger.fatal("This is another fatal message");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
    }
}
