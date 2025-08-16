package com.example.myapplication.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Vacations")
public class Vacations {
    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String VacationName;
    private String HotelName;

    public Vacations(int vacationID, String VacationName, String HotelName) {
        this.vacationID = vacationID;
        this.VacationName = VacationName;
        this.HotelName = HotelName;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return VacationName;
    }

    public void setVacationName(String vacationName) {
        VacationName = vacationName;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String hotelName) {
        HotelName = hotelName;
    }
}
