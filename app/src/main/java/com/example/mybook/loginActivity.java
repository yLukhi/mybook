package com.example.mybook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class loginActivity extends AppCompatActivity {

    public EditText emailId, password;
    Button btnSignIn;
    TextView tvsignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hello
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText3);
        btnSignIn = findViewById(R.id.button);
        tvsignUp = findViewById(R.id.textView);

        mAuthStateListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null)
                {
                    Toast.makeText(loginActivity.this,"You are Logged In",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(loginActivity.this,homeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(loginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
                
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty())
                {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty())
                {
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty())
                {

                    Toast.makeText( loginActivity.this,"Fields are empty!!",Toast.LENGTH_SHORT).show();

                }
                else if (!(email.isEmpty() && pwd.isEmpty()))
                {
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(loginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Intent intoHome= new Intent(loginActivity.this,homeActivity.class);
                                startActivity(intoHome);
                            }
                        }
                    });


                }
                else
                {
                    Toast.makeText( loginActivity.this,"Error occured!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(loginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListner);
    }
}
