package com.example.nazia_000.DesPatPro.navPack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.example.nazia_000.DesPatPro.entryPack.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class navProfile extends AppCompatActivity {

    private DatabaseReference myProfRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        final TextView myNameTxt =  findViewById(R.id.myProfName);
        final TextView myNmbrTxt = findViewById(R.id.myNmbr);
        final TextView myGrpTxt = findViewById(R.id.myBldGrp);
        final TextView myEmailTxt = findViewById(R.id.myEmail);
        final TextView myAdrsTxt = findViewById(R.id.myAddress);
        final ImageView myProfImg = findViewById(R.id.myImg);

        user = FirebaseAuth.getInstance().getCurrentUser();
        myProfRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        findViewById(R.id.myProfUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        myProfRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfilesClass profilesClass = dataSnapshot.getValue(ProfilesClass.class);

                Picasso.get().load(profilesClass.getImgUri()).fit().into(myProfImg);
                myNameTxt.setText("Username : "+ profilesClass.getname());
                myNmbrTxt.setText("Phone Number : " + profilesClass.getnumber());
                myGrpTxt.setText("Blood Group : "+ profilesClass.getblood_group());
                myEmailTxt.setText("E-mail : "+ user.getEmail());
                myAdrsTxt.setText("Address : "+ profilesClass.getaddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
