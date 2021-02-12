package com.yjisolutions.vitalanalyser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends Fragment {
    int RC_SIGN_IN = 0;
    Button SignInButton, signOut;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "This is an Error";
    FirebaseAuth mAuth;
    TextView userName;
    ImageView profilePic;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login, container, false);

        //#######################################################################################################################//
        //google sign  in

        userName = root.findViewById(R.id.user_name_login);
        profilePic = root.findViewById(R.id.profilePic_login);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        SignInButton = root.findViewById(R.id.sign_in_button);
        signOut = root.findViewById(R.id.signOut);

        SignInButton.setOnClickListener(v -> SignInGoogle());
        signOut.setOnClickListener(v -> Logout());

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }

        return root;
    }

    public void SignInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {

                        Log.d("TAG", "signInWithCredential:success");
                        Toast.makeText(getActivity(), "Successfully Logged In", Toast.LENGTH_SHORT).show();

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();


                    } else {
                        Exception exception = task.getException();

                        Log.w("TAG", "signInWithCredential:failure" + exception, task.getException());
                        Toast.makeText(getActivity(), "Failed to Logged In", Toast.LENGTH_SHORT).show();

                        Toast.makeText(getContext(), "Authentication failed." + exception,
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);

                    }
                });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);

                }
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo = String.valueOf(user.getPhotoUrl());

            Glide.with(getContext()).load(photo).into(profilePic);
            userName.setText(name);

            signOut.setEnabled(true);
            signOut.setVisibility(View.VISIBLE);
            SignInButton.setVisibility(View.INVISIBLE);


        } else {
            signOut.setVisibility(View.INVISIBLE);
            SignInButton.setVisibility(View.VISIBLE);
        }
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                task -> updateUI(null));
        Toast.makeText(getActivity(), "Successfully Logged Out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();


    }
}
