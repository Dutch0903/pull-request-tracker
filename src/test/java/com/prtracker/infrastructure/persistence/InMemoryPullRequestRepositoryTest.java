package com.prtracker.infrastructure.persistence;

import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.PullRequestId;
import com.prtracker.infrastructure.persistence.dto.PullRequestDto;
import com.prtracker.infrastructure.persistence.mapper.PullRequestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.prtracker.testfixtures.domain.entity.PullRequestTestBuilder.aPullRequest;
import static com.prtracker.testfixtures.infrastructure.persistence.dto.PullRequestDtoTestBuilder.aPullRequestDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryPullRequestRepositoryTest {

    @Mock
    private FileStorage fileStorage;

    @Mock
    private PullRequestMapper mapper;

    @InjectMocks
    private InMemoryPullRequestRepository inMemoryPullRequestRepository;

    @Test
    void save_whenCalled_shouldAddPullRequestToList() {
        PullRequest pullRequest = aPullRequest().build();

        assertEquals(0, inMemoryPullRequestRepository.findAll().size());
        inMemoryPullRequestRepository.save(pullRequest);

        assertEquals(List.of(pullRequest), inMemoryPullRequestRepository.findAll());
    }

    @Test
    void save_whenPullRequestAlreadyExists_shouldOverrideExisting() {
        PullRequest pullRequest = aPullRequest().build();

        inMemoryPullRequestRepository.save(pullRequest);
        assertEquals(1, inMemoryPullRequestRepository.findAll().size());

        inMemoryPullRequestRepository.save(pullRequest);
        assertEquals(1, inMemoryPullRequestRepository.findAll().size());
    }

    @Test
    void delete_whenCalled_shouldDeletePullRequestFromList() {
        PullRequest pullRequest = aPullRequest().build();
        inMemoryPullRequestRepository.save(pullRequest);

        assertEquals(1, inMemoryPullRequestRepository.findAll().size());

        inMemoryPullRequestRepository.delete(pullRequest);

        assertEquals(0, inMemoryPullRequestRepository.findAll().size());
    }

    @Test
    void findById_whenPullRequestExists_shouldReturnPullRequest() {
        PullRequest pullRequest = aPullRequest().build();
        inMemoryPullRequestRepository.save(pullRequest);

        Optional<PullRequest> result = inMemoryPullRequestRepository.findById(pullRequest.getId());

        assertTrue(result.isPresent());
        assertEquals(pullRequest, result.get());
    }

    @Test
    void findById_whenPullRequestDoesNotExist_shouldReturnEmpty() {
        Optional<PullRequest> result = inMemoryPullRequestRepository.findById(PullRequestId.create());

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_whenCalled_shouldReturnAllPullRequests() {
        PullRequest pullRequest1 = aPullRequest().build();
        PullRequest pullRequest2 = aPullRequest().build();

        inMemoryPullRequestRepository.save(pullRequest1);
        inMemoryPullRequestRepository.save(pullRequest2);

        List<PullRequest> result = inMemoryPullRequestRepository.findAll();
        assertTrue(result.contains(pullRequest1));
        assertTrue(result.contains(pullRequest2));
    }

    @Test
    void findAllByCodeRepositoryId_whenCalled_shouldReturnMatchingPullRequests() {
        CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
        PullRequest pullRequest1 = aPullRequest().withCodeRepositoryId(codeRepositoryId).build();
        PullRequest pullRequest2 = aPullRequest().withCodeRepositoryId(codeRepositoryId).build();

        inMemoryPullRequestRepository.save(pullRequest1);
        inMemoryPullRequestRepository.save(pullRequest2);

        List<PullRequest> result = inMemoryPullRequestRepository.findAllByCodeRepositoryId(codeRepositoryId);
        assertTrue(result.contains(pullRequest1));
        assertTrue(result.contains(pullRequest2));
    }

    @Test
    void findAllByCodeRepositoryId_whenNoMatch_shouldReturnEmpty() {
        PullRequest pullRequest = aPullRequest().withCodeRepositoryId(CodeRepositoryId.create()).build();
        inMemoryPullRequestRepository.save(pullRequest);

        List<PullRequest> result = inMemoryPullRequestRepository.findAllByCodeRepositoryId(CodeRepositoryId.create());

        assertTrue(result.isEmpty());
    }

    @Test
    void initialize_whenCalled_shouldLoadFromFileAndMapToPullRequest() {
        PullRequestDto pullRequestDto = aPullRequestDto().build();
        PullRequest pullRequest = aPullRequest().build();

        when(fileStorage.load(anyString(), eq(PullRequestDto.class))).thenReturn(List.of(pullRequestDto));
        when(mapper.toDomain(pullRequestDto)).thenReturn(pullRequest);

        inMemoryPullRequestRepository.initialize();

        assertEquals(List.of(pullRequest), inMemoryPullRequestRepository.findAll());

        verify(fileStorage, times(1)).load(anyString(), eq(PullRequestDto.class));
        verify(mapper, times(1)).toDomain(pullRequestDto);
    }

    @Test
    void persist_whenCalled_shouldPersistPullRequestsToFile() throws IOException {
        PullRequest pullRequest = aPullRequest().build();
        PullRequestDto pullRequestDto = aPullRequestDto().build();
        inMemoryPullRequestRepository.save(pullRequest);

        when(mapper.toDto(pullRequest)).thenReturn(pullRequestDto);

        inMemoryPullRequestRepository.persist();

        verify(fileStorage, times(1)).save(anyString(), eq(List.of(pullRequestDto)));
    }
}
