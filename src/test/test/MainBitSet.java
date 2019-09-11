package test;

import junit.framework.Assert;
import org.junit.Test;

import java.time.temporal.ChronoUnit;

public class MainBitSet {
    @Test
    public void testTemporal1() throws Exception {


        ZonedDateTime zdt1 = ZonedDateTime.of(2005, 10, 30, 0, 0, 0, 0, ZoneId.of("Europe/Moscow"));

        // case #1 - ok
        ZonedDateTime zdt2 = zdt1.plusDays(1);
        Assert.assertEquals(25, Duration.between(zdt1, zdt2).toHours());

        // case #2 - ok
        ZonedDateTime zdt3 = zdt1.plus(1, ChronoUnit.DAYS);
        Assert.assertEquals(25, Duration.between(zdt1, zdt3).toHours());

        // case #3 - ok
        OffsetDateTime odt1 = zdt1.toOffsetDateTime();
        OffsetDateTime odt2 = zdt2.toOffsetDateTime();
        Assert.assertEquals(25, Duration.between(odt1, odt2).toHours());

        // case #4 - ???
        OffsetDateTime odt3 = zdt1.toOffsetDateTime();
        OffsetDateTime odt4 = odt3.plus(1, ChronoUnit.DAYS);
        Assert.assertEquals(24, Duration.between(odt3, odt4).toHours());

        // case #5 - ok
        Instant instant1 = Instant.from(zdt1);
        Instant instant2 = Instant.from(zdt2);
        Assert.assertEquals(25, Duration.between(instant1, instant2).toHours());

        // case #6 - ???
        Instant instant3 = Instant.from(zdt1);
        Instant instant4 = instant3.plus(1, ChronoUnit.DAYS);
        Assert.assertEquals(24, Duration.between(instant3, instant4).toHours());

        // case #7 - ???
        LocalDateTime localDateTime1 = LocalDateTime.from(zdt1);
        LocalDateTime localDateTime2 = localDateTime1.plus(1, ChronoUnit.DAYS);
        Assert.assertEquals(24, Duration.between(localDateTime1, localDateTime2).toHours());

        // case #8 - ???
        LocalDateTime localDateTime3 = LocalDateTime.from(zdt1);
        LocalDateTime localDateTime4 = LocalDateTime.from(zdt2);
        Assert.assertEquals(24, Duration.between(localDateTime3, localDateTime4).toHours());


        LocalDate localDate = LocalDate.of(2011, 12, 1);
        LocalTime localTime = LocalTime.of(11, 12, 34, 111);
        System.out.println();

        Clock clock = Clock.system(ZoneId.of("Europe/Samara"));

        LocalDateTime ldt1 = LocalDateTime.ofInstant(clock.instant(), ZoneId.of("UTC"));
        System.out.println(ldt1);

        LocalDateTime ldt2 = LocalDateTime.now(clock);
        System.out.println(ldt2);

/*        Temporal t1 = Clock.systemUTC().instant();
        Temporal t2 = t1.plus(1, ChronoUnit.DAYS);

        Assert.assertEquals(Duration.ofDays(1).getSeconds(),
                t2.getLong(ChronoField.INSTANT_SECONDS) - t1.getLong(ChronoField.INSTANT_SECONDS));

        Assert.assertEquals(24, t1.until(t2, ChronoUnit.HOURS));
        Assert.assertEquals(24, Duration.between(t1, t2).get(ChronoUnit.HOURS));*/
    }

    /*public static void main(String[] args) {
        Integer i1 = null;
        Integer i2 = 7;

        System.out.println(Objects.equals(i2,i2=null));
*//*
        if (Objects.equals(i2, i1)) System.out.println("ddd");
        System.out.println(i1==i2);
        Abc a = new Abc();
        Abc a1 = new Abc();
        System.out.println(a==a1);*//*

    }*/
}
/*
class Abc{
    String s = "test";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Abc)) return false;
        Abc abc = (Abc) o;
        return Objects.equals(s, abc.s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s);
    }
}*/
