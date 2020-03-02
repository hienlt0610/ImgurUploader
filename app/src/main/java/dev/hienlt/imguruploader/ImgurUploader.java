package dev.hienlt.imguruploader;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ImgurUploader {
    private ImgurUploader() {
    }

    public static void uploadImage(String imagePath, OnImgurImageUpload onImgurImageUpload) {
        AndroidNetworking.upload("\thttps://api.imgur.com/3/image")
                .addHeaders("Authorization", "Client-ID 246efcb4bd46f5c")
                .addMultipartFile("image", new File(imagePath))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (onImgurImageUpload != null) {
                            onImgurImageUpload.onSuccess(getImageUrl(response));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (onImgurImageUpload != null) {
                            onImgurImageUpload.onError(anError);
                        }
                    }
                });
    }

    private static String getImageUrl(JSONObject response) {
        try {
            if (response.has("data")) {
                JSONObject data = response.getJSONObject("data");
                return data.optString("link");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnImgurImageUpload {
        void onSuccess(String imageUrl);

        void onError(Exception e);
    }
}
