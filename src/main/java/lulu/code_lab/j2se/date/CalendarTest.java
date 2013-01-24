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
	 * 获取本周的开始结束日期.
	 */
	@Test
	public void testGetDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		//设置每周开始星期,国内为周一
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		System.out.println(tl.get().format(calendar.getTime()));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		System.out.println(tl.get().format(calendar.getTime()));
	}

	/**
	 * 获取上周开始结束日期.
	 */
	@Test
	public void testGetLastWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.WEEK_OF_YEAR, -1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		System.out.println(tl.get().format(calendar.getTime()));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		System.out.println(tl.get().format(calendar.getTime()));

	}

}
