package com.example.hospital;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
public class AppoinmentDAO {
	
	    public int addAppointment(Appoinment a) {
	        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, notes) VALUES (?, ?, ?, ?)";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            ps.setInt(1, a.getPatientId());
	            ps.setInt(2, a.getDoctorId());
	            // Convert LocalDateTime to Timestamp
	            Timestamp ts = Timestamp.valueOf(a.getAppointmentDate());
	            ps.setTimestamp(3, ts);
	            ps.setString(4, a.getNotes());
	            int affected = ps.executeUpdate();
	            if (affected == 0) return -1;
	            try (ResultSet keys = ps.getGeneratedKeys()) {
	                if (keys.next()) {
	                    int id = keys.getInt(1);
	                    a.setId(id);
	                    return id;
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return -1;
	    }

	    public Appoinment getAppointment(int id) {
	        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    Appoinment a = new Appoinment();
	                    a.setId(rs.getInt("appointment_id"));
	                    a.setPatientId(rs.getInt("patient_id"));
	                    a.setDoctorId(rs.getInt("doctor_id"));
	                    Timestamp ts = rs.getTimestamp("appointment_date");
	                    if (ts != null) {
	                        a.setAppointmentDate(ts.toLocalDateTime());
	                    }
	                    a.setNotes(rs.getString("notes"));
	                    return a;
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return null;
	    }

	    public List<Appoinment> getAllAppointments() {
	        List<Appoinment> list = new ArrayList<>();
	        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Appoinment a = new Appoinment();
	                a.setId(rs.getInt("appointment_id"));
	                a.setPatientId(rs.getInt("patient_id"));
	                a.setDoctorId(rs.getInt("doctor_id"));
	                Timestamp ts = rs.getTimestamp("appointment_date");
	                if (ts != null) a.setAppointmentDate(ts.toLocalDateTime());
	                a.setNotes(rs.getString("notes"));
	                list.add(a);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return list;
	    }

	    public boolean updateAppoinment(Appoinment a) {
	        String sql = "UPDATE appointments SET patient_id=?, doctor_id=?, appointment_date=?, notes=? WHERE appointment_id=?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, a.getPatientId());
	            ps.setInt(2, a.getDoctorId());
	            ps.setTimestamp(3, Timestamp.valueOf(a.getAppointmentDate()));
	            ps.setString(4, a.getNotes());
	            ps.setInt(5, a.getId());
	            return ps.executeUpdate() > 0;
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }

	    public boolean deleteAppoinment(int id) {
	        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            return ps.executeUpdate() > 0;
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }
}
