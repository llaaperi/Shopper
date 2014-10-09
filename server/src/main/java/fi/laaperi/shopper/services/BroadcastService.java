package fi.laaperi.shopper.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.atmosphere.cpr.Broadcaster;
import org.springframework.stereotype.Service;

@Service
public class BroadcastService {
	
	private final static Logger logger = Logger.getLogger(BroadcastService.class);
	
	private Map<String, Broadcaster> broadcastTokens = new ConcurrentHashMap<String, Broadcaster>();
	
	
	public BroadcastService(){
	    logger.info("BS created");
	}
	
	public void broadcast(String message) {
		logger.info("Broadcasting message:"+message+" to " + broadcastTokens.size() +" recepients" ); 
		for (Broadcaster token : broadcastTokens.values()) {
				token.broadcast(message);
		}
	}
 
	public void addBroadcastToken(Broadcaster token) {
		logger.info("Added token " + token.getID() + " - n=" + broadcastTokens.size());
		broadcastTokens.put("channel", token);
	}
 
	public void destroyBroadcastToken(String channel) {
		Broadcaster token = broadcastTokens.get(channel);
		if (token != null) {
			token.destroy();
			broadcastTokens.remove(channel);
		}
	}
	
}
