package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static com.pullrequesttracker.testfixtures.domain.model.PullRequestTestBuilder.aPullRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindPullRequestsTest {
    @Mock
    private PullRequestRepository pullRequestRepository;

    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @InjectMocks
    private FindPullRequests findPullRequests;

    @Test
    void execute_whenNoFiltersProvided_shouldReturnAllPullRequests() {
        CodeRepository repo = aCodeRepository().build();
        PullRequest pr1 = aPullRequest().withCodeRepositoryId(repo.getId()).build();
        PullRequest pr2 = aPullRequest().withCodeRepositoryId(repo.getId()).build();

        when(pullRequestRepository.findAll(anyList())).thenReturn(List.of(pr1, pr2));
        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repo));

        List<PullRequestListItemDto> result = findPullRequests.execute(List.of());

        assertThat(result).hasSize(2);
    }

    @Test
    void execute_whenStatusFilterIsApplied_shouldReturnOnlyMatchingPullRequests() {
        CodeRepository repo = aCodeRepository().build();
        PullRequest openPr = aPullRequest().withCodeRepositoryId(repo.getId()).build();

        when(pullRequestRepository.findAll(List.of(PullRequestFilter.status(PullRequestStatus.OPEN))))
                .thenReturn(List.of(openPr));
        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repo));

        List<PullRequestListItemDto> result = findPullRequests
                .execute(List.of(PullRequestFilter.status(PullRequestStatus.OPEN)));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().externalId()).isEqualTo(openPr.getExternalId());
    }

    @Test
    void execute_whenSearchMatchesTitle_shouldReturnMatchingPullRequests() {
        CodeRepository repo = aCodeRepository().build();
        PullRequest pr = aPullRequest().withCodeRepositoryId(repo.getId()).withTitle("Fix null pointer").build();

        when(pullRequestRepository.findAll(List.of(PullRequestFilter.search("null pointer")))).thenReturn(List.of(pr));
        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repo));

        List<PullRequestListItemDto> result = findPullRequests
                .execute(List.of(PullRequestFilter.search("null pointer")));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().title()).isEqualTo("Fix null pointer");
    }

    @Test
    void execute_whenSearchMatchesAuthor_shouldReturnMatchingPullRequests() {
        CodeRepository repo = aCodeRepository().build();
        PullRequest pr = aPullRequest().withCodeRepositoryId(repo.getId()).withAuthor("jane.doe").build();

        when(pullRequestRepository.findAll(List.of(PullRequestFilter.search("jane")))).thenReturn(List.of(pr));
        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repo));

        List<PullRequestListItemDto> result = findPullRequests.execute(List.of(PullRequestFilter.search("jane")));

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().author()).isEqualTo("jane.doe");
    }

    @Test
    void execute_shouldSortByUpdatedAtDescending() {
        CodeRepository repo = aCodeRepository().build();
        Instant older = Instant.parse("2024-01-01T00:00:00Z");
        Instant newer = Instant.parse("2024-06-01T00:00:00Z");
        PullRequest olderPr = aPullRequest().withCodeRepositoryId(repo.getId()).withUpdatedAt(older).build();
        PullRequest newerPr = aPullRequest().withCodeRepositoryId(repo.getId()).withUpdatedAt(newer).build();

        when(pullRequestRepository.findAll(anyList())).thenReturn(List.of(olderPr, newerPr));
        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repo));

        List<PullRequestListItemDto> result = findPullRequests.execute(List.of());

        assertThat(result.get(0).updatedAt()).isEqualTo(newer);
        assertThat(result.get(1).updatedAt()).isEqualTo(older);
    }

    @Test
    void execute_shouldConstructGitHubUrl() {
        CodeRepository repo = aCodeRepository().build();
        PullRequest pr = aPullRequest().withCodeRepositoryId(repo.getId()).withExternalId(42).build();

        when(pullRequestRepository.findAll(anyList())).thenReturn(List.of(pr));
        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repo));

        List<PullRequestListItemDto> result = findPullRequests.execute(List.of());

        assertThat(result.getFirst().url()).isEqualTo("https://github.com/account/repo/pull/42");
    }

    @Test
    void execute_whenRepoFilterIsApplied_shouldReturnOnlyThatReposPullRequests() {
        CodeRepository repo = aCodeRepository().build();
        PullRequest pr = aPullRequest().withCodeRepositoryId(repo.getId()).build();

        when(pullRequestRepository.findAll(List.of(PullRequestFilter.codeRepository(repo.getId()))))
                .thenReturn(List.of(pr));
        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repo));

        List<PullRequestListItemDto> result = findPullRequests
                .execute(List.of(PullRequestFilter.codeRepository(repo.getId())));

        assertThat(result).hasSize(1);
    }
}
