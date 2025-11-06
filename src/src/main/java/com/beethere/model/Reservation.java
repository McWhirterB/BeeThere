package com.beethere.model;

import java.util.*;

import com.beethere.utils.sanitizer.Sanitize;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
public class Reservation {
	@Id
	public Integer reservationId;
	public Integer employeeId;
	public String employeeName;
	public Date startTime;
	public Date endTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "reservation_room",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<Room> rooms = new HashSet<>();

    public Reservation() {}

    public Reservation(Integer reservationId, Integer employeId, String employeeName, Date startTime, Date endTime) {
        setReservationId(reservationId);
        setEmployeeId(employeId);
        setEmployeeName(employeeName);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        if (reservationId < 0){
            //LOG.error("Reservation ID cannot be less than 0");
            throw new IllegalArgumentException("Reservation ID cannot be less than 0");
        }
        this.reservationId = reservationId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        String cleanEmployeeName = Sanitize.sanitizeHtml(employeeName);
        this.employeeName = cleanEmployeeName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        Date now = new Date();
        if (endTime != null) {
            if (startTime == null){
                throw new IllegalArgumentException("Start time cannot be null");
            }
            if (startTime.after(endTime)) {
                throw new IllegalArgumentException("Start time cannot be after end time");
            }
            if (startTime.equals(endTime)) {
                throw new IllegalArgumentException("Start time cannot be the same as end time");
            }
            if (startTime.before(now)) {
                throw new IllegalArgumentException("Start time cannot be in the past");
            }
        }
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        Date now = new Date();
        if (startTime != null) {
            if (endTime == null){
                throw new IllegalArgumentException("End time cannot be null");
            }
            if (endTime.before(startTime)) {
                throw new IllegalArgumentException("End time cannot be before start time");
            }
            if (endTime.equals(startTime)) {
                throw new IllegalArgumentException("End time cannot be the same as start time");
            }
            if (endTime.before(now)) {
                throw new IllegalArgumentException("End time cannot be in the past");
            }
        }
        this.endTime = endTime;
    }

    public Set<Room> getRooms() { return rooms; }
    public void setRooms(Set<Room> rooms) { this.rooms = rooms; }
}