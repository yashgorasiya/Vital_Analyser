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

import java.util.Objects;

public class Home extends Fragment {
    private DatabaseReference mDatabase;
    LineGraphSeries<DataPoint> series_bp,series_spo2;
    private GraphView graph_bp,graph_spo2;
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
        graph_bp=root.findViewById(R.id.graphbp);
        graph_spo2=root.findViewById(R.id.graphspo2);

        animation = root.findViewById(R.id.lottieAnimationView);
        animation.setSpeed(1F);

        series_bp=new LineGraphSeries<>();
        series_spo2=new LineGraphSeries<>();

        ValueEventListener bp_spo2 = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String bp_spo22 = Objects.requireNonNull(snapshot.child("bloodp").getValue()).toString();
                String bp_spo23 = Objects.requireNonNull(snapshot.child("spo2").getValue()).toString();

                int bpi = (int) Double.parseDouble(bp_spo22);
                int spi = (int) Double.parseDouble(bp_spo23);

                i=i+1;
                series_bp.appendData(new DataPoint(i,bpi),true,25);
                series_spo2.appendData(new DataPoint(i,spi),true,25);

                graph_bp.addSeries(series_bp);
                graph_spo2.addSeries(series_spo2);

                bp.setText(bp_spo22);
                spo2.setText(bp_spo23);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Field To Load Data", Toast.LENGTH_SHORT).show();
            }
        };
        mDatabase.addValueEventListener(bp_spo2);

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

