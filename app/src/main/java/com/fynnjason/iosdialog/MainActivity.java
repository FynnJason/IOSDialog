package com.fynnjason.iosdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fynnjason.library.view.IOSDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> list = new ArrayList<>();
        list.add("相册");
        list.add("拍照");

        final IOSDialog iosDialog = new IOSDialog(list, "我是标题");
        iosDialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iosDialog.slideDown();
            }
        });
        iosDialog.setOptionsListener(new IOSDialog.optionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        iosDialog.show(getSupportFragmentManager(), "ios");
    }
}
