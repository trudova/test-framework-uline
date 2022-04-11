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
        String userName = ConfigurationReader.getProperty("uline.database.username");
        String password = ConfigurationReader.getProperty("uline.database.password");

        try {
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("CONNECTION WAS SUCCESSFUL");
        } catch (SQLException e) {
            System.out.println("ConNection failed " + e.getMessage());
        }
    }

    /*
       TODO create a Result Set
        */
    public static ResultSet runQuery(String query) {
        try {
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = statement.executeQuery(query);
            System.out.println("RS was created");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + "ERROR WHILE CREATE A RESULT SET");
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
            e.printStackTrace();
        }

    }

    /*
        TODO get the row count in the table
         */
    public static int getRowCount() {
        int rowCount = 0;
        try {
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return columnCount;
    }

    /*
        TODO return all the column name as a list
         */
    public static List<String> getAllColumnNAmes() {
        List<String> columnNames = new ArrayList<>();

        try {
            for (int col = 1; col <= getColumnCount(); col++) {
                String colName = rsmd.getColumnName(col);
                columnNames.add(colName);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        resetCursor();
        return columnNames;
    }

    public static void resetCursor() {
        try {
            rs.beforeFirst();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
        TODO get entire row data as a list of string
         */
    public static List<String> getRowDAtaAsList(int rowNum) {
        List<String> rowDataList = new ArrayList<>();
        try {
            rs.absolute(rowNum);
            for (int col = 1; col <= getColumnCount(); col++) {
                String cellValue = rs.getString(col);
                rowDataList.add(cellValue);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return rowDataList;
    }

     /*
    TODO get the data of particular cell
     */

    public static String getCellData(int rowNum, int columnIndex) {
        String cellData = "";
        try {
            rs.absolute(2);
            cellData = rs.getString(columnIndex);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
        List<String> listOfColCell = new ArrayList<>();
        resetCursor();
        try {
            while (rs.next()) {
               String cellValue= rs.getString(columnIndex);
               listOfColCell.add(cellValue);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resetCursor();
        return listOfColCell;
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
    public static void  displayAllData(){
        resetCursor();
        int colCount=getColumnCount();
        try {
            while (rs.next()) {
                for (int col =1;col<=colCount; col++){
                    System.out.printf("%-25s",rs.getString(col));
                }
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resetCursor();

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
        }

        return map;
    }
    /*
   TODO get list of maps row objects
    */
    public static List<Map<String, String>> getAllDataAsListOfMap() {
        List<Map<String ,String >> listOfMap = new ArrayList<>();
        for (int rowNum =1;rowNum<= getRowCount();rowNum++){
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
