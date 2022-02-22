package com.example.chapter6.Util;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

public class MediaUtil {

    private static Map<String, MediaType> mediaTypeMap;

    static{
        mediaTypeMap = new HashMap<>();

        mediaTypeMap.put("JPEG",MediaType.IMAGE_JPEG);
        mediaTypeMap.put("JPG",MediaType.IMAGE_JPEG);
        mediaTypeMap.put("PNG",MediaType.IMAGE_PNG);
        mediaTypeMap.put("GIF",MediaType.IMAGE_GIF);
    }

    public static MediaType getMediaType(String type){
        return mediaTypeMap.get(type.toUpperCase());
    }

    public static boolean containsMediaType(String mediaType){
        return mediaTypeMap.values().contains(MediaType.valueOf(mediaType));
    }
}
