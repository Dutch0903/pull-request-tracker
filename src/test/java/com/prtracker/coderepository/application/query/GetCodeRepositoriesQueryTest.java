package com.prtracker.coderepository.application.query;

import com.prtracker.coderepository.application.query.GetCodeRepositories;
import com.prtracker.coderepository.application.query.port.CodeRepositoryReadPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GetCodeRepositoriesQueryTest {
    @Mock
    private CodeRepositoryReadPort codeRepositoryReadPort;

    @InjectMocks
    private GetCodeRepositories getCodeRepositories;

    @Test
    void execute_whenCalled_shouldCallCodeRepositoryReadPort() {
        getCodeRepositories.execute();

        verify(codeRepositoryReadPort, times(1)).findAllAsViews();
    }
}
