package sample;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.SimpleTriggerContext;

public class LastDayOfMonthTrigger implements Trigger {

    private final LocalTime time;

    public LastDayOfMonthTrigger(LocalTime time) {
        this.time = time;
    }

    @Override
    public Date nextExecutionTime(TriggerContext ctx) {
        Date last = ctx.lastScheduledExecutionTime();
        LocalDate date = last == null ? new LocalDate() : new LocalDate(last).plusDays(1);
        LocalDate lastDay = date.dayOfMonth().withMaximumValue();
        return lastDay.toDateTime(time).toDate();
    }

    public static void main(String[] args) {
        LastDayOfMonthTrigger trigger = new LastDayOfMonthTrigger(new LocalTime());
        SimpleTriggerContext ctx = new SimpleTriggerContext();
        for (int i = 0; i < 10; i++) {
            Date date = trigger.nextExecutionTime(ctx);
            System.out.println(date);
            ctx.update(date, null, null);
        }
    }
}
