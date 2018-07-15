package com.common.app.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author: zhengjr
 * @since: 2018/6/21
 * @describe:
 */

public class MyDbHelper extends DaoMaster.OpenHelper {

    private static final String DBNAME = "DEMO.db";

    public MyDbHelper(Context context) {
        super(context, DBNAME);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
