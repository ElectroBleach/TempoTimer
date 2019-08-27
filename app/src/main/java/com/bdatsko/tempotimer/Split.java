package com.bdatsko.tempotimer;

public class Split {
    String seconds;
    String milliseconds;

    public String getSeconds() {
        return seconds;
    }

    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(String milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Split(String seconds, String milliseconds) {
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }
}
