package tech.greatinfo.sellplus.config.converter;

import org.springframework.format.Formatter;
import java.util.Locale;

/**
 *
 * ModelAttribute 无法解析 Boolean , 尝试用这个自定义 Formatter 来解决
 *
 * Created by Ericwyn on 18-8-8.
 */
public class StringToBooleanConverter implements Formatter<Boolean> {
    public StringToBooleanConverter() {

    }
    @Override
    public Boolean parse(String text, Locale locale) {
        return text.equals("true");
    }

    @Override
    public String print(Boolean b, Locale locale) {
        return b.toString();
    }
}