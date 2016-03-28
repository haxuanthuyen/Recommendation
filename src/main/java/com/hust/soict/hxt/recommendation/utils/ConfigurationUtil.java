package com.hust.soict.hxt.recommendation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by thuyenhx on 3/15/16.
 */
public class ConfigurationUtil {
    protected Logger logger = LoggerFactory.getLogger("warringLog");

    private static final String CONFIG_FILE = "conf.properties";
    final public static String[] emptyStringArray = {};
    private static ConfigurationUtil conf;
    private Properties props;

    private ConfigurationUtil() {
        props = new Properties();
        loadConf();
    }

    public static ConfigurationUtil getInstance() {
        if (conf == null) {
            conf = new ConfigurationUtil();
        }
        return conf;
    }

    private void loadConf() {
        Reader reader = null;
        try{
            reader = new FileReader(CONFIG_FILE);
            props.load(reader);
        }catch (Exception ex) {
            logger.warn("error load conf", ex);
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String get(String name) {
        return props.getProperty(name);
    }

    public String get(String name, String defaultValue) {
        return props.getProperty(name, defaultValue);
    }

    /**
     * Get the value of the <code>name</code> property as a trimmed
     * <code>String</code>, <code>null</code> if no such property exists. If the
     * key is deprecated, it returns the value of the first key which replaces
     * the deprecated key and is not null
     * <p/>
     * Values are processed for <a href="#VariableExpansion">variable
     * expansion</a> before being returned.
     *
     * @param name the property name.
     * @return the value of the <code>name</code> or its replacing property, or
     * null if no such property exists.
     */
    public String getTrimmed(String name) {
        String value = get(name);

        if (null == value) {
            return null;
        } else {
            return value.trim();
        }
    }

    /**
     * Get the value of the <code>name</code> property as an <code>int</code>.
     * <p/>
     * If no such property exists, the provided default value is returned, or if
     * the specified value is not a valid <code>int</code>, then an error is
     * thrown.
     *
     * @param name         property name.
     * @param defaultValue default value.
     * @return property value as an <code>int</code>, or
     * <code>defaultValue</code>.
     * @throws NumberFormatException when the value is invalid
     */
    public int getInt(String name, int defaultValue) {
        String valueString = getTrimmed(name);
        if (valueString == null)
            return defaultValue;
        String hexString = getHexDigits(valueString);
        if (hexString != null) {
            return Integer.parseInt(hexString, 16);
        }
        return Integer.parseInt(valueString);
    }

    /**
     * Get the value of the <code>name</code> property as a <code>long</code>.
     * If no such property exists, the provided default value is returned, or if
     * the specified value is not a valid <code>long</code>, then an error is
     * thrown.
     *
     * @param name         property name.
     * @param defaultValue default value.
     * @return property value as a <code>long</code>, or
     * <code>defaultValue</code>.
     * @throws NumberFormatException when the value is invalid
     */
    public long getLong(String name, long defaultValue) {
        String valueString = getTrimmed(name);
        if (valueString == null)
            return defaultValue;
        String hexString = getHexDigits(valueString);
        if (hexString != null) {
            return Long.parseLong(hexString, 16);
        }
        return Long.parseLong(valueString);
    }

    private String getHexDigits(String value) {
        boolean negative = false;
        String str = value;
        String hexString = null;
        if (value.startsWith("-")) {
            negative = true;
            str = value.substring(1);
        }
        if (str.startsWith("0x") || str.startsWith("0X")) {
            hexString = str.substring(2);
            if (negative) {
                hexString = "-" + hexString;
            }
            return hexString;
        }
        return null;
    }

    /**
     * Get the value of the <code>name</code> property as a <code>float</code>.
     * If no such property exists, the provided default value is returned, or if
     * the specified value is not a valid <code>float</code>, then an error is
     * thrown.
     *
     * @param name         property name.
     * @param defaultValue default value.
     * @return property value as a <code>float</code>, or
     * <code>defaultValue</code>.
     * @throws NumberFormatException when the value is invalid
     */
    public float getFloat(String name, float defaultValue) {
        String valueString = getTrimmed(name);
        if (valueString == null)
            return defaultValue;
        return Float.parseFloat(valueString);
    }

    /**
     * Get the value of the <code>name</code> property as a <code>boolean</code>
     * . If no such property is specified, or if the specified value is not a
     * valid <code>boolean</code>, then <code>defaultValue</code> is returned.
     *
     * @param name         property name.
     * @param defaultValue default value.
     * @return property value as a <code>boolean</code>, or
     * <code>defaultValue</code>.
     */
    public boolean getBoolean(String name, boolean defaultValue) {
        String valueString = getTrimmed(name);
        if (null == valueString || "".equals(valueString)) {
            return defaultValue;
        }

        valueString = valueString.toLowerCase();

        if ("true".equals(valueString))
            return true;
        else if ("false".equals(valueString))
            return false;
        else
            return defaultValue;
    }

    /**
     * Get the comma delimited values of the <code>name</code> property as an
     * array of <code>String</code>s. If no such property is specified then
     * <code>null</code> is returned.
     *
     * @param name property name.
     * @return property value as an array of <code>String</code>s, or
     * <code>null</code>.
     */
    public String[] getStrings(String name) {
        String valueString = get(name);
        return valueString.split("\\s+,\\s+");
    }

    /**
     * Get the comma delimited values of the <code>name</code> property as an
     * array of <code>String</code>s. If no such property is specified then
     * default value is returned.
     *
     * @param name         property name.
     * @param defaultValue The default value
     * @return property value as an array of <code>String</code>s, or default
     * value.
     */
    public String[] getStrings(String name, String... defaultValue) {
        String valueString = get(name);
        if (valueString == null) {
            return defaultValue;
        } else {
            return valueString.split("\\s+,\\s+");
        }
    }

    /**
     * Get the comma delimited values of the <code>name</code> property as a
     * collection of <code>String</code>s, trimmed of the leading and trailing
     * whitespace. If no such property is specified then empty
     * <code>Collection</code> is returned.
     *
     * @param name property name.
     * @return property value as a collection of <code>String</code>s, or empty
     * <code>Collection</code>
     */
    public Collection<String> getTrimmedStringCollection(String name) {
        String valueString = get(name);
        if (null == valueString) {
            Collection<String> empty = new ArrayList<String>();
            return empty;
        }
        return new ArrayList<String>(Arrays.asList(getTrimmedStrings(valueString)));
    }

    /**
     * Get the comma delimited values of the <code>name</code> property as an
     * array of <code>String</code>s, trimmed of the leading and trailing
     * whitespace. If no such property is specified then an empty array is
     * returned.
     *
     * @param name property name.
     * @return property value as an array of trimmed <code>String</code>s, or
     * empty array.
     */
    public String[] getTrimmedStrings(String name) {
        String valueString = get(name);
        return valueString.trim().split("\\s+,\\s+");
    }

    /**
     * Get the comma delimited values of the <code>name</code> property as an
     * array of <code>String</code>s, trimmed of the leading and trailing
     * whitespace. If no such property is specified then default value is
     * returned.
     *
     * @param name         property name.
     * @param defaultValue The default value
     * @return property value as an array of trimmed <code>String</code>s, or
     * default value.
     */
    public String[] getTrimmedStrings(String name, String... defaultValue) {
        String valueString = get(name);
        if (null == valueString) {
            return defaultValue;
        } else {
            return valueString.trim().split("\\s+,\\s+");
        }
    }
}