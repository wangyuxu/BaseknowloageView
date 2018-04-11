package wyx.ad.baseknowlogeforview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import wyx.ad.baseknowlogeforview.customview.WaterWaveView;

public class MainActivity extends AppCompatActivity {

    private WaterWaveView w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        w = (WaterWaveView) this.findViewById(R.id.abc);
//        w.startAnim();
    }
}
