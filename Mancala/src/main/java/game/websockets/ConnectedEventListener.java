package game.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import game.controllers.GameController;

@Component
public class ConnectedEventListener implements ApplicationListener<SessionSubscribeEvent> {
	
	@Autowired
	private GameController gameController;
	
	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		gameController.startGameForUser(event.getUser().getName());
	}

}
