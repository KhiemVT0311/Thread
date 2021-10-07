package com.eup.threaddemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.eup.threaddemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
     public static final  int MESSAGE_COUNTDOWN = 1000;
    private static final int MESSAGE_DONE = 1002;
    private ActivityMainBinding binding;
    private Handler mhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        initView();
    }

    private void initView() {
        binding.btnCount.setOnClickListener(this);
        mhandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MESSAGE_COUNTDOWN:
                        binding.tvTimer.setText(String.valueOf(msg.arg1));
                        break;
                    case MESSAGE_DONE:
                        Toast.makeText(MainActivity.this,"Done!!!",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }
        };
    }

    @Override
    public void onClick(View view) {
    doCounDown();
    }

    private void doCounDown() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 10;
                do {
                    time --;
                    Message message = new Message();
                    message.what = MESSAGE_COUNTDOWN;
                    message.arg1 = time;
                    mhandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                } while (time>0);
                mhandler.sendEmptyMessage(MESSAGE_DONE);
            }

        });
           thread.start();

    }

}