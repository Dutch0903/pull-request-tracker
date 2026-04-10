package com.prtracker.application.query;

import com.prtracker.application.repository.CodeRepositoryReadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GetRecentCodeRepositoriesQueryTest {
    @Mock
    private CodeRepositoryReadRepository codeRepositoryReadRepository;

    @InjectMocks
    private GetRecentCodeRepositoriesQuery getRecentCodeRepositoriesQuery;

    @Test
    void execute_whenCalled_shouldCallCodeRepositoryReadRepository() {
        getRecentCodeRepositoriesQuery.execute();

        verify(codeRepositoryReadRepository, times(1)).findAllAsViews();
    }
}
