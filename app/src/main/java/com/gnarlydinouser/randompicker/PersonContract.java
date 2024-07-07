package com.gnarlydinouser.randompicker;

import android.provider.BaseColumns;

public final class PersonContract {
    private PersonContract() {}

    public static class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "person";
        public static final String COLUMN_NAME = "name";
    }
}
