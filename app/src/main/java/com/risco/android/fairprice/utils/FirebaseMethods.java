package com.risco.android.fairprice.utils;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    public Question getQuestion(DataSnapshot dataSnapshot, int num){
        Log.d(TAG, "getQuestion: Retrieving question from database with num: "+num);

        Question question = new Question();

        String numString = String.valueOf(num);

        for(DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().equals("questions")){
                try {
                    Log.d(TAG, "getQuestion: datasnapshot:"+ds);

                    question.setNumber(
                            ds.child(numString).getValue(Question.class).getNumber()
                    );

                    question.setRealprice(
                            ds.child(numString).getValue(Question.class).getRealprice()
                    );

                    question.setProduct(
                            ds.child(numString).getValue(Question.class).getProduct()
                    );
                    question.setUrl(
                            ds.child(numString).getValue(Question.class).getUrl()
                    );
                    question.setImage(
                            ds.child(numString).getValue(Question.class).getImage()
                    );

                }catch(NullPointerException ex){
                    Log.e(TAG, "getQuestion: NullPointerException: "+ex.getMessage());

                }
            }
        }
        return question;
    }
}
