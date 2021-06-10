package com.example.chatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailET , passwordEt1 , passwordEt2;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        firebaseAuth=FirebaseAuth.getInstance();
        emailET=findViewById(R.id.nameBox);
        passwordEt1=findViewById(R.id.password1box);
        passwordEt2=findViewById(R.id.password2box);
        SignUpButton=findViewById(R.id.signupbutton);
        progressDialog=new ProgressDialog(this);
        SignInTv=findViewById(R.id.signintv);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Register();
            }
        });
        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,EmailActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private Boolean isValidEmail(CharSequence target){
        return(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void Register() {
        String email = emailET.getText().toString();
        String password1 = passwordEt1.getText().toString();
        String password2 = passwordEt2.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailET.setError("Email Field Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt1.setError("Password Field Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            passwordEt2.setError("Confirm Password Field Cannot be empty");
            return;
        }
        else if(!(password2.equals(password1))){
            passwordEt1.setError("Passwords do not match ");
            return;
        }
        else if(password1.length()<4){
            passwordEt1.setError("Password should be > 4");
            return;
        }
        else if(!isValidEmail(email)){
            emailET.setError("invald email");
            return;
        }
        progressDialog.setMessage("Please wait ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "SuccessFully Registered", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this,setProfile.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
        }

    });

}}
