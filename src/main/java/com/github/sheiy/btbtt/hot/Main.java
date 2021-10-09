package com.github.sheiy.btbtt.hot;

import java.util.ArrayList;

import com.github.sheiy.btbtt.hot.util.BTFinder;
import com.github.sheiy.btbtt.hot.util.HTTPUtil;
import com.github.sheiy.btbtt.hot.util.ThreadFinder;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class Main {

    public static final Integer HOT_THRESHOLD = 5000;
    public static final String HOST = "http://www.btbtt20.com/";
    public static final String FIND_URL = HOST + "forum-index-fid-951-typeid1-0-typeid2-0-typeid3-0-typeid4-1376739";

    public static void main(String[] args) {
        var threads = new ArrayList<ImmutablePair<String, String>>();
        String x = FIND_URL + "-page-%d.htm";
        int i = 1;
        while (true) {
            String format = String.format(x, i);
            String response = HTTPUtil.get(format, String.class);
            var thread = ThreadFinder.find(response);
            System.out.println("查找" + format + " \r\n 找到" + thread.size());
            if (thread.isEmpty()) {
                break;
            }
            threads.addAll(thread);
            i++;
        }
        threads.parallelStream().forEach(thread -> {
            var pair = BTFinder.find(HTTPUtil.get(HOST + thread.getLeft(), String.class));
            if (pair != null && pair.getRight() > HOT_THRESHOLD) {
                System.out.println(thread.getRight() + " " + HOST+pair.getLeft() + " " + pair.getRight());
            }
        });
    }

}
