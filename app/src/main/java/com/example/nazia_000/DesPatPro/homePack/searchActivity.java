package com.example.nazia_000.DesPatPro.homePack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nazia_000.DesPatPro.Adapter.UserAdapter;
import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.example.nazia_000.DesPatPro.mapPack.MyLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class searchActivity extends AppCompatActivity {

    private DatabaseReference searchRef;

    private ListView listView;
    private ArrayList<ProfilesClass> profilesList;

    private Spinner listSearchSpin;
    private Button toggleBtn;
    private ImageView imgCall,imgUser;

    private String imgViewCall = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = findViewById(R.id.userListView);
        listSearchSpin = findViewById(R.id.spinnerSearchList);
        toggleBtn = findViewById(R.id.toggleList);

        View view = getLayoutInflater().inflate(R.layout.users_view,null);
        imgCall = view.findViewById(R.id.callImg);

        Handler handler = new Handler();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Blood_Group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listSearchSpin.setAdapter(adapter);
        listSearchSpin.setOnItemSelectedListener(handler);

        profilesList = new ArrayList<ProfilesClass>();
        searchRef = FirebaseDatabase.getInstance().getReference("Users");


        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyLocation.class));
            }
        });


        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(searchActivity.this,"Ho",Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final ProfilesClass pc = profilesList.get(position);
                Intent intent = new Intent(searchActivity.this,ShowProfileActivity.class);
                intent.putExtra("object",pc);
                startActivity(intent);


            }
        });

    }




    class Handler implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            profilesList.clear();
            String s = (String) listSearchSpin.getSelectedItem();

            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("blood_group")
                    .equalTo(s);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        ProfilesClass profilesClass = ds.getValue(ProfilesClass.class);

                        profilesList.add(profilesClass);
                        for (ProfilesClass p: profilesList){
                            Log.d("Places before sorting", "Place: " + p.getaddress());
                        }

                        LatLng latLngSust = new LatLng(24.9172,91.8319);
                        Collections.sort(profilesList,new SortPlaces(latLngSust));


                        for (ProfilesClass p: profilesList){
                            Log.d("Places after sorting", "Place: " + p.getaddress());
                        }


                        imgViewCall = profilesClass.getnumber();
                    }

                    UserAdapter userAdapter = new UserAdapter(searchActivity.this,profilesList);
                    listView.setAdapter(userAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
}
