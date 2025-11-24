package com.example.hospital;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DoctorDAO {


	    public int addDoctor(Doctor d) {
	        String sql = "INSERT INTO doctors (name, specialization, phone, email) VALUES (?, ?, ?, ?)";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            ps.setString(1, d.getName());
	            ps.setString(2, d.getSpecialization());
	            ps.setString(3, d.getPhone());
	            ps.setString(4, d.getEmail());
	            int affected = ps.executeUpdate();
	            if (affected == 0) return -1;
	            try (ResultSet keys = ps.getGeneratedKeys()) {
	                if (keys.next()) {
	                    int id = keys.getInt(1);
	                    d.setId(id);
	                    return id;
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return -1;
	    }

	    public Doctor getDoctor(int id) {
	        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    Doctor d = new Doctor();
	                    d.setId(rs.getInt("doctor_id"));
	                    d.setName(rs.getString("name"));
	                    d.setSpecialization(rs.getString("specialization"));
	                    d.setPhone(rs.getString("phone"));
	                    d.setEmail(rs.getString("email"));
	                    return d;
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return null;
	    }

	    public List<Doctor> getAllDoctors() {
	        List<Doctor> list = new ArrayList<>();
	        String sql = "SELECT * FROM doctors ORDER BY created_at DESC";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Doctor d = new Doctor();
	                d.setId(rs.getInt("doctor_id"));
	                d.setName(rs.getString("name"));
	                d.setSpecialization(rs.getString("specialization"));
	                d.setPhone(rs.getString("phone"));
	                d.setEmail(rs.getString("email"));
	                list.add(d);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return list;
	    }

	    public boolean updateDoctor(Doctor d) {
	        String sql = "UPDATE doctors SET name=?, specialization=?, phone=?, email=? WHERE doctor_id=?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, d.getName());
	            ps.setString(2, d.getSpecialization());
	            ps.setString(3, d.getPhone());
	            ps.setString(4, d.getEmail());
	            ps.setInt(5, d.getId());
	            return ps.executeUpdate() > 0;
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }

	    public boolean deleteDoctor(int id) {
	        String sql = "DELETE FROM doctors WHERE doctor_id = ?";
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
