package com.pullrequesttracker.presentation.cli.view.pullrequest.component;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import dev.tamboui.layout.Margin;
import dev.tamboui.layout.Padding;
import dev.tamboui.style.AnsiColor;
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
        return column(row(text(formatLine1(pr)), spacer(), text(formatAge(pr.updatedAt()))),
                row(ciSymbol(pr.ciStatus()), text(formatLine2Rest(pr)).dim()).margin(new Margin(0, 0, 1, 0)).length(2));
    }

    private String formatLine1(PullRequestListItemDto pr) {
        String prefix = pr.draft() ? "[DRAFT] " : "";
        return String.format("%s#%d  %s", prefix, pr.externalId(), pr.title());
    }

    private StyledElement<?> ciSymbol(CiStatus ciStatus) {
        return switch (ciStatus) {
            case PASSED -> text("    ✓  ").fg(Color.ansi(AnsiColor.BRIGHT_GREEN));
            case FAILED -> text("    ✗  ").fg(Color.ansi(AnsiColor.BRIGHT_RED));
            case IN_PROGRESS -> text("    ⏳  ").fg(Color.ansi(AnsiColor.BRIGHT_YELLOW));
            default -> text("    ·  ").dim();
        };
    }

    private String formatLine2Rest(PullRequestListItemDto pr) {
        String review = formatReviewStatus(pr.reviewStatus(), pr.approvalCount());
        String comments = "💬 " + pr.commentCount();
        String labels = pr.labels().isEmpty() ? "" : "  [" + String.join(", ", pr.labels()) + "]";
        return String.format("@%s  %s  %s%s", pr.author(), review, comments, labels);
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
