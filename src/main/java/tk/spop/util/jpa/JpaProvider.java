package tk.spop.util.jpa;

import lombok.*;


@Getter
@RequiredArgsConstructor
public enum JpaProvider {

    HIBERNATE("org.hibernate"), //
    ECLIPSELINK("org.eclipse.persistence"), //
    OPENJPA("org.apache.openjpa"), //
    DATANUCLEUS("org.datanucleus"), //
    OBJECTDB("com.objectdb"), //
    OTHER("");

    private final String pattern;

}