package mo.hyit.lesson.higher;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import mo.hyit.lesson.others.SQLHelper;

public class TestContentProvider extends ContentProvider {
    private static UriMatcher uriMatcher = new UriMatcher(-1);
    static {
        uriMatcher.addURI("mo.hyit.lesson.entity.student","query",1);
        uriMatcher.addURI("mo.hyit.lesson.entity.student","insert",2);
        uriMatcher.addURI("mo.hyit.lesson.entity.student","delete",3);
        uriMatcher.addURI("mo.hyit.lesson.entity.student","update",4);
    }
    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @NonNull String[] projection, @NonNull String selection, @NonNull String[] selectionArgs, @NonNull String sortOrder) {
        return null;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if(uriMatcher.match(uri) == 2){
            SQLHelper hp = new SQLHelper(getContext(),"sjk.db",null,1);
            SQLiteDatabase db = hp.getWritableDatabase();
            db.insert("Logininfo",null,values);
            db.close();
            hp.close();
        }else{
            throw new RuntimeException("uri不匹配");
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @NonNull String selection, @NonNull String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @NonNull ContentValues values, @NonNull String selection, @NonNull String[] selectionArgs) {
        return 0;
    }
}
