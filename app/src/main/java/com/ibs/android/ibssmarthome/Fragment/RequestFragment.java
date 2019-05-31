package com.ibs.android.ibssmarthome.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ibs.android.ibssmarthome.Activity.RequestComposeActivity;
import com.ibs.android.ibssmarthome.Adapter.RequestAdapter;
import com.ibs.android.ibssmarthome.Object.RequestObject;
import com.ibs.android.ibssmarthome.R;
import com.ibs.android.ibssmarthome.Utils.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.Date;

public class RequestFragment extends Fragment implements RequestAdapter.OnItemClickListener {
    private RecyclerView    recycleRequest;
    private RequestAdapter  requestAdapter;
    private ArrayList<RequestObject> requests;
    private FloatingActionButton btAddNewRequest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        //region Control Define
        recycleRequest = view.findViewById(R.id.recycleview_request);
        btAddNewRequest = view.findViewById(R.id.floatbutton_request_addrequest);

        //ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading request data", "Please wait...", true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getContext(), R.dimen.margin_all_default);
        //endregion

        //region Control Set
        requests = GetRequest();

        requestAdapter = new RequestAdapter(getActivity(), requests);
        requestAdapter.setOnItemClickListener(RequestFragment.this);

        recycleRequest.setHasFixedSize(true);
        recycleRequest.setLayoutManager(layoutManager);
        recycleRequest.setAdapter(requestAdapter);
        recycleRequest.addItemDecoration(itemDecoration);

        //endregion

        //region Control Event
        btAddNewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RequestComposeActivity.class);
                startActivity(intent);
            }
        });
        //endregion
        return view;
    }

    private ArrayList<RequestObject> GetRequest(){
        ArrayList<RequestObject> requestObjects = new ArrayList<>();
        for (int i=0;i<10;i++){
            RequestObject requestObject = new RequestObject();
            requestObject.setRequestId("request"+i);
            Date date = new Date();
            requestObject.setRequestDate(date);
            requestObject.setRequestSubject("Request No"+i);
            requestObject.setRequestContent("Chi la request test "+i+" ma thoi!");
            if (i<4) requestObject.setStatus(0);
            if (i>=4&&i<6) requestObject.setStatus(1);
            if (i>=6&&i<10) requestObject.setStatus(2);
            requestObjects.add(requestObject);
        }

        return requestObjects;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(int position, TextView requestSubject, TextView requestDate, TextView requestContent, ImageView requestStatus) {

    }
}
