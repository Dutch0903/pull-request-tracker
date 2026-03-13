package com.prtracker.application.command;

import com.prtracker.application.dto.AddTokenDto;
import com.prtracker.domain.entity.Token;
import com.prtracker.domain.repository.TokenRepository;
import com.prtracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddTokenCommand extends Command<AddTokenDto, Void> {
	private final TokenRepository tokenRepository;

	@Override
	protected Void executeInternal(AddTokenDto input) {
		Token token = new Token(TokenId.create(), input.name(), input.value());
		tokenRepository.save(token);
		return null;
	}
}
