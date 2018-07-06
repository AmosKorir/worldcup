package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RankViewHolder extends RecyclerView.ViewHolder {
    TextView name,count;
    public RankViewHolder(View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.name);
        count=(TextView)itemView.findViewById(R.id.count);
    }
}
