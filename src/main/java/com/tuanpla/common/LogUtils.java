/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.common;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LogUtils {

    public static final Logger debugLog = Logger.getLogger("debugLog");
    public static final Logger resultLog = Logger.getLogger("reportsLog");

    public static void Log(String message) {
        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        message = StringUtils.str2OneLine(className + ":" + lineNumber + "-" + message);
        debugLog.info(message);
    }

    public static void logSubmit_SMPP_VTE(String message, Level lv) {
        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        message = className + ":" + lineNumber + "-" + StringUtils.str2OneLine(message);

        if (lv.equals(Level.DEBUG)) {
            resultLog.debug(message);
        } else if (lv.equals(Level.ERROR)) {
            resultLog.error(message);
        } else {
            resultLog.info(message);
        }
    }

}
