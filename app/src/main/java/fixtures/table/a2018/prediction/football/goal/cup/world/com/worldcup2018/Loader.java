package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class Loader extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        File file= Environment.getExternalStoragePublicDirectory(".status");
    }
}
