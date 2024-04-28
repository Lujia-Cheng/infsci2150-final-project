package edu.pitt.lersais.mhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import edu.pitt.lersais.mhealth.model.MedicalHistoryRecord;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The MedicalRecordEditActivity that is used to edit Medical Record.
 *
 * @author Haobing Huang and Runhua Xu.
 */
public class MedicalRecordActivity extends BaseActivity {

    private static final String TAG = "MedicalRecordEditActivity";
    private static final String FIREBASE_DATABASE = "MedicalHistory";

    private List<CheckBox> mDiseasesList = new ArrayList<CheckBox>();
    private HashMap<String, RadioGroup> mHabits = new HashMap<>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        // If edit existing medical history record
        Intent getIntent = getIntent();
        String flag = getIntent.getStringExtra("flag");

        mDatabase = FirebaseDatabase.getInstance().getReference(FIREBASE_DATABASE).child(mCurrentUser.getUid());

     
        // todo If edit existing medical history record


    }

}
