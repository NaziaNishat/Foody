package com.example.nazia_000.DesPatPro.homePack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.RequestClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class doRequestActivity extends AppCompatActivity {

    private FirebaseAuth doRequAuth;
    private FirebaseUser user;
    private DatabaseReference doRequRef;

    private EditText NamedoRequ,NmbrdoRequ,AmountdoRequ,EmaildoRequ;

    private Spinner doRequBloodGrpSpinner;
    private Button doreqbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_request);

        user = FirebaseAuth.getInstance().getCurrentUser();

        NamedoRequ = findViewById(R.id.doRequName);
        NmbrdoRequ = findViewById(R.id.doRequNmbr);
        AmountdoRequ = findViewById(R.id.doRequAmount);
        doreqbtn = findViewById(R.id.doRequButton);

        Handler handler = new Handler();
        doreqbtn.setOnClickListener(handler);



        doRequBloodGrpSpinner = findViewById(R.id.doRequBloodGrp);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Blood_Group, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doRequBloodGrpSpinner.setAdapter(adapter1);
        doRequBloodGrpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void reqSend(){

        String name  = NamedoRequ.getText().toString().trim();
        String nmbr = NmbrdoRequ.getText().toString().trim();
        String amount = AmountdoRequ.getText().toString().trim();
        String grp = (String) doRequBloodGrpSpinner.getSelectedItem();
        String email = user.getEmail();

        String doReqId = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        doRequRef = firebaseDatabase.getReference().child("Requests").child(doReqId);
        RequestClass requestClass = new RequestClass(name,nmbr,grp,amount,email);
        doRequRef.setValue(requestClass);
        Toast.makeText(doRequestActivity.this,"Request sent",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),Homepage.class));

        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(doReqId).child("requests").push();
        userDatabase.child("bloodGroup").setValue(grp);
        userDatabase.child("amount").setValue(amount);

    }

    class Handler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            reqSend();
        }
    }
}
