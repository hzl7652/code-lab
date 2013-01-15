package lulu.code_lab.j2se.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class CalendarTest {

	private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}

	};

	/**
	 * 
	 */
	@Test
	public void testGetDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		//设置每周开始星期
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONTH);
		System.out.println(tl.get().format(calendar.getTime()));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		System.out.println(tl.get().format(calendar.getTime()));
	}

}
