package com.example.nazia_000.DesPatPro.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.example.nazia_000.DesPatPro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends ArrayAdapter<ProfilesClass> {

    private Activity context;
    private List<ProfilesClass> userList;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public UserAdapter(Activity context, List<ProfilesClass> userList){
        super(context,R.layout.users_view,userList);
        this.context = context;
        this.userList = userList;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.users_view,null,true);

        TextView nameTxt = listView.findViewById(R.id.userListName);
        TextView nmbrTxt = listView.findViewById(R.id.userListNumber);
        TextView grpTxt = listView.findViewById(R.id.userListGrp);
        TextView ageTxt = listView.findViewById(R.id.userListAge);
        TextView locTxt = listView.findViewById(R.id.userListLoc);
        TextView statTxt = listView.findViewById(R.id.userListStat);
        ImageView imgUser = listView.findViewById(R.id.userImg);


        ProfilesClass profilesClass = userList.get(position);
        nameTxt.setText("   "+profilesClass.getname());
        nmbrTxt.setText("   "+profilesClass.getnumber());
        grpTxt.setText("    "+profilesClass.getblood_group());
        ageTxt.setText("    "+profilesClass.getAge());
        locTxt.setText("    "+profilesClass.getaddress());
        statTxt.setText("Status : "+profilesClass.getStatus());
        Picasso.get().load(profilesClass.getImgUri()).fit().into(imgUser);


        return listView;
    }

}
