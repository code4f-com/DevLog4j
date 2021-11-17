package com.tuanpla.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class SMSUtils {

    static final Logger logger = Logger.getLogger(SMSUtils.class);
    private static final String HEXINDEX = "0123456789abcdef          ABCDEF";
    public static final String PARTNER_NAME = "AHP";
    public static String CHAR_PARSE_MT = "#@#";

    public static int REJECT_MSG_LENG = -1;

    public static String PhoneTo84(String number) {
        if (number == null) {
            number = "";
            return number;
        }
        number = number.replaceAll("o", "0");
        if (number.startsWith("84")) {
            return number;
        } else if (number.startsWith("0")) {
            number = "84" + number.substring(1);
        } else if (number.startsWith("+84")) {
            number = number.substring(1);
        } else {
            number = "84" + number;
        }
        return number;
    }

    public static boolean isASCII(String input) {
        boolean isASCII = true;
        for (int i = 0; i < input.length(); i++) {
            int c = input.charAt(i);
            if (c > 0x7F) {
                isASCII = false;
                break;
            }
        }
        return isASCII;
    }

    public static boolean isASCII(char ch) {
        return ch < 128;
    }

    public static void SendAlert8x65(String message, String phone) {

        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("userName", "htcfe");
            params.put("passWord", "ohmygod39458");
            params.put("phone", phone);
            params.put("mess", message);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) {
                    postData.append('&');
                }
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            System.out.println("PostData:" + postData);
            URL url = new URL("http://210.211.98.80:8765/service/notifybyPhone/MT" + "?" + postData);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(20000);
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;) {
                sb.append((char) c);
            }
            String resultPartner = sb.toString();
            System.out.println("[===> ResultPartner:" + resultPartner);
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int countSmsBrandQC(String mess, String oper) {
        int count;
        if (!Tool.checkNull(mess)) {
            int length = mess.length();
            if (oper.equals(OPER.VIETTEL.val)) {
                if (length <= 123) {
                    count = 1;
                } else if (length > 123 && length <= 268) {
                    count = 2;
                } else if (length > 268 && length <= 421) {
                    count = 3;
                } else if (length > 421 && length <= 574) {
                    count = 4;
                } else {
                    count = REJECT_MSG_LENG;
                }
            } else if (oper.equals(OPER.VINA.val)) {
                if (length <= 122) {
                    count = 1;
                } else if (length > 122 && length <= 268) {
                    count = 2;
                } else if (length > 268 && length <= 421) {
                    count = 3;
                } else if (length > 421 && length <= 574) {
                    count = 4;
                } else {
                    count = REJECT_MSG_LENG;
                }
            } else if (oper.equals(OPER.MOBI.val)) {
                if (length <= 127) {
                    count = 1;
                } else if (length > 127 && length <= 273) {
                    count = 2;
                } else if (length > 273 && length <= 426) {
                    count = 3;
                } else if (length > 426 && length <= 579) {
                    count = 4;
                } else {
                    count = REJECT_MSG_LENG;
                }
            } else if (length <= 160) {
                count = 1;
            } else if (length > 160 && length <= 306) {
                count = 2;
            } else if (length > 306 && length <= 459) {
                count = 3;
            } else if (length > 459 && length <= 612) {
                count = 4;
            } else {
                count = REJECT_MSG_LENG;
            }
        } else {
            count = 0;
        }
        return count;
    }

    public static int countSmsBrandCSKH(String mess, String oper) {
        int count;
        if (!Tool.checkNull(mess)) {
            int length = mess.length();
            if (oper.equals(OPER.VIETTEL.val)) {
                if (length <= 160) {
                    count = 1;
                } else if (length > 160 && length <= 306) {
                    count = 2;
                } else if (length > 306 && length <= 459) {
                    count = 3;
                } //
                // else if (length > 459 && length <= 612) {
                //                    count = 4;
                //                } 
                // VIETTEL BO TIN NHAN THU 4 DI
                else {
                    count = REJECT_MSG_LENG;
                }
            } else if (oper.equals(OPER.VINA.val)) {
                if (length <= 160) {
                    count = 1;
                } else if (length > 160 && length <= 306) {
                    count = 2;
                } else if (length > 306 && length <= 459) {
                    count = 3;
                } else if (length > 459 && length <= 612) {
                    count = 4;
                } else {
                    count = REJECT_MSG_LENG;
                }
            } else if (oper.equals(OPER.MOBI.val)) {
                if (length <= 160) {
                    count = 1;
                } else if (length > 160 && length <= 306) {
                    count = 2;
                } else if (length > 306 && length <= 459) {
                    count = 3;
                } else if (length > 459 && length <= 612) {
                    count = 4;
                } else {
                    count = REJECT_MSG_LENG;
                }
            } else if (length <= 160) {
                count = 1;
            } else if (length > 160 && length <= 306) {
                count = 2;
            } else if (length > 306 && length <= 459) {
                count = 3;
            } else if (length > 459 && length <= 612) {
                count = 4;
            } else {
                count = REJECT_MSG_LENG;
            }
        } else {
            count = 0;
        }
        return count;
    }

    public static int countNomalSMS(String message) {
        int result = 1;
        if (!Tool.checkNull(message)) {
            result = message.length() / 160;
            if (message.length() % 160 != 0) {
                result += 1;
            }
        }
        return result;
    }

    public static ArrayList string2List(String listPhone) {
        ArrayList list = new ArrayList();
        if (listPhone != null) {
            String[] arrPhone = listPhone.split("[,;: ]");
            if (arrPhone != null && arrPhone.length > 0) {
                for (String onePhone : arrPhone) {
                    // Valid 1 Phone
                    if (onePhone == null) {
                        continue;
                    }
                    if (checkPhoneNumber(onePhone) == 1) {
                        // So dien thoai hop le
                        list.add(PhoneTo84(onePhone));
                    } else {
                        logger.error("So dien thoai ko hop le:" + onePhone);
                    }
                }
            }
        }
        return list;
    }

    public static ArrayList validList(ArrayList<String> listPhone) {
        ArrayList list = new ArrayList();
        // VIETTEL AND OTHER
        if (listPhone != null) {
            for (String onePhone : listPhone) {
                // Valid 1 Phone
                if (onePhone == null) {
                    continue;
                }
                if (SMSUtils.checkPhoneNumber(onePhone) == 1) {
                    list.add(onePhone);
                }
            }
        }
        return list;
    }

    public static boolean validTemplate(String input, String patten) {
        boolean result = false;
        try {
            Pattern p = Pattern.compile(patten);
            Matcher m = p.matcher(input);
            result = m.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    public static void main(String[] args) {
//        String mess = "LUONG T11-2014(1)ST:7272MS:1-1Ng LV:7Lg 0%:16(2)LCB:716,587DG:81,667NODL:91\n"
//                + ",304(3)BH:CD:(4)=(2)-(3)TL:890,000Lien he P HCNS";
//        mess = StringUtils.readLine(mess);
//        System.out.println(mess);
////        String tmp = "Thong bao (.*)";
//        String tmp = "Chuc mung (.*),(.*|\\n) COSMOS (.*|\\n),Luong (.*|\\n),LUONG (.*|\\n),Cong ty (.*|\\n),Thong bao (.*|\\n)";
//        String[] arrTmp = tmp.split(",");
//        int result = 0;
//        if (!SMSUtils.validTemplate(mess, arrTmp)) {
//            result = CODE.TEMP_NOT_VALID.val;
//        }
//        System.out.println(CODE.getMess(result));
//    }
    public static boolean validTemplate(String input) {
        boolean result = false;
        try {
            Pattern p = Pattern.compile(".{1,}", Pattern.DOTALL);
            Matcher m = p.matcher(input);
            result = m.matches();
            System.out.println("result:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean validTemplate(String input, String[] patten) {
        boolean result = false;
        try {
            if (patten != null && patten.length > 0) {
                for (String onePatten : patten) {
//                    System.out.println("onePatten:" + onePatten);
                    Pattern p = Pattern.compile(onePatten, Pattern.DOTALL);
                    Matcher m = p.matcher(input);
                    result = m.matches();
//                    Tool.debug("result:" + result);
                    if (result) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean validPhoneVN(String phone) {
        String regex = "^(\\+849\\d{8})|"
                + "(849\\d{8})|"
                + "(09\\d{8})|"
                + "(\\+841\\d{9})|"
                + "(841\\d{9})|"
                + "(01\\d{9})|"
                + "(\\+848\\d{8})|"
                + "(848\\d{8})|"
                + "(08\\d{8})$";
        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);
        // Now create matcher object.
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static enum OPER {

        VIETTEL("VTE"),
        VINA("GPC"),
        MOBI("VMS"),
        VNM("VNM"),
        BEELINE("BL");
        public String val;

        public String getVal() {
            return val;
        }

        private OPER(String val) {
            this.val = val;
        }
    }

    public static String getSVNumAlias(String svnum) {
        String result = "";
        try {
            if (svnum.length() == 4) {
                result = svnum.substring(0, 1) + "x" + svnum.substring(2, svnum.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
        return result;
    }

    public static String buildSvNumFW(String svnum) {
        String result = "";
        try {
            if (svnum.length() == 4) {
                result = svnum.substring(0, 1) + "0" + svnum.substring(2, svnum.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Tool.getLogMessage(e));
        }
        return result;
    }

    public static String buildGprsInfo(String phoneNumber) {
        String gprsInfo = "";
        try {
            if (SMSUtils.buildMobileOperator(phoneNumber).equalsIgnoreCase(OPER.MOBI.val)) {
                gprsInfo = "*De DK va cai dat GPRS:Soan"
                        + "\nDK GPRS gui 994"
                        + "\nChu y: ban co the phai khoi dong lai may de cai dat co hieu luc.";
            } else if (SMSUtils.buildMobileOperator(phoneNumber).equalsIgnoreCase(OPER.VINA.val)) {
                gprsInfo = "*De cai dat GPRS:Soan"
                        + "\nGPRS ON gui 333."
                        + "\nChu y: ban co the phai khoi dong lai may de cai dat co hieu luc.";
            } else if (SMSUtils.buildMobileOperator(phoneNumber).equalsIgnoreCase(OPER.VIETTEL.val)) {
                gprsInfo = "*De cai dat GPRS:Soan"
                        + "\nD MaGoi gui 191"
                        + "\n(MaGoi: 0,5,10,25,50,300)"
                        + "\nChu y: ban co the phai khoi dong lai may de cai dat co hieu luc.";
            } else if (SMSUtils.buildMobileOperator(phoneNumber).equalsIgnoreCase(OPER.VNM.val)) {
                gprsInfo = "*De cai dat GPRS, soan tin: GPRS gui 222."
                        + "\nChu y: ban co the phai khoi dong lai may de cai dat co hieu luc.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gprsInfo;

    }

    /**
     * PLA TUAN Xap Xep COmmandCode Theo Do Dai giam dan
     *
     * @param allCommandCode
     * @return
     */
    public static String[] arrangeCommandCode(String[] allCommandCode) {
        try {
            for (int i = 0; i < allCommandCode.length; i++) {
                for (int j = i + 1; j < allCommandCode.length; j++) {
                    String stem;
                    if (allCommandCode[j].length() > allCommandCode[i].length()) {
                        stem = allCommandCode[i].toUpperCase();
                        allCommandCode[i] = allCommandCode[j].toUpperCase();
                        allCommandCode[j] = stem;
                    }
                }
            }
        } catch (Exception e) {
            return allCommandCode;
        }
        return allCommandCode;
    }

    /**
     * PLA TUAN Loai Bo Cac Ky Tu Dac biet trong Msg
     *
     * @param msg
     * @return
     */
    public static String validMessage(String msg) {
        msg = msg.replace('.', ' ');
        msg = msg.replace('!', ' ');
        msg = msg.replace('$', ' ');
        msg = msg.replace('#', ' ');
        msg = msg.replace('[', ' ');
        msg = msg.replace(']', ' ');
        msg = msg.replace('(', ' ');
        msg = msg.replace(')', ' ');
        msg = msg.replace(',', ' ');
        msg = msg.replace(';', ' ');
        msg = msg.replace('"', ' ');
        msg = msg.replace('\'', ' ');
        msg = msg.replace('\\', ' ');
        msg = msg.replace('/', ' ');
        msg = msg.replace('%', ' ');
        msg = msg.replace('<', ' ');
        msg = msg.replace('>', ' ');
        msg = msg.replace('@', ' ');
        msg = msg.replace(':', ' ');
        msg = msg.replace('=', ' ');
        msg = msg.replace('?', ' ');
        msg = msg.replace('-', ' ');
        msg = msg.replace('_', ' ');
        msg = msg.trim();
        StringTokenizer tk = new StringTokenizer(msg, " ");
        msg = "";
        while (tk.hasMoreTokens()) {
            String sTmp = (String) tk.nextToken();
            if (!msg.equals("")) {
                msg += " " + sTmp;
            } else {
                msg += sTmp;
            }
        }
        msg = StringUtils.convert2NoSign(msg);
        return msg;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.equals("")) {
            return "";
        }
        try {
            if (phoneNumber.startsWith("84") && phoneNumber.length() > 2) {
                phoneNumber = (new StringBuilder()).append("0").append(phoneNumber.substring(2)).toString();
            } else if (phoneNumber.startsWith("+84") && phoneNumber.length() > 3) {
                phoneNumber = (new StringBuilder()).append("0").append(phoneNumber.substring(3)).toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return phoneNumber;
    }

    public static String sumNick(String nick) {
        if (nick == null || "".equals(nick)) {
            return null;
        }
        nick = nick.trim();
        int sum = 0;
        if (nick.length() < 2 && isNumberic(nick)) {
            return nick;
        }
        nick = nick.toUpperCase();
        for (int i = 0; i < nick.length(); i++) {
            char ch = nick.charAt(i);
            if (ch == 'A' || ch == 'J' || ch == 'S') {
                sum++;
                continue;
            }
            if (ch == 'B' || ch == 'K' || ch == 'T') {
                sum += 2;
                continue;
            }
            if (ch == 'C' || ch == 'L' || ch == 'U') {
                sum += 3;
                continue;
            }
            if (ch == 'D' || ch == 'M' || ch == 'V') {
                sum += 4;
                continue;
            }
            if (ch == 'E' || ch == 'N' || ch == 'W') {
                sum += 5;
                continue;
            }
            if (ch == 'F' || ch == 'O' || ch == 'X') {
                sum += 6;
                continue;
            }
            if (ch == 'G' || ch == 'P' || ch == 'Y') {
                sum += 7;
                continue;
            }
            if (ch == 'H' || ch == 'Q' || ch == 'Z') {
                sum += 8;
                continue;
            }
            if (ch == 'I' || ch == 'R') {
                sum += 9;
            }
        }

        String sTmp = (new StringBuilder()).append("").append(sum).toString();
        sum = 0;
        int iTmp;
        for (; sTmp.length() != 1; sTmp = String.valueOf(iTmp)) {
            iTmp = 0;
            for (int i = 0; i < sTmp.length(); i++) {
                char temp = sTmp.charAt(i);
                if (Character.isDigit(temp)) {
                    iTmp += Integer.parseInt(String.valueOf(temp));
                }
            }

        }
        return sTmp;
    }

    public static String buildMobileOperator(String userId) {
        String mobileOperator = "OTHER";
        if (//-
                userId.startsWith("+8491") || userId.startsWith("8491") || userId.startsWith("091") || userId.startsWith("91")
                || userId.startsWith("+8494") || userId.startsWith("8494") || userId.startsWith("094") || userId.startsWith("94")
                || userId.startsWith("+84123") || userId.startsWith("84123") || userId.startsWith("0123") || userId.startsWith("123")
                || userId.startsWith("+84124") || userId.startsWith("84124") || userId.startsWith("0124") || userId.startsWith("124")
                || userId.startsWith("+84125") || userId.startsWith("84125") || userId.startsWith("0125") || userId.startsWith("125")
                || userId.startsWith("+84127") || userId.startsWith("84127") || userId.startsWith("0127") || userId.startsWith("127")
                || userId.startsWith("+84129") || userId.startsWith("84129") || userId.startsWith("0129") || userId.startsWith("129")
                || userId.startsWith("+8488") || userId.startsWith("8488") || userId.startsWith("088") || userId.startsWith("88") // NEW
                ) {
            //VINA
            mobileOperator = OPER.VINA.val;
        } else if (userId.startsWith("+8490") || userId.startsWith("8490") || userId.startsWith("090") || userId.startsWith("90")
                || userId.startsWith("+8493") || userId.startsWith("8493") || userId.startsWith("093") || userId.startsWith("93")
                || userId.startsWith("+84120") || userId.startsWith("84120") || userId.startsWith("0120") || userId.startsWith("120")
                || userId.startsWith("+84121") || userId.startsWith("84121") || userId.startsWith("0121") || userId.startsWith("121")
                || userId.startsWith("+84122") || userId.startsWith("84122") || userId.startsWith("0122") || userId.startsWith("122")
                || userId.startsWith("+84126") || userId.startsWith("84126") || userId.startsWith("0126") || userId.startsWith("126")
                || userId.startsWith("+84128") || userId.startsWith("84128") || userId.startsWith("0128") || userId.startsWith("128")
                || userId.startsWith("+8489") || userId.startsWith("8489") || userId.startsWith("089") || userId.startsWith("89") // NEW
                ) {
            // MOBILE
            mobileOperator = OPER.MOBI.val;
        } else if (userId.startsWith("+8498") || userId.startsWith("8498") || userId.startsWith("098") || userId.startsWith("98")
                || userId.startsWith("+8497") || userId.startsWith("8497") || userId.startsWith("097") || userId.startsWith("97")
                || userId.startsWith("+8496") || userId.startsWith("8496") || userId.startsWith("096") || userId.startsWith("96") // EVN Cu
                || userId.startsWith("+8416") || userId.startsWith("8416") || userId.startsWith("016") || userId.startsWith("16")
                || userId.startsWith("+8486") || userId.startsWith("8486") || userId.startsWith("086") || userId.startsWith("86") // NEW
                || userId.startsWith("42") || userId.startsWith("042") || userId.startsWith("8442") || userId.startsWith("+8442")) {
            mobileOperator = OPER.VIETTEL.val;
        } else if (userId.startsWith("92") || userId.startsWith("092") || userId.startsWith("8492") || userId.startsWith("+8492")
                || userId.startsWith("188") || userId.startsWith("0188") || userId.startsWith("84188") || userId.startsWith("+84188")
                || userId.startsWith("187") || userId.startsWith("0187") || userId.startsWith("84187") || userId.startsWith("+84187")
                || userId.startsWith("186") || userId.startsWith("0186") || userId.startsWith("84186") || userId.startsWith("+84186")) {
            // VIET NAM MOBILE
            mobileOperator = OPER.VNM.val;
        } else if (userId.startsWith("99") || userId.startsWith("099") || userId.startsWith("8499") || userId.startsWith("+8499")
                || userId.startsWith("199") || userId.startsWith("0199") || userId.startsWith("84199") || userId.startsWith("+84199")) {
            mobileOperator = OPER.BEELINE.val;
        } else {
            mobileOperator = "OTHER";
        }
        return mobileOperator;
    }

    /**
     * *
     * CHECK VIETNAMOBILE
     *
     * @param userId
     * @return
     */
    public static boolean checkVietnamobile(String userId) {
        return userId.startsWith("92") || userId.startsWith("092") || userId.startsWith("8492") || userId.startsWith("+8492")
                || userId.startsWith("188") || userId.startsWith("0188") || userId.startsWith("84188") || userId.startsWith("+84188")
                || userId.startsWith("187") || userId.startsWith("0187") || userId.startsWith("84187") || userId.startsWith("+84187")
                || userId.startsWith("186") || userId.startsWith("0186") || userId.startsWith("84186") || userId.startsWith("+84186");
    }

    public static boolean checkBeeline(String userId) {
        return userId.startsWith("99") || userId.startsWith("099") || userId.startsWith("8499") || userId.startsWith("+8499")
                || userId.startsWith("199") || userId.startsWith("0199") || userId.startsWith("84199") || userId.startsWith("+84199");
    }

    /**
     * PLA TUAN KIEM TRA SO DIEN THOAI HOP LE HAY KO
     *
     * @param userId
     * @return
     */
    public static int checkPhoneNumber(String userId) {
        userId = userId.replace('o', '0');
        int check = -1;
        try {
            long number = Long.parseLong(userId);
        } catch (NumberFormatException ne) {
            check = -2;      //"Số điện thoại bạn nhập không phải số";
            return check;
        }
        if (userId == null || "".equals(userId)) {
            return 0;   //"Bạn chưa nhập số điện thoại";
        } else if (!((userId.startsWith("88") || userId.startsWith("86") || userId.startsWith("89") || userId.startsWith("90") || userId.startsWith("91") || userId.startsWith("92") || userId.startsWith("93")
                || userId.startsWith("94") || userId.startsWith("95") || userId.startsWith("96") || userId.startsWith("97") || userId.startsWith("98"))
                && userId.length() == 9)
                && !((userId.startsWith("088") || userId.startsWith("086") || userId.startsWith("089") || userId.startsWith("090") || userId.startsWith("091") || userId.startsWith("092") || userId.startsWith("093")
                || userId.startsWith("094") || userId.startsWith("095") || userId.startsWith("096") || userId.startsWith("097") || userId.startsWith("098"))
                && userId.length() == 10)
                && !((userId.startsWith("8488") || userId.startsWith("8486") || userId.startsWith("8489") || userId.startsWith("8490") || userId.startsWith("8491") || userId.startsWith("8492") || userId.startsWith("8493")
                || userId.startsWith("8494") || userId.startsWith("8495") || userId.startsWith("8496") || userId.startsWith("8497") || userId.startsWith("8498"))
                && userId.length() == 11)
                && !((userId.startsWith("+8488") || userId.startsWith("+8486") || userId.startsWith("+8489") || userId.startsWith("+8490") || userId.startsWith("+8491") || userId.startsWith("+8492") || userId.startsWith("+8493")
                || userId.startsWith("+8494") || userId.startsWith("+8495") || userId.startsWith("+8496") || userId.startsWith("+8497") || userId.startsWith("+8498"))
                && userId.length() == 12)
                && !((userId.startsWith("0160") || userId.startsWith("0161") || userId.startsWith("0162") || userId.startsWith("0163") || userId.startsWith("0164")
                || userId.startsWith("0188") || userId.startsWith("0187") || userId.startsWith("0186") || userId.startsWith("0165") || userId.startsWith("0166") || userId.startsWith("0167") || userId.startsWith("0168") || userId.startsWith("0169")
                || userId.startsWith("0120") || userId.startsWith("0121") || userId.startsWith("0122") || userId.startsWith("0123")
                || userId.startsWith("0124") || userId.startsWith("0125") || userId.startsWith("0126")
                || userId.startsWith("0127") || userId.startsWith("0128") || userId.startsWith("0129")) && userId.length() == 11)
                && !((userId.startsWith("84160") || userId.startsWith("84161") || userId.startsWith("84162") || userId.startsWith("84163") || userId.startsWith("84164")
                || userId.startsWith("84188") || userId.startsWith("84187") || userId.startsWith("84186") || userId.startsWith("84165") || userId.startsWith("84166") || userId.startsWith("84167") || userId.startsWith("84168") || userId.startsWith("84169")
                || userId.startsWith("84120") || userId.startsWith("84121") || userId.startsWith("84122") || userId.startsWith("84123")
                || userId.startsWith("84124") || userId.startsWith("84125") || userId.startsWith("84126")
                || userId.startsWith("84127") || userId.startsWith("84128") || userId.startsWith("84129")) && userId.length() == 12)
                && !((userId.startsWith("+84160") || userId.startsWith("+84161") || userId.startsWith("+84162") || userId.startsWith("+84163") || userId.startsWith("+84164")
                || userId.startsWith("+84188") || userId.startsWith("+84187") || userId.startsWith("+84186") || userId.startsWith("+84165") || userId.startsWith("+84166") || userId.startsWith("+84167") || userId.startsWith("+84168") || userId.startsWith("+84169")
                || userId.startsWith("+84120") || userId.startsWith("+84121") || userId.startsWith("+84122") || userId.startsWith("+84123")
                || userId.startsWith("+84124") || userId.startsWith("+84125") || userId.startsWith("+84126")
                || userId.startsWith("+84127") || userId.startsWith("+84128") || userId.startsWith("+84129")) && userId.length() == 13)) {
            check = -3;
            //"Đầu số không hợp lệ";
            return check;
        } else {
            check = 1;
        }
        return check;
    }

    public static String getTimeString() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "HH:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(cal.getTime());
    }

    public static byte[] hexToByte(String s) {
        int l = s.length() / 2;
        byte data[] = new byte[l];
        int j = 0;
        for (int i = 0; i < l; i++) {
            char c = s.charAt(j++);
            int n, b;
            n = HEXINDEX.indexOf(c);
            b = (n & 0xf) << 4;
            c = s.charAt(j++);
            n = HEXINDEX.indexOf(c);
            b += (n & 0xf);
            data[i] = (byte) b;
        }
        return data;
    }

    public static String stringToHexString(String str) {
        byte[] bytes;
        String temp = "";
        try {
            bytes = str.getBytes("US-ASCII");
        } catch (Exception ex) {
            return null;
        }
        for (int i = 0; i < bytes.length; i++) {
            temp = temp + Integer.toHexString(bytes[i]);
        }
        return temp;
    }

    public static String stringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuilder strBuffer = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            strBuffer.append(Integer.toHexString((int) chars[i]));
        }
        return strBuffer.toString();
    }

    /**
     * Kiểm tra xem 1 String có phải là số
     *
     * @param sNumber
     * @return
     */
    public static boolean isNumberic(String sNumber) {
        if (sNumber == null || "".equals(sNumber)) {
            return false;
        }
        for (int i = 0; i < sNumber.length(); i++) {
            char ch = sNumber.charAt(i);
            char ch_max = '9';
            char ch_min = '0';
            if (ch < ch_min || ch > ch_max) {
                return false;
            }
        }

        return true;
    }

    public static String[] splitString(String s, int width) {
        try {
            if (width == 0) {
                String[] ret = new String[1];
                ret[0] = s;
                return ret;
            } else if (s.isEmpty()) {
                return new String[0];
            } else if (s.length() <= width) {
                String[] ret = new String[1];
                ret[0] = s;
                return ret;
            } else {
                int NumSeg = s.length() / width + 1;
                String[] ret = new String[NumSeg];
                int startPos = 0;

                for (int i = 0; i < NumSeg - 1; i++) {
                    ret[i] = s.substring(startPos, ((width * (i + 1))));
                    startPos = (i + 1) * width;
                }
                ret[NumSeg - 1] = s.substring(startPos, s.length());
                return ret;
            }
        } catch (Exception e) {
            return new String[0];
        }
    }
}
