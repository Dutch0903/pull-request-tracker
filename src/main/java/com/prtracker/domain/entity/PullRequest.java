package com.prtracker.domain.entity;

import com.prtracker.domain.enums.PullRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class PullRequest {
	private long id;
	private String title;
	private String author;
	private PullRequestStatus status;
	private Instant updatedAt;
}
