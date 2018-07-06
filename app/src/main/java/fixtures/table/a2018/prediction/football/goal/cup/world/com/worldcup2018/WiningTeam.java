package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class WiningTeam extends AppCompatActivity {
    ListView listView;
    ArrayAdapter adapter;
    String [] countrie;
    SharedPreferences sharedPreferences;
    Boolean lock=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wining_team);
        listView=(ListView)findViewById(R.id.teamlist);
        countrie=getResources().getStringArray(R.array.countries);
        Arrays.sort(countrie);

        adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, countrie);

        sharedPreferences=getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                String country=String.valueOf(adapter.getItem(position));
                editor.putString("myteam", country);
                editor.commit();



                Toast toast = Toast.makeText(getApplicationContext(), "You chose to support: "+ country,
                        Toast.LENGTH_SHORT);

                toast.show();
                finish();



                support(country);
                startMain();
            }
        });

    }

    public void startMain(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WiningTeam.this,mainer.class));
            }
        };
        Handler handler=new Handler();
        handler.postDelayed(runnable,200);
    }

    //update firebase support

    public void support(String team){
        FirebaseDatabase SupportDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference newRef=SupportDatabase.getReference().child("support").child(team);
        newRef.child("name").setValue(team);
//        newRef.setValue(0);

        final DatabaseReference nnewRef=newRef.child("count");

        nnewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int number=0;
                String current="0";
                try {
                    current= dataSnapshot.getValue().toString();
                }catch (Exception e){

                }

                 number= Integer.parseInt(current);
                 if(lock){
                     number+=1;
                    nnewRef.setValue(number);
                     lock=false;
                 }
                 try {

                 }
                 catch (Exception e){

                 }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
