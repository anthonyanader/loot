package com.example.anthony.loot;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference mDatabaseReference;
    private FirebaseStorage mStorage;

    private List<Upload> mUploads;
    private EditText mEditTextSearchBar;
    private ViewFlipper mViewFlipper;

    private ImageView mImageView;

    private Button mDonateButton;
    private Button mRecycleButton;
    private Button mSellButton;

    private TextView mName;
    private TextView mDescription;
    private TextView mTimer;

    private Button mEditButton;
    private Button mDeleteButton;


    private boolean mDataLoaded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mDataLoaded = false;

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mEditTextSearchBar = view.findViewById(R.id.edit_text_search_bar);
        mViewFlipper = view.findViewById(R.id.view_flipper);

        mDonateButton = view.findViewById(R.id.button_donate);
        mRecycleButton = view.findViewById(R.id.button_recycle);
        mSellButton = view.findViewById(R.id.button_sell);

        mImageView = view.findViewById(R.id.image_view_item);

        mName = view.findViewById(R.id.text_view_item_name);
        mDescription = view.findViewById(R.id.text_view_item_description);
        mTimer  = view.findViewById(R.id.text_view_item_timer);

        mEditButton = view.findViewById(R.id.button_edit);
        mDeleteButton = view.findViewById(R.id.button_delete);

        buildRecyclerView();

        mUploads = new ArrayList<>();

        mStorage = FirebaseStorage.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setmKey(dataSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(getContext(), mUploads);
                mRecyclerView.setAdapter(mAdapter);
                sortRecyclerView();
                loadSearchFunctionality();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.setText("365 days left");
                Toast.makeText(getActivity(), "Timer Reset", Toast.LENGTH_SHORT).show();

            }
        });
        mRecycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedButton("https://www.terracycle.ca/en-CA/");

            }
        });

        mDonateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedButton("https://www.1800gotjunk.com/ca_en/locations/junk-removal-ottawa");
            }
        });

        mSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedButton("https://www.kijiji.ca/h-ottawa/1700185");
            }
        });


        view.clearFocus();
        return view;
    }

    private void buildRecyclerView(){
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    private void filter(String text){
        ArrayList<Upload> filteredList = new ArrayList<>();

        for(Upload item : mUploads){
            if(item.getmName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    private void sortRecyclerView(){
        Collections.sort(mUploads, new Comparator<Upload>() {
            @Override
            public int compare(Upload o1, Upload o2) {
                return o1.getmTimer().compareTo(o2.getmTimer());
            }
        });

        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Upload selectedItem = mUploads.get(position);

        mViewFlipper.showNext();
        populateSelectedItemView(selectedItem);

    }

    private void populateSelectedItemView(Upload selectedItem) {
        Picasso.get()
                .load(selectedItem.getmImageUrl())
                .fit()
                .centerCrop()
                .into(mImageView);
        mName.setText(selectedItem.getmName());
        mDescription.setText(selectedItem.getmDescription());
        mTimer.setText(selectedItem.getmTimer() + " days left");
    }

    private void loadSearchFunctionality(){
        if(!mDataLoaded) {
            mDataLoaded = true;
            mEditTextSearchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filter(s.toString());
                }
            });
        }
    }

    public void setViewFlipper(int position){
        if (mViewFlipper!=null){
            mViewFlipper.setDisplayedChild(position);
        }

    }

    private void clickedButton(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
