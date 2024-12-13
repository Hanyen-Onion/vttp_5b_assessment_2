package vttp.batch5.ssf.noticeboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpSession;
import vttp.batch5.ssf.noticeboard.models.Notice;

public class Util {

    private static final String ID ="id";

    //get sess
    public static String getNoticeId(HttpSession sess) {
        
       String noticeId = (String)sess.getAttribute(ID);
       // Initialize the session if it is a new session
       if (null == noticeId) {

           noticeId = "";
           System.out.println("no id");
           sess.setAttribute(ID, noticeId);
       }

        return noticeId;
    }
    
    public static Date stringToDate(String string) {
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = df.parse(string);
            return date;
        } catch (Exception ex){
            System.out.println("date parsing error");
        }
        return null;
    }

    public static String dateToString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String stringDate = df.format(date);

        return stringDate;
    }
}
