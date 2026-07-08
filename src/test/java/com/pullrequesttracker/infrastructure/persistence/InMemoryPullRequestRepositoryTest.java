package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
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

@ExtendWith(MockitoExtension.class)
class InMemoryPullRequestRepositoryTest {
    @Mock
    private FileStorage fileStorage;

    @Mock
    private PullRequestMapper mapper;

    @InjectMocks
    private InMemoryPullRequestRepository repository;

    @Test
    void findAll_whenStatusFilterIsApplied_shouldReturnOnlyMatchingPullRequests() {
        CodeRepository repo = aCodeRepository().build();
        PullRequest openPr = aPullRequest().withCodeRepositoryId(repo.getId()).build();
        PullRequest mergedPr = aPullRequest().withCodeRepositoryId(repo.getId())
                .withMergeInfo(new MergeInfo(new Actor("merger"), Instant.now())).build();
        repository.save(openPr);
        repository.save(mergedPr);

        List<PullRequest> result = repository.findAll(List.of(PullRequestFilter.status(PullRequestStatus.OPEN)));

        assertThat(result).containsExactly(openPr);
    }

    @Test
    void findAll_whenMultipleFiltersAreApplied_shouldApplyAllAsAnd() {
        CodeRepository repo1 = aCodeRepository().build();
        CodeRepository repo2 = aCodeRepository().build();
        PullRequest pr1 = aPullRequest().withCodeRepositoryId(repo1.getId()).withTitle("Fix login bug").build();
        PullRequest pr2 = aPullRequest().withCodeRepositoryId(repo2.getId()).withTitle("Fix login bug").build();
        PullRequest pr3 = aPullRequest().withCodeRepositoryId(repo1.getId()).withTitle("Add feature").build();
        repository.save(pr1);
        repository.save(pr2);
        repository.save(pr3);

        List<PullRequest> result = repository
                .findAll(List.of(PullRequestFilter.codeRepository(repo1.getId()), PullRequestFilter.search("login")));

        assertThat(result).containsExactly(pr1);
    }

    @Test
    void findAll_whenAuthorFilterIsApplied_shouldReturnOnlyMatchingPullRequests() {
        PullRequest alicePr = aPullRequest().withAuthor("alice").build();
        PullRequest bobPr = aPullRequest().withAuthor("bob").build();
        repository.save(alicePr);
        repository.save(bobPr);

        List<PullRequest> result = repository.findAll(List.of(PullRequestFilter.author("alice")));

        assertThat(result).containsExactly(alicePr);
    }

    @Test
    void findAll_whenAuthorFilterWithUnknownAuthor_shouldReturnEmptyList() {
        PullRequest pr = aPullRequest().withAuthor("alice").build();
        repository.save(pr);

        List<PullRequest> result = repository.findAll(List.of(PullRequestFilter.author("unknown")));

        assertThat(result).isEmpty();
    }

    @Test
    void findAllAuthors_withMultiplePullRequests_shouldReturnDistinctSortedAuthors() {
        repository.save(aPullRequest().withAuthor("charlie").build());
        repository.save(aPullRequest().withAuthor("alice").build());
        repository.save(aPullRequest().withAuthor("bob").build());
        repository.save(aPullRequest().withAuthor("alice").build());

        List<String> result = repository.findAllAuthors();

        assertThat(result).containsExactly("alice", "bob", "charlie");
    }
}
