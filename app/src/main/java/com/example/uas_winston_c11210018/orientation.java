package com.example.uas_winston_c11210018;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

public class orientation  implements SensorEventListener {
    private SensorManager manager;
    private Sensor accelerometer;
    private Sensor magnometer;

    private float[] acceloutput;
    private float[] magoutput;

    private float[] orientation = new float[3];
    public float[] getOrientation(){
        return orientation;
    }

    private  float[] startorientation = null;
    public float[] getStartorientation(){
        return startorientation;
    }

    public void newgame(){
        startorientation = null;

    }
    public orientation(){
        manager = (SensorManager) constant.currentcontext.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register(){
        manager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this,magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    public void pause(){
        manager.unregisterListener(this);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            acceloutput = sensorEvent.values;
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magoutput = sensorEvent.values;
        }
    if (acceloutput != null && magoutput != null){
        float[] R = new float[9];
        float[] I = new float[9];
        boolean sucsess = SensorManager.getRotationMatrix(R,I,acceloutput,magoutput);
        if (sucsess){
            SensorManager.getOrientation(R,orientation);
            if (startorientation == null){
                startorientation = new float[orientation.length];
                System.arraycopy(orientation,0,startorientation,0,orientation.length);

            }
        }

    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
