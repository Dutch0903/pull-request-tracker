package com.pullrequesttracker.application.dto;

public enum RecentActivityType {
    MERGED, APPROVED, OPENED, CHANGES_REQUESTED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
