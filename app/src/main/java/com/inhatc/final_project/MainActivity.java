package com.inhatc.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSignin;
    private Button btnSignUp;
    private Button btnFindID;
    private Button btnFindPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignin= (Button) findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(this);
        btnSignUp=(Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        btnFindID = (Button) findViewById(R.id.btnFindID);
        btnFindID.setOnClickListener(this);
        btnFindPW = (Button) findViewById(R.id.btnFindPW);
        btnFindPW.setOnClickListener(this);
    }
    public void onClick(View v){
        if(v== btnSignin){
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginIntent);
        }
        if(v== btnSignUp){
            Intent SignupIntent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(SignupIntent);
        }
        if(v== btnFindID){
            Intent FindIDIntent = new Intent(getApplicationContext(),FindIDActivity.class);
            startActivity(FindIDIntent);
        }
        if(v== btnFindPW){
            Intent FindPWIntent = new Intent(getApplicationContext(),FindPWActivity.class);
            startActivity(FindPWIntent);
        }
    }

}