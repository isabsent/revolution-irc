package io.mrarm.irc;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.chaquo.python.Kwarg;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.List;

import io.mrarm.irc.config.SettingsHelper;

public class IRCApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private List<Activity> mActivities = new ArrayList<>();
    private List<PreExitCallback> mPreExitCallbacks = new ArrayList<>();
    private List<ExitCallback> mExitCallbacks = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();

        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));

//            https://chaquo.com/chaquopy/doc/6.0/java.html
            PyObject sys = Python.getInstance().getModule("sys");                               //import sys
            System.out.println("Python says " + sys.get("maxsize").toLong());                      //sys.maxsize
            System.out.println("Python says " + sys.get("version").toString());                    //sys.version
            System.out.println("Python says " + sys.callAttr("is_finalizing").toBoolean());   //sys.is_finalizing()

            PyObject zipfile = Python.getInstance().getModule("zipfile");                                         //import zipfile
            PyObject zf = zipfile.callAttr("ZipFile","example.zip");                                      //zf = zipfile.ZipFile("example.zip")
            zf.put("debug", 2);                                                                                //zf.debug = 2
            zf.get("comment");                                                                                        //zf.comment
            zf.callAttr("write", "filename.txt", new Kwarg("compress_type", zipfile.get("ZIP_STORED")));  //zf.write("filename.txt", compress_type=zipfile.ZIP_STORED);
        }

        SettingsHelper.getInstance(this);
        NotificationManager.createDefaultChannels(this);
        registerActivityLifecycleCallbacks(this);
    }

    public void addPreExitCallback(PreExitCallback c) {
        mPreExitCallbacks.add(c);
    }

    public void removePreExitCallback(PreExitCallback c) {
        mPreExitCallbacks.remove(c);
    }

    public void addExitCallback(ExitCallback c) {
        mExitCallbacks.add(c);
    }

    public void removeExitCallback(ExitCallback c) {
        mExitCallbacks.remove(c);
    }

    public boolean requestExit() {
        for (PreExitCallback exitCallback : mPreExitCallbacks) {
            if (!exitCallback.onAppPreExit())
                return false;
        }
        for (ExitCallback exitCallback : mExitCallbacks)
            exitCallback.onAppExiting();
        for (Activity activity : mActivities)
            activity.finish();
        ServerConnectionManager.destroyInstance();
        IRCService.stop(this);
        return true;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivities.add(activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mActivities.remove(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }


    public interface PreExitCallback {
        boolean onAppPreExit();
    }


    public interface ExitCallback {
        void onAppExiting();
    }

}
