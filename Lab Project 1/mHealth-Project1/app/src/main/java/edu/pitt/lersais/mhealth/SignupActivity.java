package edu.pitt.lersais.mhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The SignupActivity is used to handle registration, email-password registration.
 *
 * @author Haobing Huang and Runhua Xu.
 * @author Lujia Cheng and Yongxiang Zhang
 * @version 2024.04.20
 */

public class SignupActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "SignupActivity";

    // BEGIN
    private FirebaseAuth mAuth;
    // END

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // BEGIN
        mAuth = FirebaseAuth.getInstance();
        // END

        mEmailEditText = findViewById(R.id.field_email);
        mPasswordEditText = findViewById(R.id.field_password);
        mConfirmPasswordEditText = findViewById(R.id.field_password_confirm);

        findViewById(R.id.email_create_account_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_create_account_button) {
            registration();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void registration() {
        // TODO: Task 2.2 implement the registration procedure here.
        // Tips: recommended steps
        // 1) get the email and password from user's input
        // 2) validate the format of user's input
        // 3) present a progress dialog (check in the BaseActivity)
        // 4) call related register method provided by Firebase Authentication
        // 5) close the progress dialog

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                || password.isEmpty() || !password.equals(confirmPassword)) {
            return;
        }

        showProgressDialog();

        // @link https://firebase.google.com/docs/auth/android/start#sign_up_new_users
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "create user with email success");

                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        } else {
                            Log.w(TAG, "create user with email failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication Failed.",
                                    Toast.LENGTH_LONG).show();
                        }
                        hideProgressDialog();
                    }
                });
    }
}
