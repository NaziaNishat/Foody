package com.example.nazia_000.DesPatPro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.ProfilesClass;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

public class infoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View mWindow;
    private Context context;

    private TextView nameTxt,nmbrTxt,grpTxt, adrsTxt, ageTxt,statxt;
    private ImageView img;
    private String nam,nmb,grp,adr;


    public infoWindowAdapter(Context context){
         this.context = context;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        try {
            View view = ((Activity) context).getLayoutInflater().inflate(R.layout.window_view, null);

            nameTxt = view.findViewById(R.id.winVName);
            nmbrTxt = view.findViewById(R.id.winVNumber);
            grpTxt = view.findViewById(R.id.winVGrp);
            adrsTxt = view.findViewById(R.id.winVLoc);
            ageTxt = view.findViewById(R.id.winVAge);
            img = view.findViewById(R.id.winVImg);
            statxt = view.findViewById(R.id.winVStat);

            ProfilesClass profilesClass = (ProfilesClass) marker.getTag();

            Picasso.get().load(profilesClass.getImgUri()).into(img);
            nameTxt.setText("   "+profilesClass.getname());
            nmbrTxt.setText("   "+profilesClass.getnumber());
            grpTxt.setText("    "+profilesClass.getblood_group());
            adrsTxt.setText("   "+profilesClass.getaddress());
            ageTxt.setText("    "+profilesClass.getAge());
            statxt.setText("Status : "+profilesClass.getStatus());


            return view;
        }catch (Exception e){}

        return  null;
    }
}
