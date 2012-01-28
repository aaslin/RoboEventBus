package se.aaslin.developer.roboeventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.aaslin.developer.roboeventbus.event.EventHandler;
import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class RoboEventBus {

	private static RoboEventBus eventBus = new RoboEventBus();
	private Map<RoboEvent.Type<?>, List<?>> eventManager = new HashMap<RoboEvent.Type<?>, List<?>>();

	public static RoboEventBus getInstance() {
		return eventBus;
	}

	private RoboEventBus() {
	}

	public <H extends EventHandler> void addHandler(RoboEvent.Type<H> type, H handler) {
		doAdd(type, handler);
	}
	
	private <H extends EventHandler> void doAdd(RoboEvent.Type<H> type, H handler){
		if (!eventManager.containsKey(type)) {
			eventManager.put(type, new ArrayList<H>());
		}
		@SuppressWarnings("unchecked")
		List<H> handlers = (List<H>) eventManager.get(type);
		handlers.add(handler);
	}
	
	public void fireEvent(RoboEvent<?> event){
		doFire(event);
	}
	
	private <H extends EventHandler> void doFire(RoboEvent<H> event){
		@SuppressWarnings("unchecked")
		List<H> handlers = (List<H>) eventManager.get(event.getType());
		
		for(H handler : handlers){
			event.dispatch(handler);
		}
	}

}
