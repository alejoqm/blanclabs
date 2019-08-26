package org.blanclabas.adjudicationperformance.utils;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.time.StopWatch;

public class TimeUtils {

	private StopWatch stopWatch;
	private String meassure;

	public  TimeUtils(final String meassure) {
		this.meassure = meassure;
		stopWatch = new StopWatch();
		stopWatch.start();
	}

	public String stop() {
		stopWatch.stop();
		Double seconds = stopWatch.getTime(TimeUnit.MILLISECONDS) / 1000.0;
		String message = meassure + ": " + stopWatch.getTime(TimeUnit.MILLISECONDS) + " ms " +  seconds + " s";
		System.out.println(message);
		return message;
	}
}
