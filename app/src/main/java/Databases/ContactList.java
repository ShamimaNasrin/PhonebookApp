package Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ContactList extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "User_info";
    private static final String TABLE_NAME = "Contactlist";
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String PHONE = "Phone";
    private static final String EMAIL = "Email";
    private static final int DATABASE_VERSION_NO = 3;
    private Context context;

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
            ""+NAME+" VARCHAR(255)," +
            ""+PHONE+" INTEGER," +
            ""+EMAIL+" VARCHAR(30));";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public ContactList(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION_NO);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            Toast.makeText(context, "onCreate successful", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }catch (Exception e){
            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            Toast.makeText(context, "onUpgrade is called", Toast.LENGTH_SHORT).show();
            sqLiteDatabase.execSQL(DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }catch (Exception e){
            Toast.makeText(context, "Exception", Toast.LENGTH_SHORT).show();
        }
    }

    public Long InsertContactData(String name,String phone,String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(PHONE,phone);
        contentValues.put(EMAIL,email);

        long rowID = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return rowID;
    }

    public Cursor showContact(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

    public Integer deleteMethod(String names){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,NAME+" = ?",new String[]{names});
    }

    public Cursor fetchData(String name){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+NAME+"= ?",new String[]{name});
        return cursor;
    }
    public Boolean updateMethod(String name,String phone,String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(PHONE,phone);
        contentValues.put(EMAIL,email);

        sqLiteDatabase.update(TABLE_NAME,contentValues,NAME+"=?",new String[]{name});
        return true;
    }

    public Boolean CheckNameMethod(String Name){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        Boolean cursorresult = false;
        if (cursor.getCount()==0){
            Toast.makeText(context, "Null database", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                String c_name = cursor.getString(1);

                if (c_name.equals(Name)){
                    cursorresult = true;
                    break;
                }
            }
        }
        return cursorresult;
    }
}
