import android.app.Activity;
import android.widget.Spinner;

import com.bdatsko.tempotimer.R;
import com.bdatsko.tempotimer.tt;

public class raceSelection extends Activity {
    Spinner course = (Spinner) findViewById(R.id.course);
    String courseOutput = course.getSelectedItem().toString();

    public String getCourseOutput() {
        return this.courseOutput;
    }

}
