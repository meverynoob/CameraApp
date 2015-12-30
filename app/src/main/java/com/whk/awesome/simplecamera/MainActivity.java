package com.whk.awesome.simplecamera;
//Credits to Mike Newell for the tutorial on making this app

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import java.io.File;


public class MainActivity extends AppCompatActivity {

    private static int TAKE_PICTURE = 1;
    private Uri imageUri;
    public String log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cameraButton = (Button)findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(onCameraButtonPress);
    }

    private OnClickListener onCameraButtonPress = new OnClickListener () {
        public void onClick(View v){
            launchCamera(v);
        }
    };

    private void launchCamera(View v){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picture.jpg");
        imageUri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == Activity.RESULT_OK) {
            Uri selectedImage = imageUri;
            getContentResolver().notifyChange(selectedImage, null);

            ImageView imageView = (ImageView)findViewById(R.id.this_image_view);
            ContentResolver contentResolver = getContentResolver();
            Bitmap bitmap;

            try{
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage);
            imageView.setImageBitmap(bitmap);
            } catch(Exception exception){
                Log.e(log, exception.toString());
            }
        }
    }
}
