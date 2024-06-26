package edu.pitt.lersais.mhealth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import edu.pitt.lersais.mhealth.util.DownloadImageTask;

/**
 * The SettingActivity that is used to handle profile features such as update
 * display name and
 * profile photo.
 *
 * @author Haobing Huang and Runhua Xu.
 * @author Lujia Cheng and Yongxiang Zhang
 * @version 2024.04.20
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Profile_Activity";
    private static final int REQUEST_CODE_FOR_GALLERY = 520;

    private TextView uidTextView;
    private EditText nameEditText;
    private ImageView photoImageView;

    private FirebaseAuth mAuth;
    private StorageReference mProfileImagesReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            mProfileImagesReference = FirebaseStorage.getInstance().getReference().child("images/profile/" + mAuth.getCurrentUser().getUid() + ".jpg");
            nameEditText = findViewById(R.id.edit_text_display_name);
            photoImageView = findViewById(R.id.image_view_profile_photo);
            uidTextView = findViewById(R.id.text_view_uid);
            uidTextView.setText(currentUser.getUid());
            findViewById(R.id.button_update_profile).setOnClickListener(this);
            findViewById(R.id.button_chose_photo).setOnClickListener(this);

            displayNameAndPhoto(currentUser);
        }
    }

    private void displayNameAndPhoto(FirebaseUser currentUser) {
        // TODO: Task 3.3 Display user's display_name and photo if he/she already have.
        // Tips:
        // We have provided a tool DownloadImageTask in util package.
        // You can construct an instance of DownloadImageTask with ImageView instance,
        // and then call execute function to download the image from Firebase Storage
        // e.g. new DownloadImageTask(photoImageView).execute(photoUrl)
        if (currentUser.getDisplayName() != null && !currentUser.getDisplayName().isEmpty()) {
            nameEditText.setText(currentUser.getDisplayName());
        }
        if (currentUser.getPhotoUrl() != null) {
            Uri photoUrl = currentUser.getPhotoUrl();
            new DownloadImageTask(photoImageView).execute(photoUrl.toString());
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_update_profile) {
            // TODO: 3.1-3.2 implement the update function for profile information, namely,
            // name and photo.
            // Tips:
            // 1. acquire the image and upload the photo to the storage
            // 2. call the method provided by Firebase Storage to upload
            // 3. get the photo url and update the user profile
            // IMPORTANT: as storage service is integrated, store the photo in the following
            // url in the Firebase Storage.
            // "images/profile/[USER'S UID].jpg"

            // @link https://firebase.google.com/docs/storage/android/upload-files#upload_from_data_in_memory
            photoImageView.setDrawingCacheEnabled(true);
            photoImageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) photoImageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = mProfileImagesReference.putBytes(data);

            // fixme it seem cannot upload new profile picture
            // upload return erro 412 precondition failed.
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(ProfileActivity.this, exception.toString(),
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProfileActivity.this, "Upload Photo Success",
                            Toast.LENGTH_SHORT).show();
                }
            });

        } else if (i == R.id.button_chose_photo) {
            // TODO: Task 3.1-3.2 implement the function to allow user choose photo from
            // local album.
            startActivityForResult(
                    new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                    REQUEST_CODE_FOR_GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: get the photo that user choose and display
        if (requestCode == REQUEST_CODE_FOR_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            photoImageView.setImageURI(selectedImageUri);
            Toast.makeText(ProfileActivity.this, "select profile photo success",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
