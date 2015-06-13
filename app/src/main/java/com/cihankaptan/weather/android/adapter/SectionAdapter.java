package com.cihankaptan.weather.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cihankaptan.weather.R;
import com.cihankaptan.weather.android.ui.MainActivity;

/**
 * Created by cnkaptan on 6/3/15.
 */
public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder>{
    String[] sections;
    MainActivity mainActivity;

    public SectionAdapter(String[] sections,MainActivity mainActivity) {
        this.sections = sections;
        this.mainActivity = mainActivity;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_list_item, parent, false);
        SectionViewHolder sectionViewHolder = new SectionViewHolder(view);
        return sectionViewHolder;
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return sections.length;
    }



    public class SectionViewHolder extends RecyclerView.ViewHolder{
        ImageView itemIcon;
        TextView itemText;
        public SectionViewHolder(View itemView) {
            super(itemView);
            itemIcon = (ImageView) itemView.findViewById(R.id.item_icon);
            itemText = (TextView) itemView.findViewById(R.id.item_text);
        }


        public void bind(final int position){
            if (position == 0){
                itemIcon.setImageResource(R.drawable.ic_action_sun);
            }else{
                itemIcon.setImageResource(R.drawable.ic_action_calendar);
            }
            itemText.setText(sections[position]);
            itemText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.selectItem(position);
                }
            });
        }


    }

}
