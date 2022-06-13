package com.inhatc.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class FindIDActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtNickname;
    EditText edtPhone2;
    Button btnFindID2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findid);

        edtNickname = (EditText) findViewById(R.id.edtNickname);
        edtPhone2 = (EditText) findViewById(R.id.edtPhone2);
        btnFindID2 = (Button) findViewById(R.id.btnFindID2);
        btnFindID2.setOnClickListener(this);
    }


    public void onClick(View v) {
        if(v == btnFindID2){
            DatabaseReference reference = database.getReference(edtNickname.getText().toString());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.child("name").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();

                    if(name.equals(edtNickname.getText().toString())&&edtPhone2.getText().toString().equals(phone)){
                        String email = snapshot.child("email").getValue().toString();
                        Toast.makeText(getApplicationContext(),"아이디 : "+email, Toast.LENGTH_SHORT).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },3000);

                    }
                    else{
                       Toast.makeText(getApplicationContext(),"회원정보가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),"회원정보가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


}

