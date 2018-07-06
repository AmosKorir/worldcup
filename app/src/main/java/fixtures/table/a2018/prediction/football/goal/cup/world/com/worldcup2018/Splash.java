package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    String myteam;
    ProgressBar mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences=getSharedPreferences("FAVOURITE", Context.MODE_PRIVATE);
        myteam=sharedPreferences.getString("myteam","");
        mprogress=(ProgressBar)findViewById(R.id.b);
        mprogress.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN );

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if (myteam.isEmpty()){
                    startActivity(new Intent(Splash.this, Intro.class));
                    finish();
                }else {
                    startActivity(new Intent(Splash.this, mainer.class));
                    finish();
                }
            }
        };
        Handler handler=new Handler();
        handler.postDelayed(runnable,3000);
    }
}
