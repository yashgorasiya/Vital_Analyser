package com.yjisolutions.vitalanalyser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Setting extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_setting, container, false);

        Button classic = root.findViewById(R.id.classic);
        Button matireal = root.findViewById(R.id.matirial);

//        CardView home1 = (CardView) root.findViewById(R.id.card_graph);
//        CardView home2 = root.findViewById(R.id.cardView);
//        CardView home3 = root.findViewById(R.id.cardView2);
//        CardView home4 = root.findViewById(R.id.cardView3);
//        CardView home5 = root.findViewById(R.id.cardView4);
//        CardView home6 = root.findViewById(R.id.cardView5);
//        CardView home7 = root.findViewById(R.id.cardView6);
//        CardView home8 = root.findViewById(R.id.cardViewspo2);


        classic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               // try to update color.card resouse
                getView().setBackgroundColor(getResources().getColor(R.color.white));
//                @SuppressLint("ResourceType") View home = root.findViewById(fragment_support);
//                View home1 = home.getRootView();
//
//                home1.setBackgroundColor(getResources().getColor(R.color.white));
//                Resources res = getResources();
//                res.getColor(R.color.card);

                classic.setEnabled(false);
                matireal.setEnabled(true);
            }
        });

        matireal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getView().setBackgroundColor(getResources().getColor(R.color.card));
                classic.setEnabled(true);
                matireal.setEnabled(false);
            }
        });
        return root;
    }
}