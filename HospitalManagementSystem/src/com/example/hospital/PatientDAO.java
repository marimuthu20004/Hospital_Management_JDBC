package com.example.hospital;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PatientDAO {

	    public int addPatient(Patient p) {
	        String sql = "INSERT INTO patients (name, age, gender, phone, address) VALUES (?, ?, ?, ?, ?)";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	            ps.setString(1, p.getName());
	            ps.setInt(2, p.getAge());
	            ps.setString(3, p.getGender());
	            ps.setString(4, p.getPhone());
	            ps.setString(5, p.getAddress());
	            int affected = ps.executeUpdate();
	            if (affected == 0) return -1;
	            try (ResultSet keys = ps.getGeneratedKeys()) {
	                if (keys.next()) {
	                    int id = keys.getInt(1);
	                    p.setId(id);
	                    return id;
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return -1;
	    }

	    public Patient getPatient(int id) {
	        String sql = "SELECT * FROM patients WHERE patient_id = ?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    Patient p = new Patient();
	                    p.setId(rs.getInt("patient_id"));
	                    p.setName(rs.getString("name"));
	                    p.setAge(rs.getInt("age"));
	                    p.setGender(rs.getString("gender"));
	                    p.setPhone(rs.getString("phone"));
	                    p.setAddress(rs.getString("address"));
	                    return p;
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return null;
	    }

	    public List<Patient> getAllPatients() {
	        List<Patient> list = new ArrayList<>();
	        String sql = "SELECT * FROM patients ORDER BY created_at DESC";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Patient p = new Patient();
	                p.setId(rs.getInt("patient_id"));
	                p.setName(rs.getString("name"));
	                p.setAge(rs.getInt("age"));
	                p.setGender(rs.getString("gender"));
	                p.setPhone(rs.getString("phone"));
	                p.setAddress(rs.getString("address"));
	                list.add(p);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return list;
	    }

	    public boolean updatePatient(Patient p) {
	        String sql = "UPDATE patients SET name=?, age=?, gender=?, phone=?, address=? WHERE patient_id=?";
	        try (Connection conn = DBConnection.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, p.getName());
	            ps.setInt(2, p.getAge());
	            ps.setString(3, p.getGender());
	            ps.setString(4, p.getPhone());
	            ps.setString(5, p.getAddress());
	            ps.setInt(6, p.getId());
	            return ps.executeUpdate() > 0;
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return false;
	    }

	    public boolean deletePatient(int id) {
	        String sql = "DELETE FROM patients WHERE patient_id = ?";
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
