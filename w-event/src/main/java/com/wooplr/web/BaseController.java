package com.wooplr.web;

import java.util.ArrayList;
import java.util.List;

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
		String compare = request.getParameter("compare");
		limit = Integer.parseInt(request.getParameter("limit"));
		lastEventId = request.getParameter("lastEventId");
		Response response = new Response();
		try {
			ArrayList<Event> eventList = (ArrayList<Event>) eventService.getRecentEvents(lastEventId, limit, compare);
			response.setEventList(eventList);
			response.setCode(Response.SUCCESS);
			if (!eventList.isEmpty()) {
				Event event = eventList.get(eventList.size() - 1);
				response.setLastEventId(event.getId());
				response.setFirstEventId(eventList.get(0).getId());
			} else {
				response.setLastEventId(lastEventId);
			}
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
		int timeInterval = 0;
		if ((request != null) && (request.getParameter("sEcho") != null) && (request.getParameter("sEcho") != "")) {
			timeInterval = Integer.parseInt(request.getParameter("timeInterval"));
		}

		Response response = new Response();
		try {
			List<com.wooplr.persistence.entity.EventCount> topEventEntryList = eventService.getTopEvents(timeInterval);
			response.setEventList((ArrayList<com.wooplr.persistence.entity.EventCount>) topEventEntryList);
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
		Response response = new Response();
		try {
			List<com.wooplr.persistence.entity.EventCount> eventCounts = eventService.getEventCount(timeInterval);

			response.setCode(Response.SUCCESS);
			response.setEventList((ArrayList<com.wooplr.persistence.entity.EventCount>) eventCounts);
		} catch (Exception e) {
			logger.error("error occured in addEvent", e);
			response.setCode(Response.FAILURE);
			response.setMessage(e.getMessage());
			response.setEventList(new ArrayList<Event>());
		}
		return response;
	}
}
