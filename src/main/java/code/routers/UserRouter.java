package code.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import code.handlers.UserHandler;

@Configuration
public class UserRouter {
	
	@Bean
	RouterFunction<ServerResponse> getUsers(UserHandler handler) {
		return RouterFunctions.
		route(GET("/users"), handler::findAll)
		.andRoute(POST("/users"), handler::insert)
		.andRoute(PUT("/users/{id}"), handler::update)
		.andRoute(DELETE("/users/{id}"), handler::delete);
	}

}
