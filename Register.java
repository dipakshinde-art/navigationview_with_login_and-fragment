package com.example.intance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Register extends AppCompatActivity {
    EditText Email, Pass, Username, Fullname;
    Button registeruser;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //hide bar getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Email = findViewById(R.id.registermail);
        Pass = findViewById(R.id.registeropass);
        Username = findViewById(R.id.registusername);
        Fullname = findViewById(R.id.registername);
        progressBar = findViewById(R.id.progress_circular);

        registeruser = findViewById(R.id.RegisterButton);
        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullname, username, password, email;
                fullname = Fullname.getText().toString();
                username = Username.getText().toString();
                password = Pass.getText().toString();
                email = Email.getText().toString();

                if (!fullname.equals(" ") && !username.equals(" ") && !password.equals(" ") && !email.equals(" ")) {
                    progressBar.setVisibility(View.VISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putData = new PutData("https://192.168.0.103:8080/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Log.i("PutData", result);
                                    if (result.equals("Sign Up Success")) {
                                        Intent intent = new Intent(Register.this, MainActivity2.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(Register.this, "please enter information ", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
