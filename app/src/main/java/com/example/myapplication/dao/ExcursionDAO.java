package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.entities.Excursions;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursions excursion);

    @Update
    void update(Excursions excursion);

    @Delete
    void delete(Excursions excursion);

    @Query("SELECT * FROM excursions ORDER BY excursionID ASC")
    List<Excursions> getAllExcursions();

    @Query("SELECT * FROM excursions WHERE vacationID = :productID ORDER BY excursionid ASC ")
    List<Excursions> getAssociatedExcursions(int productID);

}
