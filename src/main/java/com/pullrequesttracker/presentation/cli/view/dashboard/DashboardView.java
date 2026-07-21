package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.dto.AttentionItemDto;
import com.pullrequesttracker.application.dto.CodeRepositorySummaryDto;
import com.pullrequesttracker.application.dto.PullRequestSummaryDto;
import com.pullrequesttracker.presentation.cli.navigation.ViewRefreshConfiguration;
import com.pullrequesttracker.presentation.cli.component.CharSpacer;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.toolkit.element.Element;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static com.pullrequesttracker.presentation.cli.component.SectionPanel.sectionPanel;
import static dev.tamboui.toolkit.Toolkit.*;

@ViewComponent(name = ViewName.DASHBOARD, isStartView = true)
public class DashboardView extends View {
    private final DashboardController controller;
    private final DashboardState state;
    private final int attentionPreviewLimit;

    public DashboardView(DialogManager dialogManager, DashboardController controller, DashboardState state,
            DashboardKeyHandler keyHandler, ViewRefreshConfiguration viewRefreshProperties,
            @Value("${dashboard.attention-preview-limit:4}") int attentionPreviewLimit) {
        super(dialogManager, keyHandler, viewRefreshProperties);
        this.controller = controller;
        this.state = state;
        this.attentionPreviewLimit = attentionPreviewLimit;
    }

    @Override
    protected void refreshState() {
        controller.loadCodeRepositorySummaries();
        controller.loadPullRequestSummary();
        controller.loadAttentionItems();
    }

    @Override
    protected Element renderBody() {
        return column(row(needsAttentionSection()).percent(25).fill(), row(readyToMergeSection()).percent(25),
                row(column(repositoriesSection()).percent(50), column(overviewSection()).percent(50)).percent(50));
    }

    private Element needsAttentionSection() {
        List<AttentionItemDto> all = state.getOrElse(DashboardState.ATTENTION_ITEMS, List.of());

        if (all.isEmpty()) {
            return sectionPanel("NEEDS YOUR ATTENTION", text("✓ Nothing needs your attention right now.").dim());
        }

        List<Element> rows = new ArrayList<>();
        all.stream().limit(attentionPreviewLimit)
                .forEach(pr -> rows.add(text("#" + pr.externalId() + "  " + pr.title())));
        if (all.size() > attentionPreviewLimit) {
            rows.add(text("... and " + (all.size() - attentionPreviewLimit) + " more  [a to view]").dim());
        }
        return sectionPanel("NEEDS YOUR ATTENTION", column(rows.toArray(Element[]::new)));
    }

    private Element readyToMergeSection() {
        return sectionPanel("READY TO MERGE", text("No pull requests are ready to merge."));
    }

    private Element repositoriesSection() {
        List<CodeRepositorySummaryDto> repos = state.get(DashboardState.REPOSITORY_SUMMARIES);
        Element content = repos.isEmpty()
                ? text("No repositories configured.").dim()
                : column(repos.stream().map(r -> overviewRow(r.fullName(), "" + r.pullRequestCount()))
                        .toArray(Element[]::new));

        return sectionPanel("REPOSITORIES", content);
    }

    private Element overviewSection() {
        PullRequestSummaryDto summary = state.get(DashboardState.PULL_REQUEST_SUMMARY);

        return sectionPanel("OVERVIEW",
                column(overviewRow("Open PRs", "" + summary.open()),
                        overviewRow("Ready for review", "" + summary.readyForReview()),
                        overviewRow("Drafts", "" + summary.drafts()),
                        overviewRow("Stale (no update >7d)", "" + summary.stale()),
                        overviewRow("Failing CI", "" + summary.failingContinuousIntegration())));
    }

    private Element overviewRow(String label, String value) {
        return row(text(label), CharSpacer.of('.'), text(value));
    }
}
