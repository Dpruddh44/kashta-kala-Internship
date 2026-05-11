package com.kalakashta.app.persist;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CraftAccess_Impl implements CraftAccess {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CraftRecord> __insertionAdapterOfCraftRecord;

  private final EntityDeletionOrUpdateAdapter<CraftRecord> __deletionAdapterOfCraftRecord;

  public CraftAccess_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCraftRecord = new EntityInsertionAdapter<CraftRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `crafts` (`rid`,`photoUri`,`caption`,`note`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CraftRecord entity) {
        statement.bindLong(1, entity.getRid());
        statement.bindString(2, entity.getPhotoUri());
        statement.bindString(3, entity.getCaption());
        statement.bindString(4, entity.getNote());
        statement.bindLong(5, entity.getTimestamp());
      }
    };
    this.__deletionAdapterOfCraftRecord = new EntityDeletionOrUpdateAdapter<CraftRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `crafts` WHERE `rid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CraftRecord entity) {
        statement.bindLong(1, entity.getRid());
      }
    };
  }

  @Override
  public Object save(final CraftRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCraftRecord.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object remove(final CraftRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCraftRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CraftRecord>> stream() {
    final String _sql = "SELECT * FROM crafts ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"crafts"}, new Callable<List<CraftRecord>>() {
      @Override
      @NonNull
      public List<CraftRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRid = CursorUtil.getColumnIndexOrThrow(_cursor, "rid");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<CraftRecord> _result = new ArrayList<CraftRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CraftRecord _item;
            final int _tmpRid;
            _tmpRid = _cursor.getInt(_cursorIndexOfRid);
            final String _tmpPhotoUri;
            _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            final String _tmpCaption;
            _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new CraftRecord(_tmpRid,_tmpPhotoUri,_tmpCaption,_tmpNote,_tmpTimestamp);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
