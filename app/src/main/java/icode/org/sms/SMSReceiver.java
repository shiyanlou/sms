package icode.org.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    private static final String strRes = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        if(strRes.equals(arg1.getAction())){
            StringBuilder sb = new StringBuilder();
            Bundle bundle = arg1.getExtras();
            if(bundle!=null){
                Object[] pdus = (Object[])bundle.get("pdus");//拿到短信的内容
                SmsMessage[] msg = new SmsMessage[pdus.length];
                for(int i = 0 ;i<pdus.length;i++){
                    //创建信息
                    msg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }

                for(SmsMessage curMsg:msg){
                    sb.append("来自:【");
                    sb.append(curMsg.getDisplayOriginatingAddress());//发送人
                    sb.append("】信息 ： ");
                    sb.append(curMsg.getDisplayMessageBody());//信息内容
                }
                //将信息简单的显示出来
                Toast.makeText(arg0,
                         sb.toString(),
                        Toast.LENGTH_SHORT).show();//将短信内容显示出来
            }
        }
    }
}
