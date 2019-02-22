/* Calendar/Scheduler Program written in Java by Albert Ki */
/* Sources:
    https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html#MONTH
    https://stackoverflow.com/questions/1404210/java-date-vs-calendar
    https://stackoverflow.com/questions/6802483/how-to-directly-initialize-a-hashmap-in-a-literal-way
    https://stackoverflow.com/questions/1152846/java-calendar-set-not-giving-correct-result
    https://stackoverflow.com/questions/9397203/last-day-of-month-calculation
    https://stackoverflow.com/questions/18333099/how-to-find-which-day-of-the-week-it-is-java
    https://stackoverflow.com/questions/6538791/what-is-the-difference-between-calendar-week-of-month-and-calendar-day-of-week-i
    https://www.journaldev.com/17899/java-simpledateformat-java-date-format
    https://stackoverflow.com/questions/18448671/how-to-avoid-concurrentmodificationexception-while-removing-elements-from-arr
*/
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.lang.IndexOutOfBoundsException;
import java.util.Iterator;

public class MeetingScheduler implements Scheduler {
    private Calendar cal = Calendar.getInstance();
    private Locale locale = Locale.getDefault();
    private ArrayList<Integer> daysOfMeeting = new ArrayList<>();   // day(s) of week for meeting
    private ArrayList<Calendar> holidays = new ArrayList<>();
    private ArrayList<Calendar> meetings = new ArrayList<>();
    private String pattern = "MM/dd/yy";    // for printing
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private int year;
    private int month;  // Jan=0...Dec=11
    private int day;

    /* Default constructor - intializes calendar to present date */
    public ScheduleImp() {
        this.year = this.cal.get(Calendar.YEAR);
        this.month = this.cal.get(Calendar.MONTH);
        this.day = this.cal.get(Calendar.DAY_OF_MONTH);
        System.out.println(simpleDateFormat.format(this.cal.getTime()));
    }
    /* Non-default constructor - initializes calendar to given date */
    public ScheduleImp(int yy, int mm, int dd) {
        this.year = yy;
        this.month = mm-1;
        this.day = dd;
    }
    /* SCHEDULES holiday on given date (mm/dd) of year */
    public void addHoliday(int mm, int dd) {
        Calendar holidayCal = Calendar.getInstance();
        holidayCal.set(year, mm-1, dd);
        this.holidays.add(holidayCal);
        System.out.println("**********************************************");
        System.out.println("Holiday set for: " + mm + "/" + dd);
    }
    /* REMOVES scheduled holiday */
    public void removeHoliday(int mm, int dd) {
        for (int i = 0; i < this.holidays.size(); i++) {
            if ((this.holidays.get(i).get(Calendar.MONTH) == (mm-1)) &&
                  (this.holidays.get(i).get(Calendar.DAY_OF_MONTH) == dd)) {
                this.holidays.remove(i);
                System.out.println("**********************************************");
                System.out.println("Holiday on " + mm + "/" + dd + " removed!");
                break;
            }
        }
    }

    /* CHECKS whether given date(mm/dd) is a holiday/vacation with NO meetings */
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
    public ArrayList<Calendar> getMeetingDates() {
        System.out.println("**********************************************");
        System.out.println("Meeting Dates:");
        int mm = -1;
        for (int i = 0; i < this.meetings.size(); i++) {
            if (this.meetings.get(i).get(Calendar.MONTH) != mm) {
                System.out.println(this.meetings.get(i).getDisplayName(Calendar.MONTH, Calendar.LONG, locale) + ":");
                mm = this.meetings.get(i).get(Calendar.MONTH);
            }
            System.out.println(simpleDateFormat.format(this.meetings.get(i).getTime()));
        }
        System.out.println("**********************************************");
        System.out.println("Total Num. of Meetings: " + this.meetings.size());

        return this.meetings;
    }

    /* Schedules the day(s) of the week for meetings */
    public void scheduleMeetingDay(int meetingDayOfWeek) {
        if (!this.daysOfMeeting.contains(meetingDayOfWeek)) {
            this.daysOfMeeting.add(meetingDayOfWeek);
            System.out.println("**********************************************");
            System.out.print("Scheduled Meeting Day(s):");
            for (int i : this.daysOfMeeting) {
                System.out.print(" " + daysOfWeek[i-1]);
            }
            System.out.println();
            this.addMeetings(meetingDayOfWeek);
        }
    }

    /* ADDS meeting dates to meetings list */
    private void addMeetings(int meetingDayOfWeek) {
        int yy = this.year;         /* CHOOSE the YEAR */
        int startMth = this.month;   /* CHOOSE the MONTH */
        int lastMonth = cal.getMaximum(Calendar.MONTH); // last mth in yr = 11 (0,...,11)
        int lastDayOfMth;   /* Date of last day of the month (28/30/31)*/
        int totalNumMeetings = 0;
        int dayOfWeek;

        /* ADD meeting dates for each month */
        for (int m = startMth; m <= lastMonth; m++) {
            Calendar temp_cal = Calendar.getInstance();
            temp_cal.set(yy,m,1);
            lastDayOfMth = temp_cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            // TODO: how to start from startDay, and then dd=1 for subsequent mths?
            for (int dd = 1;  dd <= lastDayOfMth;  dd++) {
                temp_cal = Calendar.getInstance();
                temp_cal.set(yy,m,dd);
                dayOfWeek = temp_cal.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == meetingDayOfWeek && !isHoliday(m,dd)) {
                    this.meetings.add(temp_cal);
                    totalNumMeetings++;
                    dd += 6;        /* 1 meeting per wk */
                }
            }
        }
    }
    /* Removes scheduled meeting day from daysOfMeeting array, removes from meetings list */
    public void removeMeetingDay(int dayToRemove) throws IndexOutOfBoundsException {
        int index = this.daysOfMeeting.indexOf(dayToRemove);
        try {
            this.daysOfMeeting.remove(index);
            System.out.println("**********************************************");
            System.out.println("Meeting Days on " + daysOfWeek[dayToRemove-1] + " removed!");
            this.removeMeetings(dayToRemove);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("**********************************************");
            System.out.println("ERROR: Meeting day not existent or day number invalid!");
            System.out.println("[1 = Sunday, 2 = Monday, 3 = Tuesday, 4 = Wednesday, 5 = Thursday, 6 = Friday, 7 = Saturday]");
        }
    }

    /* Helper method to remove dates on the meeting day from meetings list */
    private void removeMeetings(int dayToRemove){
        System.out.println("**********************************************");
        System.out.println("REMOVED Meetings on:");
        Iterator<Calendar> iter = this.meetings.iterator();
        while (iter.hasNext()) {
            Calendar c = iter.next();
            if (c.get(Calendar.DAY_OF_WEEK) == dayToRemove) {
                System.out.println(simpleDateFormat.format(c.getTime()));
                iter.remove();
            }
        }
    }

}
