package com.risco.android.fairprice.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.risco.android.fairprice.R;
import com.risco.android.fairprice.utils.UniversalImageLoader;

public class HomeScreenActivity extends AppCompatActivity {
    private Context mContext=HomeScreenActivity.this;

    private Button normalModeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        normalModeButton = (Button) findViewById(R.id.button_normal);

        normalModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChooseActivity.class);
                startActivity(intent);
            }
        });

        initImageLoader();
    }

    /**
     * Initializate Universal Image Loader
     */

    private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);

        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
}
