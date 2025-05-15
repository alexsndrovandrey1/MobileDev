package ru.mirea.alexsandrovaa.employeedb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class}, version = 2)  // ← увеличь версию
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDao employeeDao();
}