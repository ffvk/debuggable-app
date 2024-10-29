package io.ionic.starter;

import android.content.Intent;
import android.content.Context;
import android.app.Activity;
import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.JSObject;

@CapacitorPlugin(name = "OnboardingPlugin")
public class OnboardingPlugin extends Plugin {

    private static final int ONBOARDING_REQUEST_CODE = 1111;
    private PluginCall savedCall;  // Save the PluginCall for later use

    @PluginMethod
    public void triggerOnboardingSDK(PluginCall call) {
        this.savedCall = call;  // Save the call
        Context context = getContext();
        
        // Check if context is valid before creating an intent
        if (context != null) {
            Intent intent = new Intent(context, HostActivity.class); // Ensure this is recognized
            getActivity().startActivityForResult(intent, ONBOARDING_REQUEST_CODE);
        } else {
            savedCall.reject("Context is null; failed to start onboarding SDK.");
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ONBOARDING_REQUEST_CODE && savedCall != null) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                boolean status = data.getBooleanExtra("status", false);
                int response = data.getIntExtra("response", 0);
                String message = data.getStringExtra("message");

                JSObject result = new JSObject();
                result.put("status", status);
                result.put("response", response);
                result.put("message", message);
                savedCall.resolve(result);
            } else {
                savedCall.reject("Onboarding canceled or failed.");
            }
            savedCall = null;  // Clear the saved call after handling
        }
    }
}
