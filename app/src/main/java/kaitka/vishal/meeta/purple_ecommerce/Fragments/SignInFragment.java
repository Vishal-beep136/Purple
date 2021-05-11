package kaitka.vishal.meeta.purple_ecommerce.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import kaitka.vishal.meeta.purple_ecommerce.Activites.MainActivity;
import kaitka.vishal.meeta.purple_ecommerce.R;

import static kaitka.vishal.meeta.purple_ecommerce.Activites.RegisterActivity.onResetPasswordFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {




    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText password;
    private ImageButton closeBtn;
    private Button signInBtn;
    private ProgressDialog dialog;
    private TextView forgotPassword;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public static boolean disableCloseBtn = false;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_sign_in, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        dontHaveAnAccount = view.findViewById(R.id.signInNotHaveAccountTv);
        parentFrameLayout = getActivity().findViewById(R.id.registerFramelayout);

        email = view.findViewById(R.id.signInEmailBox);
        password = view.findViewById(R.id.signInPassBox);
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Checking...");
        dialog.setCancelable(false);

        forgotPassword = view.findViewById(R.id.signInForgetPassTv);


        closeBtn = view.findViewById(R.id.signInCloseBtn);
        signInBtn = view.findViewById(R.id.signInBtn);

        if (disableCloseBtn){
            closeBtn.setVisibility(View.GONE);
        }
        else {
            closeBtn.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(v -> setFragment(new SignUpFragment()));

        forgotPassword.setOnClickListener(v -> {
            onResetPasswordFragment = true;
            setFragment(new ResetPasswordFragment());
        });

        closeBtn.setOnClickListener(v -> startActivity(new Intent(getActivity(), MainActivity.class)));


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signInBtn.setOnClickListener(v -> {
            dialog.show();
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        dialog.dismiss();
                        if (task.isSuccessful()){
                            //it can be in a method.
                            if (disableCloseBtn){
                                disableCloseBtn = false;
                            }
                            else {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                            getActivity().finish();

                            //it can be in a method.
                        }
                        else {
                            Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(password.getText())){
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(getResources().getColor(R.color.black));

            }
            else {
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(getResources().getColor(R.color.light_black));
            }

        }
        else {
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(getResources().getColor(R.color.light_black));
        }
    }


}