package com.uline.companycode.utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DB_utility {

    private static Connection conn;
    private static Statement statement;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;

    /*TODO create a conection

     */
    public static void createConnection() {
        String url = ConfigurationReader.getProperty("uline.database.url");
        String username = ConfigurationReader.getProperty("uline.database.username");
        String password = ConfigurationReader.getProperty("uline.database.password");
//        try {
//            conn = DriverManager.getConnection(url, username, password);
//            System.out.println("CONNECTION WAS SUCCESSFUL");
//        } catch (SQLException e) {
//            System.out.println("CONNECTION FAILED");
//        }
        createConnection(url, username, password);
    }

    public static void createConnection(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("CONNECTION WAS SUCCESSFUL");
        } catch (SQLException e) {
            System.out.println("CONNECTION FAILED");
        }
    }

    /*
    TODO create a Result Set
     */
    public static ResultSet runQuery(String query) {
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);
            System.out.println("rs was created");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "while resultSet was set");
        }

        return rs;
    }

    /*
    TODO close the connection and result set
     */
    public static void destroy() {

        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " while the closing");
        }
    }

    /*
    TODO ger the row count in the table
     */
    public static int getRowCount() {
        int rowCount = 0;
        try {
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "while row counting");
        } finally {
            resetCursor();
        }

        return rowCount;
    }

    /*
    TODO get column count
     */
    public static int getColumnCount() {
        int columnCount = 0;
        try {
            rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "while column count");
        } finally {
            resetCursor();
        }
        return columnCount;
    }

    /*
    TODO return all the column name as a list
     */
    public static List<String> getAllColumnNAmes() {
        List<String> columnNameList = new ArrayList<>();

        try {
            for (int col = 1; col <= rsmd.getColumnCount(); col++) {
                String colName = rsmd.getColumnName(col);
                columnNameList.add(colName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resetCursor();
        }
        return columnNameList;
    }

    /*
    TODO get entire row data as a list of string
     */
    public static List<String> getRowDataAsList(int rowNum) {

        List<String> rowDataAsLst = new ArrayList<>();
        int colCount = getColumnCount();

        try {
            rs.absolute(rowNum);

            for (int colIndex = 1; colIndex <= colCount; colIndex++) {

                String cellValue = rs.getString(colIndex);
                rowDataAsLst.add(cellValue);

            }


        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE getRowDataAsList " + e.getMessage());
        } finally {
            resetCursor();
        }


        return rowDataAsLst;
    }


    /*
    TODO get the data of particular cell
     */

    public static String getCellData(int rowNum, int columnIndex) {
        String cellData = "";
        try {
            rs.absolute(rowNum);
            cellData = rs.getString(columnIndex);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resetCursor();
        }
        return cellData;
    }

    public static String getCellData(int rowNum, String colName) {
        String cellData = "";
        try {
            rs.absolute(rowNum);
            cellData = rs.getString(colName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            resetCursor();
        }
        return cellData;
    }

    /*
    TODO get the list of string data od entire column
     */
    public static List<String> getColumnDataAsList(int columnIndex) {
        List<String> columnDataList = new ArrayList<>();
        try {
            rs.beforeFirst();
            while (rs.next()) {
                String calValue = rs.getString(columnIndex);
                columnDataList.add(calValue);
            }
        } catch (SQLException e) {
            System.out.println("get the list of string data od entire column failed " + e.getMessage());
        } finally {
            resetCursor();
        }
        return columnDataList;
    }

    public static List<String> getColumnDataAsList(String columnName) {
        List<String> columnDataList = new ArrayList<>();
        try {
            rs.beforeFirst();
            while (rs.next()) {
                String calValue = rs.getString(columnName);
                columnDataList.add(calValue);
            }
        } catch (SQLException e) {
            System.out.println("get the list of string data od entire column failed " + e.getMessage());
        } finally {
            resetCursor();
        }
        return columnDataList;
    }

    /*
    TODO display all  the data
     */
    public static void displayAllData() {

        int columnCount = getColumnCount();
        resetCursor();
        try {

            while (rs.next()) {

                for (int colIndex = 1; colIndex <= columnCount; colIndex++) {

                    //System.out.print( rs.getString(colIndex) + "\t" );
                    System.out.printf("%-25s", rs.getString(colIndex));
                }
                System.out.println();

            }

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE displayAllData " + e.getMessage());
        } finally {
            resetCursor();
        }

    }

    /*
    TODO get the map of one row and column name
     */
    public static void resetCursor() {
        try {
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Map<String, String> getRowMap(int rowNum) {
        Map<String, String> map = new LinkedHashMap<>();
        try {
            rs.absolute(rowNum);
            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {
                String keyColName = rsmd.getColumnName(colNum);
                String cellData = getCellData(rowNum, colNum);
                map.put(keyColName, cellData);
            }
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            resetCursor();
        }

        return map;
    }

    /*
    TODO get list of maps row objects
     */
    public static List<Map<String, String>> getAllDataAsListOfMap() {
        List<Map<String, String>> listOfMap = new ArrayList<>();
        for (int rowNum = 1; rowNum <= getRowCount(); rowNum++) {
            listOfMap.add(getRowMap(rowNum));
        }
        resetCursor();
        return listOfMap;
    }

    /*
    TODO get first sel  from first column
     */
    public static String getFirstCellData() {

        return getCellData(1, 1);
    }

}
