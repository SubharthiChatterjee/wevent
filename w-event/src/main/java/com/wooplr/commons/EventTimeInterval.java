package com.wooplr.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * @author subharthi chatterjee
 * 
 */
public enum EventTimeInterval {

	EVENT_TIME_INTERVAL_8(8) {
		@Override
		protected long getTimeIntervalInMillis() {
			return timeIntervalInHours * 60 * 60 * 1000;
		}
	},
	EVENT_TIME_INTERVAL_24(24) {
		@Override
		protected long getTimeIntervalInMillis() {
			return timeIntervalInHours * 60 * 60 * 1000;
		}
	},
	EVENT_TIME_INTERVAL_168(168) {
		@Override
		protected long getTimeIntervalInMillis() {
			return timeIntervalInHours * 60 * 60 * 1000;
		}
	};

	protected int timeIntervalInHours;

	@SuppressWarnings("serial")
	private static final ArrayList<Integer> timeIntervalsInHoursList = new ArrayList<Integer>() {
		{
			add(EVENT_TIME_INTERVAL_8.getTimeIntervalInHours());
			add(EVENT_TIME_INTERVAL_24.getTimeIntervalInHours());
			add(EVENT_TIME_INTERVAL_168.getTimeIntervalInHours());
		}
	};

	EventTimeInterval(int timeIntervalInHours) {
		this.timeIntervalInHours = timeIntervalInHours;
	}

	/**
	 * @return the timeIntervalInHours
	 */
	public int getTimeIntervalInHours() {
		return timeIntervalInHours;
	}

	public static List<Integer> getTimeIntervalsInHours() {
		return timeIntervalsInHoursList;
	}

	protected abstract long getTimeIntervalInMillis();
}
