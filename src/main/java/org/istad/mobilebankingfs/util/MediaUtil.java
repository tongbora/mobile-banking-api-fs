package org.istad.mobilebankingfs.util;

public class MediaUtil {

    public static String extractExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
