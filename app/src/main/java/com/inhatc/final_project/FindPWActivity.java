package com.inhatc.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FindPWActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtEmail2;
    EditText edtNickname2;
    EditText edtPhone3;
    Button btnFindPW2;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

        edtEmail2 = (EditText) findViewById(R.id.edtEmail2);
        edtNickname2 = (EditText) findViewById(R.id.edtNickname2);
        edtPhone3 = (EditText) findViewById(R.id.edtPhone3);
        btnFindPW2 = (Button) findViewById(R.id.btnFindPW2);
        btnFindPW2.setOnClickListener(this);
    }

    public void onClick(View v){
       if(v == btnFindPW2){
           DatabaseReference reference = database.getReference(edtNickname2.getText().toString());
           reference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   String name = snapshot.child("name").getValue().toString();
                   String phone = snapshot.child("phone").getValue().toString();
                   String email = snapshot.child("email").getValue().toString();
                   if(name.equals(edtNickname2.getText().toString())&&edtPhone3.getText().toString().equals(phone)&&edtEmail2.getText().toString().equals(email)){
                       firebaseAuth.sendPasswordResetEmail(edtEmail2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"비밀번호 재설정 이메일을 전송했습니다.",Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"회원정보가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                                }
                           }
                       });

                   }
               }
               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(),"회원정보가 일치하지 않습니다.",Toast.LENGTH_LONG);
               }
           });
       }
    }
}
