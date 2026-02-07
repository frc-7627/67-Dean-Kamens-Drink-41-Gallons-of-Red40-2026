package frc.bofalib.dashboard;

import java.util.Collection;
import java.util.Objects;

public final class KeyBuilder {
    private final StringBuilder stringBuilder;

    private KeyBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public static KeyBuilder empty() {
        return new KeyBuilder(new StringBuilder());
    }

    public static KeyBuilder of(String root) {
        return new KeyBuilder(new StringBuilder(root));
    }

    public void extend(String extension) {
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

    public void extend(Collection<String> extensions) {
        extensions.forEach(this::extend);
    }

    public KeyBuilder extended(String extension) {
        extend(extension);
        return this;
    }

    public KeyBuilder extended(Collection<String> extensions) {
        extend(extensions);
        return this;
    }

    public KeyBuilder copy() {
        return new KeyBuilder(new StringBuilder(stringBuilder));
    }

    public KeyBuilder copyExtended(String extension) {
        return copy().extended(extension);
    }

    public KeyBuilder copyExtended(Collection<String> extensions) {
        return copy().extended(extensions);
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    public String extendedToString(String extension) {
        return extended(extension).toString();
    }

    public String extendedToString(Collection<String> extensions) {
        return extended(extensions).toString();
    }

    public String copyExtendedToString(String extension) {
        return copy().extendedToString(extension);
    }

    public String copyExtendedToString(Collection<String> extensions) {
        return copy().extendedToString(extensions);
    }
}
