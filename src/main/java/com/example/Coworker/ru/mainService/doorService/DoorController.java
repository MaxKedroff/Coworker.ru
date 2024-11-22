package com.example.Coworker.ru.mainService.doorService;

import com.example.Coworker.ru.authenticationService.config.entity.User;
import com.example.Coworker.ru.authenticationService.config.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/main/door")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@EnableScheduling
public class DoorController {

    @Autowired
    UserService userService;

    @Autowired
    DoorService doorService;

    @Autowired
    private TaskScheduler scheduler;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/unlock")
    public ResponseEntity<String> unlockDoor(@RequestParam String QR_code){
        if (doorService.isValidQRCode(QR_code)){
            sendDoorStatus("Дверь открыта");
            scheduler.schedule(() -> sendDoorStatus("Дверь закрыта"),
                    new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(20)));
            return ResponseEntity.ok("Дверь открыта");
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Доступ запрещен");
        }
    }

    private void sendDoorStatus(String status) {
        System.out.println(status); // Логирование для наглядности
    }

    @GetMapping("/shouldActivateScanner")
    public ResponseEntity<String> shouldActivateScanner(@RequestHeader("Authorization") String header) {
        String token = header.substring(7);
        String username = userService.getUserByToken(token);
        User user = userService.getByUsername(username);
        boolean res = doorService.shouldActivateScanner(user);
        System.out.println(res);
        if (res){
            return ResponseEntity.ok("сканнер активирован");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("рано активировать сканер");
    }

}
