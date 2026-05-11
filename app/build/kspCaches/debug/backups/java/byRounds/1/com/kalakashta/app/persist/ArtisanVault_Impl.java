package com.kalakashta.app.persist;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ArtisanVault_Impl extends ArtisanVault {
  private volatile InvoiceAccess _invoiceAccess;

  private volatile CraftAccess _craftAccess;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `invoices` (`rid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `clientName` TEXT NOT NULL, `itemLabel` TEXT NOT NULL, `timber` TEXT NOT NULL, `spec` TEXT NOT NULL, `sqft` REAL NOT NULL, `materialTotal` REAL NOT NULL, `labourTotal` REAL NOT NULL, `margin` REAL NOT NULL, `grandTotal` REAL NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `crafts` (`rid` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `photoUri` TEXT NOT NULL, `caption` TEXT NOT NULL, `note` TEXT NOT NULL, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7374c787a29a1cb48fd7eac5a05d1c4c')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `invoices`");
        db.execSQL("DROP TABLE IF EXISTS `crafts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsInvoices = new HashMap<String, TableInfo.Column>(11);
        _columnsInvoices.put("rid", new TableInfo.Column("rid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("clientName", new TableInfo.Column("clientName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("itemLabel", new TableInfo.Column("itemLabel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("timber", new TableInfo.Column("timber", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("spec", new TableInfo.Column("spec", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("sqft", new TableInfo.Column("sqft", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("materialTotal", new TableInfo.Column("materialTotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("labourTotal", new TableInfo.Column("labourTotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("margin", new TableInfo.Column("margin", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("grandTotal", new TableInfo.Column("grandTotal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInvoices.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInvoices = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesInvoices = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoInvoices = new TableInfo("invoices", _columnsInvoices, _foreignKeysInvoices, _indicesInvoices);
        final TableInfo _existingInvoices = TableInfo.read(db, "invoices");
        if (!_infoInvoices.equals(_existingInvoices)) {
          return new RoomOpenHelper.ValidationResult(false, "invoices(com.kalakashta.app.persist.InvoiceRecord).\n"
                  + " Expected:\n" + _infoInvoices + "\n"
                  + " Found:\n" + _existingInvoices);
        }
        final HashMap<String, TableInfo.Column> _columnsCrafts = new HashMap<String, TableInfo.Column>(5);
        _columnsCrafts.put("rid", new TableInfo.Column("rid", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCrafts.put("photoUri", new TableInfo.Column("photoUri", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCrafts.put("caption", new TableInfo.Column("caption", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCrafts.put("note", new TableInfo.Column("note", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCrafts.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCrafts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCrafts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCrafts = new TableInfo("crafts", _columnsCrafts, _foreignKeysCrafts, _indicesCrafts);
        final TableInfo _existingCrafts = TableInfo.read(db, "crafts");
        if (!_infoCrafts.equals(_existingCrafts)) {
          return new RoomOpenHelper.ValidationResult(false, "crafts(com.kalakashta.app.persist.CraftRecord).\n"
                  + " Expected:\n" + _infoCrafts + "\n"
                  + " Found:\n" + _existingCrafts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7374c787a29a1cb48fd7eac5a05d1c4c", "1b999e262591f9404b2604975d36795d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "invoices","crafts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `invoices`");
      _db.execSQL("DELETE FROM `crafts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(InvoiceAccess.class, InvoiceAccess_Impl.getRequiredConverters());
    _typeConvertersMap.put(CraftAccess.class, CraftAccess_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public InvoiceAccess invoices() {
    if (_invoiceAccess != null) {
      return _invoiceAccess;
    } else {
      synchronized(this) {
        if(_invoiceAccess == null) {
          _invoiceAccess = new InvoiceAccess_Impl(this);
        }
        return _invoiceAccess;
      }
    }
  }

  @Override
  public CraftAccess crafts() {
    if (_craftAccess != null) {
      return _craftAccess;
    } else {
      synchronized(this) {
        if(_craftAccess == null) {
          _craftAccess = new CraftAccess_Impl(this);
        }
        return _craftAccess;
      }
    }
  }
}
