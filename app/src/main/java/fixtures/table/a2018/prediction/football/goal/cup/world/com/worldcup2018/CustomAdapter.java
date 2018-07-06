package fixtures.table.a2018.prediction.football.goal.cup.world.com.worldcup2018;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ModelRank> {
    ArrayList<ModelRank>arrayList=new ArrayList<ModelRank>();
    private Context mContext;

    public CustomAdapter( Context context, int resource, ArrayList<ModelRank> objects) {
        super(context, resource, objects);
        arrayList=objects;
        mContext=context;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.rankitem,parent,false);

        ModelRank rank = arrayList.get(position);


        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(rank.getName());
        TextView count = (TextView) listItem.findViewById(R.id.count);
        count.setText(rank.getCount());



        return listItem;
    }
}
