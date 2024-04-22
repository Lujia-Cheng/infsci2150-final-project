package edu.pitt.lersais.mhealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;


/**
 * The LoginActivity is used to handle login authentication, email password authentication.
 *
 * @author Haobing Huang and Runhua Xu.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Login_Password_Activity";
    private static final int REQUEST_SIGNUP = 123;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    // BEGIN
    private FirebaseAuth mAuth;
    // END

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // BEGIN
        mAuth = FirebaseAuth.getInstance();
        // END

        mEmailEditText = findViewById(R.id.field_email);
        mPasswordEditText = findViewById(R.id.field_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.link_signup).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.email_sign_in_button) {
            login();
        }
        else if (i == R.id.link_signup) {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    private void login() {
        // TODO: Task 2.3 implement the login code here with validation for user inputs.
        // Tips: recommended steps
        // 1) get the email and password from user's input
        // 2) validate the format of user's input
        // 3) present a progress dialog (check in the BaseActivity)
        // 4) call related authentication method provided by Firebase Authentication
        // 5) close the progress dialog

        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                || password.isEmpty()) {
            return;
        }

        showProgressDialog();
        // @link https://firebase.google.com/docs/auth/android/start#sign_in_existing_users
        mAuth.signInWithEmailAndPassword(email, password);
        hideProgressDialog();

    }

}
