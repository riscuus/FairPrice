package com.risco.android.fairprice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.risco.android.fairprice.R;
import com.risco.android.fairprice.models.Question;
import com.risco.android.fairprice.utils.FirebaseMethods;
import com.risco.android.fairprice.utils.UniversalImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Albert Risco on 21/01/2018.
 */

public class EasyModeActivity extends AppCompatActivity {
    private static final String TAG = "EasyModeActivity";

    private Context mContext = EasyModeActivity.this;

    //Layout things
    private Button redButton;
    private Button blueButton;
    private Button purpleButton;
    private Button greenButton;
    private TextView productName;
    private ProgressBar photoProgress;
    private ImageView productPhoto;
    private ImageView correctPhoto;
    private ImageView progressTime;
    private Button tryAgainButton;
    private ImageView heart1;
    private ImageView heart2;
    private ImageView heart3;
    private TextView textPoints;



    //Firebase things
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    //product price
    private int productRealPrice;

    //product number
    private int num=1;

    //Handler
    private final Handler handler = new Handler();

    //timer
    private CountDownTimer timer;

    //lives
    private int lives;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_normal_easy);

        mFirebaseMethods=new FirebaseMethods(mContext);
        lives=3;


        initializeWidgets();
        setupFirebase();
        setHearts();
        initializeButtonsListeners();


    }

    private void setupCountDown(){
        timer = new CountDownTimer(9000, 500) {

            public void onTick(long millisUntilFinished) {
                Long millis = millisUntilFinished;
                Log.d(TAG, "onTick: millis: "+millis);
                Log.d(TAG, "onTick: millisUntilFinished: "+millisUntilFinished);
                if(millis>8000 && millis<9000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_1));
                    Log.d(TAG, "onTick: 8000--9000");
                }
                if(millis>7000 && millis<8000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_2));
                    Log.d(TAG, "onTick: 7000--8000");
                }
                if(millis>6000 && millis<7000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_15));
                    Log.d(TAG, "onTick: 6000--7000");
                }

                if(millis>5000 && millis<6000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_3));
                    Log.d(TAG, "onTick: 5000--6000");
                }
                if(millis>4000 && millis<5000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_4));
                    Log.d(TAG, "onTick: 4000--5000");
                }

                if(millis>3000 && millis<4000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_35));
                    Log.d(TAG, "onTick: 3000--4000");
                }

                if(millis>2000 && millis<3000){
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_5));
                    Log.d(TAG, "onTick: 2000--3000");

                }
                if(millis>1000 && millis<2000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_6));
                    Log.d(TAG, "onTick: 1000--2000");
                }
                if(millis>500 && millis<1000) {
                    progressTime.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.timer_7));
                    Log.d(TAG, "onTick: 1000--2000");
                }


            }

            public void onFinish() {
                setContentView(R.layout.acitivity_mode_normal_losed);
                initializeTryAgainButton();
            }
        }.start();

    }

    private void initializeTryAgainButton() {
        tryAgainButton=(Button)findViewById(R.id.button_tryagain);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HomeScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        textPoints =(TextView)findViewById(R.id.text_points);
        Log.d(TAG, "initializeTryAgainButton: num: "+num);
        textPoints.setText(String.valueOf(num-1));
    }

    private void initializeButtonsListeners() {

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                correctPhoto.setVisibility(View.GONE);
            }
        };

        final Runnable setupFirebaseRunnable = new Runnable() {
            @Override
            public void run() {
                setupFirebase();
            }
        };

        final Runnable setContentView = new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.acitivity_mode_normal_losed);
                initializeTryAgainButton();
            }
        };



        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String redButtonText = redButton.getText().toString().replace("€", "");
                if(redButtonText.equals(String.valueOf(productRealPrice)) && lives!=0){
                    timer.cancel();

                    num=num+1;

                    correctPhoto.setVisibility(View.VISIBLE);
                    handler.postDelayed(r, 1000);


                    handler.postDelayed(setupFirebaseRunnable, 1000);
                }else{
                    lives=lives-1;
                    setHearts();
                    if(lives==0){

                        handler.postDelayed(setContentView, 200);
                    }
                }
            }
        });

        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String blueButtonText = blueButton.getText().toString().replace("€", "");
                if(blueButtonText.equals(String.valueOf(productRealPrice)) && lives!=0){
                    timer.cancel();
                    num=num+1;

                    correctPhoto.setVisibility(View.VISIBLE);
                    handler.postDelayed(r, 1000);

                    handler.postDelayed(setupFirebaseRunnable, 1000);


                }else{
                    lives=lives-1;
                    setHearts();
                    if(lives==0){
                        handler.postDelayed(setContentView, 200);
                    }
                }
            }
        });

        purpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String purpleButtonText = purpleButton.getText().toString().replace("€", "");
                if(purpleButtonText.equals(String.valueOf(productRealPrice)) && lives!=0){
                    timer.cancel();
                    num=num+1;

                    correctPhoto.setVisibility(View.VISIBLE);
                    handler.postDelayed(r, 1000);

                    handler.postDelayed(setupFirebaseRunnable, 1000);

                }else{
                    lives=lives-1;
                    setHearts();
                    if(lives==0){
                        handler.postDelayed(setContentView, 200);
                    }
                }
            }
        });

        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String greenButtonText = greenButton.getText().toString().replace("€", "");
                Log.d(TAG, "onClick: greenButtonText: "+ greenButtonText);
                if(greenButtonText.equals(String.valueOf(productRealPrice)) && lives!=0){
                    timer.cancel();
                    num=num+1;

                    correctPhoto.setVisibility(View.VISIBLE);
                    handler.postDelayed(r, 1000);

                    handler.postDelayed(setupFirebaseRunnable, 1000);

                }else{
                    lives=lives-1;
                    setHearts();
                    if(lives==0){
                        handler.postDelayed(setContentView, 200);
                    }
                }
            }
        });

    }

    private void setHearts() {
        switch (lives){
            case 3:
                heart1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart));
                heart2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart));
                heart3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart));
                break;
            case 2:
                heart1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart));
                heart2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart));
                heart3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_no_heart));
                break;
            case 1:
                heart1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_heart));
                heart2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_no_heart));
                heart3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_no_heart));
                break;
            case 0:
                heart1.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_no_heart));
                heart2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_no_heart));
                heart3.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_no_heart));
                break;
        }
    }

    private void initializeWidgets(){
        redButton=(Button)findViewById(R.id.button_red);
        blueButton=(Button)findViewById(R.id.button_blue);
        greenButton=(Button)findViewById(R.id.button_green);
        purpleButton=(Button)findViewById(R.id.button_purple);
        productName=(TextView)findViewById(R.id.text_product_name);
        photoProgress=(ProgressBar)findViewById(R.id.progress_photo);
        productPhoto=(ImageView)findViewById(R.id.image_product);
        correctPhoto=(ImageView)findViewById(R.id.image_correct);
        progressTime=(ImageView)findViewById(R.id.progress_time);
        heart1=(ImageView)findViewById(R.id.heart1);
        heart2=(ImageView)findViewById(R.id.heart2);
        heart3=(ImageView)findViewById(R.id.heart3);


    }

    private void setWidgets(Question question){
        Log.d(TAG, "setWidgets: question: "+question.toString());
        try{
            //setting global variable
            productRealPrice=question.getReal_price().intValue();

            setImage(question);
            ArrayList<Integer> randomPrices = setRandomPrices(question.getReal_price());
            randomPrices.add(question.getReal_price().intValue());

            Collections.shuffle(randomPrices);

            blueButton.setText(String.valueOf(randomPrices.get(0))+"€");
            greenButton.setText(String.valueOf(randomPrices.get(1))+"€");
            purpleButton.setText(String.valueOf(randomPrices.get(2))+"€");
            redButton.setText(String.valueOf(randomPrices.get(3))+"€");

            productName.setText(question.getProduct());
        }catch (Exception e){
            Log.e(TAG, "setWidgets: "+e.getMessage());

        }


    }

    private void setImage(Question question){
        UniversalImageLoader.setImage(question.getUrl(), productPhoto,photoProgress, "");
    }

    private ArrayList setRandomPrices(Long longReal){
        int real = longReal.intValue();
        int lowLimit1=real/2;
        int lowLimit2=real-(real/10);
        int highLimit1=real+(real/10);
        int highLimit2=real*2;
        ArrayList<Integer> prices = new ArrayList<>();


        Random r = new Random();
        for(int i=0; i<3; i++){
            prices.add(r.nextInt(highLimit2-highLimit1)+highLimit1);
            prices.add(r.nextInt(lowLimit2-lowLimit1)+lowLimit1);
            prices.remove(r.nextInt(prices.size()-1));
        }

        return prices;
    }

    /*
    ------------------Firebase--------------------------
     */

    private void setupFirebase(){
        Log.d(TAG, "setupFirebase: setting up firebase database");

        mFireBaseDatabase = FirebaseDatabase.getInstance();
        myRef=mFireBaseDatabase.getReference();



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setWidgets(mFirebaseMethods.getQuestion(dataSnapshot, num));
                setupCountDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
