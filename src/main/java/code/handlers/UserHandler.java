package code.handlers;

import static org.springframework.http.MediaType.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

import code.documents.User;
import code.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
	private final UserRepository repository;
	private final Mono<ServerResponse> notFound = ServerResponse.notFound().build();

	public Mono<ServerResponse> insert(ServerRequest request) {
		final Mono<User> user$ = request.bodyToMono(User.class);
		
		return user$.flatMap(user -> ok().contentType(APPLICATION_JSON)
			.body(this.repository.save(user), User.class)
		);
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		Flux<User> users = this.repository.findAll();
		return ok().contentType(APPLICATION_JSON)
			.body(users, User.class);
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		final String id = request.pathVariable("id");
		final Mono<User> user$ = request.bodyToMono(User.class);
		return user$.flatMap(user -> {
			user.setUserId(id);
			return ok().contentType(APPLICATION_JSON)
				.body(this.repository.save(user), User.class)
				.switchIfEmpty(this.notFound);
		}); 
	}

	public Mono<ServerResponse> delete(ServerRequest request) {
		String id = request.pathVariable("id");
		return this.repository.deleteById(id).
			flatMap(user -> ok().contentType(APPLICATION_JSON)
				.body(user, Void.class)
				.switchIfEmpty(this.notFound)
			);
	}

}
