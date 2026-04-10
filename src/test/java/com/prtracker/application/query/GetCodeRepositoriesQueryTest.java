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
public class GetCodeRepositoriesQueryTest
{
    @Mock
    private CodeRepositoryReadRepository codeRepositoryReadRepository;

    @InjectMocks
    private GetCodeRepositoriesQuery getCodeRepositoriesQuery;

    @Test
    void execute_whenCalled_shouldCallCodeRepositoryReadRepository() {
        getCodeRepositoriesQuery.execute();

        verify(codeRepositoryReadRepository, times(1)).findAllAsViews();
    }
}
