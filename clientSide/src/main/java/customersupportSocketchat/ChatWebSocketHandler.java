package customersupportSocketchat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        // Broadcast to all other sessions
        broadcastToOthers(session, "Server: A new user has joined the chat.");
        // Send a welcome message to the new user
        session.sendMessage(new TextMessage("Server: Welcome to the chat!"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String userMessage = message.getPayload();
        // Broadcast the user's message to other sessions
        broadcastToOthers(session, "User: " + userMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        // Broadcast to all remaining sessions
        broadcastToOthers(session, "Server: A user has left the chat.");
    }

    private void broadcastToOthers(WebSocketSession sender, String message) throws IOException {
        for (WebSocketSession session : sessions) {
            // Send to all sessions except the sender
            if (session.isOpen() && !session.getId().equals(sender.getId())) {
                session.sendMessage(new TextMessage(message));
            }
        }
    }
}
