package com.pullrequesttracker.infrastructure.external.github.graphql.dto;

import java.util.List;

public record NodeList<T>(List<T> nodes) {
}
