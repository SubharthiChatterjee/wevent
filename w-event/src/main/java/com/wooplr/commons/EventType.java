package com.wooplr.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * @author subharthi chatterjee
 * 
 */
public enum EventType {

	EVENT1(1), EVENT2(2), EVENT3(3), EVENT4(4);

	private int type;
	public static final List<Integer> EVENT_TYPES = new ArrayList<Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add(EVENT1.getType());
			add(EVENT2.getType());
			add(EVENT3.getType());
			add(EVENT4.getType());
		}
	};

	EventType(int type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

}
