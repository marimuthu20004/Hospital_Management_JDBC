package com.example.hospital;

public class Doctor {

	    private int id;
	    private String name;
	    private String specialization;
	    private String phone;
	    private String email;

	    public Doctor() {}

	    public Doctor(String name, String specialization, String phone, String email) {
	        this.name = name;
	        this.specialization = specialization;
	        this.phone = phone;
	        this.email = email;
	    }

	    // getters & setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }
	    public String getSpecialization() { return specialization; }
	    public void setSpecialization(String specialization) { this.specialization = specialization; }
	    public String getPhone() { return phone; }
	    public void setPhone(String phone) { this.phone = phone; }
	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    @Override
	    public String toString() {
	        return "Doctor{" +
	                "id=" + id + ", name='" + name + '\'' +
	                ", specialization='" + specialization + '\'' +
	                ", phone='" + phone + '\'' +
	                ", email='" + email + '\'' +
	                '}';
	    }
}
