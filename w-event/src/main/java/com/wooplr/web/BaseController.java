/**
 * Copyright (c) 2011 by InfoArmy Inc.  All Rights Reserved.
 * This file contains proprietary information of InfoArmy Inc.
 * Copying, use, reverse engineering, modification or reproduction of
 * this file without prior written approval is prohibited.
 *
 */
package com.wooplr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
			Integer.parseInt(request.getParameter("iDisplayStart"));
			Integer.parseInt(request.getParameter("iSortCol_0"));
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
		String lastEventId = null;
		int limit = 0;
		long timeIntervalInMillis = 0;
		if ((request != null) && (request.getParameter("sEcho") != null) && (request.getParameter("sEcho") != "")) {
			Integer.parseInt(request.getParameter("iDisplayStart"));
			Integer.parseInt(request.getParameter("iSortCol_0"));
			limit = Integer.parseInt(request.getParameter("iDisplayLength"));
			lastEventId = request.getParameter("lastEventId");
			int timeInterval = Integer.parseInt(request.getParameter("timeInterval"));
			timeIntervalInMillis = timeInterval * 60 * 60 * 1000;
		}

		Response response = new Response();
		try {
			ArrayList<Event> eventList = (ArrayList<Event>) eventService.getTopEvents(lastEventId, limit,
					timeIntervalInMillis);
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

	@RequestMapping("/getEventCount.htm")
	public @ResponseBody
	Map<Integer, Integer> getEventCount(
			@RequestParam(value = "timeIntervalInMillis", required = true) long timeIntervalInMillis) {
		Map<Integer, Integer> eventMap = new HashMap<Integer, Integer>();
		try {
			eventMap = eventService.getEventCount(timeIntervalInMillis);
		} catch (Exception e) {
			logger.error("error occured in addEvent", e);
		}
		return eventMap;
	}
}
