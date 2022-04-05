package pmo.sample.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import pmo.sample.app.Pengguna.Pengguna;

public class Register extends Activity {
    //Deklarasi variabel
    private EditText et_email, et_password;
    private Button btnlogin, btnregister;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private String getEmail,getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        btnregister.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Perintah tombol register
                progressBar.setVisibility(View.VISIBLE);
                cekDataUser();

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Register.this, Login_Utama.class);
                startActivity(intent);*/
                onBackPressed();

            }
        });

    }

    private void cekDataUser() {
        //mendapatkan data inputan user
        getEmail = et_email.getText().toString();
        getPassword = et_password.getText().toString();

        //Untuk mengecek apakah edittext email dan password kosong
        if(TextUtils.isEmpty(getEmail)|| TextUtils.isEmpty(getPassword)){
            Toast.makeText(this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }else {
            //mengecek panjang karakter password
            if (getPassword.length()<6){
                Toast.makeText(this, "Password Terlalu Pendek, minimal 6 karakter", Toast.LENGTH_SHORT).show();
            }else{
                createUserAccount();
            }
        }

    }

    private void createUserAccount() {
        auth.createUserWithEmailAndPassword(getEmail,getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        Pengguna user = new Pengguna(getEmail,getPassword);
                        FirebaseDatabase.getInstance().getReference("Pengguna")
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                //Cek status Keberhasilan saat mendaftarkan email dan sandi baru
                                if (task.isSuccessful()){
                                    progressBar.setVisibility(View.GONE);
                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(Register.this, "Registrasi Berhasil !!, Please Check your email for verification", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Register.this,Login_Utama.class));
                                                finish();
                                            }else {
                                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                    } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}