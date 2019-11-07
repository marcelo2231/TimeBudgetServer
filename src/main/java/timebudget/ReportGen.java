package timebudget;

import timebudget.exceptions.BadEventException;
import timebudget.exceptions.BadUserException;
import timebudget.model.Category;
import timebudget.model.Event;
import timebudget.model.User;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReportGen  {
    public static Map<Integer, Float> getReport(User user, List<Event> eventsOnePeriod) throws BadUserException, BadEventException {
        Map<Integer, List<Event>> groupedEvents = new HashMap<>();

        for (Event e: eventsOnePeriod)
            groupedEvents.put(e.getCategoryID(), new ArrayList<Event>());
        
        for (Event e: eventsOnePeriod)
            groupedEvents.get(e.getCategoryID()).add(e);
    
        Map<Integer, Float> categoryTotals = new HashMap<>();

        for (Integer catId : groupedEvents.keySet()) {
            categoryTotals.put(catId, 0f);

            for (Event event : groupedEvents.get(catId)) {
                int time_elapsed = event.getEndAt() - event.getStartAt();
                float hours = (float)time_elapsed / 3600f;

                categoryTotals.put(catId, hours + categoryTotals.get(catId));
            }
        }

        return categoryTotals;
    }	
}