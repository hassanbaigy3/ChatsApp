package com.example.chatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EmailActivity extends AppCompatActivity {
    private EditText emailET , passwordEt;
    private Button SignInButton;
    private TextView SignUpTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        firebaseAuth=FirebaseAuth.getInstance();
        emailET=findViewById(R.id.nameBox);
        passwordEt=findViewById(R.id.password1box);
        SignInButton=findViewById(R.id.profileDoneBtn);
        progressDialog=new ProgressDialog(this);
        SignUpTv=findViewById(R.id.signuplink);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        SignUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EmailActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
    });
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            Intent intent2 = new Intent(EmailActivity.this,MainActivity.class);
            startActivity(intent2);
            finish();
        }
}
    private Boolean isValidEmail(CharSequence target){
        return(!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void Login(){
        String email = emailET.getText().toString();
        String password1 = passwordEt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailET.setError("Email Field Cannot be empty");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt.setError("Password Field Cannot be empty");
            return;
        }
        else if(password1.length()<4){
            passwordEt.setError("Password should be > 4");
            return;
        }
        else if(!isValidEmail(email)){
            emailET.setError("invald email");
            return;
        }

        progressDialog.setMessage("Please wait ... ");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.signInWithEmailAndPassword(email,password1).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EmailActivity.this, "SuccessFully Registered", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(EmailActivity.this,DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(EmailActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                });

    }
}