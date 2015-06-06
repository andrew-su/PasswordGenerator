package io.github.andrew_su.passwordgenerator2;

import android.app.Application;

import com.parse.Parse;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "5ZfGLryLV1cPxd5Mc9OjeHw1diSVIJgCorWCAwqA", "90XR8dI6dGu2cmJ7VluRbUWGkg4LTfFA3XqFgcH3");
    }
}
