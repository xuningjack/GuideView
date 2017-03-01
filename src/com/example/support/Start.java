package com.example.support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Activity的启动器
 * @author Jack
 */
public class Start {
    
    public static final int REQUEST_CODE_INVALID = -1;
    
    public static void start(Fragment fragment, Class<?> cls) {
        start(null, fragment, cls, null, REQUEST_CODE_INVALID, 0, 0);
    }
    
    public static void start(Fragment fragment, Class<?> cls, Bundle extras) {
        start(null, fragment, cls, extras, REQUEST_CODE_INVALID, 0, 0);
    }
    
    public static void start(Fragment fragment, Class<?> cls, int req_code) {
        start(null, fragment, cls, null, req_code, 0, 0);
    }
    
    public static void start(Fragment fragment, Class<?> cls, Bundle extras, int req_code) {
        start(null, fragment, cls, extras, req_code, 0, 0);
    }
    
    public static void start(Activity activity, Class<?> cls) {
        start(activity, null, cls, null, REQUEST_CODE_INVALID, 0, 0);
    }
    
    public static void start(Activity activity, Class<?> cls, Bundle extras) {
        start(activity, null, cls, extras, REQUEST_CODE_INVALID, 0, 0);
    }
    
    public static void start(Activity activity, Class<?> cls, int req_code) {
        start(activity, null, cls, null, req_code, 0, 0);
    }
    
    public static void start(Activity activity, Class<?> cls, Bundle extras, int req_code) {
        start(activity, null, cls, extras, req_code, 0, 0);
    }
    
    public static void start(Activity activity, Class<?> cls, int enterAnim, int exitAnim) {
        start(activity, null, cls, null, REQUEST_CODE_INVALID, enterAnim, exitAnim);
    }
    
    public static void start(Activity activity, Class<?> cls, Bundle extras, int enterAnim, int exitAnim) {
        start(activity, null, cls, extras, REQUEST_CODE_INVALID, enterAnim, exitAnim);
    }
    
    public static void start(Activity activity, Class<?> cls, int req_code, int enterAnim, int exitAnim) {
        start(activity, null, cls, null, req_code, enterAnim, exitAnim);
    }
    
    public static void start(Activity activity, Class<?> cls, Bundle extras, int req_code, int enterAnim, int exitAnim) {
        start(activity, null, cls, extras, req_code, enterAnim, exitAnim);
    }
    
    public static void start(Activity activity, Fragment fragment, Class<?> cls, Bundle extras, int req_code,
            int enterAnim, int exitAnim) {
        if (null != activity) {
            Intent intent = new Intent(activity, cls);
            if (null != extras) {
                intent.putExtras(extras);
            }
            
            if (REQUEST_CODE_INVALID == req_code) {
                activity.startActivity(intent);
            } else {
                activity.startActivityForResult(intent, req_code);
            }
            
            if (0 != enterAnim || 0 != exitAnim) {
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        } else if (null != fragment) {
            activity = fragment.getActivity();
            
            if (null != activity) {
                Intent intent = new Intent(activity, cls);
                if (null != extras) {
                    intent.putExtras(extras);
                }
                if (REQUEST_CODE_INVALID == req_code) {
                    fragment.startActivity(intent);
                } else {
                    fragment.startActivityForResult(intent, req_code);
                }
                
                if (0 != enterAnim || 0 != exitAnim) {
                    activity.overridePendingTransition(enterAnim, exitAnim);
                }
            }
        }
    }
}
