package com.pullrequesttracker.presentation.cli.view.repository.component;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.application.dto.RecentActivityEntryDto;
import com.pullrequesttracker.application.dto.RepositoryStatsDto;
import com.pullrequesttracker.presentation.cli.component.CharSpacer;
import com.pullrequesttracker.presentation.cli.component.RelativeTimeFormatter;
import dev.tamboui.layout.Margin;
import dev.tamboui.layout.Padding;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.Column;
import dev.tamboui.toolkit.elements.Panel;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.text;

public class RepositoryDetailPanel {
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());

    private final CodeRepositoryDto projection;
    private final RepositoryStatsDto stats;

    public RepositoryDetailPanel(CodeRepositoryDto projection, RepositoryStatsDto stats) {
        this.projection = projection;
        this.stats = stats;
    }

    public Panel render() {
        String title = projection != null ? projection.owner() + "/" + projection.name() : "Details";

        Element body;
        if (projection == null) {
            body = text("Select a repository to view details.").dim();
        } else if (stats == null) {
            body = text("Loading stats...").dim();
        } else {
            body = column(
                    row(
                            openPullRequestsColumn().fill().margin(Margin.horizontal(1)),
                            ciStatusColumn().fill().margin(Margin.horizontal(1))
                    ).fill(),
                    row(
                        reviewStatusColumn()
                    ).fill(),
                    row(
                            recentActivities()
                    ).fill()
            ).fill();
        }

        return panel(title, body).id("repository-detail-panel").rounded().padding(Padding.symmetric(1, 2));
    }

    private Column openPullRequestsColumn() {
        return column(
                text("OPEN PRS"),
                dotStatRow("Open", stats.openPrCount()),
                dotStatRow("Drafts", stats.draftCount()),
                dotStatRow("Stale (>7d)", stats.stalePrCount())
        );
    }

    private Column ciStatusColumn() {
        return column(
                text("CI STATUS"),
                dotStatRow("Passing", stats.passingCiCount()),
                dotStatRow("Failing", stats.failingCiCount()),
                dotStatRow("Pending", stats.pendingCiCount())
        );
    }

    private Column reviewStatusColumn() {
        return column(
                text("REVIEW STATUS"),
                dotStatRow("Awaiting review", stats.awaitingReviewCount()),
                dotStatRow("Changes requested", stats.changesRequestedCount()),
                dotStatRow("Approved", stats.approvedCount())
        ).percent(50);
    }

    private Column recentActivities() {
        Column column = column(
                text("RECENT ACTIVITY")
        ).fill();

        for (RecentActivityEntryDto entry : stats.recentActivity()) {
            column.add(row(
                    text(entry.author() + " " + entry.type() + " #" + entry.prNumber()),
                    CharSpacer.of('.'),
                    text(RelativeTimeFormatter.format(entry.occurredAt()))
            ));
        }

        return column;
    }

    private Element dotStatRow(String label, int value) {
        return row(text(label), CharSpacer.of('.'), text(String.valueOf(value)));
    }
}
