package com.risco.android.fairprice.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.risco.android.fairprice.models.Question;

/**
 * Created by Albert Risco on 22/01/2018.
 */

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    private Context mContext;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    public FirebaseMethods(Context context){
        mContext=context;
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
    }

    public Question getQuestion(DataSnapshot dataSnapshot){
        Log.d(TAG, "getQuestion: Retrieving question from database");

        Question question = new Question();

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals("questions")){
                try {
                    Log.d(TAG, "getQuestion: datasnapshot:"+ds);

                    question.setNumber(
                            ds.child("1").getValue(Question.class).getNumber()
                    );

                    question.setReal_price(
                            ds.child("1").getValue(Question.class).getReal_price()
                    );

                    question.setProduct(
                            ds.child("1").getValue(Question.class).getProduct()
                    );
                    question.setUrl(
                            ds.child("1").getValue(Question.class).getUrl()
                    );

                }catch(NullPointerException ex){
                    Log.d(TAG, "getQuestion: NullPointerException: "+ex.getMessage());
                }
            }
        }
        return question;
    }
}