package com.web.curation.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {
	
	private MessageService messageService;
	private final SimpMessagingTemplate template;
	
	@GetMapping(value = "/{teamId}")
	@ApiOperation(value = "해당 팀 채팅 최근 10개 가져오기")
	public ResponseEntity<Map<String, Object>> getChallenge(
			@PathVariable int teamId,
			@RequestParam(value = "page", defaultValue = "0") int pageNum){
		Map<String, Object> resultMap = new HashMap<>();
		
		resultMap.put("data", messageService.getMessages(teamId, pageNum));
		resultMap.put("message", "success");
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	@MessageMapping("/message")
	public void sendMessage(@Payload MessageDto messageDto) {
		messageService.insertMessage(messageDto);
		template.convertAndSend("/sub/" + messageDto.getTeamId(), messageDto);
	}
}