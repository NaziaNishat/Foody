package com.example.nazia_000.DesPatPro.entryPack;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nazia_000.DesPatPro.mapPack.MyLocation;
import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth proAuth;
    private DatabaseReference proReference;
    private FirebaseUser user;

    private Spinner proSpinner;
    private Spinner proReadySpinner;
    private Button savepro;
    private EditText namePro,agePro,nmbrPro,sendpro;
    private ImageView imgPro;

    private MyLocation myLocation;

    private static int PReqCode = 1;
    private static int REQUESCODE = 1;
    private Uri pickedImgUri;

    private String username,age,nmbr,address,grp,rdy,user_id;
    public static String email,imgUri;
    private boolean clicked = false;

    private double latS,lonS;
    private  LatLng latLngCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myLocation = new MyLocation();

        proAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        namePro = findViewById(R.id.proName);
        agePro = findViewById(R.id.proAge);
        nmbrPro = findViewById(R.id.proContact);
        savepro = findViewById(R.id.proSave);
        sendpro = findViewById(R.id.proSend);
        imgPro = findViewById(R.id.profImg);

        Intent i =getIntent();
        email = i.getStringExtra("email");

        proSpinner = findViewById(R.id.proBldGrp);
        proReadySpinner = findViewById(R.id.proYesNo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Blood_Group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterYesNo = ArrayAdapter.createFromResource(this, R.array.ReadyToDonate, android.R.layout.simple_spinner_item);
        adapterYesNo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        proSpinner.setAdapter(adapter);
        proReadySpinner.setAdapter(adapterYesNo);

        HandlerSpinner handlerSpinner = new HandlerSpinner();
        proSpinner.setOnItemSelectedListener(handlerSpinner);

        HandlerSpinner1 handlerSpinner1 = new HandlerSpinner1();
        proReadySpinner.setOnItemSelectedListener(handlerSpinner1);

        Handler handler = new Handler();
        savepro.setOnClickListener(handler);
        imgPro.setOnClickListener(handler);

    }

    private void addProfile(){

        username = namePro.getText().toString().trim();
        age = agePro.getText().toString().trim();
        nmbr = nmbrPro.getText().toString().trim();
        address = sendpro.getText().toString().trim();
        grp = (String) proSpinner.getSelectedItem();
        rdy = (String) proReadySpinner.getSelectedItem();

        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(age) && TextUtils.isEmpty(nmbr) && TextUtils.isEmpty(address)){
            Toast.makeText(ProfileActivity.this,"Fill up all field",Toast.LENGTH_SHORT).show();
        }
        else{

            Toast.makeText(ProfileActivity.this,"Profile updated",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Login_activity.class));
            upProfile();
        }

    }

    private void upProfile(){

        if(pickedImgUri != null)
        {
            user_id = proAuth.getUid();
            final StorageReference imageStore = FirebaseStorage.getInstance().getReference("User_photos").child(user_id);
            UploadTask uploadTask = imageStore.putFile(pickedImgUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageStore.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        add(downloadUri);

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        }
        else if(pickedImgUri == null)
        {
            ProfilesClass profilesClass = new ProfilesClass(username,age,nmbr,grp,rdy,
                    address,email,null,user_id,latS,lonS);
            String ref_id = proAuth.getUid();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            proReference = firebaseDatabase.getReference().child("Users").child(ref_id);
            proReference.setValue(profilesClass);
        }

    }

    private void  add(Uri downloadUri){

        Geocoder geocoder = new Geocoder(getApplicationContext());


        try {

            List<Address> addresses1 = geocoder.getFromLocationName(address,1);
            latS = addresses1.get(0).getLatitude();
            lonS = addresses1.get(0).getLongitude();
            latLngCheck = new LatLng(latS,lonS);


        } catch (IOException e) {
            e.printStackTrace();
        }


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        user_id = proAuth.getUid();
        proReference = firebaseDatabase.getReference().child("Users").child(user_id);
        ProfilesClass profilesClass = new ProfilesClass(username,age,nmbr,grp,rdy,address,email,
                downloadUri.toString(),user_id,latS,lonS);
        proReference.setValue(profilesClass);
    }

    class Handler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.proSave){
                addProfile();
            }

            if(v.getId() == R.id.profImg){
                if(Build.VERSION.SDK_INT >= 22){
                    checkRequestForPermission();
                }

                else {

                    openGallery();
                }
            }
        }
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESCODE && data != null)
        {
            //user has successfully picked an image.we need to save its reference ti uri variable

            pickedImgUri=data.getData();
            imgPro.setImageURI(pickedImgUri);

        }
    }


    private void checkRequestForPermission() {

        if(ContextCompat.checkSelfPermission(ProfileActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(ProfileActivity.this,android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(ProfileActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }
            else{
                ActivityCompat.requestPermissions(ProfileActivity.this,
                        new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE},PReqCode);
            }
        }
        else {
            openGallery();
        }
    }


    private void updateImgInfo(final String userName, final Uri pickedImgUri, final FirebaseUser currentUser) {

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());

        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profileUpdt=new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .setPhotoUri(uri).build();

                        //url = "https://firebasestorage.googleapis.com/v0/b/account-e9c95.appspot.com/o/users_photos%2Fimage%3A382916?alt=media&token=d3abd070-f443-44e3-8357-31052c531a6a";


                        currentUser.updateProfile(profileUpdt).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if(task.isSuccessful()){

                                }
                            }
                        });

                    }
                });
            }
        });

    }


    class HandlerSpinner implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class HandlerSpinner1 implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}
