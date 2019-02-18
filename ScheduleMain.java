import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

class ScheduleMain {

    public static void main (String[] args) {
        ScheduleImp schedule = new ScheduleImp();
        Scanner in = new Scanner(System.in);
        System.out.println("** Please enter the weekday digit to schedule meeting day(s) for the year:");
        System.out.println("[1 = Sunday, 2 = Monday, 3 = Tuesday, 4 = Wednesday, 5 = Thursday, 6 = Friday, 7 = Saturday]");
        System.out.println("**Enter 0 when done");
        int stop = in.nextInt();
        while (stop != 0) {
            schedule.scheduleMeetingDay(stop);
            stop = in.nextInt();

        }
        schedule.addHoliday(2019, 1, 2);
        schedule.addHoliday(2019, 6, 26);
        schedule.printHolidays();
        ArrayList<Date>  arr = schedule.getMeetingDates();
    }
}
