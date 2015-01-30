package icode.org.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends Activity {

    String SENT_SMS_ACTION="SENT_SMS_ACTION";
    String DELIVERED_SMS_ACTION="DELIVERED_SMS_ACTION";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                EditText telNoText =(EditText)findViewById(R.id.mobileNo);
                EditText contentText = (EditText)findViewById(R.id.content);

                String telNo = telNoText.getText().toString();
                String content = contentText.getText().toString();
                if (validate(telNo, content))
                {
                    sendSMS(telNo, content);
                }
            }
        });
    }

    /**
     * Send SMS
     * @param phoneNumber
     * @param message
     */
    private void sendSMS(String phoneNumber, String message) {

        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent,
                0);
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0,
                deliverIntent, 0);
        //获得信息管理器
        SmsManager sms = SmsManager.getDefault();
        if (message.length() > 70) {//如果消息过长,就分割之后再发送
            List<String> msgs = sms.divideMessage(message);
            for (String msg : msgs) {
                sms.sendTextMessage(phoneNumber, null, msg, sentPI, deliverPI);//发送信息
            }
        } else {
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliverPI);
        }
        Toast.makeText(MainActivity.this, R.string.messageSuccess, Toast.LENGTH_LONG).show();

    }

    public boolean validate(String telNo, String content){

        if((null==telNo)||("".equals(telNo.trim()))){
            Toast.makeText(this, "请输入号码!",Toast.LENGTH_LONG).show();
            return false;
        }
        if((null==content)||("".equals(content.trim()))){
            Toast.makeText(this, "请输入信息内容!",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
