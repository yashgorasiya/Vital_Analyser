package com.yjisolutions.vitalanalyser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Support extends Fragment {
    private DatabaseReference mDatabase;
    LineGraphSeries<DataPoint> seriesbp,seriesspo2;
    private GraphView graphbp,graphspo2;
    private TextView bp, spo2;
    private int i = 0;
    LottieAnimationView animation;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_support, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        bp = root.findViewById(R.id.bp);
        spo2 = root.findViewById(R.id.spo2);
        graphbp=root.findViewById(R.id.graphbp);
        graphspo2=root.findViewById(R.id.graphspo2);

        animation = root.findViewById(R.id.lottieAnimationView);
        animation.setSpeed(1F);

        seriesbp=new LineGraphSeries<DataPoint>();
        seriesspo2=new LineGraphSeries<DataPoint>();

        ValueEventListener bpspo2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String bpspo22 = (String) snapshot.child("bloodp").getValue().toString();
                String bpspo23 = (String) snapshot.child("spo2").getValue().toString();

                int bpi = (int) Double.parseDouble(bpspo22);
                int spi = (int) Double.parseDouble(bpspo23);

                i=i+1;
                seriesbp.appendData(new DataPoint(i,bpi),true,25);
                seriesspo2.appendData(new DataPoint(i,spi),true,25);

                graphbp.addSeries(seriesbp);
                graphspo2.addSeries(seriesspo2);

                bp.setText(bpspo22);
                spo2.setText(bpspo23);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Field To Load Data", Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addValueEventListener(bpspo2);

        return root;

    }


}

//    var animation = findViewById<LottieAnimationView>(R.id.lottieAnimationView);
//                animation.speed = 2.0F // How fast does the animation play
//        animation.progress = 50F // Starts the animation from 50% of the beginning
//        animation.addAnimatorUpdateListener {
//            // Called everytime the frame of the animation changes
//        }
//        animation.repeatMode = LottieDrawable.RESTART // Restarts the animation (you can choose to reverse it as well)
//        animation.cancelAnimation() // Cancels the animation

