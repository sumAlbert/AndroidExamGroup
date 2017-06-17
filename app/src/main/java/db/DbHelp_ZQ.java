package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by dell2 on 2017/6/17.
 */

public class DbHelp_ZQ extends SQLiteOpenHelper {
    public static final String CREATE_NAME="create table name ("
            +"id integer primary key autoincrement,"
            +"name text)";
    private Context context;
    public DbHelp_ZQ(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NAME);
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
