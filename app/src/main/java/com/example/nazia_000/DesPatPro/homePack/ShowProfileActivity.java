package com.example.nazia_000.DesPatPro.homePack;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ShowProfileActivity extends AppCompatActivity {

    private TextView namTxt,nmbrTxt,grptxt,ageTxt,adrstxt,mailTxt;
    private ImageView imgView;
    private ProfilesClass pc;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        pc = ((Intent) intent).getParcelableExtra("object");

        namTxt = findViewById(R.id.showName);
        nmbrTxt = findViewById(R.id.showNumber);
        grptxt = findViewById(R.id.showGrp);
        ageTxt = findViewById(R.id.showAge);
        adrstxt = findViewById(R.id.showLoc);
        mailTxt = findViewById(R.id.showMail);
        imgView = findViewById(R.id.showImg);

        namTxt.setText(pc.getname());
        nmbrTxt.setText(pc.getnumber());
        grptxt.setText(pc.getblood_group());
        ageTxt.setText(pc.getAge());
        adrstxt.setText(pc.getaddress());
        mailTxt.setText(pc.getEmail());
        Picasso.get().load(pc.getImgUri()).fit().into(imgView);

        findViewById(R.id.showCallImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phn = String.valueOf(pc.getnumber());
                startActivity(new Intent(Intent.ACTION_DIAL).setData( Uri.fromParts("tel",phn, null)));
            }
        });
    }
}
