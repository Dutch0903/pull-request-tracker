package com.pullrequesttracker.presentation.cli.view.pullrequest.component;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import dev.tamboui.layout.Padding;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.StyledElement;
import dev.tamboui.toolkit.elements.ListElement;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static dev.tamboui.toolkit.Toolkit.*;

@Component
public class PullRequestList {
    private final PullRequestListState state;
    private final ListElement<?> listElement = list().highlightColor(Color.LIGHT_GREEN).highlightSymbol(">> ")
            .autoScroll();

    public PullRequestList(PullRequestListState state) {
        this.state = state;
    }

    public PullRequestListItemDto getSelectedItem() {
        List<PullRequestListItemDto> items = state.get(PullRequestListState.PULL_REQUEST_ITEMS)
                .getOrElse(Collections.emptyList());
        if (items.isEmpty())
            return null;
        int index = listElement.selected();
        return index < items.size() ? items.get(index) : null;
    }

    public Element render(List<PullRequestListItemDto> items) {
        Element content = items.isEmpty()
                ? text("No pull requests found.").dim()
                : listElement.data(items, this::renderItem);
        return panel("Pull Requests", content).fill().padding(Padding.symmetric(1, 2)).focusable()
                .onKeyEvent(event -> listElement.handleKeyEvent(event, true));
    }

    private StyledElement<?> renderItem(PullRequestListItemDto pr) {
        return column(row(text(formatLine1(pr)), spacer()), row(text(formatLine2(pr)).dim(), spacer()));
    }

    private String formatLine1(PullRequestListItemDto pr) {
        String prefix = pr.draft() ? "[DRAFT]" : "o";
        String age = formatAge(pr.updatedAt());
        return String.format("%s #%d  %s  @%s  %s", prefix, pr.externalId(), pr.title(), pr.author(), age);
    }

    private String formatLine2(PullRequestListItemDto pr) {
        String ci = formatCiStatus(pr.ciStatus());
        String review = formatReviewStatus(pr.reviewStatus(), pr.approvalCount());
        String comments = pr.commentCount() + " comments";
        String labels = pr.labels().isEmpty() ? "" : "  [" + String.join(", ", pr.labels()) + "]";
        return String.format("    %s  %s  %s%s", ci, review, comments, labels);
    }

    private String formatCiStatus(CiStatus ciStatus) {
        if (ciStatus == CiStatus.PASSED)
            return "CI ok";
        if (ciStatus == CiStatus.FAILED)
            return "CI fail";
        if (ciStatus == CiStatus.IN_PROGRESS)
            return "CI running";
        return "CI pending";
    }

    private String formatReviewStatus(ReviewStatus reviewStatus, int approvalCount) {
        if (reviewStatus == ReviewStatus.APPROVED)
            return "approved (" + approvalCount + ")";
        if (reviewStatus == ReviewStatus.CHANGES_REQUESTED)
            return "changes requested";
        if (reviewStatus == ReviewStatus.COMMENTED)
            return "commented";
        if (reviewStatus == ReviewStatus.DISMISSED)
            return "dismissed";
        return "review required";
    }

    private String formatAge(Instant updatedAt) {
        Duration age = Duration.between(updatedAt, Instant.now());
        if (age.toHours() < 1)
            return age.toMinutes() + "m ago";
        if (age.toDays() < 1)
            return age.toHours() + "h ago";
        if (age.toDays() < 7)
            return age.toDays() + "d ago";
        return (age.toDays() / 7) + "w ago";
    }
}
