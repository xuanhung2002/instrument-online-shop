package com.shop.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtil {

    public static String removeVietnameseAccents(String str) {
        if (str == null) {
            return null;
        }

        // Normalize the string to decomposed form (NFD) to separate accents from characters
        String normalizedStr = Normalizer.normalize(str, Normalizer.Form.NFD);

        // Use regular expression to remove accents (diacritics) and keep only ASCII characters
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedStr).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }
}
