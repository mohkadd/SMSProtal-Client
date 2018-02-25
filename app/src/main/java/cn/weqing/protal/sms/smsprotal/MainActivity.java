package cn.weqing.protal.sms.smsprotal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final String SAVE_FILE = "unique_id_file.dat";
    public static final String SERVER_ADD_URL = "http://url.w2fzu.com/server.php";
    public static final String SERVER_LOOK_URL = "http://url.w2fzu.com/list.php";
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.textView)).setText("扫描二维码查看短信记录");
        ((ImageView)findViewById(R.id.imageView)).setImageBitmap(
                QRCodeUtil.generateBitmap(
                        SERVER_LOOK_URL + "?token=" + this.getId(),
                        350,
                        350));
    }

    private String getId() {
        token = this.readIdFromFile();
        if (token == null) {
            token = SmsUtil.toMD5(SmsUtil.getUniqueId());
            writeIdToFile(token);
        }
        return token;
    }

    private String readIdFromFile() {
        byte[] buffer = new byte[1024];
        int count;
        StringBuilder sb = new StringBuilder();
        FileInputStream inStream = null;
        try {
            inStream = this.openFileInput(SAVE_FILE);
            while ((count = inStream.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, count));
            }
            return sb.toString();
        } catch (IOException e) {
            Log.e("MAIN_ACTIVITY", "FileInStream IOException");
            return null;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    Log.e("Read_MAIN_ACTIVITY", "FileInStream Close Exception");
                }
            }

        }
    }

    private void writeIdToFile(String content){
        if(content == null || "".equals(content)) {
            Log.e("Write_MAIN_ACTIVITY", "Arguments is empty Or null");
            return ;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(SAVE_FILE, MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fileOutputStream) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.e("Write_MAIN_ACTIVITY", "FileOutputStream Close Exception");
                }
            }
        }
    }
}
