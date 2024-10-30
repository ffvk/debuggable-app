package io.ionic.starter;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;
// import io.ionic.starter.OnboardingPlugin; // Ensure this import is correct

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPlugin(OnboardingPlugin.class); // Make sure the class is registered here
    }
}
