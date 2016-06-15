import java.sql.*;
import java.lang.String;

public class JDBCTest extends read_print {
    public static void main(String[] args) {
        System.out.println("Hello database users! We are going to call DB");
        int optiune = 0;

        do {
            printConsole("Meniul principal:");
            printConsole("1 - listare 'users'/ 2 - Creare date in user / 3 - Cautare user /");
            printConsole (" 4 - adaugare date agenda pentru un user / 5 - Stergere user /6-exit");
            printConsole("- * - * - * - * - * - * - * - *- * - * - * - * - -* - *-");

            optiune = readIntConsole("Alegeti optiunea dorita:");
            System.out.println();

            if (optiune == 1) {
                try {
                    demoRead();
                    System.out.println();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (optiune == 2) {

                try {
                    demoCreate();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (optiune == 3) {
                String nume = readStringConsole("Introduceti numele cautat: ");
                try {
                    demoSearch(nume);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (optiune == 4) {
                String nume = readStringConsole("Introduceti userul pentru care vreti aduagare date in agenda:");
                String numeNou = readStringConsole("Introduceti numele agenda:");
                String prenumeNou = readStringConsole("Introduceti prenume agenda");
                String telefonNou = readStringConsole("Introduceti numarul de telefon:");
                try {
                    demoCreateAG(nume, numeNou, prenumeNou, telefonNou);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (optiune == 5) {
                String nume = readStringConsole("ce user vreti sa stergeti:");

                try {
                    demoDelete(nume);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            }
            while (optiune != 6) ;

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
        Boolean k = true;
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO users (nume, parola) VALUES (?,?)");
        while (k) {
            String n = readStringConsole("userul: ");
            String p = readStringConsole("parola: ");
            pSt.setString(1, n);
            pSt.setString(2, p);

            // 5. execute a prepared statement
            int rowsInserted = pSt.executeUpdate();
            String kk = readStringConsole("mai doriti sa adaugati useri? Y/N: ");
            if (kk.equals("N")) {
                k = false;
            }
        }
        // 6. close the objects
        pSt.close();
        conn.close();
    }

    //adaugare date in Agenda
    private static void demoCreateAG(String nuser, String numeNou, String prenumeNou, String telefonNou) throws ClassNotFoundException, SQLException {

        // 1. load driver class.forname-incarc driverul
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/4_Simona";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 3.1. cauta userul in tabela users
        Statement st = conn.createStatement();
        System.out.println("adaugam date pentru :  " + nuser);
        ResultSet rs = st.executeQuery("Select idu FROM users where nume='" + nuser + "'");
         int aidi=0;
        // vezi ID , campul idu in tabela users
        while (rs.next()) {
            aidi = rs.getInt("idu");
            System.out.println("are ID: " + aidi);}

        // 4. adaugare date in agenda pentru un user anume
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO agendas (idu, nume, prenume, telefon) VALUES (?,?,?,?)");
        pSt.setInt(1, aidi);
        pSt.setString(2, numeNou);
        pSt.setString(3, prenumeNou);
        pSt.setString(4, telefonNou);
        // 5. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();
        //6. close the objects
        rs.close();
        st.close();
        conn.close();
    }

    private static void demoRead() throws ClassNotFoundException, SQLException {

        // 1. load driver class.forname-incarc driverul
        Class.forName("org.postgresql.Driver");
        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/4_Simona";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";
        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT nume,parola FROM users");

        // 6. iterate the result set and print the values
        while (rs.next()) {
            System.out.print(rs.getString("nume").trim());
            System.out.print("---");
            System.out.println(rs.getString("parola").trim());
        }
        // 7. close the objects
        rs.close();
        st.close();
        conn.close();

    }

    private static void demoSearch(String n) throws ClassNotFoundException, SQLException {

        // 1. load driver class.forname-incarc driverul
        Class.forName("org.postgresql.Driver");
        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/4_Simona";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";
        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT nume,parola FROM users WHERE nume='" + n + "'");

        if (!rs.isBeforeFirst()) {
            System.out.println("Numele nu este in agenda");
        }
        // 6. iterate the result set and print the values
        while (rs.next()) {
            System.out.println("numele cautat se afla in users cu datele");
            System.out.print(rs.getString("nume").trim());
            System.out.print("---");
            System.out.println(rs.getString("parola").trim());
        }
        // 7. close the objects
        rs.close();
        st.close();
        conn.close();

    }
    private static void demoDelete(String num) throws ClassNotFoundException, SQLException {

        // 1. load driver class.forname-incarc driverul
        Class.forName("org.postgresql.Driver");
        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/4_Simona";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";
        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("DELETE FROM users WHERE nume='" + num + "'");
        // 5. execute a prepared statement
        int rowsD = pSt.executeUpdate();

        System.out.println(rowsD + " linie stearsa");

        // 6. close the objects
        pSt.close();
        conn.close();
    }

}
