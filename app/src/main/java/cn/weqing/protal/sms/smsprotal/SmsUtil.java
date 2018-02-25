package cn.weqing.protal.sms.smsprotal;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

/**
 * Created by weifeng on 2018/2/15.
 */

public class SmsUtil {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
    public static final Gson gson = new Gson();


    public static String getUniqueId(){
        return UUID.randomUUID().toString();
    }

    public static String toMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] input = str.getBytes();
            messageDigest.update(input);
            byte[] resultByteArray = messageDigest.digest();
            char[] resultCharArray = new char[resultByteArray.length * 2];
            int index = 0;
            for (byte b : resultByteArray) {
                resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
                resultCharArray[index++] = hexDigits[b & 0xf];
            }
            return new String(resultCharArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean sendSmsToServer(String token, List<Sms> smsList) {
        if (smsList != null) {
            for (Sms sms: smsList) {
                OkHttpClient okHttpClient = new OkHttpClient();
                String json = gson.toJson(sms, Sms.class);
                final RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);
                Request request = new Request.Builder()
                        .url(MainActivity.SERVER_ADD_URL)
                        .addHeader("X-SMSPROTAL-TOKEN", token)
                        .post(requestBody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(Response arg0) throws IOException {
                    }
                    @Override
                    public void onFailure(Request arg0, IOException arg1) {
                    }
                });
            }
            return true;
        }
        return false;
    }
}
