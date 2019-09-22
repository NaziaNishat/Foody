package com.example.nazia_000.DesPatPro.navPack;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.nazia_000.DesPatPro.Adapter.NavMyRqstsAdptr;
import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.NavMyrequestsClass;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class navMyRequests extends AppCompatActivity {


    private ListView l1;
    private DatabaseReference databaseReference;
    private FirebaseUser auth;
    private String user;

    String uid;

    private List<NavMyrequestsClass> navMyrequestsClassList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_my_requests);

        auth = FirebaseAuth.getInstance().getCurrentUser();
        l1 = findViewById(R.id.navMyRequests);
        navMyrequestsClassList = new ArrayList<NavMyrequestsClass>();
        // final TextView bloodGroup=findViewById(R.id.navRqstGroup);
        //final TextView amount=findViewById(R.id.navRqstGroup);
        ProfilesClass bloodGroup,amount;

        //String bloodGroup,amount;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid()).child("requests");

    }


    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    NavMyrequestsClass myrequestsClass=ds.getValue(NavMyrequestsClass.class);
                    navMyrequestsClassList.add(myrequestsClass);
                    //ProfilesClass profilesClass = ds.getValue(ProfilesClass.class);



                    NavMyRqstsAdptr adptr = new NavMyRqstsAdptr(navMyRequests.this,navMyrequestsClassList);
                    l1.setAdapter(adptr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
