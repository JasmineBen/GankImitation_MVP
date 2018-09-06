package com.conan.gankimitation.data.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.conan.gankimitation.data.database.StringToListConvert;
import java.util.List;

import com.conan.gankimitation.model.GankEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GANK_ENTITY".
*/
public class GankEntityDao extends AbstractDao<GankEntity, String> {

    public static final String TABLENAME = "GANK_ENTITY";

    /**
     * Properties of entity GankEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property CreatedTime = new Property(1, java.util.Date.class, "createdTime", false, "CREATED_TIME");
        public final static Property Desc = new Property(2, String.class, "desc", false, "DESC");
        public final static Property Type = new Property(3, String.class, "type", false, "TYPE");
        public final static Property PublishedTime = new Property(4, java.util.Date.class, "publishedTime", false, "PUBLISHED_TIME");
        public final static Property Source = new Property(5, String.class, "source", false, "SOURCE");
        public final static Property Url = new Property(6, String.class, "url", false, "URL");
        public final static Property Used = new Property(7, boolean.class, "used", false, "USED");
        public final static Property Publisher = new Property(8, String.class, "publisher", false, "PUBLISHER");
        public final static Property Images = new Property(9, String.class, "images", false, "IMAGES");
    }

    private final StringToListConvert imagesConverter = new StringToListConvert();

    public GankEntityDao(DaoConfig config) {
        super(config);
    }
    
    public GankEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GANK_ENTITY\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"CREATED_TIME\" INTEGER," + // 1: createdTime
                "\"DESC\" TEXT," + // 2: desc
                "\"TYPE\" TEXT," + // 3: type
                "\"PUBLISHED_TIME\" INTEGER," + // 4: publishedTime
                "\"SOURCE\" TEXT," + // 5: source
                "\"URL\" TEXT," + // 6: url
                "\"USED\" INTEGER NOT NULL ," + // 7: used
                "\"PUBLISHER\" TEXT," + // 8: publisher
                "\"IMAGES\" TEXT);"); // 9: images
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GANK_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GankEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        java.util.Date createdTime = entity.getCreatedTime();
        if (createdTime != null) {
            stmt.bindLong(2, createdTime.getTime());
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(3, desc);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
 
        java.util.Date publishedTime = entity.getPublishedTime();
        if (publishedTime != null) {
            stmt.bindLong(5, publishedTime.getTime());
        }
 
        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(6, source);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(7, url);
        }
        stmt.bindLong(8, entity.getUsed() ? 1L: 0L);
 
        String publisher = entity.getPublisher();
        if (publisher != null) {
            stmt.bindString(9, publisher);
        }
 
        List images = entity.getImages();
        if (images != null) {
            stmt.bindString(10, imagesConverter.convertToDatabaseValue(images));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GankEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        java.util.Date createdTime = entity.getCreatedTime();
        if (createdTime != null) {
            stmt.bindLong(2, createdTime.getTime());
        }
 
        String desc = entity.getDesc();
        if (desc != null) {
            stmt.bindString(3, desc);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(4, type);
        }
 
        java.util.Date publishedTime = entity.getPublishedTime();
        if (publishedTime != null) {
            stmt.bindLong(5, publishedTime.getTime());
        }
 
        String source = entity.getSource();
        if (source != null) {
            stmt.bindString(6, source);
        }
 
        String url = entity.getUrl();
        if (url != null) {
            stmt.bindString(7, url);
        }
        stmt.bindLong(8, entity.getUsed() ? 1L: 0L);
 
        String publisher = entity.getPublisher();
        if (publisher != null) {
            stmt.bindString(9, publisher);
        }
 
        List images = entity.getImages();
        if (images != null) {
            stmt.bindString(10, imagesConverter.convertToDatabaseValue(images));
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public GankEntity readEntity(Cursor cursor, int offset) {
        GankEntity entity = new GankEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)), // createdTime
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // desc
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // type
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // publishedTime
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // source
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // url
            cursor.getShort(offset + 7) != 0, // used
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // publisher
            cursor.isNull(offset + 9) ? null : imagesConverter.convertToEntityProperty(cursor.getString(offset + 9)) // images
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GankEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setCreatedTime(cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)));
        entity.setDesc(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setType(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPublishedTime(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setSource(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUrl(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUsed(cursor.getShort(offset + 7) != 0);
        entity.setPublisher(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setImages(cursor.isNull(offset + 9) ? null : imagesConverter.convertToEntityProperty(cursor.getString(offset + 9)));
     }
    
    @Override
    protected final String updateKeyAfterInsert(GankEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(GankEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GankEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
