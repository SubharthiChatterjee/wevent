/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package com.wooplr.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wooplr.persistence.entity.Event;
import com.wooplr.service.EventService;

/**
 * @author subharthi chatterjee
 * 
 */
@Controller
public class BaseController {

	@Autowired
	private EventService eventService;

	private static final Logger logger = Logger.getLogger(BaseController.class);

	@RequestMapping("/index.htm")
	public ModelAndView populateIndex(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("index");
	}

	@RequestMapping("/addEvent.htm")
	public @ResponseBody
	Response addEvent(@RequestParam(value = "eventType", required = true) int eventType) {
		Response response = new Response();
		response.setCode(Response.SUCCESS);
		try {
			eventService.addEvent(eventType, "ahdhue", "djiji");
		} catch (Exception e) {
			logger.error("error occured in addEvent", e);
			response.setMessage(e.getMessage());
			response.setCode(Response.FAILURE);
		}
		return response;
	}

	@RequestMapping("/getRecentEventsPage.htm")
	public ModelAndView getRecentEvents(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("recentEvents");
	}

	@RequestMapping("/getRecentEvents.htm")
	public @ResponseBody
	Response getRecentEventsPaginatedView(HttpServletRequest request, HttpServletResponse httpResponse) {
		String lastEventId = null;
		int limit = 0;
		if ((request != null) && (request.getParameter("sEcho") != null) && (request.getParameter("sEcho") != "")) {
			limit = Integer.parseInt(request.getParameter("iDisplayLength"));
			lastEventId = request.getParameter("lastEventId");
		}
		Response response = new Response();
		try {
			ArrayList<Event> eventList = (ArrayList<Event>) eventService.getRecentEvents(lastEventId, limit);
			response.setEventList(eventList);
			response.setCode(Response.SUCCESS);
			Event event = eventList.get(eventList.size() - 1);
			response.setLastEventId(event.getId());
		} catch (Exception e) {
			logger.error("error occured in addEvent", e);
			response.setCode(Response.FAILURE);
			response.setMessage(e.getMessage());
			response.setEventList(new ArrayList<Event>());
		}
		return response;
	}

	@RequestMapping("/getTopEventsPage.htm")
	public ModelAndView getTopEventsPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("topEvents");
	}

	@RequestMapping("/getTopEvents.htm")
	public @ResponseBody
	Response getTopEvents(HttpServletRequest request, HttpServletResponse httpResponse) {
		long timeIntervalInMillis = 0;
		if ((request != null) && (request.getParameter("sEcho") != null) && (request.getParameter("sEcho") != "")) {
			int timeInterval = Integer.parseInt(request.getParameter("timeInterval"));
			timeIntervalInMillis = timeInterval * 60 * 60 * 1000;
		}

		Response response = new Response();
		try {
			List<Entry<Integer, Integer>> topEventEntryList = eventService.getTopEvents(timeIntervalInMillis);
			ArrayList<EventCount> eventList = new ArrayList<EventCount>();

			for (Map.Entry<Integer, Integer> topEventMapEntry : topEventEntryList) {
				EventCount eventCount = new EventCount();
				eventCount.setType(topEventMapEntry.getKey());
				eventList.add(eventCount);
			}
			response.setEventList(eventList);
			response.setCode(Response.SUCCESS);
		} catch (Exception e) {
			logger.error("error occured in addEvent", e);
			response.setCode(Response.FAILURE);
			response.setMessage(e.getMessage());
			response.setEventList(new ArrayList<Event>());
		}
		return response;
	}

	@RequestMapping("/getEventsCountPage.htm")
	public ModelAndView getEventsCountPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("eventsCount");
	}

	@RequestMapping("/getEventsCount.htm")
	public @ResponseBody
	Response getEventCount(@RequestParam(value = "timeInterval", required = true) int timeInterval) {
		ArrayList<EventCount> eventCountList = new ArrayList<EventCount>();
		Response response = new Response();
		try {
			Map<Integer, Integer> eventMap = eventService.getEventCount(timeInterval * 60 * 60 * 1000);
			for (Map.Entry<Integer, Integer> eventEntry : eventMap.entrySet()) {
				EventCount eventCount = new EventCount();
				eventCount.setType(eventEntry.getKey());
				eventCount.setCount(eventEntry.getValue());
				eventCountList.add(eventCount);
			}
			response.setCode(Response.SUCCESS);
			response.setEventList(eventCountList);
		} catch (Exception e) {
			logger.error("error occured in addEvent", e);
			response.setCode(Response.FAILURE);
			response.setMessage(e.getMessage());
			response.setEventList(new ArrayList<Event>());
		}
		return response;
	}
}
