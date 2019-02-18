import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

class ScheduleMain {

    public static void main (String[] args) {
        ScheduleImp schedule = new ScheduleImp(2019,1,1);
        Scanner in = new Scanner(System.in);
        System.out.println("** Please enter the weekday digit to schedule meeting day(s) for the year:");
        System.out.println("[1 = Sunday, 2 = Monday, 3 = Tuesday, 4 = Wednesday, 5 = Thursday, 6 = Friday, 7 = Saturday]");
        System.out.println("**Enter 0 when done");
        int stop = in.nextInt();
        while (stop != 0) {
            schedule.scheduleMeetingDay(stop);
            stop = in.nextInt();
        }
        schedule.addHoliday(1, 2);
        schedule.addHoliday(6, 26);
        schedule.printHolidays();
        ArrayList<Date> meetings = schedule.getMeetingDates();
        schedule.removeHoliday(1,2);
        schedule.scheduleMeetingDay(3);
        schedule.removeMeetingDay(1);
        // meetings = schedule.getMeetingDates();

        ScheduleImp schedule2 = new ScheduleImp();
        schedule2.scheduleMeetingDay(3);
        schedule2.getMeetingDates();
    }
}
