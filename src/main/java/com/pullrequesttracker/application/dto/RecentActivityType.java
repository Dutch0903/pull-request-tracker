package com.pullrequesttracker.application.dto;

public enum RecentActivityType {
    MERGED, APPROVED, OPENED;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
