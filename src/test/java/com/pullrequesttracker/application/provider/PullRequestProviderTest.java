package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.application.exception.FetchPullRequestException;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static com.pullrequesttracker.testfixtures.domain.sync.PullRequestSyncDataTestBuilder.aPullRequestSyncData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PullRequestProviderTest {
    @Mock
    private PlatformPullRequestProvider platformPullRequestProvider;

    @Test
    void fetch_withUnmappedPlatform_shouldThrowException() {
        PullRequestProvider provider = new PullRequestProvider(Collections.emptyList());
        CodeRepository repository = aCodeRepository().withPlatform(Platform.GITHUB).build();

        FetchPullRequestException exception = assertThrows(FetchPullRequestException.class,
                () -> provider.fetch(repository));

        assertEquals("No provider registered for platform: GITHUB", exception.getMessage());
    }

    @Test
    void fetch_withMappedPlatform_shouldReturnPullRequest() {
        when(platformPullRequestProvider.platform()).thenReturn(Platform.GITHUB);
        PullRequestProvider provider = new PullRequestProvider(List.of(platformPullRequestProvider));
        CodeRepository repository = aCodeRepository().build();
        PullRequestSyncData syncData = aPullRequestSyncData().build();
        when(platformPullRequestProvider.fetch(repository)).thenReturn(List.of(syncData));

        assertEquals(List.of(syncData), provider.fetch(repository));
    }
}
