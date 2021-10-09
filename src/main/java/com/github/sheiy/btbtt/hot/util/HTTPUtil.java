package com.github.sheiy.btbtt.hot.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class HTTPUtil {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private static final Gson GSON = new GsonBuilder()
            .setLenient()
            .create();

    public static <T> T get(String url, Class<T> responseType) {
        return get(url, responseType, StandardCharsets.UTF_8);
    }

    public static <T> T get(String url, Class<T> responseType, Charset charset) {
        HttpRequest httpRequest;
        try {
            httpRequest = HttpRequest.newBuilder(new URI(url)).GET().build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("URL格式不正确");
        }
        HttpResponse<String> httpResponse;
        try {
            httpResponse = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString(charset));
            if (!(httpResponse.statusCode() >= 200 || httpResponse.statusCode() < 300)) {
                throw new RuntimeException("返回码:" + httpResponse.statusCode() + " Body:" + httpResponse.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("请求失败");
        }
        if(responseType.isAssignableFrom(String.class)){
            return (T) httpResponse.body();
        }
        return GSON.fromJson(httpResponse.body(), responseType);
    }
}
