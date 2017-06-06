package com.example.root.quickflashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

/*

    @author Erkki Halinen

    Written with Android Studio in summer 2017
    Icons downloaded from http://www.iconsdb.com/ and they are CC0 licensed.

 */

//Initializes the activity and sets the button listeners.
public class MainActivity extends AppCompatActivity {

    CameraManager manager;

    String[] cameras;

    boolean switchedOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchedOn = false;

        //Sets up the manager for camera services
        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        //Tries to retrieve a list of the device's cameras
        try {

            cameras = manager.getCameraIdList();

        } catch (CameraAccessException e) {

            e.printStackTrace();

        }

        //Sets up the toggle button and its listeners
        ToggleButton toggle = (ToggleButton) findViewById(R.id.theSwitch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    //Turns the torch on and switches the icon to green
                    toggleTorch();

                } else {

                    //Turns the torch off and switches the icon to black
                    toggleTorch();

                }
            }
        });

    }


    //Toggles the camera's torch on and off and switches the flashlight icon.
    public void toggleTorch() {

        try {

            for (int j = 0; j < cameras.length; j++) {

            boolean hasFlash = manager.getCameraCharacteristics(cameras[j]).get(CameraCharacteristics.FLASH_INFO_AVAILABLE);


                if (hasFlash) {

                    //Toggle torch on
                    if(!switchedOn) {

                        manager.setTorchMode(cameras[j], true);

                        switchedOn = true;

                        //Switch icon to green
                        final ImageView imageView = (ImageView) findViewById(R.id.torch);
                        imageView.setImageResource(R.drawable.flashlight);

                        //Toggle torch off
                    } else {

                        manager.setTorchMode(cameras[j], false);

                        switchedOn = false;

                        //Switch icon to black
                        final ImageView imageView = (ImageView) findViewById(R.id.torch);
                        imageView.setImageResource(R.drawable.flashlightblack);

                    }
                }
            }

            } catch(Exception e) {

                e.printStackTrace();

            }
        }

    /*public void turnFlashOff() {

        try {

            for(int i = 0; i < cameras.length; i++) {

                boolean hasFlash = manager.getCameraCharacteristics(cameras[i]).get(CameraCharacteristics.FLASH_INFO_AVAILABLE);

                if(hasFlash) {

                    manager.setTorchMode(cameras[i], false);

                }
            }


        } catch (Exception e) {

            e.printStackTrace();

        }
    } */
}
