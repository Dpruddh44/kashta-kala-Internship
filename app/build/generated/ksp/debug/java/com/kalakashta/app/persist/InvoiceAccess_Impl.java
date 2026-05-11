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
public final class InvoiceAccess_Impl implements InvoiceAccess {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<InvoiceRecord> __insertionAdapterOfInvoiceRecord;

  private final EntityDeletionOrUpdateAdapter<InvoiceRecord> __deletionAdapterOfInvoiceRecord;

  public InvoiceAccess_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInvoiceRecord = new EntityInsertionAdapter<InvoiceRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `invoices` (`rid`,`clientName`,`itemLabel`,`timber`,`spec`,`sqft`,`materialTotal`,`labourTotal`,`margin`,`grandTotal`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InvoiceRecord entity) {
        statement.bindLong(1, entity.getRid());
        statement.bindString(2, entity.getClientName());
        statement.bindString(3, entity.getItemLabel());
        statement.bindString(4, entity.getTimber());
        statement.bindString(5, entity.getSpec());
        statement.bindDouble(6, entity.getSqft());
        statement.bindDouble(7, entity.getMaterialTotal());
        statement.bindDouble(8, entity.getLabourTotal());
        statement.bindDouble(9, entity.getMargin());
        statement.bindDouble(10, entity.getGrandTotal());
        statement.bindLong(11, entity.getTimestamp());
      }
    };
    this.__deletionAdapterOfInvoiceRecord = new EntityDeletionOrUpdateAdapter<InvoiceRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `invoices` WHERE `rid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InvoiceRecord entity) {
        statement.bindLong(1, entity.getRid());
      }
    };
  }

  @Override
  public Object save(final InvoiceRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfInvoiceRecord.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object remove(final InvoiceRecord record, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfInvoiceRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<InvoiceRecord>> stream() {
    final String _sql = "SELECT * FROM invoices ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"invoices"}, new Callable<List<InvoiceRecord>>() {
      @Override
      @NonNull
      public List<InvoiceRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfRid = CursorUtil.getColumnIndexOrThrow(_cursor, "rid");
          final int _cursorIndexOfClientName = CursorUtil.getColumnIndexOrThrow(_cursor, "clientName");
          final int _cursorIndexOfItemLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "itemLabel");
          final int _cursorIndexOfTimber = CursorUtil.getColumnIndexOrThrow(_cursor, "timber");
          final int _cursorIndexOfSpec = CursorUtil.getColumnIndexOrThrow(_cursor, "spec");
          final int _cursorIndexOfSqft = CursorUtil.getColumnIndexOrThrow(_cursor, "sqft");
          final int _cursorIndexOfMaterialTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "materialTotal");
          final int _cursorIndexOfLabourTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "labourTotal");
          final int _cursorIndexOfMargin = CursorUtil.getColumnIndexOrThrow(_cursor, "margin");
          final int _cursorIndexOfGrandTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "grandTotal");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<InvoiceRecord> _result = new ArrayList<InvoiceRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InvoiceRecord _item;
            final int _tmpRid;
            _tmpRid = _cursor.getInt(_cursorIndexOfRid);
            final String _tmpClientName;
            _tmpClientName = _cursor.getString(_cursorIndexOfClientName);
            final String _tmpItemLabel;
            _tmpItemLabel = _cursor.getString(_cursorIndexOfItemLabel);
            final String _tmpTimber;
            _tmpTimber = _cursor.getString(_cursorIndexOfTimber);
            final String _tmpSpec;
            _tmpSpec = _cursor.getString(_cursorIndexOfSpec);
            final double _tmpSqft;
            _tmpSqft = _cursor.getDouble(_cursorIndexOfSqft);
            final double _tmpMaterialTotal;
            _tmpMaterialTotal = _cursor.getDouble(_cursorIndexOfMaterialTotal);
            final double _tmpLabourTotal;
            _tmpLabourTotal = _cursor.getDouble(_cursorIndexOfLabourTotal);
            final double _tmpMargin;
            _tmpMargin = _cursor.getDouble(_cursorIndexOfMargin);
            final double _tmpGrandTotal;
            _tmpGrandTotal = _cursor.getDouble(_cursorIndexOfGrandTotal);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new InvoiceRecord(_tmpRid,_tmpClientName,_tmpItemLabel,_tmpTimber,_tmpSpec,_tmpSqft,_tmpMaterialTotal,_tmpLabourTotal,_tmpMargin,_tmpGrandTotal,_tmpTimestamp);
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
