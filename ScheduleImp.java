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

public class ScheduleImp implements MeetingSchedule {
    ArrayList<Integer> daysOfMeeting = new ArrayList<>();
    ArrayList<Calendar> holidays = new ArrayList<>();
    ArrayList<Date> meetings = new ArrayList<>();
    int year;
    int month;
    int day;
    public ScheduleImp() {

    }
    /* Non-default Constructor */
    public ScheduleImp(int yy, int mm, int dd) {
        this.year = yy;
        this.month = mm;
        this.day = dd;
    }
    /* Schedules holiday on given date (mm/dd) */
    public void addHoliday(int yy, int mm, int dd) {
        Calendar holidayCal = Calendar.getInstance();
        holidayCal.set(yy, mm-1, dd);
        holidays.add(holidayCal);
        System.out.println("**********************************************");
        System.out.println("Holiday set for: " + mm + "/" + dd);
    }
    /* Removes scheduled holiday */
    public void removeHoliday(int year, int mm, int dd) {
        for (int i = 0; i < holidays.size(); i++) {
            if((holidays.get(i).get(Calendar.MONTH)+1 == mm) &&
                (holidays.get(i).get(Calendar.DAY_OF_MONTH) == dd))
                holidays.remove(i);
        }
        System.out.println("**********************************************");
        System.out.println("Holiday on " + mm + "/" + dd + " removed!");
    }

    /* Checks whether given date(mm/dd) is a holiday/vacation with NO meetings */
    private boolean isHoliday(int mm, int dd) {
        for (int i = 0; i < holidays.size(); i++) {
            if(mm == holidays.get(i).get(Calendar.MONTH)
              && dd == holidays.get(i).get(Calendar.DAY_OF_MONTH)) {
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
        for (int i =0; i<holidays.size(); i++) {
            System.out.println((holidays.get(i).get(Calendar.MONTH)+1) + "/" + holidays.get(i).get(Calendar.DAY_OF_MONTH));
        }
        return holidays;
    }

    /* PRINTS, and RETURNS list of meeting dates */
    public ArrayList<Date> getMeetingDates() {
        Calendar cal = Calendar.getInstance();
        Locale locale = Locale.getDefault();
        String pattern = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        // int startMth = cal.get(Calendar.MONTH);        /* Calculate starting today */
        // int startDay = cal.get(Calendar.DAY_OF_MONTH); /* Calculate starting this month */
        int yy = 2019;    /* CHOOSE the YEAR */
        int startMth = 0;   /* CHOOSE the MONTH */
        int startDay = 1;   /* CHOOSE the DAY */
        int lastMonth = cal.getMaximum(Calendar.MONTH); // last mth in yr = 11 (0,...,11)
        int lastDayOfMth;   /* Date of last day of the month (28/30/31)*/
        int totNumWed = 0;
        int dayOfWeek;
        System.out.println("**********************************************");
        System.out.println("Meeting Dates:");

        /* Prints meeting dates for each month of the year */
        for (int m = startMth; m <= lastMonth; m++) {
            cal.set(yy,m,startDay);
            lastDayOfMth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            System.out.println(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + ":");
            /* Print meeting dates for the month */
            for (int dd = 1;  dd <= lastDayOfMth;  dd++) {
                cal.set(Calendar.DAY_OF_MONTH, dd);
                dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if (daysOfMeeting.contains(dayOfWeek) && !isHoliday(m,dd)) {
                    System.out.println(simpleDateFormat.format(cal.getTime()));
                    meetings.add(cal.getTime());
                    totNumWed++;
                    //dd += 6; /* Uncomment if only 1 meeting per wk */
                }
            }
        }
        System.out.println("**********************************************");
        System.out.println("Total Num. of Meetings in " + cal.get(Calendar.YEAR) + ": " + totNumWed);

        return meetings;
    }

    /* Schedules the day(s) of the week for meetings */
    public void scheduleMeetingDay(int dayOfWeek) {
        daysOfMeeting.add(dayOfWeek);
        System.out.println("**********************************************");
        System.out.print("Scheduled Meeting Day(s):");
        for (int i : daysOfMeeting) {
            System.out.print(" " + daysOfWeek[i-1]);
        }
        System.out.println();
    }

    /* Removes scheduled meeting day */
    public void removeMeetingDay(int dayToRemove) {
        int index = daysOfMeeting.indexOf(dayToRemove);
        daysOfMeeting.remove(index);
        System.out.println("**********************************************");
        System.out.println("Meeting Day on " + daysOfWeek[dayToRemove-1] + " removed!");
    }

}
