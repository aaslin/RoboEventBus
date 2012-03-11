package se.aaslin.developer.roboeventbus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.aaslin.developer.roboeventbus.event.RoboEventHandler;
import se.aaslin.developer.roboeventbus.event.RoboEvent;

public class RoboEventBus {

	private static RoboEventBus eventBus = new RoboEventBus();
	private Map<RoboEvent.Type<?>, List<?>> eventManager = new HashMap<RoboEvent.Type<?>, List<?>>();

	public static RoboEventBus getInstance() {
		return eventBus;
	}

	private RoboEventBus() {
	}

	public <H extends RoboEventHandler> RoboRegistration addHandler(RoboEvent.Type<H> type, H handler) {
		return doAdd(type, handler);
	}
	
	private <H extends RoboEventHandler> RoboRegistration doAdd(RoboEvent.Type<H> type, final H handler){
		if (!eventManager.containsKey(type)) {
			eventManager.put(type, new ArrayList<H>());
		}
		@SuppressWarnings("unchecked")
		final List<H> handlers = (List<H>) eventManager.get(type);
		handlers.add(handler);
		
		return new RoboRegistration() {
			
			public void removeHandler() {
				handlers.remove(handler);
			}
		};
	}
	
	public void fireEvent(RoboEvent<?> event){
		doFire(event);
	}
	
	private <H extends RoboEventHandler> void doFire(RoboEvent<H> event){
		@SuppressWarnings("unchecked")
		List<H> handlers = (List<H>) eventManager.get(event.getType());
		
		for(H handler : handlers){
			event.dispatch(handler);
		}
	}
}
