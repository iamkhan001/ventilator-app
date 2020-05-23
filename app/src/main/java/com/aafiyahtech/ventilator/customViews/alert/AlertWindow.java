package com.aafiyahtech.ventilator.customViews.alert;

import android.content.Context;

public class AlertWindow {

    private Context context;
    private DialogListener listener;
    private int icon;
    private int color;
    private String title, message, positiveButtonText, negativeButtonText;

    private boolean cancelable;

    public AlertWindow(Context context) {
        this.context = context;
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }


    public void show() {

    }


}
