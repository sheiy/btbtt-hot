package com.github.sheiy.btbtt.hot.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public abstract class BTFinder {

    private static final Pattern URL_PATTERN = Pattern.compile("<a href=\"(.*)\" target=\"_blank\" rel=\"nofollow\"><img src=\"/view/image/filetype/torrent.gif\" width=\"16\" height=\"16\" />(.*)</a>");
    private static final Pattern COUNT_PATTERN = Pattern.compile("<td>([0-9]*) 次</td>");

    public static ImmutablePair<String,Integer> find(String html) {
        var result = new ArrayList<ImmutablePair<String,Integer>>();
        Matcher urlMarcher = URL_PATTERN.matcher(html);
        Matcher countMarcher = COUNT_PATTERN.matcher(html);
        while (urlMarcher.find()) {
            if(countMarcher.find()){
                result.add(ImmutablePair.of(urlMarcher.group(1),Integer.parseInt(countMarcher.group(1))));
            }
        }
        if(result.isEmpty()){
            return null;
        }
        result.sort(new Comparator<ImmutablePair<String, Integer>>() {
            @Override
            public int compare(ImmutablePair<String, Integer> o1, ImmutablePair<String, Integer> o2) {
                return o1.getRight().compareTo(o2.getRight());
            }
        });
        return result.get(0);
    }

}
