
import java.sql.*;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String pass = "Muthu_1146";

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(url, user, pass);

            while (true) {
                System.out.println("\n1.Add Patient\n2.View Patients\n3.View Doctors\n4.Book Appointment\n5.Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();

                switch (ch) {

                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.next();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        System.out.print("Enter Gender: ");
                        String gender = sc.next();

                        PreparedStatement ps1 = con.prepareStatement(
                                "insert into patients(name,age,gender) values(?,?,?)");
                        ps1.setString(1, name);
                        ps1.setInt(2, age);
                        ps1.setString(3, gender);
                        ps1.executeUpdate();

                        System.out.println("Patient Added ✅");
                        break;

                    case 2:
                        ResultSet rs1 = con.createStatement().executeQuery("select * from patients");
                        System.out.println("Patients List:");
                        while (rs1.next()) {
                            System.out.println(rs1.getInt("id") + " "
                                    + rs1.getString("name") + " "
                                    + rs1.getInt("age") + " "
                                    + rs1.getString("gender"));
                        }
                        break;

                    case 3:
                        ResultSet rs2 = con.createStatement().executeQuery("select * from doctors");
                        System.out.println("Doctors List:");
                        while (rs2.next()) {
                            System.out.println(rs2.getInt("id") + " "
                                    + rs2.getString("name") + " "
                                    + rs2.getString("specialization"));
                        }
                        break;

                    case 4:
                        System.out.print("Enter Patient ID: ");
                        int pid = sc.nextInt();
                        System.out.print("Enter Doctor ID: ");
                        int did = sc.nextInt();
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String date = sc.next();

                        PreparedStatement ps2 = con.prepareStatement(
                                "insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?)");
                        ps2.setInt(1, pid);
                        ps2.setInt(2, did);
                        ps2.setString(3, date);
                        ps2.executeUpdate();

                        System.out.println("Appointment Booked ✅");
                        break;

                    case 5:
                        System.out.println("Thank You!");
                        con.close();
                        return;

                    default:
                        System.out.println("Invalid Choice ❌");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
