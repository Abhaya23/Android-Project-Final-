package com.example.travelnepalapp.Feedback;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelnepalapp.BusinessLogic.Feedbackquery;
import com.example.travelnepalapp.Activities.Notification;
import com.example.travelnepalapp.R;
import com.example.travelnepalapp.Activities.StrictMode;

public class Feedback extends AppCompatActivity implements View.OnClickListener {


    EditText fname, lname, email, message;
    Button btnfeedback;
    Toolbar toolbars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        fname = findViewById(R.id.et_feedback_fname);
        lname = findViewById(R.id.et_feedback_lname);
        email = findViewById(R.id.et_feedback_email);
        message = findViewById(R.id.et_feedback_feecback);
        toolbars = findViewById(R.id.toolbarss);

        setSupportActionBar(toolbars);
        getSupportActionBar().setTitle("TravelNepal App");

        btnfeedback = findViewById(R.id.btn_feedback_add);

        btnfeedback.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_feedback_add:
                addfeedback();
                break;
        }
    }

    private void addfeedback() {
        String ffname = fname.getText().toString();
        String llname = lname.getText().toString();
        String emails = email.getText().toString();
        String messages = message.getText().toString();
        Feedbackquery feedbackquery= new Feedbackquery();
        StrictMode.StrictMode();
        if(feedbackquery.addfeedback(ffname,llname,emails,messages)==false){
            Toast.makeText(Feedback.this, "Error", Toast.LENGTH_SHORT).show();
            Vibrator vibe = (Vibrator) Feedback.this.getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(100);
        }

        else {
            Toast.makeText(Feedback.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                Notification.givenotification(Feedback.this,"Thank you for you Feedback");
                resetAllField();
        }



    }

    //    private void addfeedback() {
//        String ffname = fname.getText().toString();
//        String llname = lname.getText().toString();
//        String emails = email.getText().toString();
//        String messages = message.getText().toString();
//
//
//        FeedbackAPI feedbackAPI = RetrofitHelper.instance().create(FeedbackAPI.class);
//        Call<String> feedbackcall = feedbackAPI.addfeedback(ffname, llname, emails, messages);
//        feedbackcall.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(Feedback.this, "Successfully Added", Toast.LENGTH_SHORT).show();
//                Notification.givenotification(Feedback.this,"You have send your feedback");
//                resetAllField();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(Feedback.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
    private void resetAllField() {
        fname.setText("");
        lname.setText("");
        email.setText("");
        message.setText("");
    }


}
