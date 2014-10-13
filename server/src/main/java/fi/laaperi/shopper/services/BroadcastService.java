package fi.laaperi.shopper.services;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.atmosphere.cpr.Broadcaster;
import org.springframework.stereotype.Service;

@Service
public class BroadcastService {
	
	private final static Logger logger = LoggerFactory.getLogger(BroadcastService.class);
	
	private Map<UUID, Broadcaster> broadcastTokens = new ConcurrentHashMap<UUID, Broadcaster>();
	
	public BroadcastService(){
		logger.info("Broadcast service created");
	}
	
	public void broadcast(UUID listId, String message) {
		Broadcaster broadcaster = broadcastTokens.get(listId);
		int recipients = broadcaster.getAtmosphereResources().size();
		logger.info("Broadcasting list "+listId+" to "+recipients+" recepients" ); 
		broadcaster.broadcast(message);
	}
 
	public void addBroadcastToken(UUID listId, Broadcaster token) {
		logger.info("Added broadcaster to list " +listId);
		broadcastTokens.put(listId, token);
	}
 
	public void destroyBroadcastToken(UUID listId) {
		logger.info("Destroyed broadcaster from list " +listId);
		Broadcaster broadcaster = broadcastTokens.get(listId);
		if (broadcaster != null) {
			broadcaster.destroy();
			broadcastTokens.remove(listId);
		}
	}
	
}
