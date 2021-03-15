package com.tems.baldaonline;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dictionaryBD";

    public static final String TABLE_DICTIONARY ="dictionary";
    public static final String KEY_ID = "IID";
    public static final String KEY_WORD = "word";
    public static final String KEY_CODE = "code";
    public static final String KEY_CODE_PARENT = "code_parent";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_WCASE = "wcase";
    public static final String KEY_SOUL = "soul";
            //    DROP TABLE IF EXISTS `nouns`;
            //    CREATE TABLE IF NOT EXISTS `nouns` (
            //            `IID` int(11) NOT NULL AUTO_INCREMENT,
            //  `word` varchar(60) NOT NULL,
            //  `code` int(11) NOT NULL,
            //  `code_parent` int(11) NOT NULL,
            //  `gender` enum('муж','жен','ср','общ') DEFAULT NULL,
            //  `wcase` enum('им') DEFAULT NULL,
            //  `soul` tinyint(1) DEFAULT NULL,
            //    PRIMARY KEY (`IID`)
            //) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4159395 ;
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_DICTIONARY + "("     + KEY_ID + " integer primary key, "
                                                                + KEY_WORD + " text, "
                                                                + KEY_CODE + " integer, "
                                                                + KEY_CODE_PARENT + " integer, "
                                                                + KEY_GENDER + " text, "
                                                                + KEY_WCASE + " text, "
                                                                + KEY_SOUL + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_DICTIONARY);
        onCreate(db);
    }
}
