import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

interface MeetingSchedule {

    String[] daysOfWeek = { "Sun.", "Mon.", "Tue.", "Wed.",
        "Thur.", "Fri.", "Sat."};

    void addHoliday(int year, int month, int date);   /* Schedules holiday on given date (mm/dd) */
    void removeHoliday(int year, int month, int date);  /* Removes scheduled holiday */
    ArrayList<Calendar> printHolidays();              /* PRINTS, and RETURNS list of holiday dates */
    ArrayList<Date> getMeetingDates();                /* PRINTS, and RETURNS list of meeting dates */
    void scheduleMeetingDay(int dayOfWeek);           /* Schedules the day of the week for meetings */
    void removeMeetingDay(int dayToRemove);           /* Removes scheduled meeting day */
}
