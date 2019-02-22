/* Calendar/Scheduler Program written in Java by Albert Ki */
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

interface Scheduler {

    String[] daysOfWeek = { "Sun.", "Mon.", "Tue.", "Wed.",
        "Thur.", "Fri.", "Sat."};
    /* Mutator methods */
    void addHoliday(int month, int date);       /* Schedules holiday on given date (mm/dd) */
    void removeHoliday(int month, int date);    /* Removes scheduled holiday */
    void scheduleMeetingDay(int meetingDayOfWeek);     /* Schedules the day of the week for meetings */
    void removeMeetingDay(int dayToRemove);     /* Removes scheduled meeting day */

    /* Accessor methods */
    ArrayList<Calendar> printHolidays();        /* PRINTS, and RETURNS list of holiday dates */
    ArrayList<Calendar> getMeetingDates();          /* PRINTS, and RETURNS list of meeting dates */

}
