/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.util;

//import connection.GenericDatabase;
//import java.io.File;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elsys
 */
public class Function {
    // check if a column exist in resultset
    public static boolean isExist(ResultSet rs, String column) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            if (metaData.getColumnName(i).equalsIgnoreCase(column)) {
                return true;
            }
        }
        return false;
    }

    // get Field by name
    public static Field getField(Object object, String fieldName) {
        Field[] fields = getAllFields(object);
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

// set Object value
    public static void setObjectValue(Object object, Field field, Object value) throws Exception {        
        object.getClass().getMethod("set" + field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1)), field.getType()).invoke(object, value);
    }

// set Object value
    public static Object getObjectValue(Object object, Field field) throws Exception {
        return object.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1))).invoke(object);
    }

// get annoted table name 
    public static String getTableName(Object object) {
        return object.getClass().getAnnotation(Table.class).name();
    }
// get annoted Column 

    public static Column getAttributeName(Field field) {
        if (field.getAnnotation(Column.class) != null) {
            return field.getAnnotation(Column.class);
        }
        return null;
    }
// get this Fields

    public static Field[] getThisFields(Object object) {
        return object.getClass().getDeclaredFields();
    }

// get super Fields
    public static Field[] getSuperFields(Object object) {
        return object.getClass().getSuperclass().getDeclaredFields();
    }

// get all Fields
    public static Field[] getAllFields(Object object) {
        Field[] superFields = getSuperFields(object);
        Field[] thisFields = getThisFields(object);

// concate table
        int len1 = superFields.length;
        int len2 = thisFields.length;
        Field[] result = new Field[len1 + len2];
        System.arraycopy(superFields, 0, result, 0, len1);
        System.arraycopy(thisFields, 0, result, len1, len2);

        return result;
    }

// get field value in a object
    public static Object getAttributeValue(Object object, Field field) throws Exception {
        String fieldName = field.getName();
        fieldName = fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));
        Object result = null;
        try {
            result = object.getClass().getMethod("get" + fieldName).invoke(object);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    // get all annoted field 
    public static List<Column> getAttributesName(Object object) {
        List<Column> result = new ArrayList<>();
        for (Field declaredField : getAllFields(object)) {
            if (getAttributeName(declaredField) != null) {
                result.add(getAttributeName(declaredField));
            }
        }
        return result;
    }

    // get all annoted field value 
    public static List getAttributesValue(Object object) throws Exception {
        List result = new ArrayList<>();
        for (Field declaredField : getAllFields(object)) {
            if (getAttributeName(declaredField) != null) {

                try {
                    result.add(getAttributeValue(object, declaredField));
                } catch (Exception e) {
                    throw e;
                }
            }
        }
        return result;
    }

}
