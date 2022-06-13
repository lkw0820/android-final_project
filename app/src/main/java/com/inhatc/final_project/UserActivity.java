package com.inhatc.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    Button btnStop = null;
    String name = null;
    String email1 = null;
    String total = null;
    String recent = null;
    private TextView txtName;
    private TextView txtEmail;
    private TextView txtTotal;
    private TextView txtRecent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        btnStop = (Button) findViewById(R.id.btnStop);
        txtRecent = (TextView) findViewById(R.id.txtRecent);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        int idx = email.indexOf("@");
        String user1 = email.substring(0,idx);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(user1);


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DO data = dataSnapshot.getValue(DO.class);
                name = data.getName();
                email1 = data.getEmail();
                total = data.getTotal();
                recent = data.getDistance();
                txtName.setText(name+" 님 반갑습니다.");
                txtEmail.setText("Email : "+email1);
                txtTotal.setText("총 운동거리 : " +total+"m");
                txtRecent.setText("최근 운동거리 : "+recent+"m");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });




        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),RealMainActivity.class);
                startActivity(i);
                //finish();
            }
        });

    }
}