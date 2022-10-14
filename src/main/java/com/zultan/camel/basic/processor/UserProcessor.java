package com.zultan.camel.basic.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zultan.camel.basic.dto.User;
import com.zultan.camel.basic.service.UserService;

@Component
public class UserProcessor implements Processor{

	@Autowired
	private UserService userService;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		userService.addUsers(exchange.getIn().getBody(User.class));
		
	}

}
