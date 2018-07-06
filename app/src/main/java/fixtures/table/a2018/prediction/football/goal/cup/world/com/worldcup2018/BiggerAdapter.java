package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018.mainer.*;

import java.util.ArrayList;

import static android.view.View.GONE;
import static fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018.mainer.listView;
import static fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018.mainer.mpogress;

public class BiggerAdapter extends ArrayAdapter<ModelRank> {
    ArrayList<ModelRank> arrayList = new ArrayList<ModelRank>();
    private Context mContext;

    public BiggerAdapter(Context context, int resource, ArrayList<ModelRank> objects) {
        super(context, resource, objects);
        arrayList = objects;
        mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.biglist,parent,false);

        ModelRank rank = arrayList.get(position);
        TextView header=(TextView)listItem.findViewById(R.id.title);
        final RecyclerView recyclerView=(RecyclerView)listItem.findViewById(R.id.inner);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ModelRank model=arrayList.get(position);
        String name=model.getName();
        header.setText(name);
         FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();
         DatabaseReference mRef=mDatabase.getReference().child("grouper").child(name);
         mRef.keepSynced(true);

      final FirebaseRecyclerAdapter  adapter= new FirebaseRecyclerAdapter<Model, ViewHolder>(
                Model.class,
                R.layout.mainerow,
                ViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(final ViewHolder viewHolder, Model model, final int position) {

                viewHolder.teams.setText("Countries: "+model.getTeams());
                viewHolder.stadium.setText(" Stadium: "+model.getStadium());
                viewHolder.time.setText("Time: "+model.getTime());
                viewHolder.date.setText("Date: "+model.getDate());






            }
        };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(mpogress.getVisibility()== View.VISIBLE) {
                    mpogress.setVisibility(GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return listItem;
    }
}