/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Itzik
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

    String host = "localhost";
    String port = "3306";
    String dbName = "elbitstoragetesting";

    String username = "root";
    String pass = "root";

    private Connection conn;

    private Statement stmt;

    private DataBase db;

    DataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + this.host + ":3306/" + this.dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            this.conn = DriverManager.getConnection(url, this.username, this.pass);
            this.stmt = this.conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized String getDbName() {
        return this.dbName;
    }

    private synchronized void init() {
        try {
            if (this.db == null) {
                this.db = new DataBase();
            }
            if (this.conn.isClosed()) {
                String url = "jdbc:mysql://" + this.host + ":3306/" + this.dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                this.conn = DriverManager.getConnection(url, this.username, this.pass);
                this.stmt = this.conn.createStatement();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createTables(String tableName, boolean tablesExistAlready) {
        init();

        try {
            if (tablesExistAlready) {
                this.stmt.executeUpdate("Drop TABLE " + tableName);
            }
            String table = "CREATE TABLE ";

            this.stmt.executeUpdate(table);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            closeConnection();
        }
    }

    public synchronized boolean userAuth(String username, String password) {
        init();
        ResultSet result = null;
        String db_user = "select password from users where username = '" + username + "'";

        try {
            result = this.stmt.executeQuery(db_user);
            if (!result.isBeforeFirst()) {
                return false;
            }
            result.next();
            if (!password.equals(result.getString(1))) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            closeConnection();
        }
        return true;
    }

    public synchronized boolean isAdmin(String username) {
        init();
        ResultSet result = null;
        String db_user = "select is_admin from users where username = '" + username + "'";

        try {
            result = this.stmt.executeQuery(db_user);
            if (!result.isBeforeFirst()) {
                return false;
            }
            result.next();
            if (result.getString(1).equals("0")) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            closeConnection();
        }
        return true;
    }

    public synchronized ArrayList<String[]> getTable(String table_name) {
        init();
        ResultSet result = null;
        String db_table = "select * from " + table_name;

        try {
            result = this.stmt.executeQuery(db_table);
            if (!result.isBeforeFirst()) {
                return null;
            }
            ArrayList<String[]> table = (ArrayList) new ArrayList<>(1);
            int index = 0;
            while (result.next()) {
                ResultSetMetaData rsmd = result.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String[] row_array = new String[columnsNumber];
                for (int i = 1; i <= columnsNumber; i++) {
                    row_array[i - 1] = result.getString(i);
                }
                table.add(row_array);
                index++;
            }
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            closeConnection();
        }
    }

    public synchronized ArrayList<String[]> search(String table_name, String term) {
        init();
        ResultSet result = null;
        String search_columns = "name , part_number , serial_number ";

        String db_table = "SELECT * FROM " + table_name + " WHERE " + search_columns.split(",")[0] + "  LIKE '%" + term + "%' or " + search_columns.split(",")[1] + " LIKE '%" + term + "%' or " + search_columns.split(",")[2] + " LIKE '%" + term + "%' ";
        if (table_name.contains("baz")) {
            search_columns = "name , part_number , serial_number , iaf_number";
            db_table = db_table + "or " + search_columns.split(",")[3] + " LIKE '%" + term + "%' ";
        }
        try {
            result = this.stmt.executeQuery(db_table);
            if (!result.isBeforeFirst()) {
                return null;
            }
            ArrayList<String[]> table = (ArrayList) new ArrayList<>(1);
            int index = 0;
            while (result.next()) {
                ResultSetMetaData rsmd = result.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String[] row_array = new String[columnsNumber];
                for (int i = 1; i <= columnsNumber; i++) {
                    row_array[i - 1] = result.getString(i);
                }
                table.add(row_array);
                index++;
            }
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            closeConnection();
        }
    }

    public synchronized ArrayList<String[]> searchByShelf(String table_name, String term) {
        init();
        ResultSet result = null;
        String search_columns = "location_at_storage ";
        String db_table = "SELECT * FROM " + table_name + " WHERE " + search_columns + "  LIKE '" + term + "' or " + search_columns + " LIKE '" + term + "-%' ";

        try {
            result = this.stmt.executeQuery(db_table);
            if (!result.isBeforeFirst()) {
                return null;
            }
            ArrayList<String[]> table = (ArrayList) new ArrayList<>(1);
            int index = 0;
            while (result.next()) {
                ResultSetMetaData rsmd = result.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                String[] row_array = new String[columnsNumber];
                for (int i = 1; i <= columnsNumber; i++) {
                    row_array[i - 1] = result.getString(i);
                }
                table.add(row_array);
                index++;
            }
            return table;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            closeConnection();
        }
    }

    public synchronized String extract(String tableName, String var, String col_given, String col_extract) {
        init();

        ResultSet result = null;
        String str = "select * from " + tableName + " where " + col_given + " = '" + var + "'";
        String ans = "";

        try {
            result = this.stmt.executeQuery(str);
            if (!result.isBeforeFirst()) {
                return null;
            }
            if (col_extract.compareTo("entire") == 0) {
                ResultSetMetaData rsmd = result.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                while (result.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        System.out.print(result.getString(i) + " ");
                    }
                    System.out.println();
                }
            } else {
                result.next();
                ans = result.getString(col_extract);
            }
        } catch (Exception e) {
            return null;
        } finally {

            closeConnection();
        }
        return ans;
    }

    public synchronized String[] getItemSerials(String tableName, String id) {
        init();

        ResultSet result = null;
        String str = "select * from " + tableName + " where " + tableName + "_id = '" + id + "'";
        String[] ans = new String[0];
        try {
            result = this.stmt.executeQuery(str);
            if (!result.isBeforeFirst()) {
                return null;
            }

            result.next();
            if (result.getString("serial_number") != null) {
                List<String> list = new ArrayList<>(Arrays.asList(result.getString("serial_number").trim().split(",")));
                for (int i = 0; i < list.size(); i++) {
                    if (((String) list.get(i)).equals("")) {
                        list.remove(i);
                    }
                }
                ans = list.toArray(ans);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            closeConnection();
        }
        return ans;
    }

    public synchronized Object updateItemQuntity(String table_name, String id, int one, String user_name, String update_serials) {
        init();

        ResultSet result = null;
        String str = "select * from " + table_name + " where " + table_name + "_id = '" + id + "';";

        String quntity = "0";
        String name = "";
        try {
            result = this.stmt.executeQuery(str);
            if (!result.isBeforeFirst()) {
                System.out.println("no result found!given id = " + id);
            }

            result.next();
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            for (int i = 1; i <= columnsNumber; i++) {
                if (rsmd.getColumnLabel(i).equals("quntity_at_storage")) {
                    if (!result.getString(i).equals("")) {
                        quntity = result.getString(i);
                    }
                    if (quntity.equals("0") && one == -1) {
                        return Boolean.valueOf(false);
                    }
                }
                if (rsmd.getColumnLabel(i).equals("name")) {
                    name = result.getString(i);
                }
            }

            String update_quntity = "UPDATE " + table_name + "  SET quntity_at_storage = '" + (Integer.parseInt(quntity) + one) + "'  , serial_number = '" + update_serials + "' WHERE " + table_name + "_id = '" + id + "';";

            this.stmt.executeUpdate(update_quntity);
            String action = "Reduce quntity";
            if (one == 1) {
                action = "Add quntity";
            }
            String log = "INSERT INTO log (user , item , action , timestamp , simulator) VALUES ('" + user_name + "' , '" + name + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , '" + table_name.replace("_new", "") + "');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object insertItem(String table_name, String name, String pn, String sn, String prn, String qas, String sb, String qasys, String las, String comments, String user_name) {
        init();

        String str = "INSERT INTO " + table_name + "(name , part_number , serial_number , producer_name , quntity_at_storage , supply_by , quntity_at_system , location_at_storage , comments)VALUES('" + name + "','" + pn + "','" + sn + "','" + prn + "','" + qas + "','" + sb + "','" + qasys + "','" + las + "','" + comments + "');";

        if (table_name.equals("baz_new")) {
            str = "INSERT INTO " + table_name + "(name , part_number , serial_number , iaf_number , quntity_at_storage , loan_from_iaf , aircraft_type , location_at_storage , comments)VALUES('" + name + "','" + pn + "','" + sn + "','" + prn + "','" + qas + "','" + sb + "','" + qasys + "','" + las + "','" + comments + "');";
        }

        try {
            this.stmt.executeUpdate(str);
            String action = "insert new item";
            String log = "INSERT INTO log (user , item , action , timestamp, simulator) VALUES ('" + user_name + "' , '" + name + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , '" + table_name.replace("_new", "") + "');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object updateItem(String id, String table_name, String name, String pn, String sn, String prn, String qas, String sb, String qasys, String las, String comments, String user_name) {
        init();

        String str = "UPDATE " + table_name + "  SET name = '" + name + "' , part_number = '" + pn + "' , serial_number = '" + sn + "' , producer_name = '" + prn + "' , quntity_at_storage = '" + qas + "' , supply_by = '" + sb + "' , quntity_at_system = '" + qasys + "' , location_at_storage = '" + las + "' , comments = '" + comments + "' WHERE " + table_name + "_id = '" + id + "';";

        if (table_name.equals("baz_new")) {
            str = "UPDATE " + table_name + "  SET name = '" + name + "' , part_number = '" + pn + "' , serial_number = '" + sn + "' , iaf_number = '" + prn + "' , quntity_at_storage = '" + qas + "' , loan_from_iaf = '" + sb + "' , aircraft_type = '" + qasys + "' , location_at_storage = '" + las + "' , comments = '" + comments + "' WHERE " + table_name + "_id = '" + id + "';";
        }

        try {
            this.stmt.executeUpdate(str);
            String action = "update item description";
            String log = "INSERT INTO log (user , item , action , timestamp, simulator) VALUES ('" + user_name + "' , '" + name + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , '" + table_name.replace("_new", "") + "');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object deleteRow(String table_name, String id, String name, String user_name) {
        init();
        String str = "DELETE FROM " + table_name + " where " + table_name + "_id = '" + id + "';";

        try {
            this.stmt.executeUpdate(str);
            String action = "delete row";
            String log = "INSERT INTO log (user , item , action , timestamp, simulator) VALUES ('" + user_name + "' , '" + name + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , '" + table_name.replace("_new", "") + "');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object deleteUser(String creator, String username) {
        init();
        String str = "DELETE FROM users where username = '" + username + "';";

        try {
            this.stmt.executeUpdate(str);
            String action = "delete user";
            String log = "INSERT INTO log (user , item , action , timestamp , simulator) VALUES ('" + creator + "' , '" + username + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , 'DB');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized ArrayList searchUser(String username) {
        init();
        ResultSet result = null;
        String str = "SELECT * FROM users where username = '" + username + "';";

        ArrayList<String> ans = new ArrayList();
        try {
            result = this.stmt.executeQuery(str);
            if (!result.isBeforeFirst()) {
                ArrayList<String> a = new ArrayList();
                a.add("false");
                return a;
            }

            result.next();
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            for (int i = 1; i <= columnsNumber; i++) {
                ans.add(result.getString(i));
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            ArrayList<String> a = new ArrayList();
            a.add("false");
            a.add(e.getMessage());
            return a;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object addNewUser(String creator, String username, String password, int admin) {
        init();
        String str = "INSERT INTO users(username , password , is_admin)VALUES('" + username + "','" + password + "','" + admin + "');";

        try {
            this.stmt.executeUpdate(str);
            String action = "add new user";
            String log = "INSERT INTO log (user , item , action , timestamp , simulator) VALUES ('" + creator + "' , '" + username + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , 'DB');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object updateUser(String creator, String username, String new_username, String password, int admin) {
        init();

        String str = "UPDATE users  SET username = '" + new_username + "' , password = '" + password + "' , is_admin = '" + admin + "' WHERE username = '" + username + "';";

        try {
            this.stmt.executeUpdate(str);
            String action = "update user";
            String log = "INSERT INTO log (user , item , action , timestamp , simulator) VALUES ('" + creator + "' , '" + username + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , 'DB');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized String getImagePath(String name, String pn, String sn, String simulator) {
        init();
        ResultSet result = null;
        String str = "SELECT * FROM item_images where name = '" + name + "' and part_number = '" + pn + "' and serial_number = '" + sn + "' and simulator = '" + simulator + "';";

        String ans = null;
        try {
            result = this.stmt.executeQuery(str);
            if (!result.isBeforeFirst()) {
                return null;
            }
            result.next();
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            for (int i = 1; i <= columnsNumber; i++) {
                if (rsmd.getColumnLabel(i).equals("image_path")) {
                    ans = result.getString(i);
                }
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            closeConnection();
        }
    }

    public synchronized String getImageID(String name, String pn, String sn, String simulator) {
        init();
        ResultSet result = null;
        String str = "SELECT * FROM item_images where name = '" + name + "' and part_number = '" + pn + "' and serial_number = '" + sn + "' and simulator = '" + simulator + "';";

        String ans = null;
        try {
            result = this.stmt.executeQuery(str);
            if (!result.isBeforeFirst()) {
                return null;
            }
            result.next();
            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            for (int i = 1; i <= columnsNumber; i++) {
                if (rsmd.getColumnLabel(i).equals("id")) {
                    ans = result.getString(i);
                }
            }
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object setImage(boolean isupdate, String creator, String name, String pn, String sn, String simulator, String image_path) {
        init();
        String str = "";
        String action = "";
        if (isupdate) {
            action = "update item image";
            str = "UPDATE item_images  SET image_path = '" + image_path + "' where name = '" + name + "' and part_number = '" + pn + "' and serial_number = '" + sn + "' and simulator = '" + simulator + "'";

        } else {

            action = "set new item image";
            str = "INSERT INTO item_images(name , part_number , serial_number , simulator , image_path)VALUES('" + name + "','" + pn + "','" + sn + "','" + simulator + "','" + image_path + "');";
        }

        try {
            this.stmt.executeUpdate(str);
            String log = "INSERT INTO log (user , item , action , timestamp , simulator) VALUES ('" + creator + "' , '" + name + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , 'DB');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }

    public synchronized Object updateChangesForImageDB(String id, String creator, String name, String pn, String sn, String simulator, String image_path) {
        init();
        String str = "";
        String action = "";
        action = "update item data at image DB";
        str = "UPDATE item_images  SET name = '" + name + "' , part_number ='" + pn + "' , serial_number = '" + sn + "' where id = '" + id + "'";

        try {
            this.stmt.executeUpdate(str);
            String log = "INSERT INTO log (user , item , action , timestamp , simulator) VALUES ('" + creator + "' , '" + name + "' , '" + action + "' , '" + (new Timestamp(System.currentTimeMillis())).toString() + "' , 'DB');";
            this.stmt.executeUpdate(log);
            return Boolean.valueOf(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {

            closeConnection();
        }
    }
}
