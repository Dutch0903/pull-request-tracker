package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.repository.PullRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchAllAuthorsTest {
    @Mock
    private PullRequestRepository pullRequestRepository;

    @InjectMocks
    private FetchAllAuthors fetchAllAuthors;

    @Test
    void execute_shouldReturnAllAuthors() {
        when(pullRequestRepository.findAllAuthors()).thenReturn(List.of("alice", "bob"));

        List<String> result = fetchAllAuthors.execute();

        assertThat(result).containsExactly("alice", "bob");
    }

    @Test
    void execute_whenNoPullRequestsExist_shouldReturnEmptyList() {
        when(pullRequestRepository.findAllAuthors()).thenReturn(List.of());

        List<String> result = fetchAllAuthors.execute();

        assertThat(result).isEmpty();
    }
}
