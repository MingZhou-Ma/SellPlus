package tech.greatinfo.sellplus.config.converter;

import org.springframework.format.Formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * 弥补 Spring boot 框架原生无法解析 ModelAttribute 里面的  Date 信息
 *
 * Created by Ericwyn on 18-8-8.
 */
public class StringToDateConverter implements Formatter<Date> {

    private String pattern;

    public StringToDateConverter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern, locale);
        return dateFormat.parse(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, locale);
        return dateFormat.format(date);
    }
}