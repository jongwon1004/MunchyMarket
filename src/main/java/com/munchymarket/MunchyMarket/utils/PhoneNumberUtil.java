package com.munchymarket.MunchyMarket.utils;

public class PhoneNumberUtil {

    public static String phoneNumberFormat(String phoneNumber) {
        return  "+81" + phoneNumber.substring(1);
    }
}
