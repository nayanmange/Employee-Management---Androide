package com.example.employeeezzy;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> ID;
    ArrayList<String> Name;
    ArrayList<String> PhoneNumber;
    ArrayList<String> Div;
    CalendarView c;


    public ListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> name,
            ArrayList<String> phone,
            ArrayList<String> div

    )
    {

        this.context = context2;
        this.ID = id;
        this.Name = name;
        this.PhoneNumber = phone;
        this.Div=div;
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

            child = layoutInflater.inflate(R.layout.items, null);

            holder = new Holder();

            holder.ID_TextView = (TextView) child.findViewById(R.id.textViewID);
            holder.Name_TextView = (TextView) child.findViewById(R.id.textViewNAME);
            holder.PhoneNumberTextView = (TextView) child.findViewById(R.id.textViewPHONE_NUMBER);
            holder.d=(TextView) child.findViewById(R.id.textView_div);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.ID_TextView.setText(ID.get(position));
        holder.Name_TextView.setText(Name.get(position));
        holder.PhoneNumberTextView.setText(PhoneNumber.get(position));
        holder.d.setText(Div.get(position));

        return child;
    }

    public class Holder {

        public TextView d;
        TextView ID_TextView;
        TextView Name_TextView;
        TextView PhoneNumberTextView;
    }

}
