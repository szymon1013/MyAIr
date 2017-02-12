package com.szymon1013.myair.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.szymon1013.myair.R;
import com.szymon1013.myair.activity.DetailActivity;
import com.szymon1013.myair.aplication.MyAirApplication;
import com.szymon1013.myair.data.StationPoint;
import com.szymon1013.myair.data.StationResponse;
import com.szymon1013.myair.databinding.FragmentMapBinding;
import com.szymon1013.myair.network.StationsInBounds;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AirStationMap extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private static final String TAG = AirStationMap.class.getClass().getSimpleName().toString();
    GoogleMap mGoogleMap;
    LatLngBounds mLastShowedPins;
    MapFragment mMapFragment;
    public final LatLngBounds POLAND = new LatLngBounds(new LatLng(49.268824, 14.557019),
            new LatLng(54.869679, 23.734941));
    private FragmentMapBinding binding;

    @Inject
    Retrofit mRetrofit;

    public AirStationMap() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            mMapFragment = new MapFragment();
            getFragmentManager().beginTransaction().add(binding.mapContainer.getId(), mMapFragment).commit();
            mMapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        ((MyAirApplication) activity.getApplication()).getNetComponent().inject(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(POLAND, 0));
        loadAndShowPins(googleMap, googleMap.getProjection().getVisibleRegion().latLngBounds);
    }


    private void loadAndShowPins(GoogleMap googleMap, LatLngBounds latLngBounds) {
        if (mLastShowedPins != null && mLastShowedPins.contains(latLngBounds.southwest) &&
                mLastShowedPins.contains(latLngBounds.northeast)) {
            return;
        }
        mLastShowedPins = latLngBounds;

        StringBuilder sb = new StringBuilder();
        sb.append(latLngBounds.southwest.latitude).
                append(",").
                append(latLngBounds.southwest.longitude).
                append(",").
                append(latLngBounds.northeast.latitude).
                append(",").
                append(latLngBounds.northeast.longitude);
        String latlng = sb.toString();
        mGoogleMap = googleMap;
        StationsInBounds stationsInBounds = mRetrofit.create(StationsInBounds.class);
        Call<StationResponse> stationCall = stationsInBounds.getStations(latlng);
        stationCall.enqueue(new Callback<StationResponse>() {
            @Override
            public void onResponse(Call<StationResponse> call, Response<StationResponse> response) {
                showPinsOnMap(response);
            }

            @Override
            public void onFailure(Call<StationResponse> call, Throwable t) {

            }
        });
    }


    private void showPinsOnMap(Response<StationResponse> response) {
        mGoogleMap.clear();
        if (response.body() != null) {
            StationResponse body = response.body();
            ArrayList<StationPoint> data = body.getData();
            for (StationPoint stationPoint : data) {
                MarkerOptions markerOptions = new MarkerOptions().position(
                        new LatLng(stationPoint.getLat(), stationPoint.getLon())
                ).title(stationPoint.getAqi());
                Marker marker = mGoogleMap.addMarker(markerOptions);
                marker.setTag(stationPoint.getUid());
                marker.getId();
                mGoogleMap.setOnMarkerClickListener(this);
            }
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.MAP_DETAIL_ID, (Integer) marker.getTag());
        if (getActivity() != null) {
            getActivity().startActivity(intent);
        }
        return false;
    }

}
