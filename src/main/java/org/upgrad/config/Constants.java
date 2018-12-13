package org.upgrad.config;

import java.util.regex.Pattern;

public class Constants {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_CONTACT_NUMBER_REGEX = Pattern.compile("\\d{10}");
    public static final Integer PASSWORD_MIN_LENGTH = 8;
    public static final Pattern NUMBER_REGEX = Pattern.compile(".*\\d+.*");
    public static final Pattern SPECIAL_REGEX = Pattern.compile("[#@$%&*!^]");
    public static final Pattern VALID_ZIP_CODE = Pattern.compile("\\d{5}");

}