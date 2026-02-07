package frc.bofalib.dashboard;

import java.util.Collection;
import java.util.Objects;

public final class KeyBuilder {
    private final StringBuilder stringBuilder;

    public KeyBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public KeyBuilder(String root) {
        this.stringBuilder = new StringBuilder(root);
    }

    public void add(String extension) {
        if (!Objects.requireNonNull(extension).isEmpty()) {
            if (extension.contains("/")) {
                throw new AssertionError("Extension '" + extension + "' can't contain '/'!");
            }

            if (!stringBuilder.isEmpty()) {
                stringBuilder.append('/');
            }
            
            stringBuilder.append(extension);
        }
    }

    public void addAll(Collection<String> extensions) {
        extensions.forEach(this::add);
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
