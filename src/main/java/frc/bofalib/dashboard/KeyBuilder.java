package frc.bofalib.dashboard;

import java.util.Collection;
import java.util.Objects;

public final class KeyBuilder {
    private final StringBuilder stringBuilder;

    private KeyBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public KeyBuilder() {
        this(new StringBuilder());
    }

    public KeyBuilder(String root) {
        this(new StringBuilder(root));
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

    public KeyBuilder addedWith(String extension) {
        add(extension);
        return this;
    }

    public KeyBuilder addedWithAll(Collection<String> extensions) {
        addAll(extensions);
        return this;
    }

    public KeyBuilder copy() {
        return new KeyBuilder(new StringBuilder(stringBuilder));
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    public String toStringAddedWith(String extension) {
        return copy().addedWith(extension).toString();
    }

    public String toStringAddedWithAll(Collection<String> extensions) {
        return copy().addedWithAll(extensions).toString();
    }
}
