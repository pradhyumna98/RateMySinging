package com.example.pradhyumna.ratemysinging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddComment extends AppCompatActivity {
EditText etcomment;
Button submitpost;
FirebaseDatabase database = FirebaseDatabase.getInstance();
DatabaseReference cref = database.getReference("COMMENT");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        etcomment = findViewById(R.id.edittext);
        submitpost = findViewById(R.id.button1);

        submitpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etcomment.getText().equals("")){
                    Calendar calendar = Calendar.getInstance();
                    Date c = calendar.getTime();
                    SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
                    String formatdate = sf.format(c);
                    Comment comment = new Comment(etcomment.getText().toString() ,
                            FirebaseAuth.getInstance().getCurrentUser().getUid() , formatdate);
                    cref.push().getRef().setValue(comment);
                    finish();
                }
                else{
                    Toast.makeText(AddComment.this , "Enter Comment" , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
