package android.bignerdranch.com.androidlab41;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateRepository {
    private static DateRepository instance;
    DBHelper mDBHelper;
    SQLiteDatabase mDatabase;
    SimpleDateFormat mDateFormat;

    private DateRepository(Context context)
    {
        mDBHelper = new DBHelper(context);
        mDatabase =mDBHelper.getWritableDatabase();
        mDateFormat = new SimpleDateFormat("dd, MM, yyyy hh");
    }

    public static DateRepository getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DateRepository(context);
        }
        return instance;
    }

public void insertDate(Date date)
{
    mDatabase.delete("INFO", null, null);
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 9);
    date = calendar.getTime();
    ContentValues values = new ContentValues();
    values.put("DATE", mDateFormat.format(date));
    mDatabase.insert("INFO", null, values);
}

public Date getDate()
{
    Cursor cursor = mDatabase.query("INFO", null, null, null, null, null, null);
    if(cursor.moveToFirst())
    {
        int dateColumn = cursor.getColumnIndex("DATE");
        String res = cursor.getString(dateColumn);
        try
        {
            return mDateFormat.parse(res);
        }catch(ParseException pe)
        {
            pe.printStackTrace();
            return null;
        }
    }
    return null;
}

public static DateRepository getInstance()
    {
        return instance;
    }
}
