<h1>Accelerometer Sensor</h1>

<h2>XML Layout</h2>

```xml
    <TextView
        android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```

<h2>Java</h2>

```java
public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private TextView tv;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, sensor, sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        here the event.values will provide you with the data
//        index 0 for x axis, 1 for y axis and 2 for z axis
        tv.setText("X : "+ event.values[0] +"\nY : " + event.values[1]+"\nZ : "+event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
```

<h2>***Note***</h2>
implement <i>SensorEventListener</i> to the class where program for sensor is written
