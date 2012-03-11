package se.aaslin.developer.roboeventbus.event;

public abstract class RoboEvent<T extends RoboEventHandler> {
	
	public static class Type<T> {
	}
	
	public abstract void dispatch(T handler);
	
	public abstract RoboEvent.Type<T> getType();
}

