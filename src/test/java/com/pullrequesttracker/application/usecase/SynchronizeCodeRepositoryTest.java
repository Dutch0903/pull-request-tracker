package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.provider.PullRequestProvider;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static com.pullrequesttracker.testfixtures.domain.sync.PullRequestSyncDataTestBuilder.aPullRequestSyncData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SynchronizeCodeRepositoryTest {

    @Mock
    private PullRequestProvider pullRequestProvider;

    @Mock
    private PullRequestRepository pullRequestRepository;

    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    private final Instant fixedNow = Instant.parse("2026-06-17T10:00:00Z");
    private final Clock clock = Clock.fixed(fixedNow, ZoneId.of("UTC"));
    private SynchronizeCodeRepository synchronizeCodeRepository;

    @BeforeEach
    public void setUp() {
        synchronizeCodeRepository = new SynchronizeCodeRepository(pullRequestProvider, pullRequestRepository, codeRepositoryRepository, clock);
    }

    @Test
    void execute_whenCodeRepositoryHasNonePullRequest_shouldUpdateCheckedTime() {
        CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
        CodeRepository codeRepository = aCodeRepository().withId(codeRepositoryId).build();

        when(pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId)).thenReturn(List.of());
        when(pullRequestProvider.fetch(codeRepository)).thenReturn(List.of());

        synchronizeCodeRepository.execute(codeRepository);

        verify(codeRepositoryRepository).save(codeRepository);
        assertThat(codeRepository.getLastCheckedAt()).isEqualTo(Optional.of(fixedNow));
    }

    @Test
    void execute_whenProviderReturnsPullRequestNotInRepository_shouldSaveNewPullRequest() {
        CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
        CodeRepository codeRepository = aCodeRepository().withId(codeRepositoryId).build();

        when(pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId)).thenReturn(List.of());
        when(pullRequestProvider.fetch(codeRepository)).thenReturn(List.of(aPullRequestSyncData().withExternalId(1).build()));

        synchronizeCodeRepository.execute(codeRepository);

        verify(pullRequestRepository).save(org.mockito.ArgumentMatchers.any(PullRequest.class));
    }

    @Test
    void execute_whenProviderReturnsPullRequestAlreadyInRepository_shouldSyncAndSavePullRequest() {
        CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
        CodeRepository codeRepository = aCodeRepository().withId(codeRepositoryId).build();
        PullRequest existing = aPullRequest().withCodeRepositoryId(codeRepositoryId).withExternalId(1).withTitle("old title").build();

        when(pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId)).thenReturn(List.of(existing));
        when(pullRequestProvider.fetch(codeRepository)).thenReturn(List.of(aPullRequestSyncData().withExternalId(1).withTitle("new title").build()));

        synchronizeCodeRepository.execute(codeRepository);

        ArgumentCaptor<PullRequest> captor = ArgumentCaptor.forClass(PullRequest.class);
        verify(pullRequestRepository).save(captor.capture());
        assertThat(captor.getValue().getTitle()).isEqualTo("new title");
    }

    @Test
    void execute_whenProviderReturnsMultiplePullRequests_shouldSaveEachPullRequest() {
        CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
        CodeRepository codeRepository = aCodeRepository().withId(codeRepositoryId).build();
        PullRequest existing = aPullRequest().withCodeRepositoryId(codeRepositoryId).withExternalId(1).build();

        when(pullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId)).thenReturn(List.of(existing));
        when(pullRequestProvider.fetch(codeRepository)).thenReturn(List.of(
                aPullRequestSyncData().withExternalId(1).build(),
                aPullRequestSyncData().withExternalId(2).build()
        ));

        synchronizeCodeRepository.execute(codeRepository);

        verify(pullRequestRepository, times(2)).save(org.mockito.ArgumentMatchers.any(PullRequest.class));
    }
}
