package com.cihankaptan.weather.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cihankaptan.weather.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cnkaptan on 6/3/15.
 */
public class SectionAdapter extends BaseAdapter {
    String[] sections;
    @InjectView(R.id.item_icon)
    ImageView itemIcon;
    @InjectView(R.id.item_text)
    TextView itemText;


    public SectionAdapter(String[] sections) {
        this.sections = sections;
    }

    @Override
    public int getCount() {
        return sections.length;
    }

    @Override
    public String getItem(int position) {
        return sections[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, null, false);
        ButterKnife.inject(this,view);

        if (position == 0){
            itemIcon.setImageResource(R.drawable.ic_action_sun);
        }else{
            itemIcon.setImageResource(R.drawable.ic_action_calendar);
        }
        itemText.setText(getItem(position));

        return view;
    }

}
