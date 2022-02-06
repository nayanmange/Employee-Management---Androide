package com.example.employeeezzy;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.TextView;

public class ListAdapter2 extends BaseAdapter {

    Context context;
    ArrayList<String> ID;
    ArrayList<String> Name;
    CalendarView c;


    public ListAdapter2(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name
    )
    {

        this.context = context2;
        this.ID = id;
        this.Name = name;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return ID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.items2, null);

            holder = new Holder();

            holder.ID_TextView = (TextView) child.findViewById(R.id.textViewID);
            holder.Name_TextView = (TextView) child.findViewById(R.id.textViewNAME);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.ID_TextView.setText(ID.get(position));
        holder.Name_TextView.setText(Name.get(position));

        return child;
    }

    public class Holder {

        TextView ID_TextView;
        TextView Name_TextView;
    }

}
