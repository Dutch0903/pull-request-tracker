package com.prtracker.application.query;

import com.prtracker.application.repository.TokenReadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GetTokensQueryTest {
    @Mock
    private TokenReadRepository tokenReadRepository;

    @InjectMocks
    private GetTokensQuery getTokensQuery;

    @Test
    void execute_whenCalled_shouldUseTokenReadRepository() {
        getTokensQuery.execute();

        verify(tokenReadRepository, times(1)).findAllAsProjection();
    }
}
