package com.blazedemo.drivers;

public enum Browser {
    CHROME {
        @Override
        public AbstractDriver getDriverFactory() {
            return new ChromeFactory();
        }
    },
    EDGE {
        @Override
        public AbstractDriver getDriverFactory() {
            return new EdgeFactory();
        }
    },
    FIREFORX {
        @Override
        public AbstractDriver getDriverFactory() {
            return new FireFoxFactory();
        }
    };


    public abstract AbstractDriver getDriverFactory();
}
