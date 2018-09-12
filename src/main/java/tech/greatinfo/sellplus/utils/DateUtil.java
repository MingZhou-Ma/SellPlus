package tech.greatinfo.sellplus.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Ericwyn on 18-7-27.
 */
public class DateUtil {
    private static HashMap<String, SimpleDateFormat> formatMap = new HashMap<>();
    public static Date formatString(String date,String format){
        SimpleDateFormat sdf = null;
        if ((sdf = formatMap.get(format)) == null){
            sdf = new SimpleDateFormat(format);
            formatMap.put(format,sdf);
        }
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date,String format){
        SimpleDateFormat sdf = null;
        if ((sdf = formatMap.get(format)) == null){
            sdf = new SimpleDateFormat(format);
            formatMap.put(format,sdf);
        }
        return sdf.format(date);
    }


}
