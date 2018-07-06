package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScrollingActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef,supportref;

    FirebaseRecyclerAdapter <Model,ViewHolder> adapter;
    RecyclerView recyclerView;
    Dialog dialog,lockidialog;
    SharedPreferences sharedPreferences;

    String myteam;

    //working with the dialog
    TextView teamName;
    TextView supporters;
    String numSupporters;
    AdView mAdView;
    InterstitialAd interstitialAd;
    Button unlock,lock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        recyclerView=(RecyclerView)findViewById(R.id.teamlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lockidialog=new Dialog(this);
        lockidialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lockidialog.setContentView(R.layout.lockingdialog);
        lock=(Button)lockidialog.findViewById(R.id.lock);
        unlock=(Button)lockidialog.findViewById(R.id.unlock);

// wworking with ads
        MobileAds.initialize(this,
                "ca-app-pub-7788938439760564~2763162893");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.inter));

        interstitialAd.loadAd(new AdRequest.Builder().build());

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //working with dialog view

        teamName=(TextView)dialog.findViewById(R.id.teamname);
        supporters=(TextView)dialog.findViewById(R.id.support);
        //end of dialog
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("games");
        mRef.keepSynced(true);



        sharedPreferences=getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
        myteam=sharedPreferences.getString("myteam","");

        supportref=mDatabase.getReference().child("support").child(myteam).child("count");
        supportref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    numSupporters = dataSnapshot.getValue().toString();
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter= new FirebaseRecyclerAdapter<Model, ViewHolder>(
                Model.class,
                R.layout.itemrow,
                ViewHolder.class,
                mRef
        ) {
            @Override
            protected void populateViewHolder(final ViewHolder viewHolder, Model model, final int position) {

                viewHolder.teams.setText("Countries: "+model.getTeams());
                viewHolder.stadium.setText(" Stadium: "+model.getStadium());
                viewHolder.time.setText("Time: "+model.getTime());
                viewHolder.date.setText("Date: "+model.getDate());


                sharedPreferences=getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
                String winn=sharedPreferences.getString(position+"","");
                if (winn.equals("")){

                }else{
                    switch (winn){
                        case "1":
                            viewHolder.home.setBackgroundResource(R.drawable.clicked);
                            break;
                        case "2":
                            viewHolder.draw.setBackgroundResource(R.drawable.clicked);
                            break;
                        case "3":
                            viewHolder.away.setBackgroundResource(R.drawable.clicked);
                            break;
                    }
                }




                viewHolder.home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPreferences = getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
                        String test = sharedPreferences.getString("lock", "");
                        if (test.equals("0")) {

                            Toast.makeText(getApplicationContext(),"Please unlock your Prediction",Toast.LENGTH_LONG).show();
                        }
                    else{
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(position + "", "1");
                            editor.commit();
                            viewHolder.away.setBackgroundResource(R.drawable.shape);
                            viewHolder.home.setBackgroundResource(R.drawable.clicked);
                            viewHolder.draw.setBackgroundResource(R.drawable.shape);


                    }}
                });
                viewHolder.away.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPreferences = getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
                        String test = sharedPreferences.getString("lock", "");
                        if (test.equals("0")) {

                            Toast.makeText(getApplicationContext(),"Please unlock your Prediction",Toast.LENGTH_LONG).show();


                        } else{
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(position + "", "3");
                            editor.commit();
                            viewHolder.away.setBackgroundResource(R.drawable.clicked);
                            viewHolder.home.setBackgroundResource(R.drawable.shape);
                            viewHolder.draw.setBackgroundResource(R.drawable.shape);

                            }}
                });


                viewHolder.draw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sharedPreferences = getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
                        String test = sharedPreferences.getString("lock", "");
                        if (test.equals("0")) {
                            Toast.makeText(getApplicationContext(),"Please unlock your Prediction",Toast.LENGTH_LONG).show();

                    }  else{
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(position+"", "2");
                            editor.commit();
                            viewHolder.away.setBackgroundResource(R.drawable.shape);
                            viewHolder.home.setBackgroundResource(R.drawable.shape);
                            viewHolder.draw.setBackgroundResource(R.drawable.clicked);

                    }}
                });
            }
        };
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putString("lock", "0");
                editor.commit();
                lock.setBackgroundResource(R.drawable.shape);
                lock.setTextColor(Color.BLACK);
                unlock.setBackgroundResource(R.drawable.clicked);
                lockidialog.dismiss();
            }
        });
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();

                editor.putString("lock", "1");
                editor.commit();
                unlock.setBackgroundResource(R.drawable.shape);
                unlock.setTextColor(Color.BLACK);
                lock.setBackgroundResource(R.drawable.clicked);
                lockidialog.dismiss();
            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
                teamName.setText("You Support: "+ myteam);
                supporters.setText("Number of supporter: "+numSupporters);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(ScrollingActivity.this, Intro.class));
            return true;
        }
        if (id == R.id.locking) {
           lockidialog.show();
            return true;
        }
        if (id == R.id.changeteams) {
            startActivity(new Intent(ScrollingActivity.this,WiningTeam.class));
            return true;
        }
        if (id == R.id.ranking) {
            startActivity(new Intent(ScrollingActivity.this,Ranking.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }

    }
}
