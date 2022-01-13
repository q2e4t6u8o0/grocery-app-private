package com.apps.onlinegroceriesapps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.apps.onlinegroceriesapps.activity.screen.LoginPhoneActivity;
import com.apps.onlinegroceriesapps.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("LoginActivity", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        System.out.println("vfhbcxjbvjbn "+token);
////                        Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
//                    }
//                });

        clickMethod();
    }

    private void clickMethod() {
        binding.phone.setOnClickListener(view -> {

            startActivity(new Intent(this, LoginPhoneActivity.class));
        });
    }
}