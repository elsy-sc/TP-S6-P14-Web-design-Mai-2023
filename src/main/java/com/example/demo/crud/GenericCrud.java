/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.crud;

import com.example.demo.util.Column;
import com.example.demo.util.Function;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author elsys
 */
public class GenericCrud {

    private static void setObjects(PreparedStatement preparedStatement, List values) throws Exception {
        int i = 1;
        for (Object value : values) {
            if (value != null) {
                preparedStatement.setObject(i, value);
                i++;
            }
        }
    }

    public static Object getObject(Object object, Object id, Connection connection) throws Exception {
        Function.setObjectValue(object, Function.getField(object, "id"), id);
        List result = (List) object.getClass().getMethod("read", Connection.class, int[].class).invoke(object, connection, null);
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public Object getObject(String fieldName, Connection connection) throws Exception {
        Field field = Function.getField(this, fieldName);
        Object object = Function.getAttributeName(field).ref()[0].newInstance();
        Object id = (Long) Function.getObjectValue(this, field);
        return getObject(object, id, connection);
    }

    public void create(Connection connection) throws Exception {
        List<Column> attributesName = Function.getAttributesName(this);
        List<Object> attributesValue = Function.getAttributesValue(this);
        String query = "insert into " + Function.getTableName(this) + "(";
        int i = 0, count = 0;
        List<String> temp = new ArrayList<>();
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
                count++;
            }
        }
        if (!temp.isEmpty()) {
            int a = 0;
            for (; a < temp.size() - 1; a++) {
                query = query + temp.get(a) + ", ";
            }
            query = query + temp.get(a);
        }
        query = query + ") values (";
        if (!temp.isEmpty()) {
            i = 0;
            for (; i < count - 1; i++) {
                query = query + "?, ";
            }
            query = query + "?";
        }
        query = query + ")";
        PreparedStatement ps = connection.prepareStatement(query);
        System.out.println(query);
        setObjects(ps, attributesValue);
        int rs = ps.executeUpdate();
        ps.close();

    }

    public static void create(Object object, Connection connection) throws Exception {
        List<Column> attributesName = Function.getAttributesName(object);
        List<Object> attributesValue = Function.getAttributesValue(object);
        String query = "insert into " + Function.getTableName(object) + "(";
        int i = 0, count = 0;
        List<String> temp = new ArrayList<>();
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
                count++;
            }
        }
        if (!temp.isEmpty()) {
            int a = 0;
            for (; a < temp.size() - 1; a++) {
                query = query + temp.get(a) + ", ";
            }
            query = query + temp.get(a);
        }
        query = query + ") values (";
        if (!temp.isEmpty()) {
            i = 0;
            for (; i < count - 1; i++) {
                query = query + "?, ";
            }
            query = query + "?";
        }
        query = query + ")";
        PreparedStatement ps = connection.prepareStatement(query);
        setObjects(ps, attributesValue);
        int rs = ps.executeUpdate();
        ps.close();

    }

    public List read(String[] elasticSearchFields, String elasticSearchValue, String[] orderFields, String[] orderValues, String[] whereString, Connection connection, Integer... args) throws Exception {
        List<Object> attributesValue = null;
        List<Object> trueAttributeValue = null;
        List<Column> attributesName = null;
        String tableName = null;
        String query = null;
        List<Object> result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            attributesValue = Function.getAttributesValue(this);
            trueAttributeValue = new ArrayList<>();
            attributesName = Function.getAttributesName(this);
            tableName = Function.getTableName(this);
            query = "select ";
            int i = 0;
            for (; i < attributesName.size() - 1; i++) {
                query = query + attributesName.get(i).name() + ", ";
            }
            query = query + attributesName.get(i).name();
            query = query + " from " + tableName + " where 0=0 ";

            if ((elasticSearchFields == null || elasticSearchFields.length == 0) || (elasticSearchValue == null || elasticSearchValue.isEmpty())) {
                i = 0;
                for (; i < attributesValue.size(); i++) {
                    if (attributesValue.get(i) != null) {
                        query = query + " and " + attributesName.get(i).name() + "= ? ";
                        trueAttributeValue.add(attributesValue.get(i));
                    }
                }
            } else {
                String fieldSearch = elasticSearchFields[0];
                for (int j = 1; j < elasticSearchFields.length; j++) {
                    fieldSearch = fieldSearch + "||' '||" + elasticSearchFields[j];
                }
                String valueSearch = "'";
                String[] splittedelasticSearchValue = elasticSearchValue.split(" ");
                int j = 0;
                for (; j < splittedelasticSearchValue.length - 1; j++) {
                    valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "% ";
                }
                valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "%'";
                query = query + " and " + fieldSearch + " ilike " + valueSearch;
            }

            ///
            if (whereString != null && whereString.length != 0) {
                for (String string : whereString) {
                    query = query + " and " + string;
                }
            }
            ///

            ///
            if (orderFields != null && orderFields.length != 0 && orderValues != null && orderValues.length != 0) {
                query = query + " order by ";
                query = query + orderFields[0] + " " + orderValues[0];
                for (int j = 1; j < orderFields.length; j++) {
                    query = query + ", " + orderFields[j] + " " + orderValues[j];
                }
            }
            ///

            ///
            if (args.length == 2 && args[0] != null && args[1] != null) {
                query = query + " limit " + (args[1]) + " offset " + (args[0] - 1);
            }
            ///
            ps = connection.prepareStatement(query);
            setObjects(ps, trueAttributeValue);
            rs = ps.executeQuery();
            Field[] fields = Function.getAllFields(this);
            result = new ArrayList<>();
            while (rs.next()) {
                Object object1 = this.getClass().newInstance();
                for (Field field : fields) {
                    if (Function.getAttributeName(field) != null) {
                        Function.setObjectValue(object1, field, rs.getObject(Function.getAttributeName(field).name()));
                    }
                }
                result.add(object1);
            }
            return result;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public static List read(Object object, String[] elasticSearchFields, String elasticSearchValue, String[] orderFields, String[] orderValues, String[] whereString, Connection connection, Integer... args) throws Exception {
        List<Object> attributesValue = null;
        List<Object> trueAttributeValue = null;
        List<Column> attributesName = null;
        String tableName = null;
        String query = null;
        List<Object> result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            attributesValue = Function.getAttributesValue(object);
            trueAttributeValue = new ArrayList<>();
            attributesName = Function.getAttributesName(object);
            tableName = Function.getTableName(object);
            query = "select ";
            int i = 0;
            for (; i < attributesName.size() - 1; i++) {
                query = query + attributesName.get(i).name() + ", ";
            }
            query = query + attributesName.get(i).name();
            query = query + " from " + tableName + " where 0=0 ";

            if ((elasticSearchFields == null || elasticSearchFields.length == 0) || (elasticSearchValue == null || elasticSearchValue.isEmpty())) {
                i = 0;
                for (; i < attributesValue.size(); i++) {
                    if (attributesValue.get(i) != null) {
                        query = query + " and " + attributesName.get(i).name() + "= ? ";
                        trueAttributeValue.add(attributesValue.get(i));
                    }
                }
            } else {
                String fieldSearch = elasticSearchFields[0];
                for (int j = 1; j < elasticSearchFields.length; j++) {
                    fieldSearch = fieldSearch + "||' '||" + elasticSearchFields[j];
                }
                String valueSearch = "'";
                String[] splittedelasticSearchValue = elasticSearchValue.split(" ");
                int j = 0;
                for (; j < splittedelasticSearchValue.length - 1; j++) {
                    valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "% ";
                }
                valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "%'";
                query = query + " and " + fieldSearch + " ilike " + valueSearch;
            }

            ///
            if (whereString != null && whereString.length != 0) {
                for (String string : whereString) {
                    query = query + " and " + string;
                }
            }
            ///

            ///
            if (orderFields != null && orderFields.length != 0 && orderValues != null && orderValues.length != 0) {
                query = query + " order by ";
                query = query + orderFields[0] + " " + orderValues[0];
                for (int j = 1; j < orderFields.length; j++) {
                    query = query + ", " + orderFields[j] + " " + orderValues[j];
                }
            }
            ///

            ///
            if (args.length == 2 && args[0] != null && args[1] != null) {
                query = query + " limit " + (args[1]) + " offset " + (args[0] - 1);
            }
            ///
            ps = connection.prepareStatement(query);
            setObjects(ps, trueAttributeValue);
            rs = ps.executeQuery();
            Field[] fields = Function.getAllFields(object);
            result = new ArrayList<>();
            while (rs.next()) {
                Object object1 = object.getClass().newInstance();
                for (Field field : fields) {
                    if (Function.getAttributeName(field) != null) {
                        Function.setObjectValue(object1, field, rs.getObject(Function.getAttributeName(field).name()));
                    }
                }
                result.add(object1);
            }
            return result;

        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        }
    }

    public static List<HashMap> executeQuery(String query, String[] columns, Connection connection) throws Exception {
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<HashMap> trueresult = new ArrayList<HashMap>();
        while (rs.next()) {
            HashMap result = new HashMap();
            for (String column : columns) {
                if (rs.getObject(column) != null) {
                    result.put(column, rs.getObject(column));
                }
            }
            trueresult.add(result);
        }
        // close
        rs.close();
        ps.close();
        return trueresult;
    }

    public static void executeQueryNotReturned(String query, Connection connection) throws Exception {
        PreparedStatement ps = connection.prepareStatement(query);
        int rs = ps.executeUpdate();
        // close
        ps.close();
    }

    public static Long getNextSequenceValue(String sequenceName, Connection connection) throws Exception {
        List<HashMap> seq = executeQuery("select nextval('" + sequenceName + "')", new String[]{"nextval"}, connection);
        return (Long) seq.get(0).get("nextval");
    }

    public List executeQuery(String query, Connection connection) throws Exception {
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List trueresult = new ArrayList();
        while (rs.next()) {
            Object object1 = this.getClass().newInstance();
            for (Field column : Function.getAllFields(this)) {
                if (Function.getAttributeName(column) != null && Function.isExist(rs, column.getName()) && rs.getObject(column.getName()) != null) {
                    Function.setObjectValue(object1, column, rs.getObject(column.getName()));
                }
            }
            trueresult.add(object1);
        }
        // close
        rs.close();
        ps.close();
        return trueresult;
    }

    public static List executeQuery(Object object, String query, Connection connection) throws Exception {
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List trueresult = new ArrayList();
        while (rs.next()) {
            Object object1 = object.getClass().newInstance();
            for (Field column : Function.getAllFields(object)) {
                if (Function.getAttributeName(column) != null && Function.isExist(rs, column.getName()) && rs.getObject(column.getName()) != null) {
                    Function.setObjectValue(object1, column, rs.getObject(column.getName()));
                }
            }
            trueresult.add(object1);
        }
        // close
        rs.close();
        ps.close();
        return trueresult;
    }

    public void update(Connection connection, String[] names, String[] whereString) throws Exception {
        List<Column> attributesName = Function.getAttributesName(this);
        List<Object> attributesValue = Function.getAttributesValue(this);
        List temp = new ArrayList();
        String query = "update " + Function.getTableName(this) + " set ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
            }
        }
        if (!temp.isEmpty()) {
            int k = 0;
            for (; k < temp.size() - 1; k++) {
                query = query + temp.get(k) + "=?, ";
            }
            query = query + temp.get(k) + "=? ";
        }
        query = query + " where 0=0";

        if(names != null && names.length != 0){
            for (String name : names) {
                for (i = 0; i < attributesName.size(); i++) {
                    if (attributesValue.get(i) != null && attributesName.get(i).name().equals(name)) {
                        query = query + " and " + attributesName.get(i).name() + "=? ";
                    }
                }
            }
        }
        ///
        if (whereString != null && whereString.length != 0) {
            for (String string : whereString) {
                query = query + " and " + string;
            }
        }
        ///
        PreparedStatement ps = connection.prepareStatement(query);
        int index = 1;
        for (int j = 0; j < attributesValue.size(); j++) {

            if (attributesValue.get(j) != null) {
                ps.setObject(index, attributesValue.get(j));
                index++;
            }
        }
        if(names != null && names.length != 0) {
            for (String name : names) {
                for (i = 0; i < attributesName.size(); i++) {
                    if (attributesValue.get(i) != null && attributesName.get(i).name().equals(name)) {
                        ps.setObject(index, attributesValue.get(i));
                        index++;
                    }
                }
            }
        }

        int rs = ps.executeUpdate();
        ps.close();
    }

    public static void update(Object object, Connection connection, String[] names, String[] whereString) throws Exception {
        List<Column> attributesName = Function.getAttributesName(object);
        List<Object> attributesValue = Function.getAttributesValue(object);
        List temp = new ArrayList();
        String query = "update " + Function.getTableName(object) + " set ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
            }
        }
        if (!temp.isEmpty()) {
            int k = 0;
            for (; k < temp.size() - 1; k++) {
                query = query + temp.get(k) + "=?, ";
            }
            query = query + temp.get(k) + "=? ";
        }
        query = query + " where 0=0";

        if(names != null && names.length != 0) {
            for (String name : names) {
                for (i = 0; i < attributesName.size(); i++) {
                    if (attributesValue.get(i) != null && attributesName.get(i).name().equals(name)) {
                        query = query + " and " + attributesName.get(i).name() + "=? ";
                    }
                }
            }
        }
        ///
        if (whereString != null && whereString.length != 0) {
            for (String string : whereString) {
                query = query + " and " + string;
            }
        }
        ///
        PreparedStatement ps = connection.prepareStatement(query);
        int index = 1;
        for (int j = 0; j < attributesValue.size(); j++) {

            if (attributesValue.get(j) != null) {
                ps.setObject(index, attributesValue.get(j));
                index++;
            }
        }
        if(names != null && names.length != 0) {
            for (String name : names) {
                for (i = 0; i < attributesName.size(); i++) {
                    if (attributesValue.get(i) != null && attributesName.get(i).name().equals(name)) {
                        ps.setObject(index, attributesValue.get(i));
                        index++;
                    }
                }
            }
        }

        int rs = ps.executeUpdate();
        ps.close();
    }

    public void delete(Connection connection, String[] whereString) throws Exception {
        List<Object> attributesValue = Function.getAttributesValue(this);
        List<Column> attributesName = Function.getAttributesName(this);
        String query = "delete from " + Function.getTableName(this) + " where 0=0 ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                query = query + "and " + attributesName.get(i).name() + "=? ";
            }
        }
        if (whereString != null) {
            for (String string : whereString) {
                query = query + " and " + string + " ";
            }
        }
        PreparedStatement ps = connection.prepareStatement(query);
        setObjects(ps, attributesValue);
        int rs = ps.executeUpdate();
        ps.close();
    }

    public static void delete(Object object, Connection connection, String[] whereString) throws Exception {
        List<Object> attributesValue = Function.getAttributesValue(object);
        List<Column> attributesName = Function.getAttributesName(object);
        String query = "delete from " + Function.getTableName(object) + " where 0=0 ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                query = query + "and " + attributesName.get(i).name() + "=? ";
            }
        }
        if (whereString != null) {
            for (String string : whereString) {
                query = query + " and " + string + " ";
            }
        }
        PreparedStatement ps = connection.prepareStatement(query);
        setObjects(ps, attributesValue);
        int rs = ps.executeUpdate();
        ps.close();
    }

    public long countRow(Connection connection) throws Exception {
        try {
            long result = (long) executeQuery("select count(*) from " + Function.getTableName(this), new String[]{"count"}, connection).get(0).get("count");
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public static long countRow(Object object, Connection connection) throws Exception {
        try {
            long result = (long) executeQuery("select count(*) from " + Function.getTableName(object), new String[]{"count"}, connection).get(0).get("count");
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public long countRow(String query, Connection connection) throws Exception {
        try {
            long result = (long) executeQuery("select count(*) from  ( " + query + " ) temp", new String[]{"count"}, connection).get(0).get("count");
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public String readQuery(String[] elasticSearchFields, String elasticSearchValue, String[] orderFields, String[] orderValues, String[] whereString, Integer... args) throws Exception {
        List<Object> attributesValue = Function.getAttributesValue(this);
        List<Column> attributesName = Function.getAttributesName(this);
        String tableName = Function.getTableName(this);
        String query = "select ";
        int i = 0;
        for (; i < attributesName.size() - 1; i++) {
            query = query + attributesName.get(i).name() + ", ";
        }
        query = query + attributesName.get(i).name();
        query = query + " from " + tableName + " where 0=0 ";

        if ((elasticSearchFields == null || elasticSearchFields.length == 0) || (elasticSearchValue == null || elasticSearchValue.isEmpty())) {
            i = 0;
            for (; i < attributesValue.size(); i++) {
                if (attributesValue.get(i) != null) {
                    query = query + " and " + attributesName.get(i).name() + "= " + attributesValue.get(i);
                }
            }
        } else {
            String fieldSearch = elasticSearchFields[0];
            for (int j = 1; j < elasticSearchFields.length; j++) {
                fieldSearch = fieldSearch + "||' '||" + elasticSearchFields[j];
            }
            String valueSearch = "'";
            String[] splittedelasticSearchValue = elasticSearchValue.split(" ");
            int j = 0;
            for (; j < splittedelasticSearchValue.length - 1; j++) {
                valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "% ";
            }
            valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "%'";
            query = query + " and " + fieldSearch + " ilike " + valueSearch;
        }

        ///
        if (whereString != null && whereString.length != 0) {
            for (String string : whereString) {
                query = query + " and " + string;
            }
        }
        ///

        ///
        if (orderFields != null && orderFields.length != 0 && orderValues != null && orderValues.length != 0) {
            query = query + " order by ";
            query = query + orderFields[0] + " " + orderValues[0];
            for (int j = 1; j < orderFields.length; j++) {
                query = query + ", " + orderFields[j] + " " + orderValues[j];
            }
        }
        ///

        ///
        if (args.length == 2 && args[0] != null && args[1] != null) {
            query = query + " limit " + (args[1]) + " offset " + (args[0] - 1);
        }

        return query;
    }

    public static String readQuery(Object object, String[] elasticSearchFields, String elasticSearchValue, String[] orderFields, String[] orderValues, String[] whereString, Integer... args) throws Exception {
        List<Object> attributesValue = Function.getAttributesValue(object);
        List<Column> attributesName = Function.getAttributesName(object);
        String tableName = Function.getTableName(object);
        String query = "select ";
        int i = 0;
        for (; i < attributesName.size() - 1; i++) {
            query = query + attributesName.get(i).name() + ", ";
        }
        query = query + attributesName.get(i).name();
        query = query + " from " + tableName + " where 0=0 ";

        if ((elasticSearchFields == null || elasticSearchFields.length == 0) || (elasticSearchValue == null || elasticSearchValue.isEmpty())) {
            i = 0;
            for (; i < attributesValue.size(); i++) {
                if (attributesValue.get(i) != null) {
                    query = query + " and " + attributesName.get(i).name() + "= " + attributesValue.get(i);
                }
            }
        } else {
            String fieldSearch = elasticSearchFields[0];
            for (int j = 1; j < elasticSearchFields.length; j++) {
                fieldSearch = fieldSearch + "||' '||" + elasticSearchFields[j];
            }
            String valueSearch = "'";
            String[] splittedelasticSearchValue = elasticSearchValue.split(" ");
            int j = 0;
            for (; j < splittedelasticSearchValue.length - 1; j++) {
                valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "% ";
            }
            valueSearch = valueSearch + "%" + splittedelasticSearchValue[j] + "%'";
            query = query + " and " + fieldSearch + " ilike " + valueSearch;
        }

        ///
        if (whereString != null && whereString.length != 0) {
            for (String string : whereString) {
                query = query + " and " + string;
            }
        }
        ///

        ///
        if (orderFields != null && orderFields.length != 0 && orderValues != null && orderValues.length != 0) {
            query = query + " order by ";
            query = query + orderFields[0] + " " + orderValues[0];
            for (int j = 1; j < orderFields.length; j++) {
                query = query + ", " + orderFields[j] + " " + orderValues[j];
            }
        }
        ///

        ///
        if (args.length == 2 && args[0] != null && args[1] != null) {
            query = query + " limit " + (args[1]) + " offset " + (args[0] - 1);
        }

        return query;
    }

    public String updateQuery(String[] names, String[] setCondition, String[] whereString) throws Exception {
        List<Column> attributesName = Function.getAttributesName(this);
        List<Object> attributesValue = Function.getAttributesValue(this);
        List temp = new ArrayList();
        String query = "update " + Function.getTableName(this) + " set ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
            }
        }
        if (!temp.isEmpty()) {
            int k = 0;
            for (; k < temp.size() - 1; k++) {
                query = query + temp.get(k) + "=?, ";
            }
            query = query + temp.get(k) + "=?";
        }
        if (setCondition != null) {
            for (String string : setCondition) {
                query = query + "," + string + " ";
            }
        }
        query = query + " where 0=0";
        for (String name : names) {
            for (i = 0; i < attributesName.size(); i++) {
                if (attributesValue.get(i) != null && attributesName.get(i).name().equals(name)) {
                    query = query + " and " + attributesName.get(i).name() + "= " + attributesValue.get(i);
                }
            }
        }
        if (whereString != null) {
            for (String string : whereString) {
                query = query + " and " + string + " ";
            }
        }
        return query;
    }

    public static String updateQuery(Object object, String[] names, String[] setCondition, String[] whereString) throws Exception {
        List<Column> attributesName = Function.getAttributesName(object);
        List<Object> attributesValue = Function.getAttributesValue(object);
        List temp = new ArrayList();
        String query = "update " + Function.getTableName(object) + " set ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
            }
        }
        if (!temp.isEmpty()) {
            int k = 0;
            for (; k < temp.size() - 1; k++) {
                query = query + temp.get(k) + "=?, ";
            }
            query = query + temp.get(k) + "=?";
        }
        if (setCondition != null) {
            for (String string : setCondition) {
                query = query + "," + string + " ";
            }
        }
        query = query + " where 0=0";
        for (String name : names) {
            for (i = 0; i < attributesName.size(); i++) {
                if (attributesValue.get(i) != null && attributesName.get(i).name().equals(name)) {
                    query = query + " and " + attributesName.get(i).name() + "= " + attributesValue.get(i);
                }
            }
        }
        if (whereString != null) {
            for (String string : whereString) {
                query = query + " and " + string + " ";
            }
        }
        return query;
    }

    public String deleteQuery(String[] whereString) throws Exception {
        List<Object> attributesValue = Function.getAttributesValue(this);
        List<Column> attributesName = Function.getAttributesName(this);
        String query = "delete from " + Function.getTableName(this) + " where 0=0 ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                query = query + "and " + attributesName.get(i).name() + "=? ";
            }
        }
        if (whereString != null) {
            for (String string : whereString) {
                query = query + " and " + string + " ";
            }
        }
        return query;
    }

    public static String deleteQuery(Object object, Connection connection, String[] whereString) throws Exception {
        List<Object> attributesValue = Function.getAttributesValue(object);
        List<Column> attributesName = Function.getAttributesName(object);
        String query = "delete from " + Function.getTableName(object) + " where 0=0 ";
        int i = 0;
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                query = query + "and " + attributesName.get(i).name() + "=? ";
            }
        }
        if (whereString != null) {
            for (String string : whereString) {
                query = query + " and " + string + " ";
            }
        }
        return query;
    }

    public String createQuery(Connection connection) throws Exception {
        List<Column> attributesName = Function.getAttributesName(this);
        List<Object> attributesValue = Function.getAttributesValue(this);
        String query = "insert into " + Function.getTableName(this) + "(";
        int i = 0, count = 0;
        List<String> temp = new ArrayList<>();
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
                count++;
            }
        }
        if (!temp.isEmpty()) {
            int a = 0;
            for (; a < temp.size() - 1; a++) {
                query = query + temp.get(a) + ", ";
            }
            query = query + temp.get(a);
        }
        query = query + ") values (";
        if (!temp.isEmpty()) {
            i = 0;
            for (; i < count - 1; i++) {
                query = query + "?, ";
            }
            query = query + "?";
        }
        query = query + ")";
        return query;

    }

    public static String createQuery(Object object, Connection connection) throws Exception {
        List<Column> attributesName = Function.getAttributesName(object);
        List<Object> attributesValue = Function.getAttributesValue(object);
        String query = "insert into " + Function.getTableName(object) + "(";
        int i = 0, count = 0;
        List<String> temp = new ArrayList<>();
        for (; i < attributesName.size(); i++) {
            if (attributesValue.get(i) != null) {
                temp.add(attributesName.get(i).name());
                count++;
            }
        }
        if (!temp.isEmpty()) {
            int a = 0;
            for (; a < temp.size() - 1; a++) {
                query = query + temp.get(a) + ", ";
            }
            query = query + temp.get(a);
        }
        query = query + ") values (";
        if (!temp.isEmpty()) {
            i = 0;
            for (; i < count - 1; i++) {
                query = query + "?, ";
            }
            query = query + "?";
        }
        query = query + ")";
        return query;

    }

}
