package com.group6.placementportal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.group6.placementportal.DatabasePackage.Jobs;
import com.group6.placementportal.DatabasePackage.company;

import java.io.Serializable;
import java.util.ArrayList;

public class enrolments_screen1_adpater extends RecyclerView.Adapter<enrolments_screen1_adpater.MyViewHolder> {

    Context context;
    ArrayList<Jobs> profiles;
    private static DatabaseReference reference;
    ArrayList <String> cv;
    company com;
    public enrolments_screen1_adpater(Context c , ArrayList<Jobs> p,company company)
    {
        context = c;
        profiles = p;
        com=company;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_enrolments_job, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
       // holder.company_name.setText(profiles.get(position).getCompany_name());
        holder.job_profile.setText(profiles.get(position).getProfile());
        holder.job_id.setText(profiles.get(position).getJob_id());
        holder.company.setText(profiles.get(position).getCompany_name());
       // holder.job_location.setText(profiles.get(position).getLocation());
        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //intent.putExtra("job_profile", profiles.get(position));
                final String job_id=profiles.get(position).getJob_id();
                reference = FirebaseDatabase.getInstance().getReference().child("Jobs").child(job_id).child("Applied Students");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<String> slist=new ArrayList<>();
                        cv=new ArrayList<>();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            String s_id=dataSnapshot1.getKey();
                            slist.add(s_id);

                            cv.add((String) dataSnapshot1.child("CV").getValue());
                        }
                        Intent intent = new Intent(context, company_enrollments.class);
                        intent.putExtra("MyClass",slist);
                        intent.putExtra("Job",job_id);
                        intent.putExtra("is_job",true);
                     //   intent.putExtra("Context", (Serializable) context);
                        intent.putExtra("cv",cv);
                        intent.putExtra("company",com);
                        intent.putExtra("Screen",0);
                        intent.putExtra("is_job",true);

                        context.startActivity(intent);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView company_name,job_profile,job_location,job_id,company;;
        CardView parentlayout;
        public MyViewHolder(View itemView) {
            super(itemView);
          //  company_name = itemView.findViewById(R.id.txt_company_name);
           job_profile = itemView.findViewById(R.id.txt_job_profile);
            //job_location = itemView.findViewById(R.id.txt_job_location);
            job_id=itemView.findViewById(R.id.txt_job_id);
            company=itemView.findViewById(R.id.txt_company_names);
            parentlayout = itemView.findViewById(R.id.cardview_enrolments_job);
        }
    }
}