package com.example.pradhyumna.ratemysinging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Loginpage extends AppCompatActivity {
    private EditText inputEmail, inputPassword , inputName;
    private Button btnSignIn, btnSignUp;
    private FirebaseAuth mAuth;
    TextView tvuser;
    SharedPreferences preferences;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference("USER");
    Boolean signin=true;
    private ArrayList<User> users=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        mAuth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.userET);
        inputPassword = findViewById(R.id.passET);
        btnSignIn = findViewById(R.id.login);
        btnSignUp = findViewById(R.id.signup);
        inputName = findViewById(R.id.nameET);
        inputName.setVisibility(View.GONE);
        tvuser=findViewById(R.id.textView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            updateUI(user);
    }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonToggle();
            }
        });

    }
    public void buttonClick()
    {
        if(signin)
        {
            signIn();
        }
        else
        {
            createAccount();
        }
    }

    private void updateUI(FirebaseUser user) {
    if(user!=null){
        String email = user.getEmail();
        String uid = user.getUid();
        Intent intent = new Intent(Loginpage.this , MainActivity.class);
        startActivity(intent);
        finish();
    }
    else{
        Toast.makeText(Loginpage.this , "No login Avaialable" , Toast.LENGTH_SHORT).show();
    }
    }
    private void buttonToggle() {
        if(signin)
        {
            signin=false;
            inputName.setVisibility(View.VISIBLE);
            btnSignUp.setText("Login Here");
            btnSignIn.setText("Sign Up");
            tvuser.setText("Current User");
        }
        else
        {
            signin=true;
            inputName.setVisibility(View.GONE);
            btnSignUp.setText("Signup Here");
            btnSignIn.setText("LOGIN");
            tvuser.setText("NEW USER:");
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    User user = data.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void createAccount() {

        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();
        final String name=inputName.getText().toString();

        if (email.isEmpty() || pass.isEmpty()||name.isEmpty()) {

            Toast.makeText(Loginpage.this, "Some of the fields are empty", Toast.LENGTH_SHORT).show();

        }
        else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Log.d("success", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                User userC = new User(0 , name);
                                ref.child(user.getUid()).setValue(userC);
                                updateUI(user);
                            } else {

                                Log.w("failed", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Loginpage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                        }
                    });
        }

    }

    public void signIn() {

        String email = inputEmail.getText().toString();
        String pass = inputPassword.getText().toString();
        if (email.isEmpty() || pass.isEmpty()) {

            Toast.makeText(Loginpage.this, "Some of the fields are empty", Toast.LENGTH_SHORT).show();

        }
        else {
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Success", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Failed", "signInWithEmail:failure", task.getException());
                                Toast.makeText(Loginpage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }

    }
}
