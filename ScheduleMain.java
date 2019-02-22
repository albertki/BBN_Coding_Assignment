import java.util.Scanner;
import java.util.ArrayList;
import java.util.Calendar;

class ScheduleMain {

    public static void main (String[] args) {
/* Schedule starting from custom starting month */
        ScheduleImp schedule = new ScheduleImp(2019,1,1);
        schedule.addHoliday(2, 20);
        schedule.addHoliday(11, 20);
        schedule.addHoliday(12, 24);
        // schedule.printHolidays();
        schedule.scheduleMeetingDay(3);
        schedule.scheduleMeetingDay(4);
        schedule.getMeetingDates();
        // schedule.removeHoliday(12,24);
        // schedule.removeMeetingDay(1);
        schedule.removeMeetingDay(4);
        ArrayList<Calendar> arr = schedule.getMeetingDates();
        // System.out.println(arr.toString());

/* Schedule beginning from current month */
        // ScheduleImp schedule2 = new ScheduleImp();
        // schedule.addHoliday(11, 20);
        // schedule2.scheduleMeetingDay(4);
        // schedule2.getMeetingDates();

/* Schedule by reading input from command prompt [OPTIONAL] */
    //     ScheduleImp schedule3 = new ScheduleImp(2019,1,1);
    //     Scanner in = new Scanner(System.in);
    //     System.out.println("** Please enter the weekday digit to schedule meeting day(s) for the year:");
    //     System.out.println("[1 = Sunday, 2 = Monday, 3 = Tuesday, 4 = Wednesday, 5 = Thursday, 6 = Friday, 7 = Saturday]");
    //     System.out.println("**Enter 0 when done");
    //     int stop = in.nextInt();
    //     while (stop != 0) {
    //         schedule3.scheduleMeetingDay(stop);
    //         stop = in.nextInt();
    //     }
    //     schedule3.getMeetingDates();

    }
}
