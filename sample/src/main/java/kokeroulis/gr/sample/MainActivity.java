package kokeroulis.gr.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import kokeroulis.gr.segmentedcontrol.SegmentedButton;
import kokeroulis.gr.segmentedcontrol.SegmentedControl;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SegmentedControl control = (SegmentedControl) findViewById(R.id.segmentedControl);
        control.setEntries(Arrays.asList("foo", "bar", "zzz"));

        SegmentedButton sb = control.findButtonById(1);
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked foo", Toast.LENGTH_SHORT).show();
            }
        });

        SegmentedButton sb2 = control.findButtonBySlug("BAR");
        sb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked bar", Toast.LENGTH_SHORT).show();
            }
        });

        mSubscription = control.selectionChanged()
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer buttonId) {
                    SegmentedButton sb = control.findButtonById(buttonId);
                    final String msg = "Rx button clicked " + sb.getText().toString();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSubscription.unsubscribe();
    }
}
