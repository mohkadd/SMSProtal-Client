package cn.weqing.protal.sms.smsprotal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by weifeng on 2018/2/1.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onReceive(Context context, Intent intent) {
        List<Sms> smsList = new ArrayList<>();
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object p : pdus) {
            byte[] pdu = (byte[]) p;
            SmsMessage message = SmsMessage.createFromPdu(pdu);
            String content = message.getMessageBody();
            String receiveTime = dateFormat.format(new Date(message.getTimestampMillis()));
            String senderNumber = message.getOriginatingAddress();
            smsList.add(new Sms(senderNumber, receiveTime, content));
        }
        SmsUtil.sendSmsToServer(MainActivity.token, smsList);
    }
}
