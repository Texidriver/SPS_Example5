package com.example.example5;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Smart Phone Sensing Example 5. Object movement on canvas.
 */
public class MainActivity extends Activity implements OnClickListener, SensorEventListener {

    /**
     * The buttons.
     */
    private Button up, left, right, down;
    /**
     * The text view.
     */
    private TextView textView;
    /**
     * The shape.
     */
    private ShapeDrawable drawable;
    /**
     * The canvas.
     */
    private Canvas canvas;

    private SensorManager mSensorManager;
    private Sensor mSensorStepDetect;
    private Sensor mSensorAccelerometer;
    private Sensor mSensorMagnetometer;
    private float[] mAccelerometerData = new float[3];
    private float[] mMagnetometerData = new float[3];
    private int mDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the buttons
        up = (Button) findViewById(R.id.button1);
        left = (Button) findViewById(R.id.button2);
        right = (Button) findViewById(R.id.button3);
        down = (Button) findViewById(R.id.button4);

        // set the text view
        textView = (TextView) findViewById(R.id.textView1);

        // set listeners
        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        // get the screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        // create a drawable object
        drawable = new ShapeDrawable(new OvalShape());
        drawable.getPaint().setColor(Color.BLUE);
        drawable.setBounds(width / 2 - 20, height / 2 - 20, width / 2 + 20, height / 2 + 20);

        // create a canvas
        ImageView canvasView = (ImageView) findViewById(R.id.canvas);
        Bitmap blankBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(blankBitmap);
        canvasView.setImageBitmap(blankBitmap);

        // draw the object
        drawable.draw(canvas);

        mSensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorStepDetect = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        mSensorAccelerometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorMagnetometer = mSensorManager.getDefaultSensor(
                Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // This happens when you click any of the four buttons.
        // For each of the buttons, when it is clicked we change:
        // - The text in the center of the buttons
        // - The margins
        // - The text that shows the margin
        switch (v.getId()) {
            // UP BUTTON
            case R.id.button1: {
                Toast.makeText(getApplication(), "UP", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left, r.top - 20, r.right, r.bottom - 20);
                textView.setText("\n\tMove Up" + "\n\tTop Margin = "
                        + drawable.getBounds().top);
                break;
            }
            // DOWN BUTTON
            case R.id.button4: {
                Toast.makeText(getApplication(), "DOWN", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left, r.top + 20, r.right, r.bottom + 20);
                textView.setText("\n\tMove Down" + "\n\tTop Margin = "
                        + drawable.getBounds().top);
                break;
            }
            // LEFT BUTTON
            case R.id.button2: {
                Toast.makeText(getApplication(), "LEFT", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left - 20, r.top, r.right - 20, r.bottom);
                textView.setText("\n\tMove Left" + "\n\tLeft Margin = "
                        + drawable.getBounds().left);
                break;
            }
            // RIGHT BUTTON
            case R.id.button3: {
                Toast.makeText(getApplication(), "RIGHT", Toast.LENGTH_SHORT).show();
                Rect r = drawable.getBounds();
                drawable.setBounds(r.left + 20, r.top, r.right + 20, r.bottom);
                textView.setText("\n\tMove Right" + "\n\tLeft Margin = "
                        + drawable.getBounds().left);
                break;
            }
        }

        // redrawing of the object
        canvas.drawColor(Color.WHITE);
        drawable.draw(canvas);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();

        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                mAccelerometerData = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mMagnetometerData = event.values.clone();
                break;
            default:
                // do nothing
                break;
        }

        if (sensorType == Sensor.TYPE_STEP_DETECTOR) {
            switch (mDirection) {
                // UP
                case 0: {
                    Toast.makeText(getApplication(), "UP", Toast.LENGTH_SHORT).show();
                    Rect r = drawable.getBounds();
                    drawable.setBounds(r.left, r.top - 20, r.right, r.bottom - 20);
                    textView.setText("\n\tMove Up" + "\n\tTop Margin = "
                            + drawable.getBounds().top);
                    break;
                }
                // DOWN
                case 1: {
                    Toast.makeText(getApplication(), "DOWN", Toast.LENGTH_SHORT).show();
                    Rect r = drawable.getBounds();
                    drawable.setBounds(r.left, r.top + 20, r.right, r.bottom + 20);
                    textView.setText("\n\tMove Down" + "\n\tTop Margin = "
                            + drawable.getBounds().top);
                    break;
                }
                // LEFT
                case 2: {
                    Toast.makeText(getApplication(), "LEFT", Toast.LENGTH_SHORT).show();
                    Rect r = drawable.getBounds();
                    drawable.setBounds(r.left - 20, r.top, r.right - 20, r.bottom);
                    textView.setText("\n\tMove Left" + "\n\tLeft Margin = "
                            + drawable.getBounds().left);
                    break;
                }
                // RIGHT
                case 3: {
                    Toast.makeText(getApplication(), "RIGHT", Toast.LENGTH_SHORT).show();
                    Rect r = drawable.getBounds();
                    drawable.setBounds(r.left + 20, r.top, r.right + 20, r.bottom);
                    textView.setText("\n\tMove Right" + "\n\tLeft Margin = "
                            + drawable.getBounds().left);
                    break;
                }
                default:
                    break;
            }
            // redrawing of the object
            canvas.drawColor(Color.WHITE);
            drawable.draw(canvas);
        } else {
            float[] rotationMatrix = new float[9];
            boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix,
                    null, mAccelerometerData, mMagnetometerData);
            float[] orientationValues = new float[3];
            if (rotationOK) {
                SensorManager.getOrientation(rotationMatrix, orientationValues);
            }
            float azimuth = orientationValues[0];
            if (azimuth < (-Math.PI / 4 * 3)) {
                mDirection = 1;  // down
            } else if (azimuth < (-Math.PI / 4)) {
                mDirection = 2;  // left
            } else if (azimuth < (Math.PI / 4)) {
                mDirection = 0;  // up
            } else if (azimuth < (Math.PI / 4 * 3)) {
                mDirection = 3;  // right
            } else {
                mDirection = 1;  // down
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorStepDetect != null) {
            mSensorManager.registerListener(this, mSensorStepDetect,
                    SensorManager.SENSOR_DELAY_FASTEST);
        }
        if (mSensorAccelerometer != null) {
            mSensorManager.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorMagnetometer != null) {
            mSensorManager.registerListener(this, mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
}
