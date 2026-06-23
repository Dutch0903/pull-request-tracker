package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.TokenTestBuilder.aToken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchAllTokensTest {
    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private FetchAllTokens fetchAllTokens;

    @Test
    void execute_whenCalled_shouldMapTokenToDto() {
        Token token1 = aToken().build();
        Token token2 = aToken().build();

        when(tokenRepository.findAll()).thenReturn(List.of(token1, token2));

        List<TokenDto> result = fetchAllTokens.execute();

        assertThat(result).containsExactly(
                new TokenDto(token1.id().value(), token1.name().value(), token1.value().value(),
                        token1.platform().toString(), token1.username().value(),
                        token1.expirationDate().value().toString()),
                new TokenDto(token2.id().value(), token2.name().value(), token2.value().value(),
                        token2.platform().toString(), token2.username().value(),
                        token2.expirationDate().value().toString()));
    }

    @Test
    void execute_whenNoTokenFound_shouldReturnEmptyList() {
        List<TokenDto> result = fetchAllTokens.execute();

        assertThat(result).isEmpty();
    }
}
