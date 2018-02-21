package com.risco.android.fairprice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
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

/**
 * Created by Albert Risco on 02/02/2018.
 */

public class ChallengeActivity extends AppCompatActivity {

    private static final String TAG = "ChallengeActivity";

    private Context mContext = ChallengeActivity.this;

    //layout things
    private TextView productNameUp;
    private TextView productNameDown;
    private ImageView imageProductUp;
    private ImageView imageProductDown;
    private ImageView correctPhotoUp;
    private ImageView correctPhotoDown;
    private ProgressBar progressTime;
    private ProgressBar progressUp;
    private ProgressBar progressDown;
    private Button tryAgainButton;
    private TextView textPoints;
    private Button moreButton;
    private Button lessButton;
    private TextView realPriceUpTextView;

    //Firebase things
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;

    //product number
    private int num=1;

    //Handler
    private final Handler handler = new Handler();

    //timer
    private CountDownTimer timer;

    //productsPrices
    private int realPriceUp;
    private int realPriceDown;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        mFirebaseMethods=new FirebaseMethods(mContext);

        initializeWidgets();
        setupFirebase();
        initializeButtonsListeners();
    }

    private void setWidgetsFirstTime(Question question){
        try {
            if(num==1){
                productNameDown.setText(question.getProduct());
                realPriceDown=question.getRealprice().intValue();
                setImage(question.getImage(),imageProductDown,progressDown);
            }
            else{
                productNameUp.setText(question.getProduct());
                realPriceUp=question.getRealprice().intValue();
                realPriceUpTextView.setText(String.valueOf(realPriceUp)+"€");
                setImage(question.getImage(),imageProductUp,progressUp);
            }
        }catch(Exception ex){
            Log.e(TAG, "setWidgetsFirstTime:"+ex.getMessage());
        }


    }
    private void setWidgets(Question question){
        try {
            productNameDown.setText(question.getProduct());
            realPriceDown=question.getRealprice().intValue();
            setImage(question.getImage(),imageProductDown,progressDown);
        }catch (Exception ex){
            Log.e(TAG, "setWidgets: "+ex.getMessage());
        }

   }
    private void setImage(String url,ImageView imageProduct,ProgressBar progressBar){
        UniversalImageLoader.setImage(url, imageProduct,progressBar, "");
    }
    private void showPhotoCorrectDown(){
        final Runnable setVisibilityGoneCorrect = new Runnable() {
            @Override
            public void run() {
                correctPhotoDown.setVisibility(View.GONE);
            }
        };
        correctPhotoDown.setVisibility(View.VISIBLE);
        handler.postDelayed(setVisibilityGoneCorrect, 1000);
    }


    private void setProductUp(){
        productNameUp.setText(productNameDown.getText());
        imageProductUp.setImageDrawable(imageProductDown.getDrawable());
        realPriceUp=realPriceDown;
        realPriceUpTextView.setText(String.valueOf(realPriceUp)+"€");
    }

    private void callNextProductDown(){
        final Runnable setupFirebaseRunnable = new Runnable() {
            @Override
            public void run() {
                setupFirebase();
            }
        };
        handler.postDelayed(setupFirebaseRunnable, 1000);
    }
    private void initializeWidgets() {
        imageProductDown = findViewById(R.id.image_product_down);
        imageProductUp = findViewById(R.id.image_product_up);
        correctPhotoDown = findViewById(R.id.image_correct_up);
        correctPhotoUp = findViewById(R.id.image_correct_down);
        progressTime= findViewById(R.id.progress_time);
        productNameUp=findViewById(R.id.productNameUp);
        productNameDown=findViewById(R.id.productNameDown);
        moreButton=findViewById(R.id.button_more);
        lessButton=findViewById(R.id.button_less);
        realPriceUpTextView=findViewById(R.id.real_price_up);

    }

    private void initializeButtonsListeners(){
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(realPriceDown>realPriceUp || realPriceUp==realPriceDown){
                    answerCorrect();

                }
                else{
                    answerIncorrect();
                }
            }
        });
        lessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(realPriceDown<realPriceUp || realPriceUp==realPriceDown){
                    answerCorrect();

                }
                else{
                    answerIncorrect();
                }
            }
        });

    }
    private void answerCorrect(){
        timer.cancel();

        num=num+1;

        //showPhotoCorrectDown();

        setProductUp();

        callNextProductDown();

    }

    private void answerIncorrect(){
        timer.cancel();

        showLayoutGameOver();
    }
    private void showLayoutGameOver(){
        final Runnable setContentView = new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.acitivity_mode_normal_losed);
                initializeTryAgainButton();
            }
        };
        handler.postDelayed(setContentView, 200);
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
                if(num==1){
                    setWidgetsFirstTime(mFirebaseMethods.getQuestion(dataSnapshot, num));
                    num=num+1;
                    setWidgetsFirstTime(mFirebaseMethods.getQuestion(dataSnapshot, num));
                    setupCountDown();
                }
                else{
                    setWidgets(mFirebaseMethods.getQuestion(dataSnapshot, num));
                    setupCountDown();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
}
