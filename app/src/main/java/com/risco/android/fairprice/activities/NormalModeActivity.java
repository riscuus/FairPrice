package com.risco.android.fairprice.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.util.Random;

/**
 * Created by Albert Risco on 21/01/2018.
 */

public class NormalModeActivity extends AppCompatActivity {
    private static final String TAG = "NormalModeActivity";

    private Context mContext = NormalModeActivity.this;

    //Layout things
    private Button redButton;
    private Button blueButton;
    private Button purpleButton;
    private Button greenButton;
    private TextView productName;
    private ProgressBar photoProgress;
    private ImageView productPhoto;


    //Firebase things
    private FirebaseDatabase mFireBaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_normal);

        mFirebaseMethods=new FirebaseMethods(mContext);

        initializeWidgets();
        setupFirebase();

    }

    private void initializeWidgets(){
        redButton=(Button)findViewById(R.id.button_red);
        blueButton=(Button)findViewById(R.id.button_blue);
        greenButton=(Button)findViewById(R.id.button_green);
        purpleButton=(Button)findViewById(R.id.button_purple);
        productName=(TextView)findViewById(R.id.text_product_name);
        photoProgress=(ProgressBar)findViewById(R.id.progress_photo);
        productPhoto=(ImageView)findViewById(R.id.image_product);
    }

    private void setWidgets(Question question){
        Log.d(TAG, "setWidgets: question: "+question.toString());
        setImage(question);
        ArrayList<Integer> randomPrices = setRandomPrices(question.getReal_price());
        redButton.setText(String.valueOf(question.getReal_price()));
        blueButton.setText(String.valueOf(randomPrices.get(0)));
        greenButton.setText(String.valueOf(randomPrices.get(1)));
        purpleButton.setText(String.valueOf(randomPrices.get(2)));

        productName.setText(question.getProduct());

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
                setWidgets(mFirebaseMethods.getQuestion(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
