package com.pullrequesttracker.presentation.cli.view.attention.component;

import com.pullrequesttracker.application.dto.AttentionItemDto;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.presentation.cli.component.RelativeTimeFormatter;
import dev.tamboui.layout.Margin;
import dev.tamboui.layout.Padding;
import dev.tamboui.layout.Rect;
import dev.tamboui.style.AnsiColor;
import dev.tamboui.style.Color;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.element.StyledElement;
import dev.tamboui.toolkit.elements.ListElement;
import dev.tamboui.toolkit.event.EventResult;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.pullrequesttracker.presentation.cli.view.attention.component.NeedsAttentionSection.CREATED_PRS;
import static com.pullrequesttracker.presentation.cli.view.attention.component.NeedsAttentionSection.REQUESTED_REVIEW;
import static dev.tamboui.toolkit.Toolkit.*;

@Component
public class NeedsAttentionGroupedList {

    private static final int COL_ID = 6;
    private static final int COL_REPOSITORY = 30;
    private static final int COL_STATUS = 18;
    private static final int COL_AGE = 8;
    private static final int COL_TITLE_MAX = 60;

    private final ListElement<?> createdPrsListElement = list().highlightColor(Color.LIGHT_GREEN)
            .highlightSymbol(">> ").autoScroll().fill();
    private final ListElement<?> requestedReviewListElement = list().highlightColor(Color.LIGHT_GREEN)
            .highlightSymbol(">> ").autoScroll().fill();

    private List<AttentionItemDto> createdPrsItems = List.of();
    private List<AttentionItemDto> requestedReviewItems = List.of();
    private NeedsAttentionSection activeSection = CREATED_PRS;

    public AttentionItemDto getSelectedItem() {
        if (isCreatedPrsFocused()) {
            return createdPrsItems.isEmpty() ? null : createdPrsItems.get(createdPrsListElement.selected());
        }
        return requestedReviewItems.isEmpty() ? null : requestedReviewItems.get(requestedReviewListElement.selected());
    }

    private boolean isCreatedPrsFocused() {
        return activeSection == CREATED_PRS;
    }

    private boolean isRequestedReviewFocused() {
        return activeSection == REQUESTED_REVIEW;
    }

    public Element render(List<AttentionItemDto> createdPrs, List<AttentionItemDto> requestedReviewPrs) {
        createdPrsItems = createdPrs;
        requestedReviewItems = requestedReviewPrs;

        return new Element() {
            @Override
            public void render(Frame frame, Rect area, RenderContext context) {
                buildLayout().render(frame, area, context);
            }

            @Override
            public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
                return Size.UNKNOWN;
            }
        };
    }

    private Element buildLayout() {
        createdPrsListElement.highlightColor(isCreatedPrsFocused() ? Color.LIGHT_GREEN : Color.DARK_GRAY);
        requestedReviewListElement.highlightColor(isRequestedReviewFocused() ? Color.LIGHT_GREEN : Color.DARK_GRAY);

        Element myPrsContent = createdPrsItems.isEmpty()
                ? text("No open pull requests.").dim()
                : column(renderHeader(), createdPrsListElement.data(createdPrsItems, isCreatedPrsFocused() ? this::renderRow : pr -> renderRow(pr).dim()));
        Element toReviewContent = requestedReviewItems.isEmpty()
                ? text("No PRs waiting for your review.").dim()
                : column(renderHeader(), requestedReviewListElement.data(requestedReviewItems, isRequestedReviewFocused() ? this::renderRow : pr -> renderRow(pr).dim()));

        return column(
                panel("MY PULL REQUESTS", myPrsContent)
                        .id("created_prs")
                        .focusable()
                        .borderColor(isCreatedPrsFocused() ? Color.LIGHT_GREEN : Color.DARK_GRAY)
                        .fill()
                        .padding(Padding.symmetric(1, 2))
                        .onKeyEvent(event -> {
                            activeSection = CREATED_PRS;
                            if (event.isConfirm()) return EventResult.UNHANDLED;
                            return createdPrsListElement.handleKeyEvent(event, true);
                        }),
                panel("TO REVIEW", toReviewContent)
                        .id("requested_review")
                        .focusable()
                        .borderColor(isRequestedReviewFocused() ? Color.LIGHT_GREEN : Color.DARK_GRAY)
                        .fill()
                        .padding(Padding.symmetric(1, 2))
                        .onKeyEvent(event -> {
                            activeSection = REQUESTED_REVIEW;
                            if (event.isConfirm()) return EventResult.UNHANDLED;
                            return requestedReviewListElement.handleKeyEvent(event, true);
                        })
        ).fill();
    }

    private Element renderHeader() {
        return row(
                text("CI").dim(),
                text(("%-" + COL_ID + "s").formatted("#ID")).dim(),
                text("TITLE").dim().fill(),
                text(("%-" + COL_REPOSITORY + "s").formatted("REPOSITORY")).dim(),
                text(("%-" + COL_STATUS + "s").formatted("STATUS")).dim(),
                text(("%-" + COL_AGE + "s").formatted("AGE")).dim()
        ).spacing(2).margin(new Margin(0, 0, 0, 3)).length(1);
    }

    private StyledElement<?> renderRow(AttentionItemDto pr) {
        return row(
                ciSymbol(pr.ciStatus()),
                text(("%-" + COL_ID + "s").formatted("#" + pr.externalId())),
                text(formatWithEllipsis(pr.title(), COL_TITLE_MAX)).fill(),
                text(formatWithEllipsis(pr.repositoryFullName(), COL_REPOSITORY)).dim(),
                text(("%-" + COL_STATUS + "s").formatted(formatReviewStatus(pr.reviewStatus()))).dim(),
                text(("%-" + COL_AGE + "s").formatted(RelativeTimeFormatter.format(pr.createdAt()))).dim()
        ).spacing(2);
    }

    private static String formatWithEllipsis(String value, int maxWidth) {
        if (value.length() <= maxWidth) return ("%-" + maxWidth + "s").formatted(value);
        return value.substring(0, maxWidth - 1) + "…";
    }

    private StyledElement<?> ciSymbol(CiStatus ciStatus) {
        return switch (ciStatus) {
            case PASSED -> text("✓").fg(Color.ansi(AnsiColor.BRIGHT_GREEN));
            case FAILED -> text("✗").fg(Color.ansi(AnsiColor.BRIGHT_RED));
            case IN_PROGRESS -> text("⏳").fg(Color.ansi(AnsiColor.BRIGHT_YELLOW));
            default -> text("·").dim();
        };
    }

    private String formatReviewStatus(ReviewStatus reviewStatus) {
        return switch (reviewStatus) {
            case APPROVED -> "APPROVED";
            case CHANGES_REQUESTED -> "CHANGES REQUESTED";
            case COMMENTED -> "COMMENTED";
            case DISMISSED -> "DISMISSED";
            default -> "REVIEW REQUIRED";
        };
    }
}
