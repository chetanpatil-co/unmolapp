package com.sspl.unmolapp.Util;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;

public class GupShupSMS {
    private String url = " ";

    public static boolean sendSMS(String message, String No, Date todate, String flow) {
        String mobileNo = null;
        String SMSText = null;
        StringBuffer sms = new StringBuffer();
        if (flow.equalsIgnoreCase("1")) {
            sms.append("Sorry, this number has already registered.");
        }
        else if (flow.equalsIgnoreCase("2")) {
            sms.append("Congrats..!! Registered Successfully.");
        }
        else if (flow.equalsIgnoreCase("3")) {
            sms.append("Your coupon code is : " + message);
        }
        else if (flow.equalsIgnoreCase("4")) {
            sms.append("Your coupon is validated.Please Visit at  : " + message);
        }
        //for already used coupon users
        else if (flow.equalsIgnoreCase("5")) {
            sms.append("Coupon already used, Thank you.");
        }
        //coupon sell 70%
        else if (flow.equalsIgnoreCase("6")) {
            sms.append("Product Limit of  " + message + " reached 70%");
        }
        //OTP
        else if (flow.equalsIgnoreCase("7")) {
            sms.append("Your OTP is : " + message);
        }

        if ((message != null) && ((message.length()) > 0)) {
            mobileNo = No;

            try {
                URL smsURL = new URL(
                        "http://enterprise.smsgupshup.com/GatewayAPI/rest?method=SendMessage&send_to="
                                + mobileNo
                                + "&msg="
                                + URLEncoder.encode(sms.toString(), "UTF-8")
                                + "&msg_type=TEXT&userid=2000062157&auth_scheme=plain&password=abc1234&v=1.1&format=text");

                URLConnection tacConnection = smsURL.openConnection();
                DataInputStream dis = new DataInputStream(
                        tacConnection.getInputStream());
                String inputLine;

                while ((inputLine = dis.readLine()) != null) {
                    System.out.println(inputLine);
                }

                dis.close();
                System.out.println("SMS Sent To -- " + No);
                System.out.println("SMS Text -- " + message);
            } catch (MalformedURLException me) {
                System.out.println("MalformedURLException: " + me);
                return false;
            } catch (IOException ioe) {
                System.out.println("IOException: " + ioe);
                return false;
            } catch (Exception e) {
                System.out.println("Exception: " + e);
                return false;
            } finally {

            }
        }

        return true;
    }
}
