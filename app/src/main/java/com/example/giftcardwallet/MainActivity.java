package com.example.giftcardwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricConstants;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.app.KeyguardManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //executor = Executors.newSingleThreadExecutor();
        //FragmentActivity fragmentActivity = this;
        executor = ContextCompat.getMainExecutor(this);


        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                System.out.println(errorCode + (String) errString);

                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_CANCELED) {

                } else {
                    System.out.println("an irrecoverable error has occurred");
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(new Intent(MainActivity.this, GiftCardScreen.class));
                System.out.println("Authentication Successful");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                System.out.println("recognized illegitimate biometric signature");
            }


        } );

         promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Security measure")
                .setSubtitle("Biometric")
                .setDeviceCredentialAllowed(true)
                .build();
        findViewById(R.id.preAuthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
}
