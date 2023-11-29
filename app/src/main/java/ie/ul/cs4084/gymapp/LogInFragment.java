package com.example.cs4084_project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogInFragment extends Fragment {

    EditText email, password; //The EditTexts for the user's login info
    Button log, switchToReg; //The buttons for logging in or switching to the register screen
    FirebaseAuth mAuth; //The FireBaseAuth that will be used to verify
    View fragView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogInFragment newInstance(String param1, String param2) {
        LogInFragment fragment = new LogInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Setting up XML elements
        email = view.findViewById(R.id.emailEdit);
        password = view.findViewById(R.id.passwordEdit);
        log = view.findViewById(R.id.logButt);
        switchToReg = view.findViewById(R.id.switchtoreg);
        mAuth = FirebaseAuth.getInstance();
        fragView = view; //Making the view accessible by other methods

        switchToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If a new user accidentally, clicks Sign In, they can switch to Register New Account
                Navigation.findNavController(fragView).navigate(R.id.action_logInFragment_to_registerUserFragment);
            }
        });

        //When this button is clicked, we need to verify the login and let them through
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Before email");
                //Getting what was entered into the EditTexts
                String userEmail = email.getText().toString();
                String userPass = password.getText().toString();
                System.out.println(userEmail + " " + userPass);

                if(userEmail.equals("") || userPass.equals("")){
                    //Nothing was entered, don't do anything
                }else{
                    //System.out.println("Before login");
                        mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //Correct email and password, let user through
                                    System.out.println("Before next frag");
                                    Bundle bundle = new Bundle();
                                    bundle.putString("name", userEmail); //Pass the user's email to the Main Menu for the welcome text
                                    Navigation.findNavController(fragView).navigate(R.id.action_logInFragment_to_mainMenuFragment, bundle);
                                    Toast.makeText(fragView.getContext(), "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Login failed, tell user why
                                    Toast.makeText(fragView.getContext(), "Login error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });
    }
}