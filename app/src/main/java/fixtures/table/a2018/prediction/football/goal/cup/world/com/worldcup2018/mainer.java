package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mainer extends AppCompatActivity {
    BiggerAdapter adapter;
    ArrayList <ModelRank> arrayList;
    String [] group;
    Button ranking,prediction,changteam;
    static android.widget.ProgressBar mpogress;
    static  ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainer);
        group=getResources().getStringArray(R.array.group);
        arrayList= new ArrayList<ModelRank>();
        listView=(ListView)findViewById(R.id.biggerlist);
        listView.setVisibility(View.INVISIBLE);
        final TextView textView=(TextView)findViewById(R.id.count);
        TextView teamname=(TextView)findViewById(R.id.teamname);

        mpogress=(android.widget.ProgressBar)findViewById(R.id.loader);

        ranking=(Button)findViewById(R.id.ranking);
        prediction=(Button)findViewById(R.id.prediction);
        changteam=(Button)findViewById(R.id.changeteam);

        SharedPreferences sharedPreferences=getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
        String myteam=sharedPreferences.getString("myteam","");
        teamname.setText("You support "+myteam);
        FirebaseDatabase mDatabase= FirebaseDatabase.getInstance();

        DatabaseReference supportref=mDatabase.getReference().child("support").child(myteam).child("count");


        supportref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String num=dataSnapshot.getValue().toString();
                textView.setText(num);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        changteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainer.this,WiningTeam.class));
                finish();
            }
        });
        prediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainer.this,ScrollingActivity.class));
            }
        });

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainer.this,Ranking.class));
            }
        });


        for(int i=0;i<group.length;i++){
            ModelRank model=new ModelRank();
            model.setName(group[i]);
            arrayList.add(model);
        }

        adapter=new BiggerAdapter(this, R.layout.biglist,arrayList);
        listView.setAdapter(adapter);




    }
}
