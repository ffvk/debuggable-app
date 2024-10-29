package io.ionic.starter;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

public class HostActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Your onboarding initialization logic here
        
        // Example: return a result when onboarding is done
        Intent resultIntent = new Intent();
        resultIntent.putExtra("status", true);
        resultIntent.putExtra("response", 1); // Example response
        resultIntent.putExtra("message", "Onboarding completed successfully");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
