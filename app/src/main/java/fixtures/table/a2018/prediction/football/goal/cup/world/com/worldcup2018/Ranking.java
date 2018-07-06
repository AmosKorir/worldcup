package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Ranking extends AppCompatActivity {
    ListView rankList;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    ArrayList<ModelRank> arrayList;
   CustomAdapter adapter;

    AdView mAdView;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.inter));

        interstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }

        rankList=(ListView)findViewById(R.id.rankinglist);
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("support");
        mRef.keepSynced(true);

          arrayList=new ArrayList<>();

        adapter=new CustomAdapter(Ranking.this,R.layout.rankitem,arrayList);







        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  recyclerView.setAdapter(adapter);
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelRank rank=new ModelRank();
                    try {
                        String name = snapshot.getKey();
                        rank.setName(name);

                        String namer = snapshot.child("count").getValue().toString();
                        rank.setCount(namer);

                        arrayList.add(rank);
                    }catch (Exception e){

                    }
                }
                Collections.sort(
                       arrayList,new compareCount(true));
                adapter.notifyDataSetChanged();

                rankList.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "World cup 2018", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }

            }
        });
    }

    class compareCount implements Comparator<ModelRank> {
        private int mod=1;
        public compareCount(boolean desc) {
            if (desc)
                mod = -1;
        }

        @Override
        public int compare(ModelRank o1, ModelRank o2) {
            int a= Integer.parseInt(o1.count);
            int b=Integer.parseInt(o2.count);

            return mod*o1.count.compareTo(o2.count);
        }


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }

    }

}
