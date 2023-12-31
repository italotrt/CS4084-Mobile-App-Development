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
 * Use the {@link RegisterUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterUserFragment extends Fragment {

    EditText newMail; //EditTexts containing new user info
    EditText newPass;
    View fragView;
    Button regButton, switchToLog; //Buttons to switch fragments
    FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterUserFragment newInstance(String param1, String param2) {
        RegisterUserFragment fragment = new RegisterUserFragment();
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
        return inflater.inflate(R.layout.fragment_register_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragView = view; //Make view accessible for other methods
        //Set up XML elements
        newMail = view.findViewById(R.id.newemail);
        newPass = view.findViewById(R.id.newpassword);
        regButton = view.findViewById(R.id.regButt);
        switchToLog = view.findViewById(R.id.switchtolog);
        mAuth = FirebaseAuth.getInstance();

        //If a pre-existing user clicked the new user button, they can switch fragments
        switchToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(fragView).navigate(R.id.action_registerUserFragment_to_logInFragment);
            }
        });
        //Get details and sign the user up
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    //A method to check the text fields for a valid email and password, then store them in the database
    public void createUser(){
        //Get username and password from EditTexts
        String newUserEmail = newMail.getText().toString();
        String newUserPass = newPass.getText().toString();
        if(newUserEmail.equals("") || newUserPass.equals("")){
            //Nothing was entered in at least one of the fields, don't do anything
        }else{
            //Store email and password (if valid) into database
            mAuth.createUserWithEmailAndPassword(newUserEmail, newUserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Valid email and password, user is now registered
                        System.out.println("Before next frag");
                        Bundle bundle = new Bundle(); //Store user email for use in the Main Menu welcome text
                        bundle.putString("name", newUserEmail);
                        //Change fragments
                        Navigation.findNavController(fragView).navigate(R.id.action_registerUserFragment_to_mainMenuFragment, bundle);
                        Toast.makeText(fragView.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    } else {
                        //Registration failed, tell user why
                        Toast.makeText(fragView.getContext(), "Login error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}