package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kaitka.vishal.meeta.purple_ecommerce.R;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private long SPLASH_TIME = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        SystemClock.sleep(SPLASH_TIME);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null){
            Intent registerIntent =  new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
            finish();
        }
        else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }

    }
}