package com.risco.android.fairprice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
    private ProgressBar progressTime;
    private Button tryAgainButton;
    private TextView textPoints;
    private TextView livesText;
    private ImageView incorrectPhoto;



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
        timer = new CountDownTimer(10000, 100) {

            public void onTick(long millisUntilFinished) {
                Long millis = millisUntilFinished;
                Log.d(TAG, "onTick: millis: "+millis);
                progressTime.setProgress(100-(millis.intValue()/100));



            }

            public void onFinish() {
                progressTime.setProgress(100);
                lives=lives-1;
                setHearts();
                showPhotoIncorrect();
                if(lives==0){
                    setContentView(R.layout.acitivity_mode_normal_losed);
                    initializeTryAgainButton();
                }
                else{
                    timer.start();
                }

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
    private void showPhotoIncorrect(){
        final Runnable setVisibilityGoneIncorrect = new Runnable() {
            @Override
            public void run() {
                incorrectPhoto.setVisibility(View.GONE);
            }
        };
        incorrectPhoto.setVisibility(View.VISIBLE);
        handler.postDelayed(setVisibilityGoneIncorrect, 1000);
    }

    private void initializeButtonsListeners() {

        final Runnable setVisibilityCorrectGone = new Runnable() {
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
                    handler.postDelayed(setVisibilityCorrectGone, 1000);


                    handler.postDelayed(setupFirebaseRunnable, 1000);
                }else{
                    lives=lives-1;
                    setHearts();
                    showPhotoIncorrect();
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
                    handler.postDelayed(setVisibilityCorrectGone, 1000);

                    handler.postDelayed(setupFirebaseRunnable, 1000);


                }else{
                    lives=lives-1;
                    setHearts();
                    showPhotoIncorrect();
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
                    handler.postDelayed(setVisibilityCorrectGone, 1000);

                    handler.postDelayed(setupFirebaseRunnable, 1000);

                }else{
                    lives=lives-1;
                    setHearts();
                    showPhotoIncorrect();
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
                    handler.postDelayed(setVisibilityCorrectGone, 1000);

                    handler.postDelayed(setupFirebaseRunnable, 1000);

                }else{
                    lives=lives-1;
                    setHearts();
                    showPhotoIncorrect();
                    if(lives==0){
                        handler.postDelayed(setContentView, 200);
                    }
                }
            }
        });

    }

    private void setHearts() {
        livesText.setText(String.valueOf(lives));

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
        progressTime=(ProgressBar) findViewById(R.id.progress_time);
        livesText=findViewById(R.id.text_lives);
        incorrectPhoto=findViewById(R.id.image_no_correct);


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
