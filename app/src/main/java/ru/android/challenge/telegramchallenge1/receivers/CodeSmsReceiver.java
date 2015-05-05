package ru.android.challenge.telegramchallenge1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Egor on 05.05.2015.
 */
public class CodeSmsReceiver extends BroadcastReceiver {
    public static final String NEW_SMS = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TELEGRAM_ORIGINATING_ADDRESS = "Telegram";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pduArray = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pduArray.length];
                for (int i = 0; i < pduArray.length; ++i) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
                }
                for (SmsMessage message : messages) {
                    if (message.getOriginatingAddress().equals(TELEGRAM_ORIGINATING_ADDRESS)) {
                        String code = message.getMessageBody().split(" ")[2];
                        Toast.makeText(context, "Code: " + code, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
