package br.com.gestaodeeventos.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import br.com.gestaodeeventos.R;

public class ImageUtil {

    public static Bitmap base64ToBitmap(String base64) {
        try {
            String base64Image;

            try {
                base64Image = base64.split(",")[1];
            } catch (Exception e) {
                base64Image = base64;
            }

            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        } catch (Exception e) {
            return null;
        }
    }

    public static RequestOptions imgOptions() {
        RequestOptions ro = new RequestOptions();
        ro.diskCacheStrategy(DiskCacheStrategy.ALL);
        ro.placeholder(R.drawable.no_image);
        ro.error(R.drawable.no_image);

        return ro;
    }

}
