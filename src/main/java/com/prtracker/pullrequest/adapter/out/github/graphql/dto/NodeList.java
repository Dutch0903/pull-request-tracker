package com.prtracker.pullrequest.adapter.out.github.graphql.dto;

import java.util.List;

public record NodeList<T>(List<T> nodes) {
}
