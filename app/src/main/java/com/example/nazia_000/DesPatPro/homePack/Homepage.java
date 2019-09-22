package com.example.nazia_000.DesPatPro.homePack;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.example.nazia_000.DesPatPro.mapPack.MyLocation;
import com.example.nazia_000.DesPatPro.navPack.navMyRequests;
import com.example.nazia_000.DesPatPro.navPack.navProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

public class Homepage extends AppCompatActivity{

    private NavigationView navigationView;

    private DatabaseReference homeRef,noRef;
    private FirebaseUser user;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private android.support.v7.widget.GridLayout gridHomepage;
    private CardView requSee,requDo,search,info;
    private TextView drawerName, emailName;
    private ImageView drawerImg;

    private String bldGrp;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        noRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //token id

        final String token_id;
        token_id= FirebaseInstanceId.getInstance().getToken();

        Intent i = getIntent();
        email = i.getStringExtra("email");

        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("email")
                .equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    ProfilesClass profilesClass = ds.getValue(ProfilesClass.class);
                    bldGrp = profilesClass.getblood_group();
                    noRef.child("fcm-token").child(bldGrp).child(token_id).child("token").setValue(token_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //homepage
        gridHomepage = findViewById(R.id.homepageGrid);
        requSee = findViewById(R.id.seeRequ);
        requDo = findViewById(R.id.doRequ);
        search = findViewById(R.id.searching);
        info = findViewById(R.id.information);

        navigationView = findViewById(R.id.navigationId);

        //navigation drawer
        drawerLayout = findViewById(R.id.drawerId);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Handler handler = new Handler();
        requSee.setOnClickListener(handler);
        requDo.setOnClickListener(handler);
        search.setOnClickListener(handler);
        info.setOnClickListener(handler);

        user = FirebaseAuth.getInstance().getCurrentUser();
        homeRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        View navHeaderView = navigationView.getHeaderView(0);
        drawerName = navHeaderView.findViewById(R.id.nameDrawer);
        emailName = navHeaderView.findViewById(R.id.emailDrawer);
        drawerImg = navHeaderView.findViewById(R.id.imgDrawer);

        homeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProfilesClass profilesClass = dataSnapshot.getValue(ProfilesClass.class);


                drawerName.setText(""+profilesClass.getname());
                emailName.setText(""+user.getEmail());
                Picasso.get().load(profilesClass.getImgUri()).fit().into(drawerImg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.profile:
                        updateActivity(navProfile.class);
                        break;
                    case R.id.myRequests:
                        updateActivity(navMyRequests.class);
                        break;

                }
                return false;
            }
        });

    }



    //three dots menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dots_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.signout){
            FirebaseAuth.getInstance().signOut();
            finish();
        }

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return  super.onOptionsItemSelected(item);
    }

    private void updateActivity(Class cls){

        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    class Handler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.seeRequ){
                updateActivity(seeRequestActivity.class);
            }

            if(v.getId() == R.id.doRequ){
                updateActivity(doRequestActivity.class);
            }

            if(v.getId() == R.id.searching){
                updateActivity(MyLocation.class);
            }

            if(v.getId() == R.id.information){
                //updateActivity(infoActivity.class);
                String wUrl = "https://www.google.com/maps/search/nearby+blood+bank/@24.8994441,91.8519916,15z/data=!3m1!4b1";
                Uri uri = Uri.parse(wUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        }
    }

}
