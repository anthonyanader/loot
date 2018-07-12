package com.example.anthony.loot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.GregorianCalendar;

public class PostFragment extends Fragment {

    ImageView imageLootItem;
    EditText editName;
    EditText editDescription;
    EditText editDaysToKeep;
    EditText editYearsToKeep;
    Button addButton;

    final int REQUEST_CODE_GALLERY = 999;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.post_fragment, container, false);

        imageLootItem = (ImageView) view.findViewById(R.id.imageLootItem);
        editName = (EditText) view.findViewById(R.id.editTextName);
        editDescription = (EditText) view.findViewById(R.id.editTextDescription);
        editDaysToKeep = (EditText) view.findViewById(R.id.editTextDaysToKeep);
        editYearsToKeep = (EditText) view.findViewById(R.id.editTextYearsToKeep);
        addButton = (Button) view.findViewById(R.id.addButton);

        imageLootItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, REQUEST_CODE_GALLERY);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar now = new GregorianCalendar();
                try {
                    MainActivity.dbHelper.insertData(
                            editName.getText().toString().trim(),
                            imageViewToByte(imageLootItem),
                            editDescription.getText().toString().trim(),
                            Integer.parseInt(editDaysToKeep.getText().toString()),
                            Integer.parseInt(editYearsToKeep.getText().toString()),
                            now
                    );
                    Toast.makeText(getActivity().getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    editName.setText("");
                    editDescription.setText("");
                    editDaysToKeep.setText("");
                    editYearsToKeep.setText("");
                    // change this to plus somehow
                    imageLootItem.setImageResource(R.drawable.ic_camera_alt_black_24dp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == MainActivity.RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageLootItem.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
