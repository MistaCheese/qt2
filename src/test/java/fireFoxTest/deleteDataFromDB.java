package fireFoxTest;

import java.sql.*;

import static devTools.wayToDriver.*;

public class deleteDataFromDB {
    public static void users(String userLogin, String table) {

        Connection connection = null;
        ResultSet rs = null;
        int rs1 = 0;
        String id = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            rs = st.executeQuery("SELECT id FROM userentity WHERE login ='" + userLogin + "'");

            while (rs.next()) {
                id = rs.getString(1);
            }

            if (table.equals("doctors")) {
                st.executeUpdate("DELETE FROM medicalexamination WHERE doctor_id ='" + id + "'");

            }

            st.executeUpdate("DELETE FROM " + table + " WHERE id ='" + id + "'");
            st.executeUpdate("DELETE FROM userentity WHERE login = '" + userLogin + "'");
            st.close();
            rs.close();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    public static String getIdUser(String userLogin, String table) {
        Connection connection = null;
        ResultSet rs = null;
        String id = null;


        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            rs = st.executeQuery("SELECT id FROM " + table + " WHERE login ='" + userLogin + "'");

            while (rs.next()) {
                id = rs.getString(1);
            }


            st.close();
            rs.close();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();


        }
        return id;
    }
}