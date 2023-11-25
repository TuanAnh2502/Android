package com.example.dembuocchan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dembuocchan.ChiTietKeHoachActivity;
import com.example.dembuocchan.KeHoach.KeHoach;
import com.example.dembuocchan.KeHoach.KeHoachAdapter;
import com.example.dembuocchan.KeHoach.OnClickKehoachListener;
import com.example.dembuocchan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KeHoachFragment extends Fragment {
    private RecyclerView recyclerView;
    private KeHoachAdapter keHoachAdapter;
    private View kehoachView;
    private List<KeHoach> keHoachList;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        kehoachView = inflater.inflate(R.layout.fragment_ke_hoach, container, false);


        recyclerView = kehoachView.findViewById(R.id.rcv_planner);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(kehoachView.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        keHoachList = new ArrayList<>();
        load();
        keHoachAdapter = new KeHoachAdapter(keHoachList, new OnClickKehoachListener() {
            @Override
            public void onClick(KeHoach keHoach) {
                OnClickGoTo(keHoach);
            }
        });
        recyclerView.setAdapter(keHoachAdapter);

        return kehoachView;
    }

    private void load() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        // tham chiếu đến bẳng chuongtrinhluyentap
        databaseReference.child("chuongtrinhluyentap").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                keHoachList.clear();
                for( DataSnapshot snap : snapshot.getChildren()){
                    KeHoach keHoach = new KeHoach();
                    if(snap.child("name").getValue(String.class).equals("Bắt đầu đi bộ")){
                        keHoach = new KeHoach(1,R.drawable.batdau,snap.child("name").getValue(String.class),
                                snap.child("level").getValue(String.class),
                                snap.child("tansuat").getValue(String.class));
                    }
                    else if(snap.child("name").getValue(String.class).equals("Đi bộ 5km")){
                        keHoach = new KeHoach(2,R.drawable.dibo5km,snap.child("name").getValue(String.class),
                                snap.child("level").getValue(String.class),
                                snap.child("tansuat").getValue(String.class));
                    }
                    else if(snap.child("name").getValue(String.class).equals("Đi bộ từ 5km đến 10km")) {
                        keHoach = new KeHoach(3, R.drawable.dibo10km, snap.child("name").getValue(String.class),
                                snap.child("level").getValue(String.class),
                                snap.child("tansuat").getValue(String.class));
                    }
                    else if(snap.child("name").getValue(String.class).equals("Đi bộ giảm cân")) {
                        keHoach = new KeHoach(4, R.drawable.dibogiamcan, snap.child("name").getValue(String.class),
                                snap.child("level").getValue(String.class),
                                snap.child("tansuat").getValue(String.class));
                    }
                    keHoachList.add(keHoach);
                }
                keHoachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void OnClickGoTo(KeHoach kehoach){
        Intent intent = new Intent(getActivity(), ChiTietKeHoachActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_Kehoach",kehoach);
        intent.putExtras(bundle);
        intent.putExtra("idchuongtrinh",kehoach.getId());
        startActivity(intent);
    }
}