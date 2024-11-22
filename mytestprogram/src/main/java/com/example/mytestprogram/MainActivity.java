package com.example.mytestprogram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
//申请动态调用权限
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//视频播放
import android.app.AlertDialog;
import android.content.DialogInterface;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 检查拨打电话权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        // 检查发送短信权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, 2);
        }

        findViewById(R.id.call_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789"));
                startActivity(intent);
            }
        });

        findViewById(R.id.mail_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:224081245"));
                intent.putExtra("sms_body", "你好！");
                startActivity(intent);
            }
        });

        findViewById(R.id.examp_button).setOnClickListener(v -> {
            // 弹出选择对话框，选择视频来源
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("选择视频来源")
                    .setItems(new CharSequence[]{"网络视频", "本地视频"}, (dialog, which) -> {
                        if (which == 0) {
                            // 如果选择了 "网络视频"
                           showNetworkVideoDialog();
                        } else if (which == 1) {
                            // 如果选择了 "本地视频"
                            showLocalVideoList(); // 显示本地视频列表
                        }
                    });
            builder.show();
        });

        findViewById(R.id.aboutme_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
            }
        });
    }

    // 显示本地视频列表的函数
    private void showLocalVideoList() {
        // 获取 raw 目录下所有的视频文件
        String[] videoFiles = getResources().getStringArray(R.array.raw_video_files);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("选择本地视频")
                .setItems(videoFiles, (dialog, which) -> {
                    // 选择一个视频后，开始播放
                    String videoUri = "android.resource://" + getPackageName() + "/" + getResources().getIdentifier(videoFiles[which], "raw", getPackageName());
                    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra("video_uri", videoUri); // 传递视频 URI
                    startActivity(intent);
                });
        builder.show();
    }

    // 显示网络视频URL输入框
    public void showNetworkVideoDialog() {
        // 创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("输入视频URL");

        // 创建输入框
        final EditText input = new EditText(MainActivity.this);
        input.setHint("请输入视频URL...");
        builder.setView(input);

        // 设置按钮
        builder.setPositiveButton("播放", (dialog, which) -> {
            String url = input.getText().toString();
            if (!url.isEmpty()) {
                // 如果 URL 是有效的，播放视频
                if (url.startsWith("https://www.bilibili.com/video/")) {
                    Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                    intent.putExtra("video_uri", url); // 传递网络视频URL
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "请输入有效的B站视频URL", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "请输入有效的URL", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", null);
        builder.show();
    }

    //处理权限结果 重写 onRequestPermissionsResult 方法：
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) { // 拨打电话权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "拨打电话权限已授予", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "拨打电话权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 2) { // 发送短信权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "发送短信权限已授予", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "发送短信权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 3) { // 发送本地视频权限
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "读取视频权限已授予", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "读取视频权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
