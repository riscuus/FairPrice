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

/**
 * Created by Albert Risco on 27/01/2018.
 */

public class HardModeActivity extends AppCompatActivity {

    //Layout things
    private Button oneButton;
    private Button twoButton;
    private Button threeButton;
    private Button fourButton;
    private Button fiveButton;
    private Button sixButton;
    private Button sevenButton;
    private Button eightButton;
    private Button nineButton;
    private Button zeroButton;
    private Button backButton;
    private Button okButton;
    private TextView productName;
    private ProgressBar photoProgress;
    private ImageView productPhoto;
    private ImageView correctPhoto;
    private ProgressBar progressTime;
    private TextView livesText;
    private ImageView incorrectPhoto;
    private Button tryAgainButton;
    private TextView textPoints;
    private TextView editText;
    private ImageView greatPhoto;


    public static final String TAG = "HardModeActivity";
    private Context mContext = HardModeActivity.this;

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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_hard);

        mFirebaseMethods=new FirebaseMethods(mContext);
        lives=4;

        initializeWidgets();
        setupFirebase();
        setHearts();
        initializeButtonsListeners();

    }
    private void initializeWidgets(){
        oneButton=findViewById(R.id.one);
        twoButton=findViewById(R.id.two);
        threeButton=findViewById(R.id.three);
        fourButton=findViewById(R.id.four);
        fiveButton=findViewById(R.id.five);
        sixButton=findViewById(R.id.six);
        sevenButton=findViewById(R.id.seven);
        eightButton=findViewById(R.id.eight);
        nineButton=findViewById(R.id.nine);
        zeroButton=findViewById(R.id.zero);
        backButton=findViewById(R.id.back);
        okButton=findViewById(R.id.ok);
        productName=(TextView)findViewById(R.id.text_product_name);
        photoProgress=(ProgressBar)findViewById(R.id.progress_photo);
        productPhoto=(ImageView)findViewById(R.id.image_product);
        correctPhoto=(ImageView)findViewById(R.id.image_correct);
        livesText=findViewById(R.id.text_lives);
        incorrectPhoto=findViewById(R.id.image_no_correct);
        editText=findViewById(R.id.price_text);
        greatPhoto=findViewById(R.id.great_image);

    }

    private void setWidgets(Question question) {
        Log.d(TAG, "setWidgets: question: "+question.toString());
        try{
            productRealPrice=question.getReal_price().intValue();
            productName.setText(question.getProduct());
            setImage(question);
        }catch(Exception ex){
            Log.e(TAG, "setWidgets: exception "+ex.getMessage());

        }

    }
    private void setImage(Question question){
        UniversalImageLoader.setImage(question.getUrl(), productPhoto,photoProgress, "");
    }
    private void setHearts() {
        livesText.setText(String.valueOf(lives));

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

    private void showPhotoCorrect(){
        final Runnable setVisibilityGoneCorrect = new Runnable() {
            @Override
            public void run() {
                correctPhoto.setVisibility(View.GONE);
            }
        };
        correctPhoto.setVisibility(View.VISIBLE);
        handler.postDelayed(setVisibilityGoneCorrect, 1000);
    }

    private void showPhotoExactPrice(){
        final Runnable setVisibilityGoneGreat = new Runnable() {
            @Override
            public void run() {
                greatPhoto.setVisibility(View.GONE);
            }
        };
        greatPhoto.setVisibility(View.VISIBLE);
        handler.postDelayed(setVisibilityGoneGreat, 1000);
    }

    private void callNextProduct(){
        final Runnable setupFirebaseRunnable = new Runnable() {
            @Override
            public void run() {
                setupFirebase();
            }
        };
        handler.postDelayed(setupFirebaseRunnable, 1000);
    }

    private void initializeButtonsListeners(){
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"1");
                }
                else editText.setText("1");
            }
        });

        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"2");
                }
                else editText.setText("2");
            }
        });

        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"3");
                }
                else editText.setText("3");
            }
        });

        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"4");
                }
                else editText.setText("4");
            }
        });

        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"5");
                }
                else editText.setText("5");
            }
        });

        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"6");
                }
                else editText.setText("6");
            }
        });

        sevenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"7");
                }
                else editText.setText("7");
            }
        });

        eightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"8");
                }
                else editText.setText("8");
            }
        });

        nineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"9");
                }
                else editText.setText("9");
            }
        });

        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText()!=null){
                    editText.setText(editText.getText()+"0");
                }
                else editText.setText("0");
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String text = editText.getText().toString();
                    editText.setText(text.subSequence(0, text.length()-1));

                }catch(Exception ex){
                    editText.setText("");
                    Log.e(TAG, "onClick: Exception:" + ex.getMessage());
                }

            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                checkPrice();
                editText.setText("");
            }
        });
    }

    private void checkPrice(){
        try{
            String text = editText.getText().toString();
            int price = Integer.valueOf(text);
            int limitUp = productRealPrice +productRealPrice*25/100;
            int limitDown = productRealPrice -productRealPrice*25/100;

            if(price==productRealPrice){
                num=num+1;

                showPhotoExactPrice();

                callNextProduct();

            }

            else if(price<limitUp && price>limitDown && price!=0){
                num=num+1;

                showPhotoCorrect();

                callNextProduct();
            }
            else{
                lives=lives-1;
                setHearts();
                showPhotoIncorrect();
                timer.start();
                if(lives==0){
                    timer.cancel();

                    final Runnable setContentView = new Runnable() {
                        @Override
                        public void run() {
                            setContentView(R.layout.acitivity_mode_normal_losed);
                            initializeTryAgainButton();
                        }
                    };
                    handler.postDelayed(setContentView, 200);
                }
            }
        }catch (Exception ex){
            Log.e(TAG, "checkPrice: exception "+ex.getMessage());
        }
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
    /*
    ------------------------------------------------------------------------------------------------
     */
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


}
