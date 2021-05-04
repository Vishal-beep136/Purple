package kaitka.vishal.meeta.purple_ecommerce.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import kaitka.vishal.meeta.purple_ecommerce.Fragments.SignInFragment;
import kaitka.vishal.meeta.purple_ecommerce.Fragments.SignUpFragment;
import kaitka.vishal.meeta.purple_ecommerce.R;

public class RegisterActivity extends AppCompatActivity {


    private FrameLayout frameLayout;
    public static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        frameLayout = findViewById(R.id.registerFramelayout);
        if (setSignUpFragment){
            setSignUpFragment = false;
            setDefaultFragment(new SignUpFragment());
        }else {
            setDefaultFragment(new SignInFragment());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (onResetPasswordFragment){
                onResetPasswordFragment = false;
                setFragment(new SignInFragment());
                return false;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}