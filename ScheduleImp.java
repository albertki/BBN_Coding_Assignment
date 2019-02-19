/* Sources:
https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html#MONTH
https://stackoverflow.com/questions/1404210/java-date-vs-calendar
https://stackoverflow.com/questions/6802483/how-to-directly-initialize-a-hashmap-in-a-literal-way
https://stackoverflow.com/questions/1152846/java-calendar-set-not-giving-correct-result
https://stackoverflow.com/questions/9397203/last-day-of-month-calculation
https://stackoverflow.com/questions/18333099/how-to-find-which-day-of-the-week-it-is-java
https://stackoverflow.com/questions/6538791/what-is-the-difference-between-calendar-week-of-month-and-calendar-day-of-week-i
https://www.journaldev.com/17899/java-simpledateformat-java-date-format
*/
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.lang.IndexOutOfBoundsException;

public class ScheduleImp implements MeetingSchedule {
    private Calendar cal = Calendar.getInstance();
    private Locale locale = Locale.getDefault();
    private ArrayList<Integer> daysOfMeeting = new ArrayList<>();
    private ArrayList<Calendar> holidays = new ArrayList<>();
    private ArrayList<Date> meetings = new ArrayList<>();
    private String pattern = "MM/dd/yy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private int year;
    private int month;  // 0...11
    private int day;

    /* Default constructor */
    public ScheduleImp() {
        this.year = this.cal.get(Calendar.YEAR);
        this.month = this.cal.get(Calendar.MONTH);
        this.day = this.cal.get(Calendar.DAY_OF_MONTH);
        System.out.println(simpleDateFormat.format(this.cal.getTime()));
    }
    /* Non-default constructor */
    public ScheduleImp(int yy, int mm, int dd) {
        this.year = yy;
        this.month = mm-1;
        this.day = dd;
    }
    /* Schedules holiday on given date (mm/dd) of year */
    public void addHoliday(int mm, int dd) {
        Calendar holidayCal = Calendar.getInstance();
        holidayCal.set(year, mm-1, dd);
        this.holidays.add(holidayCal);
        System.out.println("**********************************************");
        System.out.println("Holiday set for: " + mm + "/" + dd);
    }
    /* Removes scheduled holiday */
    public void removeHoliday(int mm, int dd) {
        for (int i = 0; i < this.holidays.size(); i++) {
            if((this.holidays.get(i).get(Calendar.MONTH) == (mm-1)) &&
                (this.holidays.get(i).get(Calendar.DAY_OF_MONTH) == dd)) {
                this.holidays.remove(i);
                System.out.println("**********************************************");
                System.out.println("Holiday on " + mm + "/" + dd + " removed!");
                break;
            }
        }
    }

    /* Checks whether given date(mm/dd) is a holiday/vacation with NO meetings */
    private boolean isHoliday(int mm, int dd) {
        for (int i = 0; i < this.holidays.size(); i++) {
            if(mm == this.holidays.get(i).get(Calendar.MONTH)
              && dd == this.holidays.get(i).get(Calendar.DAY_OF_MONTH)) {
                  System.out.println("No meeting on: " + (mm+1) + "/" + dd);
                  return true;
            }
        }
        return false;
    }
    /* PRINTS, and RETURNS list of holiday dates */
    public ArrayList<Calendar> printHolidays() {
        System.out.println("**********************************************");
        System.out.println("Dates of Holidays(No Meetings):");
        for (int i =0; i<this.holidays.size(); i++) {
            System.out.println((this.holidays.get(i).get(Calendar.MONTH)+1) + "/" + this.holidays.get(i).get(Calendar.DAY_OF_MONTH));
        }
        return this.holidays;
    }

    /* PRINTS, and RETURNS list of meeting dates */
    public ArrayList<Date> getMeetingDates() {
        // int startMth = cal.get(Calendar.MONTH);        /* Calculate starting today */
        // int startDay = cal.get(Calendar.DAY_OF_MONTH); /* Calculate starting this month */
        int yy = this.year;    /* CHOOSE the YEAR */
        int startMth = this.month;   /* CHOOSE the MONTH */
        int startDay = this.day;   /* CHOOSE the DAY */
        int lastMonth = cal.getMaximum(Calendar.MONTH); // last mth in yr = 11 (0,...,11)
        int lastDayOfMth;   /* Date of last day of the month (28/30/31)*/
        int totNumWed = 0;
        int dayOfWeek;
        System.out.println("**********************************************");
        System.out.println("Meeting Dates:");

        /* Prints meeting dates for each month of the year */
        for (int m = startMth; m <= lastMonth; m++) {
            this.cal.set(yy,m,startDay);
            lastDayOfMth = this.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            System.out.println(this.cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + ":");
            /* Print meeting dates for the month */
            // TODO: how to start from startDay, and then dd=1 for subsequent mths?
            for (int dd = 1;  dd <= lastDayOfMth;  dd++) {
                this.cal.set(Calendar.DAY_OF_MONTH, dd);
                dayOfWeek = this.cal.get(Calendar.DAY_OF_WEEK);
                if (this.daysOfMeeting.contains(dayOfWeek) && !isHoliday(m,dd)) {
                    System.out.println(simpleDateFormat.format(this.cal.getTime()));
                    this.meetings.add(this.cal.getTime());
                    totNumWed++;
                    //dd += 6; /* Uncomment if only 1 meeting per wk */
                }
            }
        }
        System.out.println("**********************************************");
        System.out.println("Total Num. of Meetings: " + totNumWed);

        return meetings;
    }

    /* Schedules the day(s) of the week for meetings */
    public void scheduleMeetingDay(int dayOfWeek) {
        if (!this.daysOfMeeting.contains(dayOfWeek)) {
            this.daysOfMeeting.add(dayOfWeek);
        }
        System.out.println("**********************************************");
        System.out.print("Scheduled Meeting Day(s):");
        for (int i : this.daysOfMeeting) {
            System.out.print(" " + daysOfWeek[i-1]);
        }
        System.out.println();
    }

    /* Removes scheduled meeting day */
    public void removeMeetingDay(int dayToRemove) throws IndexOutOfBoundsException{
        int index = this.daysOfMeeting.indexOf(dayToRemove);
        try {
            this.daysOfMeeting.remove(index);
            System.out.println("**********************************************");
            System.out.println("Meeting Day on " + daysOfWeek[dayToRemove-1] + " removed!");
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("**********************************************");
            System.out.println("ERROR: Meeting day not existent or day number invalid!");
            System.out.println("[1 = Sunday, 2 = Monday, 3 = Tuesday, 4 = Wednesday, 5 = Thursday, 6 = Friday, 7 = Saturday]");
        }
    }

}
