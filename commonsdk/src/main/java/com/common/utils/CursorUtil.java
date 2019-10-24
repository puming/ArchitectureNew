package com.common.utils;

import android.database.Cursor;
import android.util.Log;

import com.common.BuildConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author tengxp
 */
public class CursorUtil {
    public static final String TAG = "CursorUtil";

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Column {
        String name();
    }

    public static <T> List<T> resolve(Cursor cursor, Class<T> clazz) {
        return resolve(cursor, clazz, true);
    }

    public static <T> List<T> resolve(Cursor cursor, Class<T> clazz, boolean finallyClose) {
        List list = new ArrayList<T>();
        int count = cursor == null ? 0 : cursor.getCount();
        if (count > 0) {
            Field[] fields = clazz.getDeclaredFields();
            String[] columnNameArray = cursor.getColumnNames();
            List<String> columnNameList = Arrays.asList(columnNameArray);
            HashMap<String, Integer> typeMap = new HashMap<String, Integer>();

            try {
                if (cursor.moveToFirst()) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < columnNameList.size(); i++) {
                        String name = columnNameList.get(i);
                        int type = checkResolvedType(fields, name, cursor.getType(i));

                        sb.append("resolve name = " + name + ", resolved type = " + type + ", \n");
                        typeMap.put(name, type);
                    }
                    Log.i(TAG, sb.delete(sb.lastIndexOf(","), sb.length()).toString());

                    do {
                        T t = clazz.newInstance();

                        for (int i = 0; i < fields.length && t != null; i++) {
                            Field field = fields[i];
                            field.setAccessible(true);

                            String name = field.getName();
                            int index = columnNameList.indexOf(name);

                            if (index == -1) {
                                Column column = field.getAnnotation(Column.class);
                                if (column != null) {
                                    name = column.name();
                                    index = columnNameList.indexOf(name);
                                } else {
                                    continue;
                                }
                            }

                            Object obj;
                            switch (typeMap.get(name)) {
                                case Cursor.FIELD_TYPE_STRING:
                                    obj = cursor.getString(index);
                                    break;

                                case Cursor.FIELD_TYPE_FLOAT:
                                    obj = cursor.getFloat(index);
                                    break;

                                case Cursor.FIELD_TYPE_INTEGER:
                                    obj = cursor.getInt(index);
                                    break;

                                case Cursor.FIELD_TYPE_BLOB:
                                    obj = cursor.getBlob(index);
                                    break;

                                default:
                                    continue;

                            }
                            field.set(t, obj);
                        }

                        Log.i(TAG, "resolve : " + t);

                        list.add(t);
                    } while (cursor.moveToNext());
                }
            } catch (Exception error) {
                if (BuildConfig.DEBUG) {
                    error.printStackTrace();
                }
            } finally {
                if (finallyClose) {
                    cursor.close();
                }
            }
        }

        return list;
    }

    private static int checkResolvedType(Field[] fields, String columnName, int type) {
        for (Field field : fields) {
            boolean isRightColumn = field.getName().equals(columnName);
            if (!isRightColumn) {
                Column c = field.getAnnotation(Column.class);
                isRightColumn = c != null && c.name().equals(columnName);
            }

            if (isRightColumn) {
                int declaredType = getDeclaredType(field);
                switch (declaredType) {
                    case Cursor.FIELD_TYPE_NULL:
                        type = declaredType;
                        break;

                    default:
                        if (declaredType != type) {
                            boolean isDataBlob = type == Cursor.FIELD_TYPE_BLOB;
                            String declaredStr = "declared type";
                            String dataStr = "data type";
                            if (isDataBlob || declaredType == Cursor.FIELD_TYPE_BLOB) {
                                Log.e(TAG, "Column : " + columnName + "'s " + declaredStr + " : " + declaredStr
                                        + " is " + "different from " + dataStr + " : " + type + ", and "
                                        + (isDataBlob ? dataStr : declaredStr) + " is Cursor.FIELD_TYPE_BLOB that " +
                                        "can't cast.");
                                type = Cursor.FIELD_TYPE_NULL;
                            } else {
                                Log.e(TAG, "Column : " + columnName + "'s " + declaredStr + " : " + declaredType
                                        + " is " + "different from " + dataStr + " : " + type + ", so cast " + dataStr
                                        + " to Cursor.FIELD_TYPE_STRING.");
                                type = Cursor.FIELD_TYPE_STRING;
                            }
                        }
                        break;
                }
                break;
            }
        }
        return type;
    }

    private static int getDeclaredType(Field field) {
        int type = Cursor.FIELD_TYPE_NULL;
        Class fieldType = field.getType();
        if (fieldType == String.class) {
            type = Cursor.FIELD_TYPE_STRING;
        } else if (fieldType == int.class || fieldType == Integer.class) {
            type = Cursor.FIELD_TYPE_INTEGER;
        } else if (fieldType == float.class || fieldType == Float.class) {
            type = Cursor.FIELD_TYPE_FLOAT;
        } else if (fieldType == byte[].class || fieldType == Byte[].class) {
            type = Cursor.FIELD_TYPE_BLOB;
        }

        return type;
    }
}
