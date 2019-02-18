import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.text.DateFormat;

public class FirstSchedulerMain {
    public static void main(String args[]) throws InterruptedException {
        Calendar cal = Calendar.getInstance();
        Locale locale = Locale.getDefault();
        System.out.println(cal.get(Calendar.DAY_OF_WEEK)); //prints 7 (sat)
        System.out.println(cal.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.SHORT, locale)); // prints day of today (mon/tue/wed..etc.)

        // DateFormat format = DateFormat.getInstance();
        // format.setCalendar(cal);
        // System.out.println(format.format(cal));

        int dayOfMeeting = 4; // = wednesday
        Date date = cal.getTime();

        System.out.println("\nBefore setting cal to first day of yr:");
        System.out.println(date); // prints Tues Jan 01 ... 2019

        cal.set(2019, 0, 1); // set cal to 1/1/2019
        date = cal.getTime();
        System.out.println("\nAfter setting cal to first day of yr:");
        System.out.println(date); // prints Tues Jan 01 ... 2019
        System.out.println("Tuesday = " + cal.get(Calendar.DAY_OF_WEEK));

        int lastMonth = cal.getMaximum(Calendar.MONTH); // max mth in yr = 11 (0,...11)
        int lastDayOfMth = -1;
        System.out.println(lastMonth);
        int totNumWed = 0;

        Calendar holidayCal = Calendar.getInstance();
        holidayCal.set(2019,0,2); // feb. 13

        ArrayList<Calendar> holidays = new ArrayList<>();
        holidays.add(holidayCal);
        System.out.println("holiday: " + holidayCal.getTime());
        // System.out.println(holidayCal.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) &&
        //     holidayCal.get(Calendar.MONTH) == cal.get(Calendar.MONTH));
        for (int m = 0; m <= lastMonth; m++) {
            cal.set(Calendar.MONTH, m);
            lastDayOfMth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int numWed = 0;
            for (int d = 1;  d <= lastDayOfMth;  d++) {
                cal.set(Calendar.DAY_OF_MONTH, d);
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                System.out.println("Month: " + m);
                System.out.println("dayOfMonth: " + d); // actual dd date
                System.out.println("dayOfWeek: " + dayOfWeek); // Sun=1...Sat=7

                System.out.println("holiday Month: " + holidays.get(0).get(Calendar.MONTH));
                System.out.println("holiday dayOfMonth: " + holidays.get(0).get(Calendar.DAY_OF_MONTH));
                System.out.println("holiday dayOfWeek: " + holidays.get(0).get(Calendar.DAY_OF_WEEK));
                if (dayOfWeek == dayOfMeeting && (d != holidays.get(0).get(Calendar.DAY_OF_MONTH) || m != holidays.get(0).get(Calendar.MONTH))) {
                    // (cal_mth == hol_mth) && (cal_dd == hol_dd)
                    // ^neg = cal_mth != hol_mth || cal_dd != hol_dd
                    System.out.println("dayOfWeek == dayOfMeeting on dd: " + d);
                    numWed++;
                    totNumWed++;
                    d +=6;
                }
                System.out.println();
            }
            System.out.println("numWed in month " + cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale) + ": " + numWed);
        }
        System.out.println("total numWed in year: " + totNumWed);
    }
}
