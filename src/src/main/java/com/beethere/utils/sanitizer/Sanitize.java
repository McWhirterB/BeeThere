package com.beethere.utils.sanitizer;

public class Sanitize {

    public static String sanitizeHtml(String untrustedHtml) {
            if (untrustedHtml == null) {
                return "";
            }
            return untrustedHtml.replaceAll("<[^>]*>", "")
                            .replaceAll("&nbsp;", " ")
                            .replaceAll("&lt;", "<")
                            .replaceAll("&gt;", ">")
                            .replaceAll("&amp;", "&")
                            .replaceAll("&quot;", "\"")
                            .replaceAll("&#39;", "'");
        }
}