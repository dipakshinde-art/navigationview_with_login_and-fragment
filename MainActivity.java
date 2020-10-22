package com.example.intance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class MainActivity extends AppCompatActivity {

    EditText Username, Password;
    Button login;
    TextView register;
    ProgressBar progressBar;

    private static final String TAG = "MainActivityLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.loginusername);
        Password = findViewById(R.id.loginpassword);
        login = findViewById(R.id.LoginButton);
        register = findViewById(R.id.lnkRegister);
        progressBar = findViewById(R.id.progress_circular);

        progressBar.setVisibility(View.INVISIBLE);
        //check user is present or not
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password;

                username = String.valueOf(Username.getText());
                password = String.valueOf(Password.getText());

                if (!username.equals(" ") && !password.equals(" ")) {

                    progressBar.setVisibility(View.INVISIBLE);
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //Creating array for data
                            String[] data = new String[2];

                            data[0] = username;
                            data[1] = password;
                            //url address datastore // 192.168.0.108shinde
                            PutData putData = new PutData("http://192.168.0.102:8080/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();

                                    //End ProgressBar (Set visibility to GONE)
                                   // progressBar.setVisibility(View.INVISIBLE);
                                   // Log.i("PutData", result);
                                    Log.e(TAG,"PutData" );
                                    if (result.equals("Login Success")) {
                                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });


                } else {
                    Log.e(TAG,"HERE");
                    Toast.makeText(getApplicationContext(), "please enter correct information ", Toast.LENGTH_LONG).show();
                }

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
