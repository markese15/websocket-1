package co.develhope.websocket1.controller;

import co.develhope.websocket1.entities.Message;
import co.develhope.websocket1.entities.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/notification")
    public ResponseEntity sendNotification(@RequestBody Message message){
        SimpleMessage simpleMessage = new SimpleMessage("Type: " +message.getType() + " Message: " + message.getMessage());
        simpMessagingTemplate.convertAndSend("/topic/broadcast", simpleMessage);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @MessageMapping("/hello")
    @SendTo("/topic/broadcast")
    public SimpleMessage handleMessageFromWebSocket(Message message){
        System.out.println("Arrived something on /app/hello - " + message);
        return new SimpleMessage("From " + message.getType() + " - Text " + message.getMessage());
    }

}

