package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.dto.CodeRepositorySummaryDto;
import com.pullrequesttracker.application.dto.PullRequestSummaryDto;
import com.pullrequesttracker.presentation.cli.component.CharSpacer;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;

import java.util.List;

import static com.pullrequesttracker.presentation.cli.component.SectionPanel.sectionPanel;
import static dev.tamboui.toolkit.Toolkit.*;

@ViewComponent(name = ViewName.DASHBOARD, isStartView = true)
public class DashboardView extends View {
    private final DashboardController controller;
    private final DashboardState state;

    public DashboardView(DialogManager dialogManager, DashboardController controller, DashboardState state,
            DashboardKeyHandler keyHandler) {
        super(dialogManager, keyHandler);
        this.controller = controller;
        this.state = state;
        this.controller.loadCodeRepositorySummaries();
        this.controller.loadPullRequestSummary();
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    @Override
    protected Element renderBody() {
        return column(row(needsAttentionSection()).percent(25).fill(), row(readyToMergeSection()).percent(25),
                row(column(repositoriesSection()).percent(50), column(overviewSection()).percent(50)).percent(50));
    }

    private Element needsAttentionSection() {
        return sectionPanel("NEEDS YOUR ATTENTION", text("✓ Nothing needs your attention right now."));
    }

    private Element readyToMergeSection() {
        return sectionPanel("READY TO MERGE", text("No pull requests are ready to merge."));
    }

    private Element repositoriesSection() {
        List<CodeRepositorySummaryDto> repos = state.get(DashboardState.REPOSITORY_SUMMARIES).data();
        Element content = repos.isEmpty()
                ? text("No repositories configured.").dim()
                : column(repos.stream().map(r -> overviewRow(r.fullName(), "" + r.pullRequestCount()))
                        .toArray(Element[]::new));

        return sectionPanel("REPOSITORIES", content);
    }

    private Element overviewSection() {
        PullRequestSummaryDto summary = state.get(DashboardState.PULL_REQUEST_SUMMARY).data();

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
