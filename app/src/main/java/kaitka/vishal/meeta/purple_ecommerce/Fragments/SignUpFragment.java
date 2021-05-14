package kaitka.vishal.meeta.purple_ecommerce.Fragments;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaitka.vishal.meeta.purple_ecommerce.Activites.MainActivity;
import kaitka.vishal.meeta.purple_ecommerce.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;

    private ImageButton closeBtn;
    private Button signUpBtn;
    private ProgressDialog dialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    public static boolean disableCloseBtnSignUp = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        alreadyHaveAnAccount = view.findViewById(R.id.signUpNotHaveAccountTv);

        parentFrameLayout = getActivity().findViewById(R.id.registerFramelayout);
        email = view.findViewById(R.id.signUpEmailBox);
        fullName = view.findViewById(R.id.signUpFullName);
        password = view.findViewById(R.id.SignUpPassword);
        confirmPassword = view.findViewById(R.id.signUpConfirmPassword);

        closeBtn = view.findViewById(R.id.signUpCloseBtn);
        signUpBtn = view.findViewById(R.id.signUpBtn);

        dialog = new ProgressDialog(getContext());
        dialog.setMessage("creating account please wait..");
        dialog.setCancelable(false);

        if (disableCloseBtnSignUp) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }


        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(v -> setFragment(new SignInFragment()));

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

        //full name textChangedListener
        fullName.addTextChangedListener(new TextWatcher() {
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

        //password textChangedListener;
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

        //confirm password textChangedListener
        confirmPassword.addTextChangedListener(new TextWatcher() {
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

        //SignUpBtn Click Listener
        signUpBtn.setOnClickListener(v -> checkEmailAndPassword());
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_out_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    @SuppressLint("ResourceAsColor")
    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(fullName.getText())) {
                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 6) {
                    if (!TextUtils.isEmpty(confirmPassword.getText())) {
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(R.color.white);
                    } else {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(R.color.light_black);
                    }
                }
            } else {
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(R.color.light_black);
            }
        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(R.color.light_black);

        }
    }//check inputs method

    private void checkEmailAndPassword() {
        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                dialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            dialog.dismiss();
                            if (task.isSuccessful()) {

                                Map<String,Object> userData = new HashMap<>();
                                userData.put("fullname", fullName.getText().toString());

                                firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                        .set(userData)
                                        .addOnCompleteListener(task12 -> {
                                            if (task12.isSuccessful()) {

                                                //creating CollectionReference which store firebase Firestore collection "USERS" and inside that a collection "USER_DATA".
                                                CollectionReference userDataReference = firebaseFirestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");


                                                //here creating maps like for wishlist, ratings, orders and etc start here

                                                // NO. 1 this map for Wishlist
                                                Map<String, Object> wishlistMap = new HashMap<>();
                                                wishlistMap.put("list_size", (long) 0);

                                                // No. 2 this map for Ratings
                                                Map<String, Object> ratingsMap = new HashMap<>();
                                                ratingsMap.put("list_size", (long) 0);

                                                //here creating maps like for wishlist, ratings, orders and etc end here


                                                //here all documentsNames are created like My wishlist, my orders and etc. start here
                                                List<String> documentNames = new ArrayList<>();
                                                documentNames.add("MY_WISHLIST");
                                                documentNames.add("MY_RATINGS");

                                                //here all documentsNames are created like My wishlist, my orders and etc. start here

                                                //Gitana bhi  map bane ga sab ya ha per bane ga. start here
                                                List<Map<String, Object>> documentFields = new ArrayList<>();
                                                documentFields.add(wishlistMap);
                                                documentFields.add(ratingsMap);

                                                //Gitana bhi  map bane ga sab ya ha per bane ga. end here

                                                for (int x = 0; x < documentNames.size(); x++){
                                                    int finalX = x;
                                                    userDataReference.document(documentNames.get(x))
                                                            .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                if (finalX == documentNames.size() -1) {
                                                                    mainIntent();
                                                                }
                                                            }
                                                            else {
                                                                dialog.show();
                                                                signUpBtn.setEnabled(true);
                                                                signUpBtn.setTextColor(SignUpFragment.this.getResources().getColor(R.color.black));
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(SignUpFragment.this.getActivity(), error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }


                                            } else {
                                                String error = task12.getException().getMessage();
                                                Toast.makeText(SignUpFragment.this.getActivity(), error, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                signUpBtn.setEnabled(true);
                                signUpBtn.setTextColor(getResources().getColor(R.color.black));
                                String error = task.getException().getMessage();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                confirmPassword.setError("Password Doesn't Matched!");
            }

        } else {
            email.setError("Invalid Email!");
        }
    }

    private void mainIntent() {
        if (disableCloseBtnSignUp) {
            disableCloseBtnSignUp = false;
        } else {
            SignUpFragment.this.startActivity(new Intent(SignUpFragment.this.getActivity(), MainActivity.class));
        }
        SignUpFragment.this.getActivity().finish();
    }

}