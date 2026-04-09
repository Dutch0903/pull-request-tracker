package com.prtracker.domain.valueobject;

import com.prtracker.domain.exceptions.InvalidFullNameException;

public record FullName(String owner, String name) {
    public FullName {
        if (owner == null) {
            throw new InvalidFullNameException("Owner cannot be null");
        }

        if (name == null) {
            throw new InvalidFullNameException("Name cannot be null");
        }
    }

    @Override
    public String toString() {
        return owner + "/" + name;
    }
}
