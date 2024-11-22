package com.example.mytestprogram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;


public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        // 为每个图标设置点击事件
        ImageButton icon1 = findViewById(R.id.icon1);
        ImageButton icon2 = findViewById(R.id.icon2);
        ImageButton icon3 = findViewById(R.id.icon3);
        ImageButton icon4 = findViewById(R.id.icon4);

        // 图标1的点击事件
        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://github.com/");
            }
        });

        // 图标2的点击事件
        icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://mail.qq.com/");
            }
        });

        // 图标3的点击事件
        icon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://chatgpt.com/");
            }
        });

        // 图标4的点击事件
        icon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("https://store.steampowered.com/");
            }
        });
    }

    // 打开链接
    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
