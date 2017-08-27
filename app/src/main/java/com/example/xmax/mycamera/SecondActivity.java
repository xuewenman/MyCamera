package com.example.xmax.mycamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;


/**
 * Created by xmax on 2017/5/9.
 */

public class SecondActivity extends Activity {
    private Button rowCut;
    private Button colCut;
    private ImageView picture2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_layout);
        rowCut  = (Button)findViewById(R.id.row);
        colCut  = (Button)findViewById(R.id.col);
        Intent intent = getIntent();
        rowCut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
