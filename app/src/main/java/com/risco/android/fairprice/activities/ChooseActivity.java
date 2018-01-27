package com.risco.android.fairprice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.risco.android.fairprice.R;

/**
 * Created by Albert Risco on 26/01/2018.
 */



public class ChooseActivity extends AppCompatActivity {
    //context
    private Context mContext = ChooseActivity.this;

    //TAG
    private static final String TAG = "ChooseActivity";

    //Layout things
    private Button playBlueButton;
    private Button playGreenButton;
    private Button playPurpleButton;
    private ImageView backArrow;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);

        initializeWidgets();
        initializeButtonsListeners();
    }



    private void initializeWidgets() {
        playBlueButton=(Button)findViewById(R.id.button_play_blue);
        //playGreenButton=(Button)findViewById(R.id.button_play_green);
        playPurpleButton=(Button)findViewById(R.id.button_play_purple);
        backArrow=(ImageView)findViewById(R.id.back_arrow);

    }
    private void initializeButtonsListeners() {
        playBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EasyModeActivity.class);
                startActivity(intent);
            }
        });

        playPurpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HardModeActivity.class);
                startActivity(intent);
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }


}
