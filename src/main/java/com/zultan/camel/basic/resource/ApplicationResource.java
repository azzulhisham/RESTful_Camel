package com.zultan.camel.basic.resource;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.BeanInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zultan.camel.basic.dto.User;
import com.zultan.camel.basic.processor.UserProcessor;
import com.zultan.camel.basic.service.UserService;

@Component
public class ApplicationResource extends RouteBuilder {

	@Autowired
	private UserService userService = new UserService();
	
	@BeanInject
	private UserProcessor userProcessor;
	
	
	@Override
	public void configure() throws Exception {
		
		restConfiguration().component("servlet").port(9090).host("localhost").bindingMode(RestBindingMode.json);
		
//		from("rest:get:services/javadsl/{city}?produces=application/json")
//		.outputType(String.class)
//		.process(this::getCity);
		
		rest()
		.get("/hello-world/{city}")
		.produces(MediaType.APPLICATION_JSON_VALUE)
		.route()
		.process(this::getCity)
		//.setBody(constant("Let's enjoy the unta ride...."))
		.endRest();

		rest()
		.get("/getusers")
		.produces(MediaType.APPLICATION_JSON_VALUE)
		.route()
		.setBody(() -> userService.getUsers())
		.endRest();
		
		rest()
		.post("/adduser")
		.consumes(MediaType.APPLICATION_JSON_VALUE)
		.type(User.class)
		.outType(User.class)
		.route()
		.process(userProcessor)
		.endRest();
	}
	
	private void getCity(Exchange exchange) throws JsonProcessingException {
		
		String city = exchange.getMessage().getHeader("city", String.class);
		
		ArrayList<User> users = userService.getUsers();

		
		ObjectMapper Obj = new ObjectMapper(); 
		String jsonStr = Obj.writeValueAsString(users);
		
		Message message = new DefaultMessage(exchange.getContext());
		//message.setBody(jsonStr);
		message.setBody("Hello...Welcome to " + city + "!!!");
		exchange.setMessage(message);
	}

}
