package com.github.sheiy.btbtt.hot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;

public abstract class ThreadFinder {

    private static final Pattern NEW_PATTERN = Pattern.compile("<a href=\"(.*)\" class=\"subject_link thread-new\" .*>(.*)</a>");
    private static final Pattern OLD_PATTERN = Pattern.compile("<a href=\"(.*)\" class=\"subject_link thread-old\" .*>(.*)</a>");

    public static List<ImmutablePair<String,String>> find(String html) {
        var result = new ArrayList<ImmutablePair<String,String>>();
        Matcher matcher = NEW_PATTERN.matcher(html);
        while (matcher.find()) {
            result.add(ImmutablePair.of(matcher.group(1),matcher.group(2)));
        }
        matcher = OLD_PATTERN.matcher(html);
        while (matcher.find()) {
            result.add(ImmutablePair.of(matcher.group(1),matcher.group(2)));
        }
        return result;
    }

}
