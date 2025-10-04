package model;
import java.sql.SQLOutput;
import java.time.LocalDate;

public class HabitLog {
    private LocalDate date;
    private boolean status;

    public HabitLog(boolean status){
        this.date = LocalDate.now();
        this.status = status;
    }
    public HabitLog(LocalDate date, boolean status){
        this.date = date;
        this.status = status;
    }

    public String toString(){
        return this.date + "→" + (this.status ? "Completed" : "Incomplete");
    }
}
