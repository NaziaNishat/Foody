package com.example.nazia_000.DesPatPro.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nazia_000.DesPatPro.R;
import com.example.nazia_000.DesPatPro.classPack.NavMyrequestsClass;

import java.util.List;

public class NavMyRqstsAdptr extends ArrayAdapter<NavMyrequestsClass>{

    private Activity context;
    private List<NavMyrequestsClass> navMyrequestsClassList1;

    public NavMyRqstsAdptr(Activity context,List<NavMyrequestsClass> navMyrequestsClassList1){
        super(context,R.layout.nav_my_rqstlist, navMyrequestsClassList1);
        this.context = context;
        this.navMyrequestsClassList1 = navMyrequestsClassList1;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView=inflater.inflate(R.layout.nav_my_rqstlist,null,true);


        NavMyrequestsClass navMyrequestsClass=navMyrequestsClassList1.get(position);
        TextView grpTxt = listView.findViewById(R.id.navRqstGrp);
        TextView amountTxt = listView.findViewById(R.id.navRqstAmount);

        grpTxt.setText("Blood Group: "+navMyrequestsClass.getBloodGroup());
        amountTxt.setText("Needed Amount: "+navMyrequestsClass.getAmount());
        return listView;

    }
}
