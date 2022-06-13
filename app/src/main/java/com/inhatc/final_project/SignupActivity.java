package com.inhatc.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks {
    private Button btnSignUp;
    private EditText edtName;
    private EditText edtSignPW;
    private EditText edtPWCheck;
    private EditText edtEmail;
    private EditText edtPhone;
    private CheckBox chkRobot;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    GoogleApiClient googleApiClient;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    String siteKey = "6LdorGMgAAAAAMsEleNslvAzzg2_CsdTEBAi5gp4";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnSignUp = (Button) findViewById(R.id.btnSignUp2);
        btnSignUp.setOnClickListener(this);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSignPW = (EditText) findViewById(R.id.edtSignPW);
        edtPWCheck = (EditText) findViewById(R.id.edtPWCheck);
        edtPhone= (EditText) findViewById(R.id.edtPhone);
        chkRobot = (CheckBox) findViewById(R.id.chkRobot);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(SignupActivity.this)
                .build();
        googleApiClient.connect();

        chkRobot.setOnClickListener(this);
    }


    public void onClick(View v) {
        if(v == chkRobot){
            if(chkRobot.isChecked()){
                SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,siteKey).setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                    @Override
                    public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {
                        Status status  = recaptchaTokenResult.getStatus();
                        if((status != null) && status.isSuccess()){
                            Toast.makeText(SignupActivity.this, "인증 성공!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        if (v == btnSignUp) {
            if (edtName.getText().toString().equals("")  || edtSignPW.getText().toString().equals("") || edtPWCheck.getText().toString().equals("") || edtEmail.getText().toString().equals("")) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("회원가입 오류");
                alert.setMessage("모든 항목을 입력해주세요.");
                alert.setPositiveButton("확인",null);
                alert.show();
            }
            else if(!edtEmail.getText().toString().contains("@")){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("이메일 오류");
                alert.setMessage("이메일 형식이 잘못되었습니다.");
                alert.setPositiveButton("확인",null);
                alert.show();
            }
            else if(!(edtSignPW.getText().toString().equals(edtPWCheck.getText().toString()))){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("비밀번호 오류");
                alert.setMessage("비밀번호가 일치하지 않습니다!");
                alert.setPositiveButton("확인",null);
                alert.show();
            }
            else if(edtSignPW.getText().toString().length() < 7){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("비밀번호 오류");
                alert.setMessage("비밀번호를 7자리 이상으로 설정하세요!");
                alert.setPositiveButton("확인",null);
                alert.show();
            }
            else if(chkRobot.isChecked() == false){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("캡챠인증 오류");
                alert.setMessage("캡챠인증을 완료해주세요!");
                alert.setPositiveButton("확인",null);
                alert.show();
            }
            else {
                    firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtSignPW.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            String name = edtName.getText().toString();
                            String email = user.getEmail();
                            int idx = email.indexOf("@");
                            String user1 = email.substring(0,idx);
                            String pw = user.getUid();
                            String phone = edtPhone.getText().toString();
                            database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference(user1);
                            reference.child("name").setValue(name);
                            reference.child("email").setValue(email);
                            reference.child("pw").setValue(pw);
                            reference.child("phone").setValue(phone);
                            reference.child("latitude").setValue("0.0");
                            reference.child("longitude").setValue("0.0");
                            reference.child("distance").setValue("0.0");
                            reference.child("total").setValue("0.0");


                            Toast.makeText(SignupActivity.this,"회원가입 성공!",Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },2000);

                        }
                        else{
                            Toast.makeText(SignupActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },2000);
                        }
                    }
                });


            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) { }
    @Override
    public void onConnectionSuspended(int i) { }
}
