package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.pullrequesttracker.testfixtures.application.provider.TokenInfoTestBuilder.aTokenInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenInfoProviderTest {
    @Mock
    private PlatformTokenInfoProvider platformTokenInfoProvider;

    @Test
    void fetch_withUnmappedPlatform_shouldThrowException() {
        TokenValue tokenValue = TokenValue.from("value");
        TokenInfoProvider provider = new TokenInfoProvider(Collections.emptyList());
        TokenInfoException exception = assertThrows(TokenInfoException.class,
                () -> provider.fetch(Platform.GITHUB, tokenValue));

        assertEquals("No token info provider registered for platform: GITHUB", exception.getMessage());
    }

    @Test
    void fetch_withMappedPlatform_shouldReturnTokenInfo() {
        when(platformTokenInfoProvider.platform()).thenReturn(Platform.GITHUB);
        TokenInfoProvider provider = new TokenInfoProvider(List.of(platformTokenInfoProvider));

        TokenValue tokenValue = TokenValue.from("value");

        TokenInfo tokenInfo = aTokenInfo().build();

        when(platformTokenInfoProvider.fetch(tokenValue)).thenReturn(tokenInfo);

        assertEquals(tokenInfo, provider.fetch(Platform.GITHUB, tokenValue));
    }
}
