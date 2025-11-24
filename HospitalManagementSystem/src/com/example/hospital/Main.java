package com.example.hospital;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
public class Main {

	    private static final PatientDAO patientDAO = new PatientDAO();
	    private static final DoctorDAO doctorDAO = new DoctorDAO();
	    private static final AppoinmentDAO appointmentDAO = new AppoinmentDAO();
	    private static final Scanner sc = new Scanner(System.in);
	    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	    public static void main(String[] args) {
	        while (true) {
	            showMenu();
	            int choice = readInt("Choose: ");
	            switch (choice) {
	                case 1 -> addSamplePatient();
	                case 2 -> listPatients();
	                case 3 -> addSampleDoctor();
	                case 4 -> listDoctors();
	                case 5 -> createAppointment();
	                case 6 -> listAppointments();
	                case 7 -> {
	                    System.out.println("Exiting...");
	                    System.exit(0);
	                }
	                default -> System.out.println("Invalid option");
	            }
	        }
	    }

	    private static void showMenu() {
	        System.out.println("\n--- Hospital Management (simple) ---");
	        System.out.println("1. Add patient");
	        System.out.println("2. List patients");
	        System.out.println("3. Add doctor");
	        System.out.println("4. List doctors");
	        System.out.println("5. Create appointment");
	        System.out.println("6. List appointments");
	        System.out.println("7. Exit");
	    }

	    private static void addSamplePatient() {
	        System.out.println("\n-- Add Patient --");
	        String name = readStr("Name: ");
	        int age = readInt("Age: ");
	        String gender = readStr("Gender: ");
	        String phone = readStr("Phone: ");
	        String address = readStr("Address: ");
	        Patient p = new Patient(name, age, gender, phone, address);
	        int id = patientDAO.addPatient(p);
	        if (id > 0) System.out.println("Patient added with id: " + id);
	        else System.out.println("Failed to add patient");
	    }

	    private static void listPatients() {
	        System.out.println("\n-- Patients --");
	        List<Patient> list = patientDAO.getAllPatients();
	        list.forEach(System.out::println);
	    }

	    private static void addSampleDoctor() {
	        System.out.println("\n-- Add Doctor --");
	        String name = readStr("Name: ");
	        String spec = readStr("Specialization: ");
	        String phone = readStr("Phone: ");
	        String email = readStr("Email: ");
	        Doctor d = new Doctor(name, spec, phone, email);
	        int id = doctorDAO.addDoctor(d);
	        if (id > 0) System.out.println("Doctor added with id: " + id);
	        else System.out.println("Failed to add doctor");
	    }

	    private static void listDoctors() {
	        System.out.println("\n-- Doctors --");
	        List<Doctor> list = doctorDAO.getAllDoctors();
	        list.forEach(System.out::println);
	    }

	    private static void createAppointment() {
	        System.out.println("\n-- Create Appointment --");
	        int patientId = readInt("Patient ID: ");
	        int doctorId = readInt("Doctor ID: ");
	        String dateStr = readStr("Date and time (yyyy-MM-dd HH:mm) e.g. 2025-12-25 14:30: ");
	        LocalDateTime dt;
	        try {
	            dt = LocalDateTime.parse(dateStr, dtf);
	        } catch (Exception e) {
	            System.out.println("Invalid date format.");
	            return;
	        }
	        String notes = readStr("Notes: ");
	        Appoinment a = new Appoinment(patientId, doctorId, dt, notes);
	        int id = appointmentDAO.addAppointment(a);
	        if (id > 0) System.out.println("Appointment created with id: " + id);
	        else System.out.println("Failed to create appointment");
	    }

	    private static void listAppointments() {
	        System.out.println("\n-- Appointments --");
	        List<Appoinment> list = appointmentDAO.getAllAppointments();
	        list.forEach(System.out::println);
	    }

	    // helper read methods
	    private static String readStr(String prompt) {
	        System.out.print(prompt);
	        return sc.nextLine().trim();
	    }

	    private static int readInt(String prompt) {
	        System.out.print(prompt);
	        String s = sc.nextLine().trim();
	        try {
	            return Integer.parseInt(s);
	        } catch (NumberFormatException e) {
	            return -1;
	        }
	    }
}
