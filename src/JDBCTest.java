import java.sql.*;
import java.util.Objects;
import java.util.Scanner;
import java.lang.String;

public class JDBCTest {

    public static void main(String[] args) {
        System.out.println("Hello database users! We are going to call DB");
        try {
//            demoCreate();
            String nuser = readStringConsole("userul pentru care doriti aduagare in agenda ");
            demoCreateAG(nuser);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        private static void demoCreate() throws ClassNotFoundException, SQLException {

            // 1. load driver class.forname-incarc driverul
            Class.forName("org.postgresql.Driver");

            // 2. define connection params to db
            final String URL = "jdbc:postgresql://54.93.65.5:5432/4_Simona";
            final String USERNAME = "fasttrackit_dev";
            final String PASSWORD = "fasttrackit_dev";

            // 3. obtain a connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 4. create a query statement
            Boolean k;
            PreparedStatement pSt = conn.prepareStatement("INSERT INTO users (nume, parola) VALUES (?,?)");
            while (k=true) {
                String n = readStringConsole("userul: ");
                String p = readStringConsole("parola: ");
                pSt.setString(1, n);
                pSt.setString(2, p);

                // 5. execute a prepared statement
                int rowsInserted = pSt.executeUpdate();
                int kk = readIntConsole("mai doriti sa adaugati useri? 1/0: ");
                if (kk==1) {k=false;}
            }

            // 6. close the objects
            pSt.close();
            conn.close();
        }
//adaugare date in Agenda
    private static void demoCreateAG(String nuser) throws ClassNotFoundException, SQLException {

        // 1. load driver class.forname-incarc driverul
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/4_Simona";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        // 3.1. cauta userul in users
        Statement st = conn.createStatement();
         System.out.println("userul pentru care doriti adaugare date este: " + nuser);

        ResultSet rs = st.executeQuery("Select idu FROM users where nume='nuser'");
        String aidi = rs.getString("idu");
        System.out.println("are ID: " + aidi);

        // 4. adaugare date in agenda pentru un user anume
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO agendas (idu, nume, prenume, telefon) VALUES (?,?,?,?)");
        pSt.setString(1, aidi);
            String nn = readStringConsole("nume: ");
            String pp = readStringConsole("prenume: ");
            String t= readStringConsole("nr telefon: ");
            pSt.setString(2, nn);
            pSt.setString(3, pp);
            pSt.setString(4, t);



//            // 5. execute a prepared statement
//            int rowsInserted = pSt.executeUpdate();


        // 6. close the objects
        rs.close();
        st.close();
        conn.close();
    }


    public static String readStringConsole(String label) {
        System.out.print(label);
        String input = new Scanner(System.in).nextLine();
        return input;
    }
    public static int readIntConsole(String label) {
        System.out.print(label);
        int input = new Scanner(System.in).nextInt();
        return input;
    }
}
