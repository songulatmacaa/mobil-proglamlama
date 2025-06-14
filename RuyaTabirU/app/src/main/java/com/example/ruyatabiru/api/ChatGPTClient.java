package com.example.ruyatabiru.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.*;

public class ChatGPTClient {

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public interface ChatGPTResponseCallback {
        void onResponse(String response);
        void onFailure(String error);
    }

    public static void yorumAl(String kullaniciRuyasi, ChatGPTResponseCallback callback) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            messages.put(new JSONObject().put("role", "system").put("content", "Sen bir rüya yorumcusun. Kullanıcının yazdığı rüyayı kısa, anlamlı ve Türkçe olarak yorumla."));
            messages.put(new JSONObject().put("role", "user").put("content", kullaniciRuyasi));

            json.put("messages", messages);
        } catch (JSONException e) {
            callback.onFailure("JSON oluşturulamadı: " + e.getMessage());
            return;
        }

        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("İstek başarısız: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure("Yanıt başarısız: " + response.code());
                    return;
                }

                String responseBody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONArray choices = jsonObject.getJSONArray("choices");
                    String message = choices.getJSONObject(0).getJSONObject("message").getString("content");
                    callback.onResponse(message.trim());
                } catch (JSONException e) {
                    callback.onFailure("Yanıt ayrıştırılamadı: " + e.getMessage());
                }
            }
        });
    }
}
