package com.example.anthony.loot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ItemView extends AppCompatActivity {
    private ImageView mImageView;

    private Button mDonateButton;
    private Button mRecycleButton;
    private Button mSellButton;

    private TextView mName;
    private TextView mDescription;
    private TextView mTimer;

    private Button mEditButton;
    private Button mDeleteButton;

    private DatabaseReference mDatabaseReference;
    private String mItemKey;

    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        Intent intent = getIntent();
        mItemKey = intent.getStringExtra("ITEM_KEY");

        mDonateButton = findViewById(R.id.button_donate);
        mRecycleButton = findViewById(R.id.button_recycle);
        mSellButton = findViewById(R.id.button_sell);

        mImageView = findViewById(R.id.image_view_item);

        mName = findViewById(R.id.text_view_item_name);
        mDescription = findViewById(R.id.text_view_item_description);
        mTimer  = findViewById(R.id.text_view_card_timer);

        mEditButton = findViewById(R.id.button_edit);
        mDeleteButton = findViewById(R.id.button_delete);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");

    }


}
