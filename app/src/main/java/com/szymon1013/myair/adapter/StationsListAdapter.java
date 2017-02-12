package com.szymon1013.myair.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szymon1013.myair.R;
import com.szymon1013.myair.activity.DetailActivity;
import com.szymon1013.myair.data.StationDetail;
import com.szymon1013.myair.data.StationDetailResponse;
import com.szymon1013.myair.databinding.SubscribedItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StationsListAdapter extends RecyclerView.Adapter<StationsListAdapter.ViewHolder> {

    private ArrayList<StationDetail> mResultStations;

    public StationsListAdapter(ArrayList<StationDetail> resultStations) {
        mResultStations = resultStations != null ? resultStations : new ArrayList<StationDetail>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.subscribed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StationDetail stationDetail = mResultStations.get(position);
        if (stationDetail != null)
            holder.binder.setStationDetail(stationDetail);
    }

    @Override
    public int getItemCount() {
        if (mResultStations != null)
            return mResultStations.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SubscribedItemBinding binder;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            binder = DataBindingUtil.bind(itemView);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            int id = getStationId(getAdapterPosition());
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.MAP_DETAIL_ID, id);
            context.startActivity(intent);

            Log.d("test", "onClick: " + getItemId());
        }


    }

    private int getStationId(int i) {
        return mResultStations.get(i).getIdx();
    }

    public void setData(List<StationDetailResponse> stationDetails) {
        mResultStations.clear();
        if (stationDetails != null) {
            for (StationDetailResponse stationDetail : stationDetails) {
                mResultStations.add(stationDetail.getData());
            }
        }

        // Sorting
        Collections.sort(mResultStations, new Comparator<StationDetail>() {
            @Override
            public int compare(StationDetail station1, StationDetail station2) {
                return station1.getIdx().compareTo(station1.getIdx());
            }
        });
        notifyDataSetChanged();
    }
}
