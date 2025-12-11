package com.beethere.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.beethere.utils.sanitizer.Sanitize;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;
	private Integer employeeId;
	private String employeeName;
	private Date startTime;
	private Date endTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "reservation_room",
        joinColumns = @JoinColumn(name = "reservation_id"),
        inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<Room> rooms = new HashSet<>();

    private static final Logger APPLICATION_LOGGER = LogManager.getLogger("Application");

    public Reservation() {
        APPLICATION_LOGGER.debug("Constructing Default Reservation");
    }

    public Reservation(Integer reservationId, Integer employeId, String employeeName, Date startTime, Date endTime) {
        APPLICATION_LOGGER.debug("Constructing Reservation");
        setReservationId(reservationId);
        setEmployeeId(employeId);
        setEmployeeName(employeeName);
        setStartTime(startTime);
        setEndTime(endTime);
    }

    public Integer getReservationId() {
        APPLICATION_LOGGER.debug("Getting reservation ID");
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        APPLICATION_LOGGER.debug("Setting reservation ID");
        if (reservationId <= 0){
            APPLICATION_LOGGER.error("Reservation ID cannot be <= 0");
            throw new IllegalArgumentException("Reservation ID cannot be <= 0");
        }
        this.reservationId = reservationId;
    }

    public Integer getEmployeeId() {
        APPLICATION_LOGGER.debug(employeeName, APPLICATION_LOGGER);
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        APPLICATION_LOGGER.debug("Setting employee ID");
        if (employeeId <= 0){
            APPLICATION_LOGGER.error("Employee ID cannot be <= 0");
            throw new IllegalArgumentException("Employee ID cannot be <= 0");
        }
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        APPLICATION_LOGGER.debug("Getting employee name");
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        APPLICATION_LOGGER.debug("Setting employee name");
        String cleanEmployeeName = Sanitize.sanitizeHtml(employeeName);
        if (cleanEmployeeName == null || cleanEmployeeName.isEmpty()) {
            APPLICATION_LOGGER.error("Employee name cannot be null or empty");
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        this.employeeName = cleanEmployeeName;
    }

    public Date getStartTime() {
        APPLICATION_LOGGER.debug("Getting start time");
        return startTime;
    }

    public void setStartTime(Date startTime) {
        APPLICATION_LOGGER.debug("Setting start time");
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
        APPLICATION_LOGGER.debug("Getting end time");
        return endTime;
    }

    public void setEndTime(Date endTime) {
        APPLICATION_LOGGER.debug("Setting end time");
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
