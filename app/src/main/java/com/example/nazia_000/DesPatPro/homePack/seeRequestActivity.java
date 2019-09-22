package com.example.nazia_000.DesPatPro.homePack;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nazia_000.DesPatPro.Adapter.RequestAdapter;
import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.RequestClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class seeRequestActivity extends AppCompatActivity {

    private Dialog dialog;

    private ListView listView;
    private DatabaseReference requRef;
    private List<RequestClass> requestClassList;
    private RequestAdapter requestAdapter;

    private FirebaseUser user;

    private Button acceptBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        dialog = new Dialog(this);

        listView = findViewById(R.id.requ_list_view);
        requRef = FirebaseDatabase.getInstance().getReference("Requests");
        requestClassList = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final RequestClass requestClass = requestClassList.get(position);

                if(requestClass.getEmail().equals(user.getEmail())){
                    showPopUp(position);
                }
                else{
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                }
            }
        });

    }

    private void showPopUp(int position){

        final int pos = position;

        TextView txtclose;
        Button removebtn;

        dialog.setContentView(R.layout.remove_popup);
        removebtn = dialog.findViewById(R.id.removeRequ);
        txtclose = dialog.findViewById(R.id.txtclose);
        txtclose.setText("X");

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final RequestClass requestClass = requestClassList.get(pos);

                DatabaseReference databaseReference =
                        FirebaseDatabase.getInstance().getReference("Requests").child(user.getUid());

                databaseReference.removeValue();

                requestClassList.remove(requestClass);
                requestAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),"remove clicked",Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        requRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RequestClass requestClass = dataSnapshot.getValue(RequestClass.class);
                requestClassList.add(requestClass);

                requestAdapter = new RequestAdapter(seeRequestActivity.this,requestClassList);
                listView.setAdapter(requestAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}


