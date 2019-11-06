package timebudget;

import timebudget.exceptions.BadEventException;
import timebudget.exceptions.BadUserException;
import timebudget.model.Event;
import timebudget.model.User;

import java.util.Map;
import java.util.HashMap;
import java.util.List;


public class ReportGen  {
    public static Map<Integer, Float> getReport(User user, List<Event> eventsInRange) throws BadUserException, BadEventException {
        Map<Integer, Float> categoryTotals = new HashMap<>();

        for (Event event : eventsInRange) {
            int cat_id = event.getCategoryID();


            int time_elapsed = event.getEndAt() - event.getStartAt();
            float hours = (float)time_elapsed / 3600f;


            if (!categoryTotals.containsKey(cat_id)) 
                categoryTotals.put(cat_id, hours);
            else
                categoryTotals.put(cat_id, hours + categoryTotals.get(cat_id));
        }

        return categoryTotals;
    }	
}